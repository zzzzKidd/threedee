/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the StreamWriter class.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class StreamWriterTest
{
    /** The output stream used during tests */
    private ByteArrayOutputStream stream;

    /** The tested stream writer */
    private StreamWriter writer;


    /**
     * Setup a test.
     */

    @Before
    public void setUp()
    {
        this.stream = new ByteArrayOutputStream();
        this.writer = new StreamWriter(this.stream);
    }


    /**
     * Cleans up after a test
     *
     * @throws IOException
     *             When writing fails
     */

    @After
    public void tearDown() throws IOException
    {
        this.writer.close();
    }


    /**
     * Tests writing single bytes.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteByte() throws IOException
    {
        this.writer.writeByte(0);
        this.writer.writeByte(255);
        final byte[] bytes = this.stream.toByteArray();
        assertEquals((byte) 0, bytes[0]);
        assertEquals((byte) 255, bytes[1]);
    }


    /**
     * Tests writing short values.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteShort() throws IOException
    {
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeShort(0xfedc);
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeShort(0xfedc);
        final byte[] bytes = this.stream.toByteArray();
        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);
        assertEquals((byte) 0xdc, bytes[2]);
        assertEquals((byte) 0xfe, bytes[3]);
    }


    /**
     * Tests writing integer values.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteInt() throws IOException
    {
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeInt(0xfedcba98);
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeInt(0xfedcba98);
        final byte[] bytes = this.stream.toByteArray();
        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);
        assertEquals((byte) 0xba, bytes[2]);
        assertEquals((byte) 0x98, bytes[3]);
        assertEquals((byte) 0x98, bytes[4]);
        assertEquals((byte) 0xba, bytes[5]);
        assertEquals((byte) 0xdc, bytes[6]);
        assertEquals((byte) 0xfe, bytes[7]);
    }


    /**
     * Tests writing integer values.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteLong() throws IOException
    {
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeLong(0xfedcba9876543210L);
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeLong(0xfedcba9876543210L);
        final byte[] bytes = this.stream.toByteArray();
        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);
        assertEquals((byte) 0xba, bytes[2]);
        assertEquals((byte) 0x98, bytes[3]);
        assertEquals((byte) 0x76, bytes[4]);
        assertEquals((byte) 0x54, bytes[5]);
        assertEquals((byte) 0x32, bytes[6]);
        assertEquals((byte) 0x10, bytes[7]);
        assertEquals((byte) 0x10, bytes[8]);
        assertEquals((byte) 0x32, bytes[9]);
        assertEquals((byte) 0x54, bytes[10]);
        assertEquals((byte) 0x76, bytes[11]);
        assertEquals((byte) 0x98, bytes[12]);
        assertEquals((byte) 0xba, bytes[13]);
        assertEquals((byte) 0xdc, bytes[14]);
        assertEquals((byte) 0xfe, bytes[15]);
    }


    /**
     * Tests writing a byte buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteByteBuffer() throws IOException
    {
        // Prepare the buffer with three values, position pointing to the
        // second value.
        final ByteBuffer buffer = ByteBuffer.allocate(3);
        buffer.put((byte) 53);
        buffer.put((byte) 0);
        buffer.put((byte) 255);
        buffer.rewind();
        buffer.position(1);

        // Write remaining bytes to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeByteBuffer(buffer);
        assertEquals(3, buffer.position());

        // Rewind buffer and write bytes again (ALL bytes this time)
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeByteBuffer(buffer);
        assertEquals(3, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(5, bytes.length);

        assertEquals((byte) 0, bytes[0]);
        assertEquals((byte) 255, bytes[1]);
        assertEquals((byte) 53, bytes[2]);
        assertEquals((byte) 0, bytes[3]);
        assertEquals((byte) 255, bytes[4]);
    }


    /**
     * Tests writing a short buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteShortBuffer() throws IOException
    {
        // Prepare the buffer with three values, position pointing to the
        // second value.
        final ShortBuffer buffer = ShortBuffer.allocate(3);
        buffer.put((short) 0x1234);
        buffer.put((short) 0xfedc);
        buffer.put((short) 0xba98);
        buffer.rewind();
        buffer.position(1);

        // Write remaining values to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeShortBuffer(buffer);
        assertEquals(3, buffer.position());

        // Rewind buffer and write values again (ALL values this time)
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeShortBuffer(buffer);
        assertEquals(3, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(10, bytes.length);

        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);

        assertEquals((byte) 0xba, bytes[2]);
        assertEquals((byte) 0x98, bytes[3]);

        assertEquals((byte) 0x34, bytes[4]);
        assertEquals((byte) 0x12, bytes[5]);

        assertEquals((byte) 0xdc, bytes[6]);
        assertEquals((byte) 0xfe, bytes[7]);

        assertEquals((byte) 0x98, bytes[8]);
        assertEquals((byte) 0xba, bytes[9]);
    }


    /**
     * Tests writing a int buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteIntBuffer() throws IOException
    {
        // Prepare the buffer with three values, position pointing to the
        // second value.
        final IntBuffer buffer = IntBuffer.allocate(3);
        buffer.put(0x12345678);
        buffer.put(0xfedcba98);
        buffer.put(0x76543210);
        buffer.rewind();
        buffer.position(1);

        // Write remaining values to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeIntBuffer(buffer);
        assertEquals(3, buffer.position());

        // Rewind buffer and write values again (ALL values this time)
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeIntBuffer(buffer);
        assertEquals(3, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(20, bytes.length);

        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);
        assertEquals((byte) 0xba, bytes[2]);
        assertEquals((byte) 0x98, bytes[3]);

        assertEquals((byte) 0x76, bytes[4]);
        assertEquals((byte) 0x54, bytes[5]);
        assertEquals((byte) 0x32, bytes[6]);
        assertEquals((byte) 0x10, bytes[7]);

        assertEquals((byte) 0x78, bytes[8]);
        assertEquals((byte) 0x56, bytes[9]);
        assertEquals((byte) 0x34, bytes[10]);
        assertEquals((byte) 0x12, bytes[11]);

        assertEquals((byte) 0x98, bytes[12]);
        assertEquals((byte) 0xba, bytes[13]);
        assertEquals((byte) 0xdc, bytes[14]);
        assertEquals((byte) 0xfe, bytes[15]);

        assertEquals((byte) 0x10, bytes[16]);
        assertEquals((byte) 0x32, bytes[17]);
        assertEquals((byte) 0x54, bytes[18]);
        assertEquals((byte) 0x76, bytes[19]);
    }


    /**
     * Tests writing a long buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteLongBuffer() throws IOException
    {
        // Prepare the buffer with three values, position pointing to the
        // second value.
        final LongBuffer buffer = LongBuffer.allocate(3);
        buffer.put(0x0123456789abcdefL);
        buffer.put(0xfedcba9876543210L);
        buffer.put(0xffeeddccbbaa9988L);
        buffer.rewind();
        buffer.position(1);

        // Write remaining values to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeLongBuffer(buffer);
        assertEquals(3, buffer.position());

        // Rewind buffer and write values again (ALL values this time)
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeLongBuffer(buffer);
        assertEquals(3, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(40, bytes.length);

        assertEquals((byte) 0xfe, bytes[0]);
        assertEquals((byte) 0xdc, bytes[1]);
        assertEquals((byte) 0xba, bytes[2]);
        assertEquals((byte) 0x98, bytes[3]);
        assertEquals((byte) 0x76, bytes[4]);
        assertEquals((byte) 0x54, bytes[5]);
        assertEquals((byte) 0x32, bytes[6]);
        assertEquals((byte) 0x10, bytes[7]);

        assertEquals((byte) 0xff, bytes[8]);
        assertEquals((byte) 0xee, bytes[9]);
        assertEquals((byte) 0xdd, bytes[10]);
        assertEquals((byte) 0xcc, bytes[11]);
        assertEquals((byte) 0xbb, bytes[12]);
        assertEquals((byte) 0xaa, bytes[13]);
        assertEquals((byte) 0x99, bytes[14]);
        assertEquals((byte) 0x88, bytes[15]);

        assertEquals((byte) 0xef, bytes[16]);
        assertEquals((byte) 0xcd, bytes[17]);
        assertEquals((byte) 0xab, bytes[18]);
        assertEquals((byte) 0x89, bytes[19]);
        assertEquals((byte) 0x67, bytes[20]);
        assertEquals((byte) 0x45, bytes[21]);
        assertEquals((byte) 0x23, bytes[22]);
        assertEquals((byte) 0x01, bytes[23]);

        assertEquals((byte) 0x10, bytes[24]);
        assertEquals((byte) 0x32, bytes[25]);
        assertEquals((byte) 0x54, bytes[26]);
        assertEquals((byte) 0x76, bytes[27]);
        assertEquals((byte) 0x98, bytes[28]);
        assertEquals((byte) 0xba, bytes[29]);
        assertEquals((byte) 0xdc, bytes[30]);
        assertEquals((byte) 0xfe, bytes[31]);

        assertEquals((byte) 0x88, bytes[32]);
        assertEquals((byte) 0x99, bytes[33]);
        assertEquals((byte) 0xaa, bytes[34]);
        assertEquals((byte) 0xbb, bytes[35]);
        assertEquals((byte) 0xcc, bytes[36]);
        assertEquals((byte) 0xdd, bytes[37]);
        assertEquals((byte) 0xee, bytes[38]);
        assertEquals((byte) 0xff, bytes[39]);
    }


    /**
     * Tests writing a float buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteFloatBuffer() throws IOException
    {
        final float[] values = { 53, 49.152f };

        // Prepare the buffer with two values
        final FloatBuffer buffer = FloatBuffer.allocate(2);
        buffer.put(values);
        buffer.rewind();

        // Write remaining values to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeFloatBuffer(buffer);
        assertEquals(2, buffer.position());

        // Rewind buffer and write values again in little endian
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeFloatBuffer(buffer);
        assertEquals(2, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(16, bytes.length);
        for (int i = 0; i < 2; i++)
        {
            final int check = Float.floatToIntBits(values[i]);

            // Check big endian
            assertEquals((byte) ((check >> 24) & 0xff), bytes[i * 4]);
            assertEquals((byte) ((check >> 16) & 0xff), bytes[i * 4 + 1]);
            assertEquals((byte) ((check >> 8) & 0xff), bytes[i * 4 + 2]);
            assertEquals((byte) (check & 0xff), bytes[i * 4 + 3]);

            // Check little endian
            assertEquals((byte) (check & 0xff), bytes[i * 4 + 8]);
            assertEquals((byte) ((check >> 8) & 0xff), bytes[i * 4 + 1 + 8]);
            assertEquals((byte) ((check >> 16) & 0xff), bytes[i * 4 + 2 + 8]);
            assertEquals((byte) ((check >> 24) & 0xff), bytes[i * 4 + 3 + 8]);
        }
    }


    /**
     * Tests writing a double buffer.
     *
     * @throws IOException
     *             When writing fails
     */

    @Test
    public void testWriteDoubleBuffer() throws IOException
    {
        final double[] values = { 53, 49.1521827361243234 };

        // Prepare the buffer with two values
        final DoubleBuffer buffer = DoubleBuffer.allocate(2);
        buffer.put(values);
        buffer.rewind();

        // Write remaining values to buffer and check buffer position
        this.writer.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.writer.writeDoubleBuffer(buffer);
        assertEquals(2, buffer.position());

        // Rewind buffer and write values again in little endian
        buffer.rewind();
        this.writer.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        this.writer.writeDoubleBuffer(buffer);
        assertEquals(2, buffer.position());

        // Check the written bytes
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(32, bytes.length);
        for (int i = 0; i < 2; i++)
        {
            final long check = Double.doubleToLongBits(values[i]);

            // Check big endian
            assertEquals((byte) ((check >> 56) & 0xff), bytes[i * 8]);
            assertEquals((byte) ((check >> 48) & 0xff), bytes[i * 8 + 1]);
            assertEquals((byte) ((check >> 40) & 0xff), bytes[i * 8 + 2]);
            assertEquals((byte) ((check >> 32) & 0xff), bytes[i * 8 + 3]);
            assertEquals((byte) ((check >> 24) & 0xff), bytes[i * 8 + 4]);
            assertEquals((byte) ((check >> 16) & 0xff), bytes[i * 8 + 5]);
            assertEquals((byte) ((check >> 8) & 0xff), bytes[i * 8 + 6]);
            assertEquals((byte) (check & 0xff), bytes[i * 8 + 7]);

            // Check little endian
            assertEquals((byte) (check & 0xff), bytes[i * 8 + 16]);
            assertEquals((byte) ((check >> 8) & 0xff), bytes[i * 8 + 1 + 16]);
            assertEquals((byte) ((check >> 16) & 0xff), bytes[i * 8 + 2 + 16]);
            assertEquals((byte) ((check >> 24) & 0xff), bytes[i * 8 + 3 + 16]);
            assertEquals((byte) ((check >> 32) & 0xff), bytes[i * 8 + 4 + 16]);
            assertEquals((byte) ((check >> 40) & 0xff), bytes[i * 8 + 5 + 16]);
            assertEquals((byte) ((check >> 48) & 0xff), bytes[i * 8 + 6 + 16]);
            assertEquals((byte) ((check >> 56) & 0xff), bytes[i * 8 + 7 + 16]);
        }
    }


    /**
     * Tests the writeString method.
     *
     * @throws IOException
     *             When write fails
     */

    @Test
    public void testWriteString() throws IOException
    {
        this.writer.writeString("Köln");
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(5, bytes.length);
        assertEquals('K', bytes[0]);
        assertEquals((byte) 0xc3, bytes[1]);
        assertEquals((byte) 0xb6, bytes[2]);
        assertEquals('l', bytes[3]);
        assertEquals('n', bytes[4]);
    }


    /**
     * Tests the writeString method with an ISO encoding.
     *
     * @throws IOException
     *             When write fails
     */

    @Test
    public void testWriteStringISO() throws IOException
    {
        this.writer.writeString("Köln", "ISO-8859-15");
        final byte[] bytes = this.stream.toByteArray();
        assertEquals(4, bytes.length);
        assertEquals('K', bytes[0]);
        assertEquals((byte) 0xf6, bytes[1]);
        assertEquals('l', bytes[2]);
        assertEquals('n', bytes[3]);
    }
}
