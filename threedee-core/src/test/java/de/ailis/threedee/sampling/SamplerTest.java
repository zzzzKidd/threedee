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
            .addSample(1f, new SamplerValue<Float>(5f, Interpolation.LINEAR));
        sampler
            .addSample(2f, new SamplerValue<Float>(10f, Interpolation.LINEAR));
        sampler
            .addSample(3f, new SamplerValue<Float>(15f, Interpolation.LINEAR));
        sampler
            .addSample(4f, new SamplerValue<Float>(5f, Interpolation.LINEAR));

        assertEquals(Float.valueOf(5f), sampler.getSample(0f));
        assertEquals(Float.valueOf(5f), sampler.getSample(1f));
        assertEquals(Float.valueOf(7.5f), sampler.getSample(1.5f));
        assertEquals(Float.valueOf(10f), sampler.getSample(2f));
        assertEquals(Float.valueOf(15f), sampler.getSample(3f));
        assertEquals(Float.valueOf(5f), sampler.getSample(4f));
    }
}
