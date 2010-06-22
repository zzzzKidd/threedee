/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.ailis.threedee.math.Bounds;
import de.ailis.threedee.rendering.BoundsRenderer;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.utils.BufferIterator;
import de.ailis.threedee.utils.BufferUtils;
import de.ailis.threedee.utils.FloatBufferBuilder;
import de.ailis.threedee.utils.IntBufferBuilder;


/**
 * A model
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Model
{
    /** The materials used in the model */
    private final Material[] materials;

    /** The model elements */
    private final ModelGroup[] elements;

    /** The bounds */
    private final Bounds bounds;


    /**
     * Constructor
     *
     * @param elements
     *            The model elements
     * @param materials
     *            The materials used in the model
     */

    public Model(final ModelGroup[] elements, final Material[] materials)
    {
        this.elements = elements;
        this.materials = materials;

        // Create bounds
        this.bounds = new Bounds();
        for (final ModelGroup group : this.elements)
            this.bounds.update(group.getBounds());
    }


    /**
     * Returns the model groups.
     *
     * @return The model groups
     */

    public ModelGroup[] getGroups()
    {
        return this.elements;
    }


    /**
     * Returns the materials.
     *
     * @return The materials
     */

    public Material[] getMaterials()
    {
        return this.materials;
    }


    /**
     * Renders the model to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void render(final GL gl)
    {
        for (final ModelGroup elements : this.elements)
            elements.render(gl, this.materials);
    }


    /**
     * Renders the normals of the model.
     *
     * @param gl
     *            The GL context
     */

    public void renderNormals(final GL gl)
    {
        final boolean oldLighting = gl.glIsEnabled(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_LIGHTING);

        final float scale = this.bounds.getSize() / 50;

        for (final ModelGroup elements : this.elements)
            elements.renderNormals(gl, scale);

        if (oldLighting) gl.glEnable(GL.GL_LIGHTING);
    }


    /**
     * Renders the bounds of the model.
     *
     * @param gl
     *            The GL context
     * @param renderGroupBounds
     *            If group bounds should be rendered, too
     */

    public void renderBounds(final GL gl, final boolean renderGroupBounds)
    {
        final boolean oldLighting = gl.glIsEnabled(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_LIGHTING);

        BoundsRenderer.render(gl, this.bounds);
        if (renderGroupBounds) for (final ModelGroup elements : this.elements)
            elements.renderBounds(gl);

        if (oldLighting) gl.glEnable(GL.GL_LIGHTING);
    }


    /**
     * Optimizes the model by merging model groups with same characteristics.
     * This can be very time consuming with big models so you should only use
     * this once during development to save optimized TDM files. It makes no
     * sense to optimize models every time you read them.
     *
     * @return The optimized model
     */

    public Model optimize()
    {
        // The list with merged model groups
        final List<ModelGroup> newGroups = new ArrayList<ModelGroup>();

        // Get number of groups to process
        final int max = this.elements.length;

        // Create a copy of the old Groups to play with
        final ModelGroup[] oldGroups = Arrays.copyOf(this.elements, max);

        // Main loop iterating all groups
        for (int i = 0; i < max; i++)
        {
            // Get next model group to process. Continue if this one is
            // already marked as processed
            ModelGroup a = oldGroups[i];
            if (a == null) continue;

            // Inner loop iterating over all remaining groups to merge with
            for (int j = i + 1; j < max; j++)
            {
                // Get next model group to merge with. Continue if this one
                // is already marked as processed.
                final ModelGroup b = oldGroups[j];
                if (b == null) continue;

                // Check criteries if model groups can be merged
                if (a.getMode() != b.getMode()) continue;
                if (a.hasNormals() != b.hasNormals()) continue;
                if (a.hasTexCoords() != b.hasTexCoords()) continue;
                if (a.getMaterial() != b.getMaterial()) continue;

                // Merge the model groups
                FloatBuffer normals;
                if (a.hasNormals())
                {
                    normals = BufferUtils.createDirectFloatBuffer(a
                            .getVertexCount()
                            * 3 + b.getVertexCount() * 3);
                    normals.put(a.getNormals());
                    normals.put(b.getNormals());
                    normals.rewind();
                }
                else
                    normals = null;
                FloatBuffer texCoords;
                if (a.hasTexCoords())
                {
                    texCoords = BufferUtils.createDirectFloatBuffer(a
                            .getVertexCount()
                            * 2 + b.getVertexCount() * 2);
                    texCoords.put(a.getTexCoords());
                    texCoords.put(b.getTexCoords());
                    texCoords.rewind();
                }
                else
                    texCoords = null;
                final FloatBuffer vertices = BufferUtils
                        .createDirectFloatBuffer(a.getVertexCount() * 3
                                + b.getVertexCount() * 3);
                vertices.put(a.getVertices());
                vertices.put(b.getVertices());
                vertices.rewind();

                final IntBufferBuilder builder = new IntBufferBuilder();
                builder.add(a.getIndices());
                builder.setOffset(a.getVertexCount());
                builder.add(b.getIndices());
                final Buffer indices = builder.build();

                a = new ModelGroup(a.getMaterial(), a.getMode(), indices,
                        vertices, texCoords, normals);

                // Mark model group as merged
                oldGroups[j] = null;
            }

            // Optimize vertices
            int oldVertexCount = 0;
            int newVertexCount = 0;
            final FloatBuffer vertices = a.getVertices();
            final FloatBuffer normals = a.hasNormals() ? a.getNormals() : null;
            final FloatBuffer texCoords = a.hasTexCoords() ? a.getTexCoords()
                    : null;
            final BufferIterator iterator = new BufferIterator(a.getIndices());
            final List<Float> newVertices = new ArrayList<Float>();
            final List<Float> newNormals = a.hasNormals()
                    ? new ArrayList<Float>() : null;
            final List<Float> newTexCoords = a.hasTexCoords()
                    ? new ArrayList<Float>() : null;
            final IntBufferBuilder newIndices = new IntBufferBuilder();
            while (iterator.hasNext())
            {
                final int index = iterator.next().intValue();
                final float vertexX = vertices.get(index * 3);
                final float vertexY = vertices.get(index * 3 + 1);
                final float vertexZ = vertices.get(index * 3 + 2);

                // Check if there is already a matching vertex
                int newIndex;
                for (newIndex = newVertices.size() / 3 - 1; newIndex >= 0; newIndex--)
                {
                    if (newVertices.get(newIndex * 3).floatValue() != vertexX)
                        continue;
                    if (newVertices.get(newIndex * 3 + 1).floatValue() != vertexY)
                        continue;
                    if (newVertices.get(newIndex * 3 + 2).floatValue() != vertexZ)
                        continue;

                    if (newNormals != null)
                    {
                        if (newNormals.get(newIndex * 3).floatValue() != normals
                                .get(index * 3)) continue;
                        if (newNormals.get(newIndex * 3 + 1).floatValue() != normals
                                .get(index * 3 + 1)) continue;
                        if (newNormals.get(newIndex * 3 + 2).floatValue() != normals
                                .get(index * 3 + 2)) continue;
                    }

                    if (newTexCoords != null)
                    {
                        if (newTexCoords.get(newIndex * 2).floatValue() != texCoords
                                .get(index * 2)) continue;
                        if (newTexCoords.get(newIndex * 2 + 1).floatValue() != texCoords
                                .get(index * 2 + 1)) continue;
                    }

                    break;
                }

                if (newIndex < 0)
                {
                    newIndex = newVertices.size() / 3;
                    newVertices.add(vertices.get(index * 3));
                    newVertices.add(vertices.get(index * 3 + 1));
                    newVertices.add(vertices.get(index * 3 + 2));
                    if (newNormals != null)
                    {
                        newNormals.add(normals.get(index * 3));
                        newNormals.add(normals.get(index * 3 + 1));
                        newNormals.add(normals.get(index * 3 + 2));
                    }
                    if (newTexCoords != null)
                    {
                        newTexCoords.add(texCoords.get(index * 2));
                        newTexCoords.add(texCoords.get(index * 2 + 1));
                    }
                }
                newIndices.add(newIndex);
            }

            final FloatBufferBuilder vertexBuilder = new FloatBufferBuilder();
            for (final Float value : newVertices)
                vertexBuilder.add(value);
            final FloatBuffer newestVertices = vertexBuilder.build();

            final FloatBuffer newestNormals;
            if (newNormals != null)
            {
                final FloatBufferBuilder normalBuilder = new FloatBufferBuilder();
                for (final Float value : newNormals)
                    normalBuilder.add(value);
                newestNormals = normalBuilder.build();
            }
            else
                newestNormals = null;

            final FloatBuffer newestTexCoords;
            if (newTexCoords != null)
            {
                final FloatBufferBuilder texCoordBuilder = new FloatBufferBuilder();
                for (final Float value : newTexCoords)
                    texCoordBuilder.add(value);
                newestTexCoords = texCoordBuilder.build();
            }
            else
                newestTexCoords = null;

            oldVertexCount += a.getVertexCount();
            a = new ModelGroup(a.getMaterial(), a.getMode(),
                    newIndices.build(), newestVertices, newestTexCoords,
                    newestNormals);
            newVertexCount += a.getVertexCount();

            // Add the group (Merged or not) to the new group list
            newGroups.add(a);
        }

        // Returns the optimized model
        return new Model(newGroups.toArray(new ModelGroup[0]), this.materials);
    }


    /**
     * Returns the bounds of the model.
     *
     * @return The model bounds
     */

    public Bounds getBounds()
    {
        return this.bounds;
    }
}