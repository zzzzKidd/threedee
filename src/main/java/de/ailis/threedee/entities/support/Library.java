/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities.support;

import java.util.List;


/**
 * Library interface
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T> The library type
 */

public interface Library<T> extends List<T>
{
    /**
     * Returns the geometry with the specified id. May return null if
     * geometry was not found.
     *
     * @param id
     *            The geometry id
     * @return The geometry or null if not found
     */

    public T get(String id);


    /**
     * Checks if a geometry with the specified id exists.
     *
     * @param id
     *            The geometry id to look for
     * @return True if geometry exists, false if not
     */

    public boolean contains(String id);
}
