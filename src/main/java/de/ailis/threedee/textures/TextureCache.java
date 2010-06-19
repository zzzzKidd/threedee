/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.textures;

import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.io.resources.ResourceProvider;


/**
 * Cache for textures.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TextureCache
{
    /** Cache of loaded textures */
    private final Map<String, Texture> textures = new HashMap<String, Texture>();

    /** The resource provider to use for loading textures */
    private ResourceProvider resourceProvider = new ClasspathResourceProvider();

    /** The singleton instance */
    private static final TextureCache instance = new TextureCache();


    /**
     * Private constructor to prevent instantiation.
     */

    private TextureCache()
    {
        // Empty
    }


    /**
     * Returns the singleton instance of the texture cache.
     *
     * @return The texture cache instance
     */

    public static TextureCache getInstance()
    {
        return instance;
    }


    /**
     * Sets the resource provider to use for reading textures.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public void setResourceProvider(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Returns the texture with the specified filename. It is returned from
     * the cache if possible. Otherwise a new texture object is created. The
     * texture will be loaded when it is first rendered.
     *
     * @param filename
     *            The texture file name
     * @return The texture
     */

    public Texture getTexture(final String filename)
    {
        Texture texture = this.textures.get(filename);
        if (texture == null)
        {
            texture = new Texture(filename, this.resourceProvider);
            this.textures.put(filename, texture);
        }
        return texture;
    }
}
