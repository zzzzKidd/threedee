/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.physics;

import de.ailis.threedee.math.Coord3f;
import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.scene.SceneNode;


/**
 * Spin physics
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Spin extends Coord3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The minimum spin */
    private final Constraint min = new Constraint(Float.NEGATIVE_INFINITY);

    /** The maximum spin */
    private final Constraint max = new Constraint(Float.POSITIVE_INFINITY);

    /** The spin acceleration */
    private final Acceleration acceleration = new Acceleration();

    /** The spin deceleration */
    private final Deceleration deceleration = new Deceleration();


    /**
     * Returns the spin acceleration in degree per square second. Never null.
     *
     * @return The spin acceleration
     */

    public Acceleration getAcceleration()
    {
        return this.acceleration;
    }


    /**
     * Returns the spin deceleration in degree per square second. Never null.
     *
     * @return The spin deceleration
     */

    public Deceleration getDeceleration()
    {
        return this.deceleration;
    }

    /**
     * Returns the minimum spin in degree per second.
     *
     * @return The minimum spin
     */

    public Constraint getMin()
    {
        return this.min;
    }


    /**
     * Returns the maximum spin in degree per second.
     *
     * @return The maximum spin
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

        // Apply the spin
        if (this.x != 0) matrix.rotateX(this.x * delta);
        if (this.y != 0) matrix.rotateY(this.y * delta);
        if (this.z != 0) matrix.rotateZ(this.z * delta);

        // Apply spin acceleration
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

        // Apply spin deceleration
        tmp = Math.abs(this.deceleration.getX());
        if (tmp != 0)
            if (this.x < 0)
                this.x = Math.min(0, this.x + tmp * delta);
            else if (this.x > 0)
                this.x = Math.max(0, this.x - tmp * delta);
        tmp = Math.abs(this.deceleration.getY());
        if (tmp != 0)
            if (this.y < 0)
                this.y = Math.min(0, this.y + tmp * delta);
            else if (this.y > 0)
                this.y = Math.max(0, this.y - tmp * delta);
        tmp = Math.abs(this.deceleration.getZ());
        if (tmp != 0)
            if (this.z < 0)
                this.z = Math.min(0, this.z + tmp * delta);
            else if (this.z > 0)
                this.z = Math.max(0, this.z - tmp * delta);

        return !isEmpty() || !this.acceleration.isEmpty();
    }
}
