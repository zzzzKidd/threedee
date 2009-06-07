/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.math.Vector3d;


/**
 * CustomModel
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class CustomModel implements Model
{
    /** Serial version UID */
    private static final long serialVersionUID = -2144290142774646756L;

    /** The polygons */
    private final List<Polygon> polygons = new ArrayList<Polygon>();

    /** The vertices */
    private final List<Vector3d> vertices = new ArrayList<Vector3d>();

    /** The models default material */
    private final Material material = Material.DEFAULT;


    /**
     * @see de.ailis.threedee.model.Model#countPolygons()
     */

    @Override
    public int countPolygons()
    {
        return this.polygons.size();
    }


    /**
     * @see de.ailis.threedee.model.Model#countVertices()
     */

    @Override
    public int countVertices()
    {
        return this.vertices.size();
    }


    /**
     * @see de.ailis.threedee.model.Model#getMaterial()
     */

    @Override
    public Material getMaterial()
    {
        return this.material;
    }


    /**
     * @see de.ailis.threedee.model.Model#getMaterial(de.ailis.threedee.model.Polygon)
     */

    @Override
    public Material getMaterial(final Polygon polygon)
    {
        final Material material = polygon.getMaterial();
        if (material == null) return this.material;
        return material;
    }


    /**
     * @see de.ailis.threedee.model.Model#getPolygon(int)
     */

    @Override
    public Polygon getPolygon(final int index)
    {
        return this.polygons.get(index);
    }


    /**
     * @see de.ailis.threedee.model.Model#getVertex(int)
     */

    @Override
    public Vector3d getVertex(final int index)
    {
        return this.vertices.get(index);
    }


    /**
     * Adds a new vertex.
     * 
     * @param vertex
     *            The vertex to add
     */

    public void addVertex(final Vector3d vertex)
    {
        this.vertices.add(vertex);
    }


    /**
     * Adds a new polygon.
     * 
     * @param polygon
     *            The polygon to add
     */

    public void addPolygon(final Polygon polygon)
    {
        this.polygons.add(polygon);
    }
}
