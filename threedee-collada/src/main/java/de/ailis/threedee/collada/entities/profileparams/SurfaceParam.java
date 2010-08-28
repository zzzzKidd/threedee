/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities.profileparams;

import de.ailis.threedee.collada.entities.ProfileParam;


/**
 * Sampler2D profile parameter
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SurfaceParam extends ProfileParam
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The image id */
    private String imageId;


    /**
     * Constructor
     *
     * @param id
     *            The parameter id
     */

    public SurfaceParam(final String id)
    {
        super(id);
    }


    /**
     * Returns the image id.
     *
     * @return The image id
     */

    public String getImageId()
    {
        return this.imageId;
    }


    /**
     * Sets the image id.
     *
     * @param imageId
     *            The image id to set
     */

    public void setImageId(final String imageId)
    {
        this.imageId = imageId;
    }
}
