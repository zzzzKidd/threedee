/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.FloatBuffer;

import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A camera node.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class DirectionalLight extends Light
{
    /** The position buffer */
    private final FloatBuffer position = BufferUtils.createDirectFloatBuffer(4);


    /**
     * Constructor
     */

    public DirectionalLight()
    {
        this.position.put(0).put(0).put(1).put(0).rewind();
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
        super.apply(gl);
    }
}