/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * A color attribute. Can be a RGBA color or a texture reference.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class FloatOrTexture implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The color */
    private ColladaColor color;

    /** The texture */
    private Texture texture;


    /**
     * Constructs a new color attribute using a color.
     *
     * @param color
     *            The color
     */

    public FloatOrTexture(final ColladaColor color)
    {
        setColor(color);
    }


    /**
     * Constructs a new color attribute using a texture.
     *
     * @param texture
     *            The texture to set
     */

    public FloatOrTexture(final Texture texture)
    {
        setTexture(texture);
    }


    /**
     * Sets the color. This removes the texture if present.
     *
     * @param color
     *            The color to set. Must not be null
     */

    public void setColor(final ColladaColor color)
    {
        if (color == null)
            throw new IllegalArgumentException("color must be set");
        this.color = color;
        this.texture = null;
    }


    /**
     * Returns the color. May return null if no color is set (In this case
     * a texture must be present).
     *
     * @return The color or null if no color is set.
     */

    public ColladaColor getColor()
    {
        return this.color;
    }


    /**
     * Sets the texture. This removes the color if present.
     *
     * @param texture
     *            The texture to set. Must not be null.
     */

    public void setTexture(final Texture texture)
    {
        if (texture == null)
            throw new IllegalArgumentException("texture must be set");
        this.texture = texture;
        this.color = null;
    }


    /**
     * Returns the texture. May return null if no texture is set (In this
     * case a color must be present instead).
     *
     * @return The texture or null if not set
     */

    public Texture getTexture()
    {
        return this.texture;
    }


    /**
     * Checks if this color attribute is a color.
     *
     * @return True if color attribute is a color, false if not
     */

    public boolean isColor()
    {
        return this.color != null;
    }


    /**
     * Checks if this color attribute is a texture.
     *
     * @return True if color attribute is a texture, false if not
     */

    public boolean isTexture()
    {
        return this.texture != null;
    }
}
