/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.collada.support;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.ailis.threedee.collada.support.ChunkFloatReader;


/**
 * Tests the ChunkFloatReader class.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ChunkFloatReaderTest
{
    /**
     * Tests the chunk float reader
     */

    @Test
    public void testChunkFloatReader()
    {
        final List<Float> values = new ArrayList<Float>();

        final ChunkFloatReader reader = new ChunkFloatReader()
        {
            @Override
            protected void valueFound(final float value)
            {
                values.add(value);
            }
        };

        reader.addChunk("  123.45    99.");
        reader.addChunk("12\n1\r2\t3    -4.56   5");
        reader.finish();

        assertEquals(7, values.size());

        assertEquals(123.45, values.get(0), 0.01);
        assertEquals(99.12, values.get(1), 0.01);
        assertEquals(1, values.get(2), 0.01);
        assertEquals(2, values.get(3), 0.01);
        assertEquals(3, values.get(4), 0.01);
        assertEquals(-4.56, values.get(5), 0.01);
        assertEquals(5, values.get(6), 0.01);
    }

}
