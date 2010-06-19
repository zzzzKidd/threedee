/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
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

public class StreamReaderTest
{
    /** The input stream used during tests */
    private ByteArrayInputStream stream;

    /** The tested stream reader */
    private StreamReader reader;

    /** The bytes */
    private final byte[] bytes = new byte[64];


    /**
     * Setup a test.
     */

    @Before
    public void setUp()
    {
        this.stream = new ByteArrayInputStream(this.bytes);
        this.reader = new StreamReader(this.stream);
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
        this.reader.close();
    }


    /**
     * Use the specified bytes in the test.
     *
     * @param bytes
     *            The bytes to use
     */

    private void useBytes(final long... bytes)
    {
        for (int i = 0, max = bytes.length; i < max; i++)
        {
            this.bytes[i] = (byte) (bytes[i] & 0xff);
        }
    }


    /**
     * Tests reading single bytes.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadByte() throws IOException
    {
        useBytes(0, 255);
        assertEquals(0, this.reader.readByte());
        assertEquals((byte) 255, (byte) this.reader.readByte());
    }


    /**
     * Tests reading short values.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadShort() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xdc, 0xfe);
        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        assertEquals((short) 0xfedc, (short) this.reader.readShort());
        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        assertEquals((short) 0xfedc, (short) this.reader.readShort());
    }


    /**
     * Tests reading int values.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadInt() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xba, 0x98, 0x98, 0xba, 0xdc, 0xfe);
        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        assertEquals(0xfedcba98, this.reader.readInt());
        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        assertEquals(0xfedcba98, this.reader.readInt());
    }


    /**
     * Tests reading long values.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadLong() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10, 0x10, 0x32,
                0x54, 0x76, 0x98, 0xba, 0xdc, 0xfe);
        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        assertEquals(0xfedcba9876543210L, this.reader.readLong());
        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        assertEquals(0xfedcba9876543210L, this.reader.readLong());
    }


    /**
     * Tests reading a byte buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadByteBuffer() throws IOException
    {
        useBytes(0, 255);

        final ByteBuffer buffer = this.reader.readByteBuffer(2);
        assertEquals(0, buffer.position());
        assertEquals(2, buffer.remaining());
        assertEquals(0, buffer.get());
        assertEquals((byte) 255, buffer.get());
    }


    /**
     * Tests reading a short buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadShortBuffer() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xdc, 0xfe);

        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        ShortBuffer buffer = this.reader.readShortBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals((short) 0xfedc, buffer.get());

        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        buffer = this.reader.readShortBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals((short) 0xfedc, buffer.get());
    }


    /**
     * Tests reading a int buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadIntBuffer() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xba, 0x98, 0x98, 0xba, 0xdc, 0xfe);

        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        IntBuffer buffer = this.reader.readIntBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(0xfedcba98, buffer.get());

        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        buffer = this.reader.readIntBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(0xfedcba98, buffer.get());
    }


    /**
     * Tests reading a long buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadLongBuffer() throws IOException
    {
        useBytes(0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10, 0x10, 0x32,
                0x54, 0x76, 0x98, 0xba, 0xdc, 0xfe);

        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        LongBuffer buffer = this.reader.readLongBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(0xfedcba9876543210L, buffer.get());

        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        buffer = this.reader.readLongBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(0xfedcba9876543210L, buffer.get());
    }


    /**
     * Tests reading a float buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadFloat() throws IOException
    {
        final int bits = Float.floatToIntBits(49.152f);
        useBytes(bits >> 24, bits >> 16, bits >> 8, bits, bits, bits >> 8,
                bits >> 16, bits >> 24);

        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        FloatBuffer buffer = this.reader.readFloatBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(49.152f, buffer.get(), 0.001);

        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        buffer = this.reader.readFloatBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(49.152f, buffer.get(), 0.001);
    }


    /**
     * Tests reading a double buffer.
     *
     * @throws IOException
     *             When read fails
     */

    @Test
    public void testReadDouble() throws IOException
    {
        final long bits = Double.doubleToLongBits(49.1521827367812f);
        useBytes(bits >> 56, bits >> 48, bits >> 40, bits >> 32, bits >> 24,
                bits >> 16, bits >> 8, bits, bits, bits >> 8, bits >> 16,
                bits >> 24, bits >> 32, bits >> 40, bits >> 48, bits >> 56);

        this.reader.setByteOrder(ByteOrder.BIG_ENDIAN);
        DoubleBuffer buffer = this.reader.readDoubleBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(49.1521827367812f, buffer.get(), 0.000001);

        this.reader.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        buffer = this.reader.readDoubleBuffer(1);
        assertEquals(0, buffer.position());
        assertEquals(1, buffer.remaining());
        assertEquals(49.1521827367812f, buffer.get(), 0.000001);
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
        useBytes('K', 0xc3, 0xb6, 'l', 'n');

        assertEquals("Köln", this.reader.readString(5));
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
        useBytes('K', 0xf6, 'l', 'n');

        assertEquals("Köln", this.reader.readString(4, "ISO-8859-15"));
    }
}
