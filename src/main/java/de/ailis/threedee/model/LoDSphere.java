/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Vector3d;


/**
 * A sphere model with enabled Level-of-Detail. The number of sub-divisions is
 * automatically changed when the distance/size ratio changes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class LoDSphere extends Sphere implements LoDModel
{
    /** Serial version UID */
    private static final long serialVersionUID = 6201626749087982508L;

    /** The minimum number of sub divisions */
    private final int minSubDivisions;

    /** The maximum number of sub divisions */
    private final int maxSubDivisions;

    /**
     * The maximum pixel size of a triangle on the screen before another sub
     * division is used
     */
    private final int sizeThreshold;

    /** The current sub division level */
    private int level = 1;


    /**
     * Constructs a new sphere with the specified radius and the specified LoD
     * settings. Good LoD settings maybe 1/5/1. Play with them to find values
     * which fits your needs.
     * 
     * @param radius
     *            The radius
     * @param minSubDivisions
     *            The minimum number of subdivisions
     * @param maxSubDivisions
     *            The maximum number of subdivisions
     * @param sizeThreshold
     *            The maximum pixel size of a triangle on the screen before
     *            another sub division is used
     */

    public LoDSphere(final double radius, final int minSubDivisions,
        final int maxSubDivisions, final int sizeThreshold)
    {
        super(radius, 1);
        if (minSubDivisions < 1)
            throw new IllegalArgumentException(
                "minSubDivisions must be larger than 0");
        if (maxSubDivisions < minSubDivisions)
            throw new IllegalArgumentException(
                "maxSubDivisions must be larger or equal than minSubDivisions");
        if (sizeThreshold < 1)
            throw new IllegalArgumentException(
                "sizeThreshold must be larger than 0");
        this.minSubDivisions = minSubDivisions;
        this.maxSubDivisions = maxSubDivisions;
        this.sizeThreshold = sizeThreshold;
    }


    /**
     * @see LoDModel#prepareLoD(Matrix4d,double)
     */

    @Override
    public void prepareLoD(final Matrix4d transform, final double factor)
    {
        // Create the vector at the center of the spehere and one at the
        // surface of the sphere
        final Vector3d center = new Vector3d(0, 0, 0).multiply(transform);
        final Vector3d surface =
            new Vector3d(this.getRadius(), 0, 0).multiply(transform);

        // Calculate the distance to the camera and the real radius
        final double distance = center.getLength();
        final double radius = surface.sub(center).getLength();

        // Calculate the size (radius) in pixels of the sphere on the screen
        final double size = (int) Math.round(radius * factor / distance);

        // Calculate the sub division level
        final int level =
            (int) Math.min(this.maxSubDivisions, Math.max(
                this.minSubDivisions, Math.ceil(Math.log(size
                    / this.sizeThreshold)
                    / Math.log(2))));

        // Rebuild the vertices and polygons if level has changed
        if (this.level != level)
        {
            build(level);
            this.level = level;
        }
    }
}
