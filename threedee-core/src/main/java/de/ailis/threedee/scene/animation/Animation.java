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

    /** The current time index */
    private float time;


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
        this.time = 0;
    }


    /**
     * Updates the animation with the specified time delta.
     *
     * @param delta
     *            The time delta since last call in seconds
     */

    public void update(final float delta)
    {
        this.time += delta;
        for (final SceneNode node : this.nodes)
            animate(node, this.time);
        for (final Animation animation : this.animations)
            animation.update(delta);
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
     * Removes a node from this animation.
     *
     * @param node
     *            The node to remove
     */

    public void removeNode(final SceneNode node)
    {
        this.nodes.remove(node);
    }


    /**
     * Adds the specified animation
     *
     * @param animation
     *            The animation to add
     */

    public void addAnimation(final Animation animation)
    {
        this.animations.add(animation);
    }
}
