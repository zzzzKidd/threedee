/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;


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
}