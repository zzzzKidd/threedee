/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;


/**
 * DataOutputStream
 *
 * @author k
 * @version $Revision: 84727 $
 */

public class ShortBufferBuilder
{
    /** The internal byte array buffer */
    private ByteArrayOutputStream buffer;

    /** The size */
    private int size;


    /**
     * Constructor
     */

    public ShortBufferBuilder()
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
     * Adds a short value in native byte order.
     *
     * @param value
     *            The value to add
     */

    public void add(final int value)
    {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
        {
            this.buffer.write(value & 0xff);
            this.buffer.write((value >> 8) & 0xff);
        }
        else
        {
            this.buffer.write((value >> 8) & 0xff);
            this.buffer.write(value & 0xff);
        }
        this.size++;
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
     * Builds the short buffer and returns it. The builder is reseted so
     * it can be used again.
     *
     * @return The short buffer
     */

    public ShortBuffer build()
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
        final int size = bytes.length * 2;

        // Create the short buffer
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(bytes);
        byteBuffer.position(0);
        final ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

        // Reset the builder
        reset();

        // Return the short buffer
        return shortBuffer;
    }
}
