/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;



/**
 * A effect technique.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class CommonTechnique extends FxTechnique
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The phong shading */
    private Phong phong;

    /** The blinn shading */
    private Blinn blinn;


    /**
     * Constructs a new technique.
     *
     * @param scopedId
     *            The scoped ID
     */

    public CommonTechnique(final String scopedId)
    {
        super(scopedId);
    }


    /**
     * Returns the phong shading.
     *
     * @return The phong shading or null if unspecified.
     */

    public Phong getPhong()
    {
        return this.phong;
    }


    /**
     * Sets the phong shading.
     *
     * @param phong
     *            The phong shading to set. Null for unspecified.
     */

    public void setPhong(final Phong phong)
    {
        this.phong = phong;
    }


    /**
     * Returns the blinn shading.
     *
     * @return The blinn shading or null if not specified
     */

    public Blinn getBlinn()
    {
        return this.blinn;
    }


    /**
     * Sets the blinn shading.
     *
     * @param blinn
     *            The blinn shading to set. Null for unspecified
     */

    public void setBlinn(final Blinn blinn)
    {
        this.blinn = blinn;
    }
}
