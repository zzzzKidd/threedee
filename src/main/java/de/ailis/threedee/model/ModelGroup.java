/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import de.ailis.threedee.math.Bounds;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferIterator;
import de.ailis.threedee.utils.FloatBufferBuilder;
import de.ailis.threedee.utils.IntBufferBuilder;


/**
 * Container for elements using the same material and polygon mode.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class ModelGroup
{
    /** The normal elements */
    private ModelGroup normalElements;

    /** The material index */
    private final int material;

    /** The elements */
    private final Buffer elements;

    /** The vertices used by the elements */
    private final FloatBuffer vertices;

    /** The texture coordinates */
    private final FloatBuffer texCoords;

    /** The normals */
    private final FloatBuffer normals;

    /** The elements mode */
    private final int mode;

    /** The elements type */
    private final int type;

    /** The bounding box */
    private final Bounds bounds;


    /**
     * Constructor.
     *
     * @param material
     *            The material index
     * @param mode
     *            The elements mode. This can be GL_POINTS, GL_LINES,
     *            GL_LINE_STRIP, GL_LINE_LOOP, GL_TRIANGLES, GL_TRIANGLE_STRIP
     *            or GL_TRINAGLE_FAN.
     * @param indices
     *            The indices
     * @param vertices
     *            The vertices
     * @param texCoords
     *            The texture coordinates
     * @param normals
     *            The normals
     */

    public ModelGroup(final int material, final int mode, final Object indices,
            final FloatBuffer vertices, final FloatBuffer texCoords,
            final FloatBuffer normals)
    {
        if (indices instanceof byte[])
        {
            this.type = GL.GL_UNSIGNED_BYTE;
            this.elements = ByteBuffer
                    .allocateDirect(((byte[]) indices).length).put(
                            (byte[]) indices);
        }
        else if (indices instanceof ByteBuffer)
        {
            this.type = GL.GL_UNSIGNED_BYTE;
            this.elements = (ByteBuffer) indices;
        }
        else if (indices instanceof ShortBuffer)
        {
            this.type = GL.GL_UNSIGNED_SHORT;
            this.elements = (ShortBuffer) indices;
        }
        else if (indices instanceof IntBuffer)
        {
            this.type = GL.GL_UNSIGNED_INT;
            this.elements = (IntBuffer) indices;
        }
        else
        {
            throw new IllegalArgumentException("Unknown data type: "
                    + indices.getClass());
        }
        this.elements.position(0);
        this.material = material;
        this.mode = mode;
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.normals = normals;

        // Create bounding box
        this.bounds = new Bounds();
        final BufferIterator iterator = new BufferIterator(this.elements);
        final Vector3f v = new Vector3f();
        while (iterator.hasNext())
        {
            final int index = iterator.next().intValue();
            this.bounds.update(v.set(this.vertices.get(index * 3),
                    this.vertices.get(index * 3 + 1), this.vertices
                            .get(index * 3 + 2)));
        }
    }


    /**
     * Renders the elements to the specified GL context.
     *
     * @param gl
     *            The GL context
     * @param materials
     *            The materials
     */

    public void render(final GL gl, final Material[] materials)
    {
        gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
        this.vertices.rewind();
        gl.glVertexPointer(3, 0, this.vertices);
        if (this.normals != null)
        {
            gl.glEnableClientState(GL.GL_NORMAL_ARRAY);
            this.normals.rewind();
            gl.glNormalPointer(0, this.normals);
        }
        if (this.texCoords != null)
        {
            gl.glEnableClientState(GL.GL_TEXTURE_COORD_ARRAY);
            this.texCoords.rewind();
            gl.glTexCoordPointer(2, 0, this.texCoords);
        }
        final Material material = (this.material == -1 || materials == null)
                ? Material.DEFAULT : materials[this.material];
        material.apply(gl);
        this.elements.rewind();
        gl.glDrawElements(this.mode, this.type, this.elements);
        material.remove(gl);
        if (this.texCoords != null)
            gl.glDisableClientState(GL.GL_TEXTURE_COORD_ARRAY);
        if (this.normals != null) gl.glDisableClientState(GL.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
    }


    /**
     * Renders the normals.
     *
     * @param gl
     *            The GL context
     */

    public void renderBounds(final GL gl)
    {
        BoundsRenderer.render(gl, this.bounds);
    }


    /**
     * Renders the normals.
     *
     * @param gl
     *            The GL context
     * @param scale
     *            The scale factor of the normals
     */

    public void renderNormals(final GL gl, final float scale)
    {
        // Do nothing if no normals are present
        if (this.normals == null) return;

        if (this.normalElements == null)
        {
            int index = 0;
            final FloatBufferBuilder vertexBuilder = new FloatBufferBuilder();
            final IntBufferBuilder indexBuilder = new IntBufferBuilder();
            while (this.vertices.hasRemaining())
            {
                final Vector3f a = new Vector3f(this.vertices.get(),
                        this.vertices.get(), this.vertices.get());
                final Vector3f b = new Vector3f(this.normals.get(),
                        this.normals.get(), this.normals.get());
                b.multiply(scale);
                b.add(a);
                vertexBuilder.add(a.getX()).add(a.getY()).add(a.getZ());
                indexBuilder.add(index++);
                vertexBuilder.add(b.getX()).add(b.getY()).add(b.getZ());
                indexBuilder.add(index++);
            }
            this.normalElements = new ModelGroup(-1, GL.GL_LINES, indexBuilder
                    .build(), vertexBuilder.build(), null, null);
            this.vertices.rewind();
            this.normals.rewind();
        }

        this.normalElements.render(gl, null);
    }


    /**
     * Returns the material index.
     *
     * @return The material index
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
     * Returns the model group mode (GL_TRIANGLES, GL_POINTS, ...).
     *
     * @return The model group mode
     */

    public int getMode()
    {
        return this.mode;
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
     * Returns the number of indices.
     *
     * @return The number of indices
     */

    public int getIndexCount()
    {
        return this.elements.rewind().remaining();
    }


    /**
     * Returns the indices.
     *
     * @return The indices
     */

    public Buffer getIndices()
    {
        this.elements.rewind();
        return this.elements;
    }


    /**
     * Returns the bounding box.
     *
     * @return The bounding box
     */

    public Bounds getBounds()
    {
        return this.bounds;
    }
}
