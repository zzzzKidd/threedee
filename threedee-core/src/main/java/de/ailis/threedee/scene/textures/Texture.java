/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;



/**
 * Texture
 *
 * @author k
 */

public class Texture
{
    /** The texture filename */
    public String filename;


    /**
     * Creates a new texture.
     *
     * @param filename
     *            The texture filename
     */

    public Texture(final String filename)
    {
        this.filename = filename;
    }


    /**
     * Returns the filename of the texture.
     *
     * @return The texture filename
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
        return this.filename;
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
                + ((this.filename == null) ? 0 : this.filename.hashCode());
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
        final Texture other = (Texture) obj;
        if (this.filename == null)
        {
            if (other.filename != null) return false;
        }
        else if (!this.filename.equals(other.filename)) return false;
        return true;
    }
}
