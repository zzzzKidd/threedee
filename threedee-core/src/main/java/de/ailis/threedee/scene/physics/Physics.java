/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.physics;

import java.io.Serializable;

import de.ailis.gramath.MutableMatrix4f;
import de.ailis.gramath.MutableVector3f;
import de.ailis.threedee.scene.SceneNode;


/**
 * Physics.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Physics implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The velocity. */
    private final MutableVector3f velocity = new MutableVector3f();

    /** The minimum velocity. */
    private final MutableVector3f minVelocity = new MutableVector3f(
        Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
        Float.NEGATIVE_INFINITY);

    /** The maximum velocity. */
    private final MutableVector3f maxVelocity = new MutableVector3f(
        Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
        Float.POSITIVE_INFINITY);

    /** The acceleration. */
    private final MutableVector3f acceleration = new MutableVector3f();

    /** The deceleration. */
    private final MutableVector3f deceleration = new MutableVector3f();

    /** The spin velocity. */
    private final MutableVector3f spinVelocity = new MutableVector3f();

    /** The minimum spin velocity. */
    private final MutableVector3f minSpinVelocity = new MutableVector3f(
        Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
        Float.NEGATIVE_INFINITY);

    /** The maximum spin velocity. */
    private final MutableVector3f maxSpinVelocity = new MutableVector3f(
        Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
        Float.POSITIVE_INFINITY);

    /** The spin acceleration. */
    private final MutableVector3f spinAcceleration = new MutableVector3f();

    /** The spin deceleration. */
    private final MutableVector3f spinDeceleration = new MutableVector3f();


    /**
     * Returns the spin velocity.
     *
     * @return The spin velocity.
     */

    public MutableVector3f getSpinVelocity()
    {
        return this.spinVelocity;
    }


    /**
     * Returns the velocity.
     *
     * @return The velocity.
     */

    public MutableVector3f getVelocity()
    {
        return this.velocity;
    }


    /**
     * Returns the min velocity.
     *
     * @return The min velocity.
     */

    public MutableVector3f getMinVelocity()
    {
        return this.minVelocity;
    }


    /**
     * Returns the max velocity.
     *
     * @return The max velocity.
     */

    public MutableVector3f getMaxVelocity()
    {
        return this.maxVelocity;
    }


    /**
     * Returns the acceleration.
     *
     * @return The acceleration
     */

    public MutableVector3f getAcceleration()
    {
        return this.acceleration;
    }


    /**
     * Returns the deceleration.
     *
     * @return The deceleration
     */

    public MutableVector3f getDeceleration()
    {
        return this.deceleration;
    }


    /**
     * Returns the min spin velocity.
     *
     * @return The min spin velocity.
     */

    public MutableVector3f getMinSpinVelocity()
    {
        return this.minSpinVelocity;
    }


    /**
     * Returns the max spin velocity.
     *
     * @return The max spin velocity.
     */

    public MutableVector3f getMaxSpinVelocity()
    {
        return this.maxSpinVelocity;
    }


    /**
     * Returns the spin acceleration.
     *
     * @return The spin acceleration.
     */

    public MutableVector3f getSpinAcceleration()
    {
        return this.spinAcceleration;
    }


    /**
     * Returns the spin deceleration.
     *
     * @return The spin deceleration.
     */

    public MutableVector3f getSpinDeceleration()
    {
        return this.spinDeceleration;
    }


    /**
     * Updates the spin physics.
     *
     * @param node
     *            The node to update
     * @param delta
     *            The time delta in seconds
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean updateSpin(final SceneNode node, final float delta)
    {
        float tmp;

        final MutableMatrix4f matrix = node.getTransform();

        // Get the spin values
        float x = this.spinVelocity.getX();
        float y = this.spinVelocity.getY();
        float z = this.spinVelocity.getZ();

        // Apply the spin
        if (x != 0) matrix.rotateX(x * delta);
        if (y != 0) matrix.rotateY(y * delta);
        if (z != 0) matrix.rotateZ(z * delta);

        // Apply spin acceleration
        tmp = this.spinAcceleration.getX();
        if (tmp != 0)
            x = Math.min(this.maxSpinVelocity.getX(),
                Math.max(this.minSpinVelocity.getX(), x + tmp * delta));
        tmp = this.spinAcceleration.getY();
        if (tmp != 0)
            y = Math.min(this.maxSpinVelocity.getY(),
                Math.max(this.minSpinVelocity.getY(), y + tmp * delta));
        tmp = this.spinAcceleration.getZ();
        if (tmp != 0)
            z = Math.min(this.maxSpinVelocity.getZ(),
                Math.max(this.minSpinVelocity.getZ(), z + tmp * delta));

        // Apply spin deceleration
        tmp = Math.abs(this.spinDeceleration.getX());
        if (tmp != 0)
            if (x < 0)
                x = Math.min(0, x + tmp * delta);
            else if (x > 0)
                x = Math.max(0, x - tmp * delta);
        tmp = Math.abs(this.spinDeceleration.getY());
        if (tmp != 0)
            if (y < 0)
                y = Math.min(0, y + tmp * delta);
            else if (y > 0)
                y = Math.max(0, y - tmp * delta);
        tmp = Math.abs(this.spinDeceleration.getZ());
        if (tmp != 0)
            if (z < 0)
                z = Math.min(0, z + tmp * delta);
            else if (z > 0)
                z = Math.max(0, z - tmp * delta);

        // Set the values
        this.spinVelocity.set(x, y, z);

        // Check if there is still movement
        return !this.spinVelocity.isNull() || !this.spinAcceleration.isNull();
    }


    /**
     * Updates the velocity physics.
     *
     * @param node
     *            The node to update
     * @param delta
     *            The time delta in seconds
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean updateVelocity(final SceneNode node, final float delta)
    {
        float tmp;

        final MutableMatrix4f matrix = node.getTransform();

        // Get velocity values
        float x = this.velocity.getX();
        float y = this.velocity.getY();
        float z = this.velocity.getZ();

        // Apply the velocity
        matrix.translate(x * delta, y * delta, z * delta);

        // Apply acceleration
        tmp = this.acceleration.getX();
        if (tmp != 0)
            x = Math.min(this.maxVelocity.getX(),
                Math.max(this.minVelocity.getX(), x + tmp * delta));
        tmp = this.acceleration.getY();
        if (tmp != 0)
            y = Math.min(this.maxVelocity.getY(),
                Math.max(this.minVelocity.getY(), y + tmp * delta));
        tmp = this.acceleration.getZ();
        if (tmp != 0)
            z = Math.min(this.maxVelocity.getZ(),
                Math.max(this.minVelocity.getZ(), z + tmp * delta));

        // Set new velocity
        this.velocity.set(x, y, z);

        // Check if there is still movement
        return !this.velocity.isNull() || !this.acceleration.isNull();
    }

    /**
     * Updates the specified node with this physics data.
     *
     * @param node
     *            The scene node to update
     * @param delta
     *            The time delta in seconds
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean update(final SceneNode node, final float delta)
    {
        boolean changed = false;

        changed |= updateSpin(node, delta);
        changed |= updateVelocity(node, delta);
        return changed;
    }
}
