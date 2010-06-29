/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling;

import java.util.Map;
import java.util.TreeMap;


/**
 * A sampler
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The output data type
 */

public class Sampler<T>
{
    /** The sampler data */
    public TreeMap<Float, SamplerValue<T>> data = new TreeMap<Float, SamplerValue<T>>();

    /** The minimum input value */
    private float minInput = Float.POSITIVE_INFINITY;

    /** The maximum input value */
    private float maxInput = Float.NEGATIVE_INFINITY;


    /**
     * Adds a sample.
     *
     * @param input
     *            The input value
     * @param value
     *            The sample value
     */

    public void addSample(final float input, final SamplerValue<T> value)
    {
        this.data.put(input, value);
        this.minInput = Math.min(input, this.minInput);
        this.maxInput = Math.max(input, this.maxInput);
    }


    /**
     * Returns the sample for the specified input.
     *
     * @param input
     *            The input
     * @return The interpolated output value or null if no values are available
     */

    public T getSample(final float input)
    {
        // Return null if no data is available
        if (this.data.isEmpty()) return null;

        // If data only contains one value then return this one
        if (this.data.size() == 1) return this.data.firstEntry().getValue().getValue();

        // Trim input value
        float trimmedInput = this.minInput + (input - this.minInput) % (this.maxInput - this.minInput);
        if (trimmedInput < this.minInput) trimmedInput += this.minInput;

        // Get the floor entry
        final Map.Entry<Float, SamplerValue<T>> floor = this.data.floorEntry(trimmedInput);
        final SamplerValue<T> floorValue = floor.getValue();
        final float inputA = floor.getKey();
        final T a = floorValue.getValue();
        final Interpolation interpolation = floorValue.getInterpolation();

        // When interpolation is STEP then we don't interpolate at all. Simply
        // return the current sample
        if (interpolation == Interpolation.STEP) return a;

        // Get the next entry. If not found then use the first value
        Map.Entry<Float, SamplerValue<T>> next = this.data.higherEntry(floor.getKey());
        if (next == null) next = this.data.firstEntry();
        final SamplerValue<T> nextValue = next.getValue();
        final float inputB = next.getKey();
        final T b = nextValue.getValue();

        // Calculate the interpolation position
        final float pos = (trimmedInput - inputA) / (inputB - inputA);

        return InterpolatorFactory
            .getInstance()
            .getInterpolator(getValueClass(a), interpolation)
            .interpolate(a, b, pos);
    }


    /**
     * Returns the class of the specified value. This is extracted into a method
     * so we only need the SuppressWarning annotation at this single place. To
     * bad Java doesn't support "T.class"...
     *
     * @param value
     *            The value
     * @return The value class
     */

    @SuppressWarnings("unchecked")
    private Class<T> getValueClass(final T value)
    {
        return (Class<T>) value.getClass();
    }
}
