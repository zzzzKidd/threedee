/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.FloatBuffer;

import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A spot light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SpotLight extends Light
{
    /** The position buffer */
    private final FloatBuffer position = BufferUtils.createDirectFloatBuffer(4);

    /** The direction of the light */
    private final FloatBuffer direction = BufferUtils
            .createDirectFloatBuffer(4);

    /** The spot cut-off */
    private float cutOff = 45;


    /**
     * Constructor
     */

    public SpotLight()
    {
        this.position.put(0).put(0).put(0).put(1).rewind();
        this.direction.put(0).put(0).put(-1).rewind();
    }


    /**
     * Applies the light to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    @Override
    public void apply(final GL gl)
    {
        final int index = getLightIndex(gl);
        gl.glLight(index, GL.GL_POSITION, this.position);
        gl.glLight(index, GL.GL_SPOT_DIRECTION, this.direction);
        gl.glLightf(index, GL.GL_SPOT_CUTOFF, this.cutOff);
        super.apply(gl);
    }


    /**
     * Returns the spot cut-off.
     *
     * @return The spot cut-off
     */

    public float getCutOff()
    {
        return this.cutOff;
    }


    /**
     * Sets the cut-off.
     *
     * @param cutOff
     *            The cut off to set
     */

    public void setCutOff(final float cutOff)
    {
        this.cutOff = cutOff;
    }
}