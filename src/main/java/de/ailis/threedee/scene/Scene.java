/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import java.awt.Color;
import java.awt.Graphics2D;

import de.ailis.threedee.RenderOptions;
import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.scene.rendering.PolygonBuffer;


/**
 * Scene
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Scene
{
    /** The root node of the scene */
    private SceneNode rootNode;

    /** The polygon buffer */
    private final PolygonBuffer buffer = new PolygonBuffer();

    /** The global ambient color */
    private Color globalAmbient = Color.DARK_GRAY;


    /**
     * Updates the scene with the specified time delta (Nanoseconds)
     * 
     * @param delta
     *            The time elapsed since the last call to this method measured
     *            in nanoseconds
     */

    public void update(final long delta)
    {
        if (this.rootNode != null) this.rootNode.update(delta);
    }


    /**
     * Renders the scene.
     * 
     * @param g
     *            The graphics context
     * @param width
     *            The output width in pixels
     * @param height
     *            The output height in pixels
     * @param renderOptions
     *            The render options
     * @param camera
     *            The camera node to use. If it is null then a fixed default
     *            camera at position 0,0,0 looking in direction 0,0,1 is used
     */

    public void render(final Graphics2D g, final int width, final int height,
        final RenderOptions renderOptions, final CameraNode camera)
    {
        // If no root node is set yet then do nothing
        if (this.rootNode == null) return;

        // Calculate the root transformation
        Matrix4d rootTransform = this.rootNode.getTransform();
        if (camera != null)
            rootTransform =
                camera.getEffectiveTransform().invert()
                    .multiply(rootTransform);

        // Initialize the polygon buffer and recursively render the scene
        // nodes into it
        this.buffer.clear();
        this.buffer.setGlobalAmbient(this.globalAmbient);
        this.buffer.setRenderOptions(renderOptions);
        this.rootNode.render(this.buffer, rootTransform);

        // Render the polygon buffer onto the screen
        this.buffer.render(g, width, height);
    }


    /**
     * Sets the root node of the scene.
     * 
     * @param node
     *            The root node to set. May be null to remove the current root
     *            node
     */

    public void setRootNode(final SceneNode node)
    {
        this.rootNode = node;
    }


    /**
     * Sets the global ambient color.
     * 
     * @param globalAmbient
     *            The global ambient color to set
     */

    public void setGlobalAmbient(final Color globalAmbient)
    {
        this.globalAmbient = globalAmbient;
    }


    /**
     * Returns the global ambient color.
     * 
     * @return The global ambient color
     */

    public Color getGlobalAmbient()
    {
        return this.globalAmbient;
    }
}
