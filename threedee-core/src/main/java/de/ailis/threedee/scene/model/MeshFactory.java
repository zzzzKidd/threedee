/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.model;

import de.ailis.threedee.assets.Mesh;
import de.ailis.threedee.builder.MeshBuilder;


/**
 * This class provides static methods for creating some standard meshes like
 * cubes and spheres.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public final class MeshFactory
{
    /**
     * Private constructor to prevent instantiation.
     */

    private MeshFactory()
    {
        // Empty
    }


    /**
     * Creates and returns a box mesh.
     *
     * @param id
     *            The id
     * @param xRadius
     *            The X radius
     * @param yRadius
     *            The Y radius
     * @param zRadius
     *            The Z radius
     * @return The box mesh
     */

    public static Mesh createBox(final String id, final float xRadius,
        final float yRadius,
            final float zRadius)
    {
        final MeshBuilder builder = new MeshBuilder();

        builder.addVertex(xRadius, yRadius, zRadius);
        builder.addVertex(-xRadius, yRadius, zRadius);
        builder.addVertex(-xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, -yRadius, -zRadius);
        builder.addVertex(xRadius, -yRadius, -zRadius);

        builder.addElement(4, 0, 1, 2, 3);
        builder.addElement(4, 4, 7, 6, 5);
        builder.addElement(4, 4, 5, 1, 0);
        builder.addElement(4, 3, 2, 6, 7);
        builder.addElement(4, 1, 5, 6, 2);
        builder.addElement(4, 0, 3, 7, 4);

        return builder.build(id);
    }
}
