/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;


/**
 * A reader with many specialized methods to read data from a stream.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class StreamReader
{
    /** The stream to write to */
    private final InputStream stream;

    /** The byte order */
    private ByteOrder byteOrder = ByteOrder.nativeOrder();


    /**
     * Creates a stream reader reading from the specified stream.
     *
     * @param stream
     *            The stream to read from
     */

    public StreamReader(final InputStream stream)
    {
        if (stream == null)
            throw new IllegalArgumentException("stream must be set");
        this.stream = stream;
    }


    /**
     * Closes the stream used by the stream reader. You don't need to call
     * this method if you close the stream yourself.
     *
     * @throws IOException
     *             When stream could not be closed
     */

    public void close() throws IOException
    {
        this.stream.close();
    }


    /**
     * Sets the byte order.
     *
     * @param byteOrder
     *            The byte order to set
     */

    public void setByteOrder(final ByteOrder byteOrder)
    {
        this.byteOrder = byteOrder;
    }


    /**
     * Returns the current byte order.
     *
     * @return The current byte orde
     */

    public ByteOrder getByteOrder()
    {
        return this.byteOrder;
    }


    /**
     * Reads a single byte from the stream.
     *
     * @return The read byte
     * @throws IOException
     *             When read fails
     */

    public int readByte() throws IOException
    {
        return this.stream.read();
    }


    /**
     * Reads a short value (16 bit).
     *
     * @return The read value
     * @throws IOException
     *             When read fails
     */

    public int readShort() throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            return this.stream.read() | this.stream.read() << 8;
        }
        else
        {
            return (this.stream.read() << 8) | this.stream.read();
        }
    }


    /**
     * Reads a integer value (32 bit).
     *
     * @return The read value
     * @throws IOException
     *             When read fails
     */

    public int readInt() throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            return this.stream.read() | (this.stream.read() << 8)
                    | (this.stream.read() << 16) | (this.stream.read() << 24);
        }
        else
        {
            return (this.stream.read() << 24) | (this.stream.read() << 16)
                    | (this.stream.read() << 8) | this.stream.read();
        }
    }


    /**
     * Reads a long value (32 bit).
     *
     * @return The read value
     * @throws IOException
     *             When read fails
     */

    public long readLong() throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            return (this.stream.read()) | (((long) this.stream.read()) << 8)
                    | (((long) this.stream.read()) << 16)
                    | (((long) this.stream.read()) << 24)
                    | (((long) this.stream.read()) << 32)
                    | (((long) this.stream.read()) << 40)
                    | (((long) this.stream.read()) << 48)
                    | (((long) this.stream.read()) << 56);
        }
        else
        {
            return (((long) this.stream.read()) << 56)
                    | (((long) this.stream.read()) << 48)
                    | (((long) this.stream.read()) << 40)
                    | (((long) this.stream.read()) << 32)
                    | (((long) this.stream.read()) << 24)
                    | (((long) this.stream.read()) << 16)
                    | (((long) this.stream.read()) << 8) | (this.stream.read());
        }
    }


    /**
     * Reads a float.
     *
     * @return The read value
     * @throws IOException
     *             When read fails
     */

    public float readFloat() throws IOException
    {
        return Float.intBitsToFloat(readInt());
    }


    /**
     * Reads a double.
     *
     * @return The read value
     * @throws IOException
     *             When read fails
     */

    public double readDouble() throws IOException
    {
        return Double.longBitsToDouble(readLong());
    }


    /**
     * Reads the specified number of bytes from the stream and return it
     * in form of a byte buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of bytes to read
     * @return The read byte buffer
     * @throws IOException
     *             When read fails
     */

    public ByteBuffer readByteBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size);
        Channels.newChannel(this.stream).read(buffer);
        buffer.rewind();
        return buffer;
    }


    /**
     * Reads the specified number of shorts from the stream and return it
     * in form of a short buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of shorts to read
     * @return The read short buffer
     * @throws IOException
     *             When read fails
     */

    public ShortBuffer readShortBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 2);
        buffer.order(this.byteOrder);
        final ShortBuffer shortBuffer = buffer.asShortBuffer();
        Channels.newChannel(this.stream).read(buffer);
        shortBuffer.rewind();
        return shortBuffer;
    }


    /**
     * Reads the specified number of ints from the stream and return it
     * in form of a int buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of ints to read
     * @return The read int buffer
     * @throws IOException
     *             When read fails
     */

    public IntBuffer readIntBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 4);
        buffer.order(this.byteOrder);
        final IntBuffer intBuffer = buffer.asIntBuffer();
        Channels.newChannel(this.stream).read(buffer);
        intBuffer.rewind();
        return intBuffer;
    }


    /**
     * Reads the specified number of longs from the stream and return it
     * in form of a longs buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of longs to read
     * @return The read long buffer
     * @throws IOException
     *             When read fails
     */

    public LongBuffer readLongBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 8);
        buffer.order(this.byteOrder);
        final LongBuffer longBuffer = buffer.asLongBuffer();
        Channels.newChannel(this.stream).read(buffer);
        longBuffer.rewind();
        return longBuffer;
    }


    /**
     * Reads the specified number of floats from the stream and return it
     * in form of a float buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of floats to read
     * @return The read float buffer
     * @throws IOException
     *             When read fails
     */

    public FloatBuffer readFloatBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 4);
        buffer.order(this.byteOrder);
        final FloatBuffer floatBuffer = buffer.asFloatBuffer();
        Channels.newChannel(this.stream).read(buffer);
        floatBuffer.rewind();
        return floatBuffer;
    }


    /**
     * Reads the specified number of doubles from the stream and return it
     * in form of a double buffer. The position of the returned buffer is at
     * the beginning.
     *
     * @param size
     *            The number of doubles to read
     * @return The read double buffer
     * @throws IOException
     *             When read fails
     */

    public DoubleBuffer readDoubleBuffer(final int size) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size * 8);
        buffer.order(this.byteOrder);
        final DoubleBuffer doubleBuffer = buffer.asDoubleBuffer();
        Channels.newChannel(this.stream).read(buffer);
        doubleBuffer.rewind();
        return doubleBuffer;
    }


    /**
     * Reads the specified number of bytes, converts them into a UTF-8
     * string and returns it.
     *
     * @param size
     *            The number of bytes to read
     * @return The read string
     * @throws IOException
     *             If read fails
     */

    public String readString(final int size) throws IOException
    {
        return readString(size, "UTF-8");
    }


    /**
     * Reads the specified number of bytes, converts them into a string using
     * the specified character encoding and returns it.
     *
     * @param size
     *            The number of bytes to read
     * @param charset
     *            The encoding to use
     * @return The read string
     * @throws IOException
     *             If read fails
     */
    public String readString(final int size, final String charset) throws IOException
    {
        final byte[] bytes = new byte[size];
        this.stream.read(bytes);
        return new String(bytes, charset);
    }
}
