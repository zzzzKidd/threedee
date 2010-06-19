/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * Base class for primitives.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Primitives implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The inputs */
    private final SharedInputs inputs = new SharedInputs();

    /** The material name */
    private String material;


    /**
     * Returns the inputs.
     *
     * @return The inputs
     */

    public SharedInputs getInputs()
    {
        return this.inputs;
    }


    /**
     * Returns the material.
     *
     * @return The material
     */

    public String getMaterial()
    {
        return this.material;
    }


    /**
     * Sets the material.
     *
     * @param material
     *            The material to set
     */

    public void setMaterial(final String material)
    {
        this.material = material;
    }


    /**
     * Returns the number of primitives.
     *
     * @return The number of primitives
     */

    public abstract int getCount();


    /**
     * Returns the primitives type.
     *
     * @return The primitives type
     */

    public abstract PrimitivesType getPrimitivesType();
}
