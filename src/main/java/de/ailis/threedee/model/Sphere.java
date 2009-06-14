/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.math.Vector3d;


/**
 * A sphere model.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Sphere implements Model
{
    /** Serial version UID */
    private static final long serialVersionUID = 8061334382115220679L;

    /** The vertices */
    private Vector3d[] vertices;

    /** The polygons */
    private Polygon[] polygons;

    /** The material */
    private Material material = Material.DEFAULT;

    /** The radius */
    private final double radius;


    /**
     * Constructs a new sphere with the specified radius and a default sub
     * division level of 5.
     * 
     * @param radius
     *            The radius
     */

    public Sphere(final double radius)
    {
        this(radius, 5);
    }


    /**
     * Constructs a new sphere with the specified radius and the specified
     * number of sub divisions.
     * 
     * @param radius
     *            The radius
     * @param subDivisions
     *            The number of sub divisions. A nice value would be between 5
     *            and 7.
     */

    public Sphere(final double radius, final int subDivisions)
    {
        this.radius = radius;
        build(subDivisions);
    }


    /**
     * Builds the vertices and polygons for the specified number of sphere sub
     * division levels.
     * 
     * @param subDivisions
     *            The number of sub divisions
     */

    protected void build(final int subDivisions)
    {
        // Calculate the size of the vertices and polygon arrays and create
        // them
        final int maxV = 2 + (int) Math.pow(2, subDivisions * 2);
        final int maxP = 2 * (maxV - 2);
        this.vertices = new Vector3d[maxV];
        this.polygons = new Polygon[maxP];

        // Get the desired radius of the sphere
        final double r = this.radius;

        // Initialize the vertices and polygons to a simple octahedron
        this.vertices[0] = new Vector3d(r, 0, 0);
        this.vertices[1] = new Vector3d(-r, 0, 0);
        this.vertices[2] = new Vector3d(0, r, 0);
        this.vertices[3] = new Vector3d(0, -r, 0);
        this.vertices[4] = new Vector3d(0, 0, r);
        this.vertices[5] = new Vector3d(0, 0, -r);
        int numV = 6;
        this.polygons[0] = new Polygon(0, 4, 2);
        this.polygons[1] = new Polygon(2, 4, 1);
        this.polygons[2] = new Polygon(1, 4, 3);
        this.polygons[3] = new Polygon(3, 4, 0);
        this.polygons[4] = new Polygon(0, 2, 5);
        this.polygons[5] = new Polygon(2, 1, 5);
        this.polygons[6] = new Polygon(1, 3, 5);
        this.polygons[7] = new Polygon(3, 0, 5);
        int numP = 8;

        // Sub-divide the polygons as often as needed
        for (int level = 1; level < subDivisions; level++)
        {
            // Create a cache for calculated mid point vertexes. This cache
            // drastically reduces the number of produces vertices
            final Map<Long, Integer> cache = new HashMap<Long, Integer>();

            // Process each polygon which was created so far
            for (int i = 0, max = numP; i < max; i++)
            {
                // Get the three vertex indexes of the triangle
                final Polygon polygon = this.polygons[i];
                final int a = polygon.getVertex(0);
                final int b = polygon.getVertex(1);
                final int c = polygon.getVertex(2);

                // Calculate the three mid point vertexes in the triangle
                final int d = getMidPoint(a, c, cache, numV);
                if (d == numV) numV++;
                final int e = getMidPoint(a, b, cache, numV);
                if (e == numV) numV++;
                final int f = getMidPoint(b, c, cache, numV);
                if (f == numV) numV++;

                // Generate the four new polygons. The first one replaces the
                // one we currently processed. The other three are appended
                // at the end of the polygon list
                this.polygons[i] = new Polygon(a, e, d);
                this.polygons[numP++] = new Polygon(e, b, f);
                this.polygons[numP++] = new Polygon(d, e, f);
                this.polygons[numP++] = new Polygon(d, f, c);
            }
        }
    }


    /**
     * Calculates the point on the sphere which lies between the specified
     * vertices. This point is cached in the specified cache so the point can be
     * re-used by adjacent triangles.
     * 
     * @param a
     *            Index of vertex a
     * @param b
     *            Index of vertex b
     * @param cache
     *            The mid point cache
     * @param numV
     *            The current number of vertices. If this method returns numV
     *            then numV must be increment by the caller because a new vertex
     *            was added
     * @return The index of the mid point vertex
     */

    private int getMidPoint(final int a, final int b,
        final Map<Long, Integer> cache, final int numV)
    {
        final long key = (long) Math.max(a, b) << 32 | Math.min(a, b);
        Integer c = cache.get(key);
        if (c == null)
        {
            final Vector3d v =
                this.vertices[a].midPoint(this.vertices[b]).toUnit().multiply(
                    this.radius);
            c = numV;
            cache.put(key, numV);
            this.vertices[numV] = v;
        }
        return c;
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
    
    
    /**
     * Returns the radius of the sphere.
     * 
     * @return The radius of the sphere
     */
    
    public double getRadius()
    {
        return this.radius;
    }
}
