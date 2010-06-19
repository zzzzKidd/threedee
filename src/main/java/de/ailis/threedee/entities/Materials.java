/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.entities.support.Library;


/**
 * Materials library.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Materials extends ArrayList<Material> implements
        Library<Material>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The effects index */
    private final Map<String, Material> index;


    /**
     * Constructor
     */

    public Materials()
    {
        this.index = new HashMap<String, Material>();
    }


    /**
     * @see ArrayList#add(Object)
     */

    @Override
    public boolean add(final Material material)
    {
        final String id = material.getId();
        if (id != null)
        {
            if (this.index.containsKey(id))
                throw new RuntimeException(
                        "There is already a effect with id " + id
                                + " in the library");
            this.index.put(id, material);
        }
        return super.add(material);
    }


    /**
     * @see Library#get(String)
     */

    public Material get(final String id)
    {
        return this.index.get(id);
    }


    /**
     * @see Library#contains(String)
     */

    public boolean contains(final String id)
    {
        return this.index.containsKey(id);
    }
}
