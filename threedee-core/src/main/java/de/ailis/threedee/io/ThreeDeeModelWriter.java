/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.scene.Model;
import de.ailis.threedee.scene.model.Material;
import de.ailis.threedee.scene.model.MeshPolygons;


/**
 * This writer writes a model in TDM format.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class ThreeDeeModelWriter extends ModelWriter
{
    /** The writer to write to */
    private StreamWriter writer;

    /** The model to write */
    private Model model;

    /** The file format version */
    private static final byte VERSION = 1;

    /** The byte order */
    private ByteOrder byteOrder = ByteOrder.nativeOrder();


    /**
     * Creates a new TDM model writer.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public ThreeDeeModelWriter(final ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }



    /**
     * Sets the byte order.
     *
     * @param byteOrder
     *            The byte order to set
     */

    public void setByteOrder(final ByteOrder byteOrder)
    {
        this.byteOrder = byteOrder;
    }


    /**
     * Returns the byte order.
     *
     * @return The byte order
     */

    public ByteOrder getByteOrder()
    {
        return this.byteOrder;
    }


    /**
     * Writes a ThreeDee model to the specified stream.
     *
     * @param model
     *            The model to write
     * @param stream
     *            The stream to write to
     * @throws IOException
     *             When write fails
     */

    @Override
    public void write(final Model model, final OutputStream stream)
            throws IOException
    {
        this.writer = new StreamWriter(stream);
        this.writer.setByteOrder(this.byteOrder);
        this.model = model;
        write();
    }


    /**
     * Writes the model.
     *
     * @throws IOException
     *             When write fails
     */

    private void write() throws IOException
    {
        writeHeader();
        writeVersion();
        writeFileFlags();
        writeMesh();
        writeMaterials();
    }


    /**
     * Writes the materials.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeMaterials() throws IOException
    {
        final Material[] materials = this.model.getMaterials();
        this.writer.writeShort(materials.length);
        for (final Material material: materials)
            writeMaterial(material);
    }


    /**
     * Writes a material.
     *
     * @param material The material to write
     * @throws IOException
     *             When write fails
     */

    private void writeMaterial(final Material material) throws IOException
    {
        writeColor(material.getAmbientColor());
        writeColor(material.getDiffuseColor());
        writeColor(material.getSpecularColor());
        writeColor(material.getEmissionColor());
        this.writer.writeFloat(material.getShininess());
        final String texture = material.getDiffuseTexture();
        if (texture == null)
            this.writer.writeByte(0);
        else
        {
            this.writer.writeByte(texture.getBytes("UTF-8").length);
            this.writer.writeString(texture);
        }
    }


    /**
     * Writes a color.
     *
     * @param color The color to write
     * @throws IOException
     *             When write fails
     */

    private void writeColor(final Color4f color) throws IOException
    {
        this.writer.writeByte((int) (color.getRed() * 255));
        this.writer.writeByte((int) (color.getGreen() * 255));
        this.writer.writeByte((int) (color.getBlue() * 255));
        this.writer.writeByte((int) (color.getAlpha() * 255));
    }


    /**
     * Writes the mesh.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeMesh() throws IOException
    {
        final MeshPolygons[] groups = this.model.getMesh().getPolygons();
        this.writer.writeInt(groups.length);
        for (final MeshPolygons group : groups)
        {
            writeMeshPolygons(group);
        }
    }


    /**
     * Writes mesh polygons.
     *
     * @param polygons
     *            The mesh polygons to write
     * @throws IOException
     *             When write fails
     */

    private void writeMeshPolygons(final MeshPolygons polygons) throws IOException
    {
        this.writer.writeShort(polygons.getMaterial());
        final boolean hasNormals = polygons.hasNormals();
        final boolean hasTexCoords = polygons.hasTexCoords();
        final byte flags = (byte) ((hasNormals ? 1 : 0) | (hasTexCoords ? 2 : 0));
        this.writer.writeByte(flags);
        this.writer.writeByte((byte) polygons.getSize());
        final int vertexCount = polygons.getVertexCount();
        this.writer.writeInt(vertexCount);
        this.writer.writeFloatBuffer(polygons.getVertices());
        if (hasNormals) this.writer.writeFloatBuffer(polygons.getNormals());
        if (hasTexCoords) this.writer.writeFloatBuffer(polygons.getTexCoords());
        this.writer.writeInt(polygons.getIndexCount());
        this.writer.writeShortBuffer(polygons.getIndices());
    }


    /**
     * Writes TDM header
     *
     * @throws IOException
     *             When write fails
     */

    private void writeHeader() throws IOException
    {
        this.writer.writeString("TDM");
    }


    /**
     * Writes file flags.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeFileFlags() throws IOException
    {
        int flags = 0;
        flags |= this.writer.getByteOrder() == ByteOrder.BIG_ENDIAN ? 1 : 0;
        this.writer.writeByte((byte) flags);
    }


    /**
     * Writes TDM version.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeVersion() throws IOException
    {
        this.writer.writeByte(VERSION);
    }
}
