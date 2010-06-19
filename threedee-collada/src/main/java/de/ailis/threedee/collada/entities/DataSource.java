/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A source
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class DataSource implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The source id */
    private String id;

    /** The data array */
    private DataArray data;


    /**
     * Constructs a source with the given id.
     *
     * @param id
     *            The id
     */

    public DataSource(final String id)
    {
        setId(id);
    }


    /**
     * Returns the source id.
     *
     * @return The source id. Never null
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the source id
     *
     * @param id
     *            The id to set. Must not be null
     */

    public void setId(final String id)
    {
        if (id == null) throw new IllegalArgumentException("id must be set");
        this.id = id;
    }


    /**
     * Sets the data array.
     *
     * @param data
     *            The data array to set
     */

    public void setData(final DataArray data)
    {
        this.data = data;
    }


    /**
     * Returns the data array.
     *
     * @return The data array
     */

    public DataArray getData()
    {
        return this.data;
    }
}
