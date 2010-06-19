/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.model.Color;
import de.ailis.threedee.properties.Lighting;
import de.ailis.threedee.support.IdChangeListener;
import de.ailis.threedee.support.Identifiable;


/**
 * The scene
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Scene implements Identifiable<Scene>
{
    /** The scene id */
    private String id;

    /** The root scene node */
    private SceneNode rootNode;

    /** The used camera */
    private CameraNode cameraNode;

    /** The last update time (Nanosecond timestamp) */
    private long lastUpdate;

    /** The color used to clear the screen */
    private Color clearColor = Color.BLACK;

    /** The id change listeners */
    private final List<IdChangeListener<Scene>> idChangeListener = new ArrayList<IdChangeListener<Scene>>();

    /** The list of touch listeners */
    private final List<TouchListener> touchListeners = new ArrayList<TouchListener>();


    /**
     * Constructs a new scene.
     *
     * @param id
     *            The scene id
     */

    public Scene(final String id)
    {
        this();
        this.id = id;
    }


    /**
     * Constructs a new scene.
     */

    public Scene()
    {
        this.lastUpdate = System.nanoTime();
        this.rootNode = createDefaultRootNode();
    }


    /**
     * Sets the id.
     *
     * @param id
     *            The id to set. Specify null to remove id
     */

    public void setId(final String id)
    {
        final String oldId = this.id;
        this.id = id;
        idChanged(oldId);
    }


    /**
     * Returns the id. May return null if no ID is set.
     *
     * @return The id or null if none set
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Creates the default root node.
     *
     * @return The default root node
     */

    private SceneNode createDefaultRootNode()
    {
        final SceneNode node = new SceneNode();
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

    public void setCameraNode(final CameraNode cameraNode)
    {
        this.cameraNode = cameraNode;
    }


    /**
     * Returns the current camera.
     *
     * @return The current camera or null if none set
     */

    public CameraNode getCameraNode()
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
     * @see de.ailis.threedee.support.Identifiable#addIdChangeListener(de.ailis.threedee.support.IdChangeListener)
     */

    @Override
    public void addIdChangeListener(final IdChangeListener<Scene> listener)
    {
        this.idChangeListener.add(listener);
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#removeIdChangeListener(de.ailis.threedee.support.IdChangeListener)
     */

    @Override
    public void removeIdChangeListener(final IdChangeListener<Scene> listener)
    {
        this.idChangeListener.remove(listener);
    }


    /**
     * Informs listeners about a changed id.
     *
     * @param oldId
     *            The old id
     */

    private void idChanged(final String oldId)
    {
        for (final IdChangeListener<Scene> listener : this.idChangeListener)
        {
            listener.idChanged(this, oldId);
        }
    }
}
