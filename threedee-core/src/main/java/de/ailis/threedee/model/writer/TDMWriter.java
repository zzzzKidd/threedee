/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import de.ailis.threedee.entities.Color;
import de.ailis.threedee.io.StreamWriter;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.model.Material;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.ModelGroup;


/**
 * This writer writes a model in TDM format.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class TDMWriter extends ModelWriter
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

    public TDMWriter(final ResourceProvider resourceProvider)
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
        writeGroups();
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
        final String texture = material.getTextureName();
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

    private void writeColor(final Color color) throws IOException
    {
        this.writer.writeByte((int) (color.getRed() * 255));
        this.writer.writeByte((int) (color.getGreen() * 255));
        this.writer.writeByte((int) (color.getBlue() * 255));
        this.writer.writeByte((int) (color.getAlpha() * 255));
    }


    /**
     * Writes the model groups.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeGroups() throws IOException
    {
        final ModelGroup[] groups = this.model.getGroups();
        this.writer.writeInt(groups.length);
        for (final ModelGroup group : groups)
        {
            writeGroup(group);
        }
    }


    /**
     * Writes the specified model group.
     *
     * @param group
     *            The model group to write
     * @throws IOException
     *             When write fails
     */

    private void writeGroup(final ModelGroup group) throws IOException
    {
        this.writer.writeShort(group.getMaterial());
        final boolean hasNormals = group.hasNormals();
        final boolean hasTexCoords = group.hasTexCoords();
        final byte flags = (byte) ((hasNormals ? 1 : 0) | (hasTexCoords ? 2 : 0));
        this.writer.writeByte(flags);
        this.writer.writeByte((byte) group.getMode());
        final int vertexCount = group.getVertexCount();
        this.writer.writeInt(vertexCount);
        this.writer.writeFloatBuffer(group.getVertices());
        if (hasNormals) this.writer.writeFloatBuffer(group.getNormals());
        if (hasTexCoords) this.writer.writeFloatBuffer(group.getTexCoords());

        this.writer.writeInt(group.getIndexCount());
        final Buffer indices = group.getIndices();
        if (vertexCount <= 256)
            this.writer.writeByteBuffer((ByteBuffer) indices);
        else if (vertexCount <= 65536)
            this.writer.writeShortBuffer((ShortBuffer) indices);
        else
            this.writer.writeIntBuffer((IntBuffer) indices);
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
