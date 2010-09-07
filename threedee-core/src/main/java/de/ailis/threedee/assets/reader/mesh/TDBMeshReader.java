/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader.mesh;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.Mesh;
import de.ailis.threedee.assets.MeshPolygons;
import de.ailis.threedee.assets.reader.TDBReader;
import de.ailis.threedee.utils.BufferUtils;


/**
 * Mesh reader for TDB files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TDBMeshReader extends TDBReader<Mesh> implements
    MeshReader
{
    /**
     * Constructor
     *
     * @param id
     *            The ID for the read asset.
     */

    public TDBMeshReader(final String id)
    {
        super(id, (byte) 1, (byte) 1);
    }



    /**
     * @see TDBReader#readAsset(Assets)
     */

    @Override
    protected Mesh readAsset(final Assets assets) throws IOException
    {
        final MeshPolygons[] polygons = readMeshPolygons();
        final String[] materials = readMaterials();
        return new Mesh(this.id, polygons, materials);
    }


    /**
     * Read and return the model groups.
     *
     * @return The model groups
     * @throws IOException
     *             When model groups could not be read
     */

    private MeshPolygons[] readMeshPolygons() throws IOException
    {
        final int groupCount = this.reader.readInt();
        final MeshPolygons[] groups = new MeshPolygons[groupCount];
        for (int i = 0; i < groupCount; i++)
        {
            groups[i] = readMeshPolygon();
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

    private MeshPolygons readMeshPolygon() throws IOException
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
        final ShortBuffer indices = BufferUtils
            .convertToNativeEndian(this.reader
                    .readShortBuffer(indexCount));
        return new MeshPolygons(material, mode, indices, vertices, texCoords,
            normals);
    }


    /**
     * Reads and returns the materials.
     *
     * @return The read materials
     * @throws IOException
     *             When read fails
     */

    private String[] readMaterials() throws IOException
    {
        final int materialCount = this.reader.readByte();
        final String[] materials = new String[materialCount];
        for (int i = 0; i < materialCount; i++)
        {
            materials[i] = this.reader.readString(this.reader.readByte());
        }
        return materials;
    }
}
