/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Group;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.properties.Lighting;


/**
 * The scene
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Scene
{
    /** The root scene node */
    private SceneNode rootNode;

    /** The used camera */
    private Camera cameraNode;

    /** The last update time (Nanosecond timestamp) */
    private long lastUpdate;

    /** The color used to clear the screen */
    private Color clearColor = Color.BLACK;

    /** The list of touch listeners */
    private final List<TouchListener> touchListeners = new ArrayList<TouchListener>();


    /**
     * Constructs a new scene.
     */

    public Scene()
    {
        this.lastUpdate = System.nanoTime();
        this.rootNode = createDefaultRootNode();
    }


    /**
     * Creates the default root node.
     *
     * @return The default root node
     */

    private SceneNode createDefaultRootNode()
    {
        final SceneNode node = new Group();
        node.addProperty(new Lighting(true));
        return node;
    }


    /**
     * Sets the clear color. This is the color used for clearing the screen
     * before rendering a frame. The default clear color is black.
     *
     * @param clearColor
     *            The clear color to set. Must not be null.
     */

    public void setClearColor(final Color clearColor)
    {
        if (clearColor == null)
            throw new IllegalArgumentException("clearColor must be set");
        if (!this.clearColor.equals(clearColor))
        {
            this.clearColor = clearColor;
            // TODO Must reinit the scene rendering
        }
    }


    /**
     * Returns the current clear color.
     *
     * @return The clear color. Never null.
     */

    public Color getClearColor()
    {
        return this.clearColor;
    }


    /**
     * Updates the scene. Call this regularly to enable animations.
     *
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean update()
    {
        final long now = System.nanoTime();
        final float delta = ((now - this.lastUpdate)) / 1000000000f;
        this.lastUpdate = now;
        if (this.rootNode != null) return this.rootNode.update(delta);
        return false;
    }


    /**
     * Sets the root scene node.
     *
     * @param rootNode
     *            The root scene node to set. Null for none.
     */

    public void setRootNode(final SceneNode rootNode)
    {
        this.rootNode = rootNode;
    }


    /**
     * Returns the current root scene node.
     *
     * @return The current root scene node or null if none set yet
     */

    public SceneNode getRootNode()
    {
        return this.rootNode;
    }


    /**
     * Sets the camera. Specify null to set no camera
     *
     * @param cameraNode
     *            The camera to set. Null for none.
     */

    public void setCameraNode(final Camera cameraNode)
    {
        this.cameraNode = cameraNode;
    }


    /**
     * Returns the current camera.
     *
     * @return The current camera or null if none set
     */

    public Camera getCameraNode()
    {
        return this.cameraNode;
    }


    /**
     * Adds a touch listener.
     *
     * @param touchListener
     *            The touch listener to add
     */

    public void addTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.add(touchListener);
    }


    /**
     * Remove touch listener.
     *
     * @param touchListener
     *            The touch listener to remove
     */

    public void removeTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.remove(touchListener);
    }


    /**
     * Fires the touch down event.
     *
     * @param event
     *            The event object
     */

    public void touchDown(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchDown(event);
        }
    }


    /**
     * Fires the touch move event.
     *
     * @param event
     *            The event object
     */

    public void touchMove(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchMove(event);
        }
    }


    /**
     * Fires the touch release event.
     *
     * @param event
     *            The event object
     */

    public void touchRelease(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchRelease(event);
        }
    }


    /**
     * Renders the scene.
     *
     * @param viewport
     *            The viewport
     */

    public void render(final Viewport viewport)
    {
        // Create some shortcuts
        final GL gl = viewport.getGL();
        final SceneNode rootNode = this.rootNode;
        final Color clearColor = this.clearColor;

        // Clear the viewport
        gl.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor
                .getBlue(), clearColor.getAlpha());
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Only perform camera and nodes when a root node is set
        if (rootNode != null)
        {
            // Apply camera transformation if camera is present
            if (this.cameraNode != null) this.cameraNode.apply(viewport);

            // Render root node
            rootNode.renderAll(viewport);

            // Remove camera transformation if camera is present
            if (this.cameraNode != null) this.cameraNode.remove(viewport);
        }

        // Finish renderering
        gl.glFlush();
    }
}
