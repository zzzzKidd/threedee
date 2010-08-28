/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * Accessor parameter.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Param
{
    /** The parameter type */
    private ParamType type;


    /**
     * Constructor.
     *
     * @param type
     *            The parameter type
     */

    public Param(final ParamType type)
    {
        setType(type);
    }


    /**
     * Returns the type.
     *
     * @return The type
     */

    public ParamType getType()
    {
        return this.type;
    }


    /**
     * Sets the type.
     *
     * @param type
     *            The type to set
     */

    public void setType(final ParamType type)
    {
        if (type == null)
            throw new IllegalArgumentException("type must not be null");
        this.type = type;
    }
}
