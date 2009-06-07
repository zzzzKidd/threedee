/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.math.Vector3d;


/**
 * A cube model.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Cube implements Model
{
    /** Serial version UID */
    private static final long serialVersionUID = 9047361878812248430L;

    /** The vertices */
    private final Vector3d[] vertices;

    /** The polygons */
    private final Polygon[] polygons;

    /** The material */
    private Material material = Material.DEFAULT;


    /**
     * Constructs a new cube with the specified sizes.
     * 
     * @param xRadius
     *            The X radius
     * @param yRadius
     *            The Y radius
     * @param zRadius
     *            The Z radius
     */

    public Cube(final double xRadius, final double yRadius,
        final double zRadius)
    {
        this.vertices =
            new Vector3d[] { new Vector3d(-xRadius, yRadius, zRadius),
                new Vector3d(-xRadius, yRadius, -zRadius),
                new Vector3d(xRadius, yRadius, -zRadius),
                new Vector3d(xRadius, yRadius, zRadius),
                new Vector3d(-xRadius, -yRadius, zRadius),
                new Vector3d(-xRadius, -yRadius, -zRadius),
                new Vector3d(xRadius, -yRadius, -zRadius),
                new Vector3d(xRadius, -yRadius, zRadius) };
        this.polygons =
            new Polygon[] { new Polygon(0, 1, 2, 3), new Polygon(7, 3, 2, 6),
                new Polygon(4, 7, 6, 5), new Polygon(1, 0, 4, 5),
                new Polygon(0, 3, 7, 4), new Polygon(6, 2, 1, 5) };
    }


    /**
     * Constructs a new cube with the specified size.
     * 
     * @param radius
     *            The radius
     */

    public Cube(final double radius)
    {
        this(radius, radius, radius);
    }


    /**
     * @see de.ailis.threedee.model.Model#countPolygons()
     */

    @Override
    public int countPolygons()
    {
        return this.polygons.length;
    }


    /**
     * @see de.ailis.threedee.model.Model#countVertices()
     */

    @Override
    public int countVertices()
    {
        return this.vertices.length;
    }


    /**
     * @see de.ailis.threedee.model.Model#getPolygon(int)
     */

    @Override
    public Polygon getPolygon(final int index)
    {
        return this.polygons[index];
    }


    /**
     * @see de.ailis.threedee.model.Model#getVertex(int)
     */

    @Override
    public Vector3d getVertex(final int index)
    {
        return this.vertices[index];
    }


    /**
     * Sets the material.
     * 
     * @param material
     *            The material to set
     */

    public void setMaterial(final Material material)
    {
        if (material == null)
            throw new IllegalArgumentException("material must not be null");
        this.material = material;
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
     * @see Model#getMaterial(Polygon)
     */

    @Override
    public Material getMaterial(final Polygon polygon)
    {
        final Material material = polygon.getMaterial();
        if (material != null) return material;
        return this.material;
    }
}
