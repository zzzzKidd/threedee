/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.writer.mesh;

import java.io.IOException;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.Mesh;
import de.ailis.threedee.assets.MeshPolygons;
import de.ailis.threedee.assets.writer.TDBWriter;


/**
 * Mesh writer for TDB files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TDBMeshWriter extends TDBWriter<Mesh> implements
    MeshWriter
{
    /**
     * Constructor
     */

    public TDBMeshWriter()
    {
        super((byte) 1);
    }


    /**
     * @see TDBWriter#writeAsset(Asset)
     */

    @Override
    protected void writeAsset(final Mesh mesh) throws IOException
    {
        final MeshPolygons[] groups = mesh.getPolygons();
        this.writer.writeShort(groups.length);
        for (final MeshPolygons group : groups)
        {
            writeMeshPolygons(group);
        }
        final String[] materials = mesh.getMaterials();
        this.writer.writeByte(materials.length);
        for (final String material: materials)
        {
            this.writer.writeByte(material.getBytes("UTF-8").length);
            this.writer.writeString(material);
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

    private void writeMeshPolygons(final MeshPolygons polygons)
        throws IOException
    {
        this.writer.writeByte(polygons.getMaterial());
        final boolean hasNormals = polygons.hasNormals();
        final boolean hasTexCoords = polygons.hasTexCoords();
        final byte flags = (byte) ((hasNormals ? 1 : 0) | (hasTexCoords ? 2 : 0));
        this.writer.writeByte(flags);
        this.writer.writeByte((byte) polygons.getSize());
        final int vertexCount = polygons.getVertexCount();
        this.writer.writeInt(vertexCount);
        this.writer.writeFloatBuffer(polygons.getVertices());
        if (hasNormals)
            this.writer.writeFloatBuffer(polygons.getNormals());
        if (hasTexCoords)
            this.writer.writeFloatBuffer(polygons.getTexCoords());
        this.writer.writeInt(polygons.getIndexCount());
        this.writer.writeShortBuffer(polygons.getIndices());
    }
}
