/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import java.nio.FloatBuffer;

import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A color using a float buffer as storage.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Color
{
    /** Color constant for red */
    public final static Color RED = new Color(1, 0, 0);

    /** Color constant for green */
    public final static Color GREEN = new Color(0, 1, 0);

    /** Color constant for blue */
    public final static Color BLUE = new Color(0, 0, 1);

    /** Color constant for white */
    public final static Color WHITE = new Color(1, 1, 1);

    /** Color constant for black */
    public final static Color BLACK = new Color(0, 0, 0);

    /** Color constant for yellow */
    public final static Color YELLOW = new Color(1, 1, 0);

    /** The color buffer */
    private final FloatBuffer buffer;


    /**
     * Constructs a new solid color.
     *
     * @param red
     *            The red component
     * @param blue
     *            The blue component
     * @param green
     *            The green component
     */

    public Color(final float red, final float green, final float blue)
    {
        this(red, green, blue, 1);
    }


    /**
     * Constructs a new alpha-transparent color.
     *
     * @param red
     *            The red component
     * @param blue
     *            The blue component
     * @param green
     *            The green component
     * @param alpha
     *            The alpha component
     */

    public Color(final float red, final float green, final float blue,
            final float alpha)
    {
        this.buffer = BufferUtils.createDirectFloatBuffer(4);
        this.buffer.put(red);
        this.buffer.put(green);
        this.buffer.put(blue);
        this.buffer.put(alpha);
        this.buffer.position(0);
    }


    /**
     * Applies the color to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void apply(final GL gl)
    {
        gl.glColorPointer(4, 0, this.buffer);
    }


    /**
     * Returns the red color component.
     *
     * @return The red color component
     */

    public float getRed()
    {
        return this.buffer.get(0);
    }


    /**
     * Returns the red color component.
     *
     * @return The red color component
     */

    public float getGreen()
    {
        return this.buffer.get(1);
    }

    /**
     * Returns the red color component.
     *
     * @return The red color component
     */

    public float getBlue()
    {
        return this.buffer.get(2);
    }


    /**
     * Returns the alpha color component.
     *
     * @return The alpha color component
     */

    public float getAlpha()
    {
        return this.buffer.get(3);
    }


    /**
     * Returns the buffer.
     *
     * @return The buffer
     */

    public FloatBuffer getBuffer()
    {
        return this.buffer;
    }


    /**
     * Applies the specified alpha value to the color and returns the new color.
     * Alpha values are additive. If specified alpha value is 1 then the color
     * is returned unchanged.
     *
     * @param alpha
     *            The alpha value
     * @return The color with the alpha value applied
     */

    public Color applyAlpha(final float alpha)
    {
        if (alpha == 1) return this;
        return new Color(getRed(), getGreen(), getBlue(), getAlpha() * alpha);
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.buffer == null) ? 0 : this.buffer.hashCode());
        return result;
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Color other = (Color) obj;
        if (this.buffer == null)
        {
            if (other.buffer != null) return false;
        }
        else if (!this.buffer.equals(other.buffer)) return false;
        return true;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "Color [red=" + getRed() + ", green=" + getGreen() + ", blue="
                + getBlue() + ", alpha=" + getAlpha() + "]";
    }
}
