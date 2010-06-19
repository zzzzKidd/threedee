/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.physics;

import java.io.Serializable;

import de.ailis.threedee.entities.SceneNode;


/**
 * Physics
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Physics implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The spin */
    private final Spin spin = new Spin();

    /** The velocity */
    private final Velocity velocity = new Velocity();


    /**
     * Returns the spin physics.
     *
     * @return The spin physics
     */

    public Spin getSpin()
    {
        return this.spin;
    }


    /**
     * Returns the velocity physics.
     *
     * @return The velocity physics
     */

    public Velocity getVelocity()
    {
        return this.velocity;
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

        changed |= this.spin.update(node, delta);
        changed |= this.velocity.update(node, delta);
        return changed;
    }
}
