/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.rendering.Renderer;


/**
 * Translates mouse events into touch events for the connected scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneTouchAdapter implements MouseMotionListener, MouseListener
{
    /** The scene */
    private final Scene scene;

    /** The renderer */
    private final Renderer renderer;


    /**
     * Constructs a new scene touch adapter.
     *
     * @param scene
     *            The scene
     * @param renderer
     *            The renderer
     */

    public SceneTouchAdapter(final Scene scene, final Renderer renderer)
    {
        this.scene = scene;
        this.renderer = renderer;
    }


    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseDragged(final MouseEvent e)
    {
        this.scene.touchMove(new TouchEvent(0, e.getX() - this.renderer.getWidth()
                / 2, this.renderer.getHeight() / 2 - e.getY()));
    }


    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseMoved(final MouseEvent e)
    {
        // Ignored
    }


    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseClicked(final MouseEvent e)
    {
        // Ignored
    }


    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseEntered(final MouseEvent e)
    {
        // Ignored
    }


    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseExited(final MouseEvent e)
    {
        // Ignored
    }


    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */

    @Override
    public void mousePressed(final MouseEvent e)
    {
        this.scene.touchDown(new TouchEvent(0, e.getX() - this.renderer.getWidth()
                / 2, this.renderer.getHeight() / 2 - e.getY()));
    }


    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseReleased(final MouseEvent e)
    {
        this.scene.touchRelease(new TouchEvent(0, e.getX()
                - this.renderer.getWidth() / 2, this.renderer.getHeight() / 2
                - e.getY()));
    }
}