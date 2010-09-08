/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;


/**
 * AssetFormat
 *
 * @author k
 */

public enum AssetFormat
{
    /** JPEG format. */
    JPEG(new String[] { ".jpeg", ".jpg" }),

    /** PNG format. */
    PNG(".png"),

    /** TDA format. */
    TDA(".tda"),

    /** TDB format. */
    TDB(".tdb"),

    /** COLLADA format. */
    DAE(".dae"),

    /** Wavefront MTL format. */
    MTL(".mtl");

    /** The formats extension. */
    private String[] extensions;


    /**
     * Constructor.
     *
     * @param extension
     *            The file extension.
     */

    private AssetFormat(final String extension)
    {
        this(new String[] { extension });
    }


    /**
     * Constructor.
     *
     * @param extensions
     *            The file extensions.
     */

    private AssetFormat(final String[] extensions)
    {
        this.extensions = extensions;
    }


    /**
     * Returns the default extension for the format.
     *
     * @return The default file extension.
     */

    public String getExtension()
    {
        return this.extensions[0];
    }


    /**
     * Returns all known extensions for the format.
     *
     * @return All known extensions for the format.
     */

    public String[] getExtensions()
    {
        return this.extensions;
    }


    /**
     * Returns the asset format for the specified filename.
     *
     * @param filename
     *            The filename.
     * @return The asset format.
     * @throws IllegalArgumentException
     *             When file has an unknown format.
     */

    public static AssetFormat valueOfFilename(final String filename)
    {
        final String name = filename.toLowerCase();
        for (final AssetFormat format : values())
        {
            for (final String extension : format.getExtensions())
            {
                if (name.endsWith(extension)) return format;
            }
        }
        throw new IllegalArgumentException("Unknown asset file format: "
            + filename);
    }
}
