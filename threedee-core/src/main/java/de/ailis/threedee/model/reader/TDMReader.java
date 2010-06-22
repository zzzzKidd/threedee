/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.StreamReader;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.reader.ModelReader;
import de.ailis.threedee.scene.Color;
import de.ailis.threedee.scene.Model;
import de.ailis.threedee.scene.model.Material;
import de.ailis.threedee.scene.model.Mesh;
import de.ailis.threedee.scene.model.MeshPolygons;
import de.ailis.threedee.utils.BufferUtils;


/**
 * This loader loads models from a ThreeDee models.
 *
 * Fileformat of a TDM file:
 *
 * <pre>
 * char[3] header; // The file header. Must be "TDM"
 * byte version; // The file version. Must be 1
 * byte flags; // File flags:
 *             // Bit 0: File endianess; 0=Little endian, 1=Big endian
 * int groupCount; // The number of model groups in the file
 * group[] groups; // The model groups
 * short materialCount; // The number of materials in the file
 * material[] materials; // The materials
 * </pre>
 *
 * Format of a group:
 *
 * <pre>
 * byte materialNameLen; // The raw byte-length of the material name
 * char[] materialName; // The name of the material to use
 * byte flags; // Group flags:
 * // Bit 0: Has normals; 0=No, 1=Yes
 * // Bit 1: Has texture coordinates; 0=No, 1=Yes
 * byte mode; // Element mode (GL_POINTS, GL_TRIANGLE, ...)
 * int vertexCount; // Number of vertices
 * float[] vertexData; // The vertex data (Size: vertexCount * 3)
 * float[] normalData; // Optional normal data (Size: vertexCount * 3)
 * float[] texCoordData; // Optional texture coord data (Size: vertexCount * 3)
 * int indexCount; // Number of indices
 * byte[]|short[]|int[] indices; // The indices. Type depends on vertexCount.
 * </pre>
 *
 * Format of a material:
 *
 * <pre>
 * color ambient; // The ambient color
 *
 * color diffuse; // The diffuse color
 *
 * color specular; // The specular color
 *
 * color emission; // The emission color
 *
 * float shininess; // The shininess
 *
 * byte textureNameLen; // The length of the texture name
 *
 * char[] textureName; // The texture name
 * </pre>
 *
 * Colors in the format are simply four bytes (RGBA).
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class TDMReader extends ModelReader
{
    /** The reader to use for reading the model */
    private StreamReader reader;

    /** The minimum supported file format version */
    public static final byte MIN_VERSION = 1;

    /** The maximum supported file format version */
    public static final byte MAX_VERSION = 1;

    /** The byte order of the model file */
    private ByteOrder byteOrder;


    /**
     * Constructs a new ThreeDee model reader.
     *
     * @param resourceProvider
     *            The resource provider to use for reading models, material
     *            libraries and texture files
     */

    public TDMReader(final ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }


    /**
     * @see de.ailis.threedee.reader.ModelReader#read(java.io.InputStream)
     */

    @Override
    public Model read(final InputStream stream) throws IOException
    {
        this.reader = new StreamReader(stream);
        return read();
    }


    /**
     * Reads a ThreeDee model.
     *
     * @return The loaded model
     * @throws IOException
     *             When model could not be loaded
     */

    private Model read() throws IOException
    {
        readHeader();
        readVersion();
        readFileFlags();
        final MeshPolygons[] polygons = readGroups();
        final Material[] materials = readMaterials();
        final String[] materialNames = new String[materials.length];
        final Mesh mesh = new Mesh(polygons, materialNames);
        final Model model = new Model(mesh);
        for (int i = materials.length - 1; i >= 0; i--)
        {
            final Material material = materials[i];
            final String name = materials[i].getId();
            materialNames[i] = name;
            model.bindMaterial(name, material);
        }
        return model;
    }


    /**
     * Read and return the model groups.
     *
     * @return The model groups
     * @throws IOException
     *             When model groups could not be read
     */

    private MeshPolygons[] readGroups() throws IOException
    {
        final int groupCount = this.reader.readInt();
        final MeshPolygons[] groups = new MeshPolygons[groupCount];
        for (int i = 0; i < groupCount; i++)
        {
            groups[i] = readGroup();
        }
        return groups;
    }


    /**
     * Reads a single group and return it.
     *
     * @return The model group
     * @throws IOException
     *             When model group could not be read
     */

    private MeshPolygons readGroup() throws IOException
    {
        final int material = this.reader.readShort();
        final byte flags = (byte) this.reader.readByte();
        final boolean hasNormals = (flags & 1) != 0;
        final boolean hasTexCoords = (flags & 2) != 0;
        final byte mode = (byte) this.reader.readByte();
        final int vertexCount = this.reader.readInt();
        final FloatBuffer vertices = BufferUtils
                .convertToNativeEndian(this.reader
                        .readFloatBuffer(vertexCount * 3));
        FloatBuffer normals = null, texCoords = null;
        if (hasNormals)
            normals = BufferUtils.convertToNativeEndian(this.reader
                    .readFloatBuffer(vertexCount * 3));
        if (hasTexCoords)
            texCoords = BufferUtils.convertToNativeEndian(this.reader
                    .readFloatBuffer(vertexCount * 2));

        final int indexCount = this.reader.readInt();
        final ShortBuffer indices = BufferUtils.convertToNativeEndian(this.reader
                    .readShortBuffer(indexCount));
        return new MeshPolygons(material, mode, indices, vertices, texCoords, normals);
    }


    /**
     * Reads and returns the materials.
     *
     * @return The read materials
     * @throws IOException
     *             When read fails
     */

    private Material[] readMaterials() throws IOException
    {
        final int materialCount = this.reader.readShort();
        final Material[] materials = new Material[materialCount];
        for (int i = 0; i < materialCount; i++)
        {
            materials[i] = readMaterial();
        }
        return materials;
    }


    /**
     * Reads and returns a material.
     *
     * @return The material
     * @throws IOException
     *             When read fails
     */

    private Material readMaterial() throws IOException
    {
        final Color ambientColor = readColor();
        final Color diffuseColor = readColor();
        final Color specularColor = readColor();
        final Color emissionColor = readColor();
        final float shininess = this.reader.readFloat();
        final int len = this.reader.readByte();
        final String texture = len == 0 ? null : this.reader.readString(len);
        return new Material(null, ambientColor, diffuseColor, specularColor,
                emissionColor, shininess, texture);
    }


    /**
     * Reads and returns a color.
     *
     * @return The read color
     * @throws IOException
     *             When read fails
     */

    private Color readColor() throws IOException
    {
        final int red = this.reader.readByte();
        final int green = this.reader.readByte();
        final int blue = this.reader.readByte();
        final int alpha = this.reader.readByte();
        return new Color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }


    /**
     * Reads and checks the TDM header. Throws an exception if file is not
     * a TDM file.
     *
     * @throws IOException
     *             When header could not be read or file is not a TDM
     *             file
     */

    private void readHeader() throws IOException
    {
        final String header = this.reader.readString(3);
        if (!header.equals("TDM"))
            throw new ReaderException("File is not a ThreeDee object");
    }


    /**
     * Reads the flags of the model file.
     *
     * Bit 0: Clear = Little-endian, Set = Big-endian
     *
     * @throws IOException
     *             When flags could not be read
     */

    private void readFileFlags() throws IOException
    {
        final byte flags = (byte) this.reader.readByte();
        this.byteOrder = ((flags & 1) == 0) ? ByteOrder.LITTLE_ENDIAN
                : ByteOrder.BIG_ENDIAN;
        this.reader.setByteOrder(this.byteOrder);
    }


    /**
     * Reads and checks the version of the TDM file.
     *
     * @throws IOException
     *             When version could not be read or version is
     *             invalid for this reader.
     */

    private void readVersion() throws IOException
    {
        final byte version = (byte) this.reader.readByte();
        if (version < MIN_VERSION)
            throw new ReaderException("File format version is too old");
        if (version > MAX_VERSION)
            throw new ReaderException("File format version is too new");
    }
}
