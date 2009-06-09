/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.rendering;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import de.ailis.threedee.math.Vector3d;


/**
 * A plane is defined by it's normal and a distance from the viewer.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Plane implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 9011274096843258629L;

    /** The plane normal */
    private final Vector3d normal;

    /** The plane distance */
    private final double distance;


    /**
     * Constructs a new plane.
     * 
     * @param normal
     *            The plane normal
     * @param distance
     *            The plane distance
     */

    public Plane(final Vector3d normal, final double distance)
    {
        this.normal = normal;
        this.distance = distance;
    }


    /**
     * Returns the plane normal.
     * 
     * @return The plane normal
     */

    public Vector3d getNormal()
    {
        return this.normal;
    }


    /**
     * Returns the plane distance
     * 
     * @return The plane distance
     */

    public double getDistance()
    {
        return this.distance;
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
        final Plane that = (Plane) obj;
        return new EqualsBuilder().append(this.normal, that.normal).append(
            this.distance, that.distance).isEquals();

    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.normal).append(this.distance)
            .toHashCode();
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("normal", this.normal).append(
            "distance", this.distance).toString();
    }
}
