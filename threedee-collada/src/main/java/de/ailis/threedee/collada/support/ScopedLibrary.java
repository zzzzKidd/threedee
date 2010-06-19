/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.support;

import java.util.ArrayList;


/**
 * A library
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The library type
 */

public class ScopedLibrary<T extends ScopedIdentifiable> extends ArrayList<T>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Returns the entry with the specified id. May return null if
     * entry was not found.
     *
     * @param id
     *            The id
     * @return The entry or null if not found
     */

    public T get(final String id)
    {
        for (final T entry : this)
        {
            if (id.equals(entry.getId())) return entry;
            if (id.equals(entry.getScopedId())) return entry;
        }
        return null;
    }


    /**
     * Checks if a geometry with the specified id exists.
     *
     * @param id
     *            The geometry id to look for
     * @return True if geometry exists, false if not
     */

    public boolean contains(final String id)
    {
        for (final T entry : this)
        {
            if (id.equals(entry.getId())) return true;
            if (id.equals(entry.getScopedId())) return true;
        }
        return false;
    }
}
