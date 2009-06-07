/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.updater;

import de.ailis.threedee.scene.SceneNode;


/**
 * The roll updater rotates a node around the Z axis.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class RollUpdater implements NodeUpdater
{
    /** The angle in clock-wise RAD */
    private double angle;


    /**
     * Constructs a new roll updater with the specified angle.
     * 
     * @param angle
     *            The angle the node should be rotated around the Z axis per
     *            second. Measured in clock-wise RAD.
     */

    public RollUpdater(final double angle)
    {
        setAngle(angle);
    }


    /**
     * @see NodeUpdater#update(SceneNode, long)
     */

    public void update(final SceneNode node, final long delta)
    {
        // Do nothing if angle is 0
        if (this.angle == 0) return;

        node.rotateZ(this.angle * delta / 1000000000);
    }


    /**
     * Returns the current angle.
     * 
     * @return The angle the node is rotated around the Z axis per second.
     *         Measured in clock-wise RAD.
     */

    public double getAngle()
    {
        return this.angle;
    }


    /**
     * Sets the angle.
     * 
     * @param angle
     *            The angle the node should be rotated around the Z axis per
     *            second. Measured in clock-wise RAD.
     */

    public void setAngle(final double angle)
    {
        this.angle = angle;
    }
}
