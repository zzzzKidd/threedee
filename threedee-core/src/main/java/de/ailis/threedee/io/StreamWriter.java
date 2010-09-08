/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;

import de.ailis.gramath.Color4f;


/**
 * A writer with many specialized methods to write data to a stream.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class StreamWriter
{
    /** The stream to write to */
    private final OutputStream stream;

    /** The byte order */
    private ByteOrder byteOrder = ByteOrder.nativeOrder();

    /** The current position in the stream. */
    private long pos = 0;


    /**
     * Creates a stream writer writing to the specified stream.
     *
     * @param stream
     *            The stream to write to
     */

    public StreamWriter(final OutputStream stream)
    {
        if (stream == null)
            throw new IllegalArgumentException("stream must be set");
        this.stream = stream;
    }


    /**
     * Closes the stream used by the stream writer. You don't need to call this
     * method if you close the stream yourself.
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
     * Writes a single byte to the stream.
     *
     * @param value
     *            The byte to write
     * @throws IOException
     *             When write fails
     */

    public void writeByte(final int value) throws IOException
    {
        this.stream.write(value);
        this.pos++;
    }


    /**
     * Writes a short value (16 bit).
     *
     * @param value
     *            The value to write
     * @throws IOException
     *             When write fails
     */

    public void writeShort(final int value) throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            this.stream.write(value & 0xff);
            this.stream.write((value >> 8) & 0xff);
        }
        else
        {
            this.stream.write((value >> 8) & 0xff);
            this.stream.write(value & 0xff);
        }
        this.pos += 2;
    }


    /**
     * Writes a integer value (32 bit).
     *
     * @param value
     *            The value to write
     * @throws IOException
     *             When write fails
     */

    public void writeInt(final int value) throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            this.stream.write(value & 0xff);
            this.stream.write((value >> 8) & 0xff);
            this.stream.write((value >> 16) & 0xff);
            this.stream.write((value >> 24) & 0xff);
        }
        else
        {
            this.stream.write((value >> 24) & 0xff);
            this.stream.write((value >> 16) & 0xff);
            this.stream.write((value >> 8) & 0xff);
            this.stream.write(value & 0xff);
        }
        this.pos += 4;
    }


    /**
     * Writes a long value (64 bit).
     *
     * @param value
     *            The value to write
     * @throws IOException
     *             When write fails
     */

    public void writeLong(final long value) throws IOException
    {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN)
        {
            this.stream.write((int) (value & 0xff));
            this.stream.write((int) ((value >> 8) & 0xff));
            this.stream.write((int) ((value >> 16) & 0xff));
            this.stream.write((int) ((value >> 24) & 0xff));
            this.stream.write((int) ((value >> 32) & 0xff));
            this.stream.write((int) ((value >> 40) & 0xff));
            this.stream.write((int) ((value >> 48) & 0xff));
            this.stream.write((int) ((value >> 56) & 0xff));
        }
        else
        {
            this.stream.write((int) ((value >> 56) & 0xff));
            this.stream.write((int) ((value >> 48) & 0xff));
            this.stream.write((int) ((value >> 40) & 0xff));
            this.stream.write((int) ((value >> 32) & 0xff));
            this.stream.write((int) ((value >> 24) & 0xff));
            this.stream.write((int) ((value >> 16) & 0xff));
            this.stream.write((int) ((value >> 8) & 0xff));
            this.stream.write((int) (value & 0xff));
        }
        this.pos += 8;
    }


    /**
     * Writes a float value.
     *
     * @param value
     *            The value to write
     * @throws IOException
     *             When write fails
     */

    public void writeFloat(final float value) throws IOException
    {
        writeInt(Float.floatToIntBits(value));
    }


    /**
     * Writes a double value.
     *
     * @param value
     *            The value to write
     * @throws IOException
     *             When write fails
     */

    public void writeDouble(final double value) throws IOException
    {
        writeLong(Double.doubleToLongBits(value));
    }


    /**
     * Writes the remaining bytes of the specified byte buffer to the stream.
     * After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeByteBuffer(final ByteBuffer buffer) throws IOException
    {
        this.pos += buffer.remaining();
        Channels.newChannel(this.stream).write(buffer);
    }


    /**
     * Writes the remaining shorts of the specified short buffer to the stream.
     * After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeShortBuffer(final ShortBuffer buffer) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer
                .remaining() * 2);
        byteBuffer.order(this.byteOrder);
        byteBuffer.asShortBuffer().put(buffer);
        byteBuffer.rewind();
        writeByteBuffer(byteBuffer);
    }


    /**
     * Writes the remaining integers of the specified int buffer to the stream.
     * After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeIntBuffer(final IntBuffer buffer) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer
                .remaining() * 4);
        byteBuffer.order(this.byteOrder);
        byteBuffer.asIntBuffer().put(buffer);
        byteBuffer.rewind();
        writeByteBuffer(byteBuffer);
    }


    /**
     * Writes the remaining longs of the specified long buffer to the stream.
     * After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeLongBuffer(final LongBuffer buffer) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer
                .remaining() * 8);
        byteBuffer.order(this.byteOrder);
        byteBuffer.asLongBuffer().put(buffer);
        byteBuffer.rewind();
        writeByteBuffer(byteBuffer);
    }


    /**
     * Writes the remaining floats of the specified float buffer to the stream.
     * After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeFloatBuffer(final FloatBuffer buffer) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer
                .remaining() * 4);
        byteBuffer.order(this.byteOrder);
        byteBuffer.asFloatBuffer().put(buffer);
        byteBuffer.rewind();
        writeByteBuffer(byteBuffer);
    }


    /**
     * Writes the remaining doubles of the specified double buffer to the
     * stream. After this method call the position of the buffer is at the end.
     *
     * @param buffer
     *            The buffer to write
     * @throws IOException
     *             When write fails
     */

    public void writeDoubleBuffer(final DoubleBuffer buffer) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer
                .remaining() * 8);
        byteBuffer.order(this.byteOrder);
        byteBuffer.asDoubleBuffer().put(buffer);
        byteBuffer.rewind();
        writeByteBuffer(byteBuffer);
    }


    /**
     * Writes a string in UTF-8 encoding.
     *
     * @param string
     *            The string to write
     * @throws IOException
     *             When write fails
     */

    public void writeString(final String string) throws IOException
    {
        writeString(string, "UTF-8");
    }


    /**
     * Writes a string in a specific encoding
     *
     * @param string
     *            The string to write
     * @param charset
     *            The encoding to use
     * @throws IOException
     *             When write fails
     */

    public void writeString(final String string, final String charset)
            throws IOException
    {
        final byte[] bytes = string.getBytes(charset);
        this.stream.write(bytes);
        this.pos += bytes.length;
    }


    /**
     * Writes a Color4f.
     *
     * @param color
     *            The color to write
     * @throws IOException
     *             When write fails
     */

    public void writeColor4f(final Color4f color) throws IOException
    {
        writeByte((int) (color.getRed() * 255));
        writeByte((int) (color.getGreen() * 255));
        writeByte((int) (color.getBlue() * 255));
        writeByte((int) (color.getAlpha() * 255));
    }


    /**
     * Returns the current position in the stream.
     *
     * @return The current position in the stream
     */

    public long getPosition()
    {
        return this.pos;
    }
}
