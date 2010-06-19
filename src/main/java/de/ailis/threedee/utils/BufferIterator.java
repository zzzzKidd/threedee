/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;


/**
 * Iterates over a buffer. Supports ByteBuffer, IntBuffer and ShortBUffer)
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class BufferIterator implements Iterator<Number>
{
    /** Reference to the buffer, no matter what type */
    private final Buffer buffer;

    /** reference to the buffer if it is a byte buffer */
    private final ByteBuffer byteBuffer;

    /** reference to the buffer if it is a short buffer */
    private final ShortBuffer shortBuffer;

    /** reference to the buffer if it is a int buffer */
    private final IntBuffer intBuffer;


    /**
     * Constructs a new iterator for the specified buffer.
     *
     * @param buffer The buffer to iterate over
     */

    public BufferIterator(final Buffer buffer)
    {
        if (buffer instanceof ByteBuffer)
        {
            this.buffer = this.byteBuffer = (ByteBuffer) buffer;
            this.shortBuffer = null;
            this.intBuffer = null;
        }
        else if (buffer instanceof ShortBuffer)
        {
            this.byteBuffer = null;
            this.buffer = this.shortBuffer = (ShortBuffer) buffer;
            this.intBuffer = null;
        }
        else if (buffer instanceof IntBuffer)
        {
            this.byteBuffer = null;
            this.shortBuffer = null;
            this.buffer = this.intBuffer = (IntBuffer) buffer;
        }
        else
            throw new IllegalArgumentException("Unsupported buffer type");
    }


    /**
     * @see java.util.Iterator#hasNext()
     */

    @Override
    public boolean hasNext()
    {
        return this.buffer.hasRemaining();
    }


    /**
     * @see java.util.Iterator#next()
     */

    @Override
    public Number next()
    {
        if (this.byteBuffer != null) return this.byteBuffer.get() & 0xff;
        if (this.shortBuffer != null) return this.shortBuffer.get() & 0xffff;
        if (this.intBuffer != null) return this.intBuffer.get() % 0xffffffff;
        throw new IllegalStateException();
    }


    /**
     * @see java.util.Iterator#remove()
     */

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("remove is not allowed");
    }
}
