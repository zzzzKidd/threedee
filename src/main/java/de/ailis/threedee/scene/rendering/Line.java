/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.rendering;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import de.ailis.threedee.math.Vector3d;


/**
 * A line
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Line implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 2466415424540661964L;

    /** The vertex index of point A of the line */
    private final int a;

    /** The vertex index of point B of the line */
    private final int b;


    /**
     * Constructs a new line.
     * 
     * @param a
     *            The point A of the line
     * @param b
     *            The point B of the line
     */

    public Line(final int a, final int b)
    {
        this.a = a;
        this.b = b;
    }


    /**
     * Returns the vertex index of point A of the line.
     * 
     * @return The vertex index of point A of the line
     */

    public int getA()
    {
        return this.a;
    }


    /**
     * Returns the vertex index of point B of the line.
     * 
     * @return The vertex index of point B of the line
     */

    public int getB()
    {
        return this.b;
    }


    /**
     * @see Object#equals(Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Line that = (Line) obj;
        return new EqualsBuilder().append(this.a, that.a).append(this.b,
            that.b).isEquals();

    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.a).append(this.b)
            .toHashCode();
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("a", this.a).append("b",
            this.b).toString();
    }


    /**
     * Clips the line with the specified plane and returns the clipped line. If
     * the line is completely clipped away then null is returned.
     * 
     * @param plane
     *            The plane to clip the line with
     * @param vertices
     *            The vertex list referenced by the points in this line
     * @return The clipped line or null if the line is completely clipped away
     */

    public Line clip(final Plane plane, final List<Vector3d> vertices)
    {
        final Vector3d a = vertices.get(this.a);
        final Vector3d b = vertices.get(this.b);
        final Vector3d planeNormal = plane.getNormal();
        final double planeDistance = plane.getDistance();
        final double distanceA = a.multiply(planeNormal) - planeDistance;
        final double distanceB = b.multiply(planeNormal) - planeDistance;

        // If both points are on the back-side of the plane then it is
        // completely clipped away, so return null
        if (distanceA < 0 && distanceB < 0) return null;

        // If both points are on the front-side of the plane then return the
        // line as it is
        if (distanceA >= 0 && distanceB >= 0) return this;

        // Calculate the intersection point
        final double s = distanceA / (distanceA - distanceB);
        final Vector3d intersect =
            new Vector3d(a.getX() + s * (b.getX() - a.getX()), a.getY() + s
                * (b.getY() - a.getY()), a.getZ() + s * (b.getZ() - a.getZ()));
       
        // Add the intersection point to the list of used vertices
        final int c = vertices.size();
        vertices.add(intersect);

        // If point A is on the back-side of the plane then return a line
        // from the intersection point to point B
        if (distanceA < 0) return new Line(c, this.b);

        // Otherwise point B is on the back-side so return a line from point
        // A to the intersection point
        return new Line(this.a, c);
    }
}
