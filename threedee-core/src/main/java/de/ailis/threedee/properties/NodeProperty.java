/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.properties;

import de.ailis.threedee.rendering.opengl.GL;


/**
 * A node property.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface NodeProperty
{
    /**
     * Applies the property to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void apply(final GL gl);


    /**
     * Removes the property from the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void remove(final GL gl);
}
