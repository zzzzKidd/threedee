/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.ailis.threedee.math.Vector3d;
import de.ailis.threedee.scene.rendering.Line;
import de.ailis.threedee.scene.rendering.Plane;


/**
 * A polygon
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Polygon implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 8001755230510072089L;

    /** The referenced vertices */
    private final int[] vertices;

    /** The material used by this polygon */
    private Material material;


    /**
     * Constructs a new polygon with a polygon-specific material.
     * 
     * @param material
     *            The material used by this polygon. Can be null to specify that
     *            this polygon has no special material and should use the
     *            material of the model
     * @param vertices
     *            The referenced vertices
     */

    public Polygon(final Material material, final int... vertices)
    {
        this.material = material;
        this.vertices = vertices;
    }


    /**
     * Constructs a new polygon without a polygon-specific material.
     * 
     * @param vertices
     *            The referenced vertices
     */

    public Polygon(final int... vertices)
    {
        this(null, vertices);
    }


    /**
     * Returns the material. Returns null if this polygon is not a
     * polygon-specific material and uses the one of the model instead.
     * 
     * @return The material
     */

    public Material getMaterial()
    {
        return this.material;
    }


    /**
     * Sets the material. Set it to null to remove the polygon-specific
     * material. The model material is used then.
     * 
     * @param material
     *            The material to set
     */

    public void setMaterial(final Material material)
    {
        this.material = material;
    }


    /**
     * Returns the number of referenced vertices.
     * 
     * @return The number of references vertices
     */

    public int countVertices()
    {
        return this.vertices.length;
    }


    /**
     * Returns the vertex with the specified index.
     * 
     * @param index
     *            The index
     * @return The vertex
     */

    public int getVertex(final int index)
    {
        return this.vertices[index];
    }


    /**
     * Returns the center of the polygon.
     * 
     * @param vertices
     *            The vertex list
     * @return The center of the polygon
     */

    public Vector3d getCenter(final List<Vector3d> vertices)
    {
        final int vertexCount = this.vertices.length;

        double ax = 0, ay = 0, az = 0;
        for (int i = 0; i < vertexCount; i++)
        {
            final Vector3d v = vertices.get(this.vertices[i]);
            ax += v.getX();
            ay += v.getY();
            az += v.getZ();
        }
        ax /= vertexCount;
        ay /= vertexCount;
        az /= vertexCount;

        return new Vector3d(ax, ay, az);
    }


    /**
     * Calculates the normal of the polygon. May return null if polygon has
     * fewer then three vertices.
     * 
     * @param vertices
     *            The vertex list
     * @return The normal or null if polygon has fewer then three vertices
     */

    public Vector3d getNormal(final List<Vector3d> vertices)
    {
        // If polygon has fewer then three vertices then it has no normal
        if (this.vertices.length < 3) return null;

        final Vector3d a = vertices.get(this.vertices[0]);
        final Vector3d b = vertices.get(this.vertices[1]);
        final Vector3d c = vertices.get(this.vertices[2]);
        final Vector3d v1 = b.sub(a);
        final Vector3d v2 = a.sub(c);
        return v1.cross(v2).toUnit();
    }


    /**
     * Clips the polygon with the specified plane and returns the clipped
     * polygon. If the polygon was clipped completely away then null is
     * returned.
     * 
     * @param plane
     *            The plane to clip the polygon with
     * @param vertices
     *            The list of vertices referenced by the polygon
     * @return The clipped polygon or null if the polygon was clipped completely
     *         away
     */

    public Polygon clip(final Plane plane, final List<Vector3d> vertices)
    {
        final int max = this.vertices.length;
        final int[] newVertices = new int[max + 1];

        int j = 0;
        for (int i = 0; i < max; i++)
        {
            // Get the two vertices of the line
            final int a = this.vertices[i];
            final int b = this.vertices[(i + 1) % max];

            // Clip the line
            final Line line = new Line(a, b).clip(plane, vertices);

            // If line is null then it is completely clipped away, so ignore it
            if (line == null) continue;

            // Add point A to the list of new vertices
            newVertices[j++] = line.getA();

            // If point B differs from original point B then also add it to the
            // list of new vertices
            final int c = line.getB();
            if (c != b) newVertices[j++] = c;
        }
        
        // Return null if the polygon is clipped away completely
        if (j == 0) return null;

        // Return the clipped polygon
        return new Polygon(this.material, Arrays.copyOf(newVertices, j));
    }
}
