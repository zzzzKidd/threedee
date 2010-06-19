/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.physics;

import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.math.Coord3f;
import de.ailis.threedee.math.Matrix4f;


/**
 * Velocity physics
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Velocity extends Coord3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The minimum velocity */
    private final Constraint min = new Constraint(Float.NEGATIVE_INFINITY);

    /** The maximum velocity */
    private final Constraint max = new Constraint(Float.POSITIVE_INFINITY);

    /** The acceleration */
    private final Acceleration acceleration = new Acceleration();


    /**
     * Returns the acceleration in units per square second. Never null.
     *
     * @return The acceleration
     */

    public Acceleration getAcceleration()
    {
        return this.acceleration;
    }


    /**
     * Returns the minimum velocity in units per second. Never null.
     *
     * @return The minimum velocity
     */

    public Constraint getMin()
    {
        return this.min;
    }


    /**
     * Returns the maximum velocity in units per second. Never null.
     *
     * @return The maximum velocity
     */

    public Constraint getMax()
    {
        return this.max;
    }


    /**
     * Updates the specified node.
     *
     * @param node
     *            The node to update
     * @param delta
     *            The time delta in seconds
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean update(final SceneNode node, final float delta)
    {
        float tmp;

        final Matrix4f matrix = node.getTransform();

        // Apply the velocity
        matrix.translate(this.x * delta, this.y * delta, this.z * delta);

        // Apply acceleration
        tmp = this.acceleration.getX();
        if (tmp != 0)
            this.x = Math.min(this.max.getX(), Math.max(this.min.getX(), this.x
                    + tmp * delta));
        tmp = this.acceleration.getY();
        if (tmp != 0)
            this.y = Math.min(this.max.getY(), Math.max(this.min.getY(), this.y
                    + tmp * delta));
        tmp = this.acceleration.getZ();
        if (tmp != 0)
            this.z = Math.min(this.max.getZ(), Math.max(this.min.getZ(), this.z
                    + tmp * delta));

        return !isEmpty() || !this.acceleration.isEmpty();
    }
}
