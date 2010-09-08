/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.scene.Scene;


/**
 * Interface for all view components.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface ViewComponent
{
    /**
     * Request a re-rendering of the view port.
     */

    void requestRender();


    /**
     * Sets the scene to display.
     *
     * @param scene The scene to display. Null for none.
     */

    void setScene(Scene scene);


    /**
     * Returns the currently displayed scene.
     *
     * @return The currently display scene. Null if none.
     */

    Scene getScene();


    /**
     * Sets the clear color. This is the color used for clearing the screen
     * before rendering the scene. The default clear color is black.
     *
     * @param clearColor
     *            The clear color to set. Must not be null.
     */

    void setClearColor(final Color4f clearColor);


    /**
     * Returns the current clear color.
     *
     * @return The clear color. Never null.
     */

     Color4f getClearColor();


     /**
      * Adds a touch listener.
      *
      * @param touchListener
      *            The touch listener to add
      */

     void addTouchListener(final TouchListener touchListener);


     /**
      * Remove touch listener.
      *
      * @param touchListener
      *            The touch listener to remove
      */

     void removeTouchListener(final TouchListener touchListener);
}
