/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.properties;

import de.ailis.threedee.opengl.GL;


/**
 * Enables or disables lighting.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Lighting implements NodeProperty
{
    /** The old lighting flag */
    private boolean oldEnabled;

    /** The lighting flag */
    private final boolean enabled;

    /**
     * Constructor.
     *
     * @param enabled
     *            If lighting is enabled
     */

    public Lighting(final boolean enabled)
    {
        this.enabled = enabled;
    }


    /**
     * @see NodeProperty#apply(GL)
     */

    public void apply(final GL gl)
    {
        this.oldEnabled = gl.glIsEnabled(GL.GL_LIGHTING);
        if (this.oldEnabled && !this.enabled)
            gl.glDisable(GL.GL_LIGHTING);
        else if (!this.oldEnabled && this.enabled)
            gl.glEnable(GL.GL_LIGHTING);
    }


    /**
     * @see NodeProperty#remove(GL)
     */

    public void remove(final GL gl)
    {
        if (this.oldEnabled && !this.enabled)
            gl.glEnable(GL.GL_LIGHTING);
        else if (!this.oldEnabled && this.enabled)
            gl.glDisable(GL.GL_LIGHTING);
    }
}
