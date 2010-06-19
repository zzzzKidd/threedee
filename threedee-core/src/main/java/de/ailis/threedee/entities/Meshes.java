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
 * Geometries library.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Meshes extends ArrayList<Mesh> implements
        Library<Mesh>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The geometries index */
    private final Map<String, Mesh> index;


    /**
     * Constructor
     */

    public Meshes()
    {
        this.index = new HashMap<String, Mesh>();
    }


    /**
     * @see ArrayList#add(Object)
     */

    @Override
    public boolean add(final Mesh mesh)
    {
        final String id = mesh.getId();
        if (id != null)
        {
            if (this.index.containsKey(id))
                throw new RuntimeException(
                        "There is already a mesh with id " + id
                                + " in the library");
            this.index.put(id, mesh);
        }
        return super.add(mesh);
    }


    /**
     * @see Library#get(String)
     */

    public Mesh get(final String id)
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
