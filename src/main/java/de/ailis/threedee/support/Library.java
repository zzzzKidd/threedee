/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A library
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The library type
 */

public class Library<T extends Identifiable<T>> extends ArrayList<T> implements
        IdChangeListener<T>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The effects index */
    private final Map<String, T> index = new HashMap<String, T>();


    /**
     * Returns the geometry with the specified id. May return null if
     * geometry was not found.
     *
     * @param id
     *            The geometry id
     * @return The geometry or null if not found
     */

    public T get(final String id)
    {
        return this.index.get(id);
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
        return this.index.containsKey(id);
    }


    /**
     * @see ArrayList#add(Object)
     */

    @Override
    public boolean add(final T entry)
    {
        entry.addIdChangeListener(this);
        addToIndex(entry);
        return super.add(entry);
    }




    /**
     * @see de.ailis.threedee.support.IdChangeListener#idChanged(de.ailis.threedee.support.Identifiable,
     *      java.lang.String)
     */

    @Override
    public void idChanged(final T identifiable, final String oldId)
    {
        if (oldId != null) this.index.remove(oldId);
        addToIndex(identifiable);
    }


    /**
     * Adds entry to index
     *
     * @param entry
     *            The entry to add
     */

    private void addToIndex(final T entry)
    {
        final String id = entry.getId();
        if (id != null)
        {
            if (this.index.containsKey(id))
                throw new RuntimeException("There is already an entry with id "
                        + id + " in the index");
            this.index.put(id, entry);
        }
    }


    /**
     * @see java.util.ArrayList#remove(java.lang.Object)
     */

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(final Object o)
    {
        final T entry = (T) o;
        final boolean removed = super.remove(entry);
        if (removed)
        {
            entry.removeIdChangeListener(this);
            this.index.remove(entry.getId());
        }
        return removed;
    }


    /**
     * @see java.util.ArrayList#remove(int)
     */

    @Override
    public T remove(final int index)
    {
        final T entry = get(index);
        if (entry != null)
        {
            entry.removeIdChangeListener(this);
            this.index.remove(entry.getId());
        }
        return entry;
    }
}
