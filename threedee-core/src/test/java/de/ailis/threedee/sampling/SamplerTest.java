/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.sampling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Tests the sampler class.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SamplerTest
{
    /**
     * Tests linear float sampling
     */

    @Test
    public void testLinearFloatSampling()
    {
        final Sampler<Float> sampler = new Sampler<Float>();
        sampler
            .addSample(0f, new SamplerValue<Float>(0f, Interpolation.LINEAR));
        sampler
            .addSample(5f, new SamplerValue<Float>(50f, Interpolation.LINEAR));
        sampler
            .addSample(10f, new SamplerValue<Float>(100f, Interpolation.LINEAR));
        sampler
            .addSample(15f, new SamplerValue<Float>(150f, Interpolation.LINEAR));
        sampler
            .addSample(20f, new SamplerValue<Float>(200f, Interpolation.LINEAR));

        assertEquals(175f, sampler.getSample(-2.5f), 0.01f);
        assertEquals(0, sampler.getSample(0f), 0.01f);
        assertEquals(25f, sampler.getSample(2.5f), 0.01f);
        assertEquals(50f, sampler.getSample(5f), 0.01f);
        assertEquals(75f, sampler.getSample(7.5f), 0.01f);
        assertEquals(100f, sampler.getSample(10f), 0.01f);
        assertEquals(125f, sampler.getSample(12.5f), 0.01f);
        assertEquals(150f, sampler.getSample(15f), 0.01f);
        assertEquals(175f, sampler.getSample(17.5f), 0.01f);
        assertEquals(0, sampler.getSample(20f), 0.01f);
        assertEquals(25f, sampler.getSample(22.5f), 0.01f);
    }
}
