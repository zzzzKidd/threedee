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

public class Texture implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The texture parameter name */
    private String texture;

    /** Semantic token for the texture coordinates to use */
    private String texcoord;


    /**
     * Constructs a new texture.
     *
     * @param texture
     *            The texture parameter name
     * @param texcoord
     *            Semantic token for the texture coordinates to use
     */

    public Texture(final String texture, final String texcoord)
    {
        setTexture(texture);
        setTexcoord(texcoord);
    }


    /**
     * Returns the texture parameter name.
     *
     * @return The texture parameter name. Never null.
     */

    public String getTexture()
    {
        return this.texture;
    }


    /**
     * Sets the texture parameter name.
     *
     * @param texture
     *            The texture parameter name to set. Must not be null.
     */

    public void setTexture(final String texture)
    {
        if (texture == null)
            throw new IllegalArgumentException("texture must be set");
        this.texture = texture;
    }


    /**
     * Returns the semantic token for the texture coordinates to use.
     *
     * @return The semantic token. Never null.
     */

    public String getTexcoord()
    {
        return this.texcoord;
    }


    /**
     * Sets the semantic token for the texture coordinates to use.
     *
     * @param texcoord
     *            The semantic token to set. Must not be null.
     */

    public void setTexcoord(final String texcoord)
    {
        if (texcoord == null)
            throw new IllegalArgumentException("texcoord must be set");
        this.texcoord = texcoord;
    }
}
