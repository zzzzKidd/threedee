/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.support;


/**
 * Interface for identifiable objects.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The identifiable type
 */

public interface Identifiable<T extends Identifiable<T>>
{
    /**
     * Returns the id
     *
     * @return The id
     */

    public String getId();


    /**
     * Sets the id
     *
     * @param id
     *            The id to set
     */

    public void setId(String id);


    /**
     * Adds id change listener.
     *
     * @param listener
     *            The listener to add
     */

    public void addIdChangeListener(IdChangeListener<T> listener);


    /**
     * Removes id change listener.
     *
     * @param listener
     *            The listener to remove
     */

    public void removeIdChangeListener(IdChangeListener<T> listener);
}
