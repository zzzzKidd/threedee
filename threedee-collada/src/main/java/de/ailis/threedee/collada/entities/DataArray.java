/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * Base class for data arrays.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class DataArray implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The source id */
    private String id;

    /** The float data */
    private float[] floatData;

    /** The string data */
    private String[] stringData;

    /** The data */
    private Object data;


    /**
     * Constructs a data array with no id
     */

    public DataArray()
    {
        this(null);
    }


    /**
     * Constructs a data array with the given id.
     *
     * @param id
     *            The id
     */

    public DataArray(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the source id.
     *
     * @return The source id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the source id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the data of the array.
     *
     * @return The data
     */

    public Object getData()
    {
        return this.data;
    }


    /**
     * Checks if data is float data.
     *
     * @return True if data is float data, false if not
     */

    public boolean isFloatData()
    {
        return this.floatData != null;
    }


    /**
     * Checks if data is string data.
     *
     * @return True if data is string data, false if not
     */

    public boolean isStringData()
    {
        return this.stringData != null;
    }


    /**
     * Returns the float data of the array.
     *
     * @return The float data
     * @throws IllegalStateException
     *             When data array does not contain float data
     */

    public float[] getFloatData()
    {
        if (this.floatData == null)
            throw new IllegalStateException(
                    "Data array does not contain float data");
        return this.floatData;
    }


    /**
     * Returns the string data of the array.
     *
     * @return The string data
     * @throws IllegalStateException
     *             When data array does not contain string data
     */

    public String[] getStringData()
    {
        if (this.stringData == null)
            throw new IllegalStateException(
                    "Data array does not contain string data");
        return this.stringData;
    }


    /**
     * Sets float data.
     *
     * @param data
     *            The float data to set
     */

    public void setData(final float[] data)
    {
        this.data = data;
        this.floatData = data;
        this.stringData = null;
    }


    /**
     * Sets string data.
     *
     * @param data
     *            The string data to set
     */

    public void setData(final String[] data)
    {
        this.data = data;
        this.floatData = null;
        this.stringData = data;
    }


    /**
     * Returns the number of values in the data array.
     *
     * @return The number of values in the data array
     */

    public int getCount()
    {
        if (this.floatData != null) return this.floatData.length;
        if (this.stringData != null) return this.stringData.length;
        return 0;
    }
}
