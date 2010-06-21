/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.builder.MeshBuilder;
import de.ailis.threedee.entities.Mesh;


/**
 * A simple cube model.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Cube
{
    /**
     * Builds the model objects and returns them.
     *
     * @param xRadius
     *            The X radius
     * @param yRadius
     *            The Y radius
     * @param zRadius
     *            The Z radius
     * @return The model objects
     */

    public static Mesh buildCube(final float xRadius,
            final float yRadius, final float zRadius)
    {
        final MeshBuilder builder = new MeshBuilder();
        builder.addVertex(-xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, yRadius, zRadius);
        builder.addVertex(xRadius, yRadius, zRadius);
        builder.addVertex(xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, -yRadius, -zRadius);
        builder.addVertex(-xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, -yRadius, -zRadius);

        builder.addElement(3, 0, 1, 2);
        builder.addElement(3, 2, 3, 0);

        builder.addElement(3, 0, 4, 5);
        builder.addElement(3, 5, 1, 0);

        builder.addElement(3, 1, 5, 6);
        builder.addElement(3, 6, 2, 1);

        builder.addElement(3, 2, 6, 7);
        builder.addElement(3, 7, 3, 2);

        builder.addElement(3, 0, 3, 7);
        builder.addElement(3, 7, 4, 0);

        builder.addElement(3, 5, 4, 7);
        builder.addElement(3, 7, 6, 5);

        return builder.build("test");
    }
}
