/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
 * DataOutputStream
 *
 * @author k
 * @version $Revision: 84727 $
 */

public class FloatBufferBuilder
{
    /** The internal byte array buffer */
    private ByteArrayOutputStream buffer;

    /** The size */
    private int size;

    /**
     * Constructor
     */

    public FloatBufferBuilder()
    {
        reset();
    }


    /**
     * Resets the builder
     */

    public void reset()
    {
        this.buffer = new ByteArrayOutputStream();
        this.size = 0;
    }


    /**
     * Adds multiple values.
     *
     * @param values
     *            The values to add
     */

    public void add(final float... values)
    {
        for (final float value : values)
            add(value);
    }


    /**
     * Adds a float value in native byte order.
     *
     * @param value
     *            The value to add
     * @return This builder for chaining
     */

    public FloatBufferBuilder add(final float value)
    {
        final int bits = Float.floatToIntBits(value);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
        {
            this.buffer.write(bits & 0xff);
            this.buffer.write((bits >> 8) & 0xff);
            this.buffer.write((bits >> 16) & 0xff);
            this.buffer.write((bits >> 24) & 0xff);
        }
        else
        {
            this.buffer.write((bits >> 24) & 0xff);
            this.buffer.write((bits >> 16) & 0xff);
            this.buffer.write((bits >> 8) & 0xff);
            this.buffer.write(bits & 0xff);
        }
        this.size++;
        return this;
    }


    /**
     * Returns the current buffer size.
     *
     * @return The current buffer size
     */

    public int getSize()
    {
        return this.size;
    }


    /**
     * Builds the float buffer and returns it. The builder is reseted so it can
     * be used again.
     *
     * @return The float buffer
     */

    public FloatBuffer build()
    {
        // Fetch the bytes from the byte array buffer
        try
        {
            this.buffer.flush();
        }
        catch (final IOException e)
        {
            throw new RuntimeException(e.toString(), e);
        }
        final byte[] bytes = this.buffer.toByteArray();
        final int size = bytes.length;

        // Create the float buffer
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(bytes);
        byteBuffer.position(0);
        final FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();

        // Reset the builder
        reset();

        // Return the float buffer
        return floatBuffer;
    }
}
