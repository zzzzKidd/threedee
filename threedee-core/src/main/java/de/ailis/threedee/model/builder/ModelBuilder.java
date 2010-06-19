/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.builder;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.exceptions.ModelBuilderException;
import de.ailis.threedee.math.Vector2f;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.model.Material;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.ModelGroup;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.FloatBufferBuilder;
import de.ailis.threedee.utils.IntBufferBuilder;


/**
 * This builder builds model objects.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class ModelBuilder
{
    /** The vertex geometry vectors */
    private final List<Vector3f> vertices = new ArrayList<Vector3f>();

    /** Returns the number of vertexes in the list */
    private int vertexCount = 0;

    /** The normals vectors */
    private final List<Vector3f> normals = new ArrayList<Vector3f>();

    /** Returns the number of normals in the list */
    private int normalCount = 0;

    /** The texture coordinates */
    private final List<Vector2f> texCoords = new ArrayList<Vector2f>();

    /** Returns the number of texture coordinates in the list */
    private int texCoordCount = 0;

    /** The materials */
    private final List<Material> materials = new ArrayList<Material>();

    /** Current material */
    private int material = -1;

    /** Current polygon mode */
    private int mode = GL.GL_TRIANGLES;

    /** The indices of the texture coordinates used in next element */
    private int[] nextTexCoords;

    /** The indices of the normal vectors used in next element */
    private int[] nextNormals;

    /** The next index */
    private int nextIndex = 0;

    /** If next element uses texture coordinates */
    private boolean useTexCoords = false;

    /** If next element uses normal vectors */
    private boolean useNormals = false;

    /** The builder to build vertex float buffers */
    private final FloatBufferBuilder vertexBuilder = new FloatBufferBuilder();

    /** The builder to build texture coordinate float buffers */
    private final FloatBufferBuilder texCoordBuilder = new FloatBufferBuilder();

    /** The builder to build normal vector float buffers */
    private final FloatBufferBuilder normalBuilder = new FloatBufferBuilder();

    /** The builder to build index buffers */
    private final IntBufferBuilder indexBuilder = new IntBufferBuilder();

    /** The list of built elements */
    private final ArrayList<ModelGroup> elements = new ArrayList<ModelGroup>();


    /**
     * Adds a vertex.
     *
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @param z
     *            The Z coordinate
     * @return The index of the added vertex
     */

    public int addVertex(final float x, final float y, final float z)
    {
        return addVertex(new Vector3f(x, y, z));
    }


    /**
     * Adds a vertex.
     *
     * @param vertex
     *            The vertex to add
     * @return The index of the added vertex
     */

    public int addVertex(final Vector3f vertex)
    {
        this.vertices.add(vertex);
        return this.vertexCount++;
    }


    /**
     * Returns the vertex with the specified index.
     *
     * @param index
     *            The index
     * @return The vertex
     */

    public Vector3f getVertex(final int index)
    {
        return this.vertices.get(index);
    }


    /**
     * Adds a normal vector.
     *
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @param z
     *            The Z coordinate
     * @return The index of the added normal
     */

    public int addNormal(final float x, final float y, final float z)
    {
        return addNormal(new Vector3f(x, y, z));
    }


    /**
     * Adds a normal vector.
     *
     * @param normal
     *            The normal vector to add
     * @return The index of the added normal
     */

    public int addNormal(final Vector3f normal)
    {
        this.normals.add(normal);
        return this.normalCount++;
    }


    /**
     * Adds a texture coordinate.
     *
     * @param u
     *            The U component
     * @param v
     *            The V component
     * @return The index of the added texture coordinate
     */

    public int addTexCoord(final float u, final float v)
    {
        return addTexCoord(new Vector2f(u, v));
    }


    /**
     * Adds a texture coordinate.
     *
     * @param texCoord
     *            The texture coordinate to add
     * @return The index of the added texture coordinate
     */

    public int addTexCoord(final Vector2f texCoord)
    {
        this.texCoords.add(texCoord);
        return this.texCoordCount++;
    }


    /**
     * Use the specified material for the next element.
     *
     * @param material
     *            The material to use
     */

    public void useMaterial(final Material material)
    {
        int index = this.materials.indexOf(material);
        if (index == -1)
        {
            index = this.materials.size();
            this.materials.add(material);
        }

        // Do nothing if material has not changed
        if (index == this.material) return;

        // Finish current elements when material has changed
        finishElements();

        // Store the new material to use
        this.material = index;
    }


    /**
     * Use the specified texture coordinates for the next element.
     *
     * @param texCoords
     *            The indices of the texture coordinates to use
     */

    public void useTexCoords(final int... texCoords)
    {
        this.nextTexCoords = texCoords;
    }


    /**
     * Use the specified normal vectors for the next element.
     *
     * @param normals
     *            The indices of the normal vectors to use
     */

    public void useNormals(final int... normals)
    {
        this.nextNormals = normals;
    }


    /**
     * Finishes the current element.
     */

    private void finishElements()
    {
        // Do nothing if no coords present yet
        if (this.vertexBuilder.getSize() == 0) return;

        // Create elements and add them to list of elements
        final FloatBuffer coordBuffer = this.vertexBuilder.build();
        final FloatBuffer texCoordBuffer = this.useTexCoords
                ? this.texCoordBuilder.build() : null;
        final FloatBuffer normalBuffer = this.useNormals ? this.normalBuilder
                .build() : null;
        final ModelGroup elements = new ModelGroup(this.material, this.mode,
            this.indexBuilder.build(),
                coordBuffer, texCoordBuffer, normalBuffer);
        this.elements.add(elements);

        // Reset builder for next elements
        this.vertexBuilder.reset();
        this.normalBuilder.reset();
        this.texCoordBuilder.reset();
        this.indexBuilder.reset();
        this.nextIndex = 0;
        this.mode = GL.GL_TRIANGLES;
    }


    /**
     * Adds a new element.
     *
     * @param mode
     *            The polygon mode
     * @param vertices
     *            The indices of the vertices to use
     */

    public void addElement(final int mode, final int... vertices)
    {
        final int size = vertices.length;

        // Check which arrays are used
        final boolean useTexCoords = this.nextTexCoords != null;
        boolean useNormals = this.nextNormals != null;

        // Auto-generate normals if needed
        if (!useNormals && size >= 3)
        {
            final int[] normals = new int[size];
            final Vector3f p1 = this.vertices.get(vertices[0]);
            final Vector3f p2 = this.vertices.get(vertices[1]);
            final Vector3f p3 = this.vertices.get(vertices[2]);
            final Vector3f v1 = p2.copy().sub(p1);
            final Vector3f v2 = p3.copy().sub(p1);
            final Vector3f normal = v1.cross(v2).normalize();
            for (int i = 0; i < size; i++)
                normals[i] = addNormal(normal);
            useNormals(normals);
            useNormals = true;
        }

        // Finish current elements if mode has changed
        if (mode != this.mode || useTexCoords != this.useTexCoords
                || useNormals != this.useNormals) finishElements();

        // Remember new elements configuration
        this.mode = mode;
        this.useTexCoords = useTexCoords;
        this.useNormals = useNormals;

        // Validate array sizes
        if (useTexCoords && size != this.nextTexCoords.length)
            throw new ModelBuilderException("Need " + size
                    + " texture coordinates");
        if (useNormals && size != this.nextNormals.length)
            throw new ModelBuilderException("Need " + size + " normals");

        // Generate the vertices
        for (int i = 0; i < size; i++)
        {
            // Gather the indices together
            final Vector3f coord = this.vertices.get(vertices[i]);
            this.vertexBuilder.add(coord.getX(), coord.getY(), coord.getZ());

            if (useTexCoords)
            {
                final Vector2f texCoord = this.texCoords
                        .get(this.nextTexCoords[i]);
                this.texCoordBuilder.add(texCoord.getX(), texCoord.getY());
            }

            if (useNormals)
            {
                final Vector3f normal = this.normals.get(this.nextNormals[i]);
                this.normalBuilder.add(normal.getX(), normal.getY(), normal
                        .getZ());
            }

            this.indexBuilder.add(this.nextIndex);
            this.nextIndex++;
        }

        // Reset state
        this.nextTexCoords = null;
        this.nextNormals = null;
    }


    /**
     * Builds the model.
     *
     * @return The model
     */

    public Model build()
    {
        finishElements();
        final Model model = new Model(this.elements.toArray(new ModelGroup[0]),
                this.materials.toArray(new Material[0]));
        return model;
    }
}
