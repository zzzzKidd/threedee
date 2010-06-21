/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * A light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaSpotLight extends ColladaLight
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The fall-off angle */
    private Float falloffAngle;


    /**
     * Constructs a point light without an id.
     */

    public ColladaSpotLight()
    {
        this(null);
    }


    /**
     * Constructs a point light with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaSpotLight(final String id)
    {
        super(id);
    }


    /**
     * Returns the falloff angle. May return null if not specified (in this
     * case 180 degrees should be used.
     *
     * @return The falloff angle in degree. May be null.
     */

    public Float getFalloffAngle()
    {
        return this.falloffAngle;
    }


    /**
     * Sets the falloff angle. Set to null to remove it (The default of
     * 180 degrees is used then).
     *
     * @param falloffAngle
     *            The falloff angle to set. Set to null to remove
     */

    public void setFalloffAngle(final Float falloffAngle)
    {
        this.falloffAngle = falloffAngle;
    }
}
