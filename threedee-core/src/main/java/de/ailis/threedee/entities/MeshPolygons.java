/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * A group of mesh polygons.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class MeshPolygons
{
     /** The material */
    private final int material;

    /** The elements */
    private final ShortBuffer indices;

    /** The vertices used by the elements */
    private final FloatBuffer vertices;

    /** The texture coordinates */
    private final FloatBuffer texCoords;

    /** The normals */
    private final FloatBuffer normals;

    /** The size of the polygons (1-3) */
    private final int size;


    /**
     * Constructor.
     *
     * @param material
     *            The material id
     * @param size
     *            The polygon size (1-3)
     * @param indices
     *            The indices
     * @param vertices
     *            The vertices
     * @param texCoords
     *            The texture coordinates
     * @param normals
     *            The normals
     */

    public MeshPolygons(final int material, final int size, final ShortBuffer indices,
            final FloatBuffer vertices, final FloatBuffer texCoords,
            final FloatBuffer normals)
    {
        this.indices = indices;
        this.material = material;
        this.size = size;
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.normals = normals;
    }


    /**
     * Returns the material id.
     *
     * @return The material id
     */

    public int getMaterial()
    {
        return this.material;
    }


    /**
     * Checks if model group has normals.
     *
     * @return True if it has normals, false if not
     */

    public boolean hasNormals()
    {
        return this.normals != null;
    }


    /**
     * Checks if model group has texture coordinates.
     *
     * @return True if it has texture coordinates, false if not
     */

    public boolean hasTexCoords()
    {
        return this.texCoords != null;
    }



    /**
     * Returns the vertices.
     *
     * @return The vertices
     */

    public FloatBuffer getVertices()
    {
        this.vertices.rewind();
        return this.vertices;
    }


    /**
     * Returns the normals.
     *
     * @return The normals
     */

    public FloatBuffer getNormals()
    {
        if (this.normals != null) this.normals.rewind();
        return this.normals;
    }


    /**
     * Returns the texture coordinates.
     *
     * @return The texture coordinates
     */

    public FloatBuffer getTexCoords()
    {
        if (this.texCoords != null) this.texCoords.rewind();
        return this.texCoords;
    }


    /**
     * Returns the indices.
     *
     * @return The indices
     */

    public ShortBuffer getIndices()
    {
        this.indices.rewind();
        return this.indices;
    }


    /**
     * Returns the polygon size. This can be 1 (for points), 2 (for lines) or
     * 3 (for triangels). Nothing else is allowed.
     *
     * @return The polygon size (1-3)
     */

    public int getSize()
    {
        return this.size;
    }


    /**
     * Returns the number of indices.
     *
     * @return The number of indices
     */

    public int getIndexCount()
    {
        return this.indices.rewind().remaining();
    }


    /**
     * Returns the number of vertices in this model group.
     *
     * @return The number of vertices
     */

    public int getVertexCount()
    {
        return this.vertices.rewind().remaining() / 3;
    }
}
