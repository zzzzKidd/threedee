/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.updater;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import de.ailis.threedee.scene.SceneNode;


/**
 * This updater can be connected to a swing component as a key listener and
 * transforms the node to which it is connected according to the keys pressed.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class KeyboardUpdater extends KeyAdapter implements NodeUpdater
{
    /** The speed in 1.0 units per second */
    private final double speed = 10;

    /** The rotation speed in RAD per second */
    private final double rotSpeed = Math.toRadians(45);

    /** Forward movement flag */
    private boolean forward;

    /** Forward movement flag */
    private boolean backward;

    /** Left movement flag */
    private boolean left;

    /** Right movement flag */
    private boolean right;

    /** Up movement flag */
    private boolean up;

    /** Down movement flag */
    private boolean down;

    /** Roll left flag */
    private boolean rollLeft;

    /** Roll right flag */
    private boolean rollRight;

    /** Pitch up flag */
    private boolean pitchUp;

    /** Pitch down flag */
    private boolean pitchDown;

    /** Yaw right flag */
    private boolean yawRight;
    
    /** Yaw left flag */
    private boolean yawLeft;


    /**
     * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
     */

    @Override
    public void keyPressed(final KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W:
                this.forward = true;
                break;

            case KeyEvent.VK_S:
                this.backward = true;
                break;

            case KeyEvent.VK_A:
                this.left = true;
                break;

            case KeyEvent.VK_D:
                this.right = true;
                break;

            case KeyEvent.VK_R:
                this.up = true;
                break;

            case KeyEvent.VK_F:
                this.down = true;
                break;

            case KeyEvent.VK_E:
                this.rollRight = true;
                break;

            case KeyEvent.VK_Q:
                this.rollLeft = true;
                break;

            case KeyEvent.VK_LEFT:
                this.yawLeft = true;
                break;

            case KeyEvent.VK_RIGHT:
                this.yawRight = true;
                break;

            case KeyEvent.VK_UP:
                this.pitchUp = true;
                break;

            case KeyEvent.VK_DOWN:
                this.pitchDown = true;
                break;
        }
    }

    /**
     * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
     */

    @Override
    public void keyReleased(final KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W:
                this.forward = false;
                break;

            case KeyEvent.VK_S:
                this.backward = false;
                break;

            case KeyEvent.VK_A:
                this.left = false;
                break;

            case KeyEvent.VK_D:
                this.right = false;
                break;

            case KeyEvent.VK_R:
                this.up = false;
                break;

            case KeyEvent.VK_F:
                this.down = false;
                break;

            case KeyEvent.VK_E:
                this.rollRight = false;
                break;

            case KeyEvent.VK_Q:
                this.rollLeft = false;
                break;

            case KeyEvent.VK_LEFT:
                this.yawLeft = false;
                break;

            case KeyEvent.VK_RIGHT:
                this.yawRight = false;
                break;

            case KeyEvent.VK_UP:
                this.pitchUp = false;
                break;

            case KeyEvent.VK_DOWN:
                this.pitchDown = false;
                break;
        }
    }


    /**
     * @see de.ailis.threedee.scene.updater.NodeUpdater#update(de.ailis.threedee.scene.SceneNode,
     *      long)
     */

    @Override
    public void update(final SceneNode node, final long delta)
    {
        final double x = this.right ? 1 : (this.left ? -1 : 0);
        final double y = this.down ? 1 : (this.up ? -1 : 0);
        final double z = this.forward ? 1 : (this.backward ? -1 : 0);
        final double rx = this.pitchUp ? 1 : (this.pitchDown ? -1 : 0);
        final double ry = this.yawRight ? 1 : (this.yawLeft ? -1 : 0);
        final double rz = this.rollLeft ? 1 : (this.rollRight ? -1 : 0);

        node.translate(x * this.speed * delta / 1000000000, y * this.speed
            * delta / 1000000000, z * this.speed * delta / 1000000000);
        
        node.rotateX(rx * this.rotSpeed * delta / 1000000000);               
        node.rotateY(ry * this.rotSpeed * delta / 1000000000);               
        node.rotateZ(rz * this.rotSpeed * delta / 1000000000);               
    }
}
