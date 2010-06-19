/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.utils;

import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


/**
 * A builder to create an integer buffer. The result can be a ByteBuffer, a
 * ShortBuffer or an IntBuffer. This depends on the values put into the buffer.
 * So the builder automatically switches the buffer type if needed.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class IntBufferBuilder
{
    /** The internal byte array buffer */
    private ByteArrayOutputStream buffer;

    /** The current number of bytes per entry */
    private int bytes;

    /** The size */
    private int size;

    /** The value offset to apply to added values */
    private int offset;


    /**
     * Constructor
     */

    public IntBufferBuilder()
    {
        reset();
    }


    /**
     * Resets the builder
     */

    public void reset()
    {
        this.buffer = new ByteArrayOutputStream();
        this.bytes = 1;
        this.size = 0;
        this.offset = 0;
    }


    /**
     * Sets the offset to apply to added values.
     *
     * @param offset
     *            The offset to apply to added values
     */

    public void setOffset(final int offset)
    {
        this.offset = offset;
    }


    /**
     * Returns the offset applied to added values.
     *
     * @return The offset
     */

    public int getOffset()
    {
        return this.offset;
    }

    /**
     * Adds multiple values to the buffer.
     *
     * @param values
     *            The values to add
     */

    public void add(final int... values)
    {
        for (final int value : values)
            add(value);
    }


    /**
     * Adds values from the specified buffer. This method automatically
     * delegates to the other methods depending on the buffer type.
     *
     * @param values
     *            The values to add
     */

    public void add(final Buffer values)
    {
        if (values instanceof ByteBuffer)
            add((ByteBuffer) values);
        else if (values instanceof ShortBuffer)
            add((ShortBuffer) values);
        else if (values instanceof IntBuffer)
            add((IntBuffer) values);
        else
            throw new IllegalArgumentException("Unknown buffer type: "
                    + values.getClass());
    }


    /**
     * Adds values from the specified buffer.
     *
     * @param values
     *            The values to add
     */

    public void add(final ByteBuffer values)
    {
        while (values.hasRemaining())
            add(values.get() & 0xff);
    }


    /**
     * Adds values from the specified buffer.
     *
     * @param values
     *            The values to add
     */

    public void add(final IntBuffer values)
    {
        while (values.hasRemaining())
            add(values.get());
    }


    /**
     * Adds values from the specified buffer.
     *
     * @param values
     *            The values to add
     */

    public void add(final ShortBuffer values)
    {
        while (values.hasRemaining())
            add(values.get() & 0xffff);
    }


    /**
     * Adds a short value in native byte order.
     *
     * @param value
     *            The value to add
     */

    public void add(final int value)
    {
        final int realValue = value + this.offset;
        final int neededBytes = getNeededBytes(realValue);
        if (neededBytes > this.bytes) newBufferType(neededBytes);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
        {
            for (int i = 0; i < this.bytes; i++)
                this.buffer.write((realValue >> (i * 8)) & 0xff);
        }
        else
        {
            for (int i = this.bytes - 1; i >= 0; i--)
                this.buffer.write((realValue >> (i * 8)) & 0xff);
        }
        this.size++;
    }


    /**
     * Checks how many bytes are needed for the specified value.
     *
     * @param value
     *            The value
     * @return The number of needed bytes to represent the value
     */

    private static int getNeededBytes(final int value)
    {
        if (value < 0x100) return 1;
        if (value < 0x10000) return 2;
        return 4;
    }


    /**
     * Switches to a new buffer type which can store values with the specified
     * number of bytes. This can be an expensive operation but fortunately it
     * happens very seldom.
     *
     * @param bytes
     *            The number of needed bytes per value
     */

    private void newBufferType(final int bytes)
    {
        final byte[] values = this.buffer.toByteArray();
        final int oldBytes = this.bytes;
        final boolean little = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        reset();
        this.bytes = bytes;
        final int oldOffset = this.offset;
        this.offset = 0;
        final int max = values.length / oldBytes;
        for (int i = 0; i < max; i++)
        {
            int v = 0;
            for (int b = 0; b < oldBytes; b++)
            {
                if (little)
                {
                    v |= (values[i + b] & 0xff) << (b * 8);
                }
                else
                {
                    v |= (values[i + b] & 0xff) << ((oldBytes - b - 1) * 8);
                }
            }
            add(v);
        }
        this.offset = oldOffset;
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
     * Builds the short buffer and returns it. The builder is reseted so it can
     * be used again.
     *
     * @return The short buffer
     */

    public Buffer build()
    {
        // Fetch the bytes from the byte array buffer
        final byte[] bytes = this.buffer.toByteArray();

        // Create the short buffer
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(bytes);
        byteBuffer.position(0);
        Buffer buffer;
        switch (this.bytes)
        {
            case 1:
                buffer = byteBuffer;
                break;

            case 2:
                buffer = byteBuffer.asShortBuffer();
                break;

            case 4:
                buffer = byteBuffer.asIntBuffer();
                break;

            default:
                throw new IllegalStateException("Invalid number of bytes: "
                        + bytes);
        }

        // Reset the builder
        reset();

        // Return the buffer
        return buffer;
    }
}
