/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * Listener interface for touch events.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface TouchListener
{
    /**
     * Called when a touch down is detected.
     *
     * @param event
     *            The touch event object
     */

    public void touchDown(TouchEvent event);


    /**
     * Called when a touch move is detected.
     *
     * @param event
     *            The touch event object
     */

    public void touchMove(TouchEvent event);


    /**
     * Called when a touch release is detected.
     *
     * @param event
     *            The touch event object
     */

    public void touchRelease(TouchEvent event);
}
