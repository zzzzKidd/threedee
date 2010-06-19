/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


/**
 * Utility methods for working with NIO buffers.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class BufferUtils
{
    /** The number of bytes allocated by a float */
    private static final int FLOAT_BYTES = Float.SIZE / 8;

    /** The number of bytes allocated by a short */
    private static final int SHORT_BYTES = Short.SIZE / 8;

    /** The number of bytes allocated by an integer */
    private static final int INTEGER_BYTES = Integer.SIZE / 8;


    /**
     * Creates a direct byte buffer with native byte order.
     *
     * @param size
     *      The data
     * @return The created direct byte buffer
     */

    public static ByteBuffer createDirectByteBuffer(final int size)
    {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }


    /**
     * Creates a direct float buffer with native byte order.
     *
     * @param size
     *      The data
     * @return The created direct float buffer
     */

    public static FloatBuffer createDirectFloatBuffer(final int size)
    {
        final ByteBuffer tmp = ByteBuffer.allocateDirect(size * FLOAT_BYTES);
        tmp.order(ByteOrder.nativeOrder());
        return tmp.asFloatBuffer();
    }


    /**
     * Creates a direct short buffer with native byte order.
     *
     * @param size
     *      The data
     * @return The created direct short buffer
     */

    public static ShortBuffer createDirectShortBuffer(final int size)
    {
        final ByteBuffer tmp = ByteBuffer.allocateDirect(size * SHORT_BYTES);
        tmp.order(ByteOrder.nativeOrder());
        return tmp.asShortBuffer();
    }


    /**
     * Creates a direct integer buffer with native byte order.
     *
     * @param size
     *      The data
     * @return The created direct integer buffer
     */

    public static IntBuffer createDirectIntegerBuffer(final int size)
    {
        final ByteBuffer tmp = ByteBuffer.allocateDirect(size * INTEGER_BYTES);
        tmp.order(ByteOrder.nativeOrder());
        return tmp.asIntBuffer();
    }


    /**
     * Converts the specified float buffer to native endian and returns this
     * new buffer. If buffer is already in correct endian format then it is
     * returned right away.
     *
     * @param buffer
     *            The float buffer to convert
     * @return The converted float buffer or the source buffer if no conversion
     *         is needed
     */

    public static FloatBuffer convertToNativeEndian(final FloatBuffer buffer)
    {
        if (buffer.order() == ByteOrder.nativeOrder()) return buffer;

        final ByteBuffer bytes = ByteBuffer.allocateDirect(buffer.capacity());
        bytes.order(ByteOrder.nativeOrder());
        final FloatBuffer floats = bytes.asFloatBuffer();
        floats.put(buffer).rewind();
        return floats;
    }


    /**
     * Converts the specified short buffer to native endian and returns this
     * new buffer. If buffer is already in correct endian format then it is
     * returned right away.
     *
     * @param buffer
     *            The short buffer to convert
     * @return The converted short buffer or the source buffer if no conversion
     *         is needed
     */

    public static ShortBuffer convertToNativeEndian(final ShortBuffer buffer)
    {
        if (buffer.order() == ByteOrder.nativeOrder()) return buffer;

        final ByteBuffer bytes = ByteBuffer.allocateDirect(buffer.capacity());
        bytes.order(ByteOrder.nativeOrder());
        final ShortBuffer shorts = bytes.asShortBuffer();
        shorts.put(buffer).rewind();
        return shorts;
    }


    /**
     * Converts the specified integer buffer to native endian and returns this
     * new buffer. If buffer is already in correct endian format then it is
     * returned right away.
     *
     * @param buffer
     *            The integer buffer to convert
     * @return The converted integer buffer or the source buffer if no
     *         conversion
     *         is needed
     */

    public static IntBuffer convertToNativeEndian(final IntBuffer buffer)
    {
        if (buffer.order() == ByteOrder.nativeOrder()) return buffer;

        final ByteBuffer bytes = ByteBuffer.allocateDirect(buffer.capacity());
        bytes.order(ByteOrder.nativeOrder());
        final IntBuffer ints = bytes.asIntBuffer();
        ints.put(buffer).rewind();
        return ints;
    }
}
