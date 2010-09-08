/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.rendering.Viewport;


/**
 * Translates mouse events into touch events for the connected scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneTouchAdapter implements MouseMotionListener, MouseListener
{
    /** The scene canvas */
    private final SceneCanvas sceneCanvas;

    /** The pressed states of the four buttons. */
    private final boolean[] pressed = { false, false, false };


    /**
     * Constructs a new touch adapter.
     *
     * @param sceneCanvas
     *            The scene canvas
     */

    public SceneTouchAdapter(final SceneCanvas sceneCanvas)
    {
        this.sceneCanvas = sceneCanvas;
    }


    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseDragged(final MouseEvent e)
    {
        final Viewport viewport = this.sceneCanvas.getViewport();
        for (int i = 0; i < 3; i++)
        {
            final boolean pressed = this.pressed[i];
            if (pressed)
                this.sceneCanvas.touchMove(new TouchEvent(i, e.getX()
                    - viewport.getWidth()
                    / 2, viewport.getHeight() / 2 - e.getY()));
        }
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
        final Viewport viewport = this.sceneCanvas.getViewport();
        final int id = e.getButton() - 1;
        this.pressed[id] = true;
        this.sceneCanvas.touchDown(new TouchEvent(id, e.getX()
            - viewport.getWidth()
                / 2, viewport.getHeight() / 2 - e.getY()));
    }


    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseReleased(final MouseEvent e)
    {
        final Viewport viewport = this.sceneCanvas.getViewport();
        final int id = e.getButton() - 1;
        this.pressed[id] = false;
        this.sceneCanvas.touchRelease(new TouchEvent(id, e.getX()
                - viewport.getWidth() / 2, viewport.getHeight() / 2
                - e.getY()));
    }
}
