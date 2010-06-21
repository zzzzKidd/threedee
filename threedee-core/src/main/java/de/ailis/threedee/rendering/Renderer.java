/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering;

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

    public void initScene(final Scene scene);


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

    public int getWidth();

    public int getHeight();
}
