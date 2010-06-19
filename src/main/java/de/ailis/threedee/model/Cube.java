/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.model.builder.ModelBuilder;
import de.ailis.threedee.opengl.GL;


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

    public static Model buildCube(final float xRadius,
            final float yRadius, final float zRadius)
    {
        final ModelBuilder builder = new ModelBuilder();
        builder.addVertex(-xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, yRadius, zRadius);
        builder.addVertex(xRadius, yRadius, zRadius);
        builder.addVertex(xRadius, yRadius, -zRadius);
        builder.addVertex(-xRadius, -yRadius, -zRadius);
        builder.addVertex(-xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, -yRadius, zRadius);
        builder.addVertex(xRadius, -yRadius, -zRadius);

        builder.addElement(GL.GL_TRIANGLES, 0, 1, 2);
        builder.addElement(GL.GL_TRIANGLES, 2, 3, 0);

        builder.addElement(GL.GL_TRIANGLES, 0, 4, 5);
        builder.addElement(GL.GL_TRIANGLES, 5, 1, 0);

        builder.addElement(GL.GL_TRIANGLES, 1, 5, 6);
        builder.addElement(GL.GL_TRIANGLES, 6, 2, 1);

        builder.addElement(GL.GL_TRIANGLES, 2, 6, 7);
        builder.addElement(GL.GL_TRIANGLES, 7, 3, 2);

        builder.addElement(GL.GL_TRIANGLES, 0, 3, 7);
        builder.addElement(GL.GL_TRIANGLES, 7, 4, 0);

        builder.addElement(GL.GL_TRIANGLES, 5, 4, 7);
        builder.addElement(GL.GL_TRIANGLES, 7, 6, 5);

        return builder.build();
    }
}
