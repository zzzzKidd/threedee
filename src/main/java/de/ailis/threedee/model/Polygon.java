/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.io.Serializable;
import java.util.ArrayList;
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

public class Polygon implements Serializable, Comparable<Polygon>
{
    /** Serial version UID */
    private static final long serialVersionUID = 8001755230510072089L;

    /** The referenced vertices */
    private final int[] vertices;

    /** The material used by this polygon */
    private Material material = null;

    /**
     * The average Z position of the polygon. This has only a valid value when
     * the updateAverageZ() method was called before
     */
    private double averageZ;


    /**
     * Constructs a new polygon with a polygon-specific material.
     * 
     * @param vertices
     *            The referenced vertices
     */

    public Polygon(final int... vertices)
    {
        this.vertices = vertices;
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
        final Polygon result = new Polygon(Arrays.copyOf(newVertices, j));
        result.setMaterial(this.material);
        return result;
    }


    /**
     * Updates the average Z value.
     * 
     * @param vertices
     *            The vertices referenced by this polygon
     */

    public void updateAverageZ(final List<Vector3d> vertices)
    {
        double averageZ = 0;
        final int vertexCount = countVertices();
        for (int v = 0; v < vertexCount; v++)
        {
            averageZ += vertices.get(getVertex(v)).getZ();
        }
        averageZ /= vertexCount;
        this.averageZ = averageZ;
    }


    /**
     * Converts this polygon into a list of triangles.
     * 
     * @return The list with triangles
     */

    public List<Polygon> triangulize()
    {
        final int max = this.vertices.length - 2;
        final List<Polygon> triangles = new ArrayList<Polygon>(max);
        for (int i = 0; i < max; i++)
        {
            final Polygon triangle =
                new Polygon(this.vertices[0], this.vertices[i + 1],
                    this.vertices[i + 2]);
            triangle.material = this.material;
            triangle.averageZ = this.averageZ;
            triangles.add(triangle);
        }
        return triangles;
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final Polygon o)
    {
        return Double.compare(o.averageZ, this.averageZ);
    }


    /**
     * Returns a clone of this polygon with the vertex indices translated by the
     * specified offset. This is needed if multiple vertex lists are appended to
     * a single list so multiple polygons reference the same vertex list.
     * 
     * @param offset
     *            The offset to add to all vertex indices
     * @return The polygon with added vertex offset
     */

    public Polygon addVertexOffset(final int offset)
    {
        final int vertices[] = new int[this.vertices.length];
        for (int i = this.vertices.length - 1; i >= 0; i--)
        {
            vertices[i] = this.vertices[i] + offset;
        }
        final Polygon polygon = new Polygon(vertices);
        polygon.setMaterial(this.material);
        return polygon;
    }
}
