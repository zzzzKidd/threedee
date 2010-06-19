/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import de.ailis.threedee.math.Bounds;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * Utility class for rendering bounds.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public final class BoundsRenderer
{
    /** The vertex buffer */
    private static final FloatBuffer vertices = BufferUtils
            .createDirectFloatBuffer(24);

    /** The index buffer */
    private static final ByteBuffer indices = BufferUtils
            .createDirectByteBuffer(24);


    /**
     * Private constructor to prevent instantiation.
     */

    private BoundsRenderer()
    {
        // Empty
    }


    /**
     * Renders the bounds.
     *
     * @param gl
     *            The GL context
     * @param bounds
     *            The bounds
     */

    public static void render(final GL gl, final Bounds bounds)
    {
        final Vector3f min = bounds.getMin();
        final Vector3f max = bounds.getMax();
        final float minX = min.getX();
        final float minY = min.getY();
        final float minZ = min.getZ();
        final float maxX = max.getX();
        final float maxY = max.getY();
        final float maxZ = max.getZ();

        vertices.put(maxX).put(maxY).put(maxZ);
        vertices.put(minX).put(maxY).put(maxZ);
        vertices.put(minX).put(minY).put(maxZ);
        vertices.put(maxX).put(minY).put(maxZ);

        vertices.put(maxX).put(minY).put(minZ);
        vertices.put(minX).put(minY).put(minZ);
        vertices.put(minX).put(maxY).put(minZ);
        vertices.put(maxX).put(maxY).put(minZ);

        indices.put((byte) 0).put((byte) 1);
        indices.put((byte) 1).put((byte) 2);
        indices.put((byte) 2).put((byte) 3);
        indices.put((byte) 3).put((byte) 0);

        indices.put((byte) 4).put((byte) 5);
        indices.put((byte) 5).put((byte) 6);
        indices.put((byte) 6).put((byte) 7);
        indices.put((byte) 7).put((byte) 4);

        indices.put((byte) 1).put((byte) 6);
        indices.put((byte) 5).put((byte) 2);
        indices.put((byte) 0).put((byte) 7);
        indices.put((byte) 4).put((byte) 3);

        indices.rewind();
        vertices.rewind();

        gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, 0, vertices);
        gl.glDrawElements(GL.GL_LINES, GL.GL_UNSIGNED_BYTE, indices);
        gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
    }
}
