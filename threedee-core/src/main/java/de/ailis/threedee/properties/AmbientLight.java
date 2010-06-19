/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.properties;

import java.nio.FloatBuffer;

import de.ailis.threedee.model.Color;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * Ambient light property.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AmbientLight implements NodeProperty
{
    /** The old ambient light */
    private final FloatBuffer oldIntensity = BufferUtils
            .createDirectFloatBuffer(16);

    /** The ambient color */
    private final Color intensity;

    /**
     * Constructor.
     *
     * @param intensity
     *            The ambient light intensity
     */

    public AmbientLight(final Color intensity)
    {
        this.intensity = intensity;
    }


    /**
     * @see NodeProperty#apply(GL)
     */

    public void apply(final GL gl)
    {
        gl.glGetFloatv(GL.GL_LIGHT_MODEL_AMBIENT, this.oldIntensity);
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, this.intensity
                .getBuffer());
    }


    /**
     * @see NodeProperty#remove(GL)
     */

    public void remove(final GL gl)
    {
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, this.oldIntensity);
    }
}
