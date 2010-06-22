/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.math.Bounds;


/**
 * A mesh.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Mesh
{
    /** The mesh polygons */
    private final MeshPolygons[] polygons;

    /** The material mapping */
    private final String[] materials;

    /** The bounds */
    private final Bounds bounds;


    /**
     * Constructor
     *
     * @param polygons
     *            The mesh polygons
     * @param materials
     *            The material mapping
     */

    public Mesh(final MeshPolygons[] polygons, final String[] materials)
    {
        this.polygons = polygons;
        this.materials = materials;

        // Create bounds
        this.bounds = new Bounds();
        for (final MeshPolygons p : polygons)
            this.bounds.update(p.getBounds());
    }


    /**
     * Returns the mesh polygons.
     *
     * @return The mesg polygons
     */

    public MeshPolygons[] getPolygons()
    {
        return this.polygons;
    }


    /**
     * Returns the material mapping.
     *
     * @return The material mapping
     */

    public String[] getMaterials()
    {
        return this.materials;
    }



    /**
     * Returns the bounds of the model.
     *
     * @return The model bounds
     */

    public Bounds getBounds()
    {
        return this.bounds;
    }
}