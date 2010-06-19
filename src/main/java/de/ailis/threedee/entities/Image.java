/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;



/**
 * A image.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Image
{
    /** Image ID */
    private final String id;

    /** The filename */
    private String filename;


    /**
     * Constructor
     *
     * @param id
     *            The id
     */

    public Image(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the id.
     *
     * @return The id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the filename.
     *
     * @param filename
     *            The filename to set
     */

    public void setFilename(final String filename)
    {
        this.filename = filename;
    }


    /**
     * Returns the filename.
     *
     * @return The filename
     */

    public String getFilename()
    {
        return this.filename;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.id);
        builder.append('=');
        builder.append(this.filename);
        return builder.toString();
    }
}
