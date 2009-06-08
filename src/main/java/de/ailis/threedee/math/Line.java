/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.math;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


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

    /** The point A of the line */
    private final Vector3d a;

    /** The point B of the line */
    private final Vector3d b;


    /**
     * Constructs a new line.
     * 
     * @param a
     *            The point A of the line
     * @param b
     *            The point B of the line
     */

    public Line(final Vector3d a, final Vector3d b)
    {
        this.a = a;
        this.b = b;
    }


    /**
     * Returns the point A of the line.
     * 
     * @return The point A of the line
     */

    public Vector3d getA()
    {
        return this.a;
    }


    /**
     * Returns the point B of the line.
     * 
     * @return The point B of the line
     */

    public Vector3d getB()
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
     * @return The clipped line or null if the line is competely clipped away
     */

    public Line clip(final Plane plane)
    {
        final Vector3d planeNormal = plane.getNormal();
        final double planeDistance = plane.getDistance();
        final double distanceA = this.a.multiply(planeNormal) - planeDistance;
        final double distanceB = this.b.multiply(planeNormal) - planeDistance;

        // If both points are on the back-side of the plane then it is
        // completely clipped away, so return null
        if (distanceA < 0 && distanceB < 0) return null;
        
        // If both points are on the front-side of the plane then return the
        // line as it is
        if (distanceA >= 0 && distanceB >=0) return this;

        // Calculate the intersection point
        final double s = distanceA / (distanceA - distanceB);
        final Vector3d intersect =
            new Vector3d(this.a.getX() + s * (this.b.getX() - this.a.getX()),
                this.a.getY() + s * (this.b.getY() - this.a.getY()), this.a
                    .getZ()
                    + s * (this.b.getZ() - this.a.getZ()));

        // If point A is on the back-side of the plane then return a line
        // from the intersection point to point B
        if (distanceA < 0)
            return new Line(intersect, this.b);
        
        // Otherwise point B is on the back-side so return a line from point
        // A to the intersection point
        return new Line(this.a, intersect);
    }
}
