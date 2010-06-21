/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering;

import de.ailis.threedee.entities.Camera;
import de.ailis.threedee.entities.Scene;


/**
 * Renderer interface
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface Renderer
{
    /**
     * Initializes the scene.
     *
     * @param scene
     *            The scene to initialize
     */

    public void init(final Scene scene);


    /**
     * Sets the viewport size. Must be called every time the viewport size
     * changes.
     *
     * @param width
     *            The new viewport width
     * @param height
     *            The new viewport height
     */

    public void setSize(final int width, final int height);


    /**
     * Renders the scene.
     *
     * @param scene
     *            The scene to render
     */

    public void render(final Scene scene);


    /**
     * Returns the viewport width.
     *
     * @return The viewport width
     */

    public int getWidth();


    /**
     * Returns the viewport height.
     *
     * @return The viewport height
     */

    public int getHeight();


    /**
     * Renders the specified camera.
     *
     * @param camera
     *            The camera to render
     */

    public void renderCamera(Camera camera);
}
