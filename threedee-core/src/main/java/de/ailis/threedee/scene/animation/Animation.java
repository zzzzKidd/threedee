/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.animation;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.scene.SceneNode;


/**
 * Animation interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Animation
{
    /** The animation id */
    private final String id;

    /** The nodes to animate */
    private final List<SceneNode> nodes = new ArrayList<SceneNode>();

    /** The sub animations */
    private final List<Animation> animations = new ArrayList<Animation>();

    /** The current animation index. */
    private float index;

    /** If animation is running. */
    private boolean running = true;

    /** The animation input type. */
    private AnimationInputType inputType = AnimationInputType.TIME;

    /** The animation speed. (1==Normal, -1=Reverse, 0=Stop, ...) */
    private float speed = 1;

    /** The acceleration. */
    private float acceleration = 0;

    /** The deceleration. */
    private float deceleration = 0;

    /** The maximum speed. */
    private float maxSpeed = Float.POSITIVE_INFINITY;

    /** The minimum speed. */
    private float minSpeed = Float.NEGATIVE_INFINITY;


    /**
     * Constructs an animation without an id
     */

    public Animation()
    {
        this(null);
    }


    /**
     * Constructs an animation with the specified id
     *
     * @param id
     *            The animation id
     */

    public Animation(final String id)
    {
        this.id = id;
        this.index = 0;
    }


    /**
     * Updates the animation with the specified time delta.
     *
     * @param delta
     *            The time delta since last call in seconds
     * @return True if animation is running, false if not
     */

    public boolean update(final float delta)
    {
        // Do nothing if animation is not running
        if (!this.running) return false;

        // Apply acceleration
        if (this.acceleration != 0)
            this.speed += this.acceleration * delta;

        // Apply deceleration
        if (this.deceleration != 0)
            if (this.speed < 0)
                this.speed = Math
                    .min(0, this.speed + this.deceleration * delta);
            else if (this.speed > 0)
                this.speed = Math
                    .max(0, this.speed - this.deceleration * delta);

        // Apply speed constraints
        this.speed = Math.min(this.maxSpeed, Math
            .max(this.minSpeed, this.speed));

        // If no speed and no acceleration is present then do nothing more.
        if (this.speed == 0 && this.acceleration == 0) return false;

        final float realDelta = delta * this.speed;
        boolean changed = false;
        this.index += realDelta;
        for (final SceneNode node : this.nodes)
            animate(node, this.index);
        for (final Animation animation : this.animations)
            changed |= animation.update(realDelta);
        return true;
    }


    /**
     * Animates a scene node.
     *
     * @param node
     *            The scene node to animate
     * @param time
     *            The time in seconds
     */

    public abstract void animate(SceneNode node, float time);


    /**
     * Returns the scene node list.
     *
     * @return The scene node list. Never null. May be empty.
     */

    public List<SceneNode> getNodes()
    {
        return this.nodes;
    }


    /**
     * Returns the animation id.
     *
     * @return The animation id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Adds a node to this animation.
     *
     * @param node
     *            The node to add
     */

    public void addNode(final SceneNode node)
    {
        this.nodes.add(node);
    }


    /**
     * Returns the sub animations.
     *
     * @return The sub animations
     */

    public List<Animation> getAnimations()
    {
        return this.animations;
    }


    /**
     * Checks if animation us running.
     *
     * @return True if animation is running, false if not.
     */

    public boolean isRunning()
    {
        return this.running;
    }


    /**
     * Starts the animation.
     */

    public void start()
    {
        this.running = true;
    }


    /**
     * Stops the animation.
     */

    public void stop()
    {
        this.running = false;
    }


    /**
     * Returns the animation input type.
     *
     * @return The animation input type. Never null.
     */

    public AnimationInputType getInputType()
    {
        return this.inputType;
    }


    /**
     * Sets the animation input type.
     *
     * @param inputType
     *            The animation input type to set. Must not be null.
     */

    public void setInputType(final AnimationInputType inputType)
    {
        this.inputType = inputType;
    }


    /**
     * Returns the current animation speed.
     *
     * @return The current animation speed.
     */

    public float getSpeed()
    {
        return this.speed;
    }


    /**
     * Sets the animation speed. Some examples: 1 means normal speed, 0 means
     * stop, -1 means reverse speed.
     *
     * @param speed
     *            The speed to set
     */

    public void setSpeed(final float speed)
    {
        this.speed = speed;
    }


    /**
     * Returns the acceleration.
     *
     * @return The acceleration
     */

    public float getAcceleration()
    {
        return this.acceleration;
    }


    /**
     * Sets the acceleration.
     *
     * @param acceleration
     *            The acceleration to set
     */

    public void setAcceleration(final float acceleration)
    {
        this.acceleration = acceleration;
    }


    /**
     * Returns the deceleration.
     *
     * @return The deceleration
     */

    public float getDeceleration()
    {
        return this.deceleration;
    }


    /**
     * Sets the deceleration.
     *
     * @param deceleration
     *            The deceleration to set
     */

    public void setDeceleration(final float deceleration)
    {
        this.deceleration = deceleration;
    }


    /**
     * Returns the maximum speed.
     *
     * @return The maximum speed
     */

    public float getMaxSpeed()
    {
        return this.maxSpeed;
    }


    /**
     * Sets the maximum speed.
     *
     * @param maxSpeed
     *            The maximum speed to set
     */

    public void setMaxSpeed(final float maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }


    /**
     * Returns the minimum speed.
     *
     * @return The minimum speed
     */

    public float getMinSpeed()
    {
        return this.minSpeed;
    }


    /**
     * Sets the minimum speed.
     *
     * @param minSpeed
     *            The minimum speed to set
     */

    public void setMinSpeed(final float minSpeed)
    {
        this.minSpeed = minSpeed;
    }
}
