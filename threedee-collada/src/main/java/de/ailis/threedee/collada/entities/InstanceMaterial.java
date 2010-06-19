/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Instance material
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class InstanceMaterial implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The material symbol */
    private String symbol;

    /** The URL of the targeted material */
    private URI target;


    /**
     * Constructs a new instance material.
     *
     * @param symbol
     *            The material symbol
     * @param target
     *            The URL of the targeted material
     */

    public InstanceMaterial(final String symbol, final URI target)
    {
        setSymbol(symbol);
        setTarget(target);
    }


    /**
     * Sets the url of the targeted material.
     *
     * @param target
     *            The url to set. Must not be null
     */

    public void setTarget(final URI target)
    {
        if (target == null)
            throw new IllegalArgumentException("target must be set");
        this.target = target;
    }


    /**
     * Returns the URL of the targeted material.
     *
     * @return The URL. Never null
     */

    public URI getTarget()
    {
        return this.target;
    }


    /**
     * Sets the material symbol.
     *
     * @param symbol
     *            The material symbol to set. Must not be null.
     */

    public void setSymbol(final String symbol)
    {
        if (symbol == null)
            throw new IllegalArgumentException("symbol must be set");
        this.symbol = symbol;
    }


    /**
     * Returns the material symbol.
     *
     * @return The material symbol. Never null.
     */

    public String getSymbol()
    {
        return this.symbol;
    }
}
