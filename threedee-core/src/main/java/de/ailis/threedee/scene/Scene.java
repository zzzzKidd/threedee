/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.scene.animation.Animation;
import de.ailis.threedee.scene.animation.AnimationInputType;
import de.ailis.threedee.scene.properties.Lighting;
import de.ailis.threedee.scene.textures.TextureManager;


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
    private Color4f clearColor = Color4f.BLACK;

    /** The list of touch listeners */
    private final List<TouchListener> touchListeners = new ArrayList<TouchListener>();

    /** The ID-to-node mapping */
    private final Map<String, SceneNode> nodes = new HashMap<String, SceneNode>();

    /** The list with animations */
    private List<Animation> animations = null;


    /**
     * Constructs a new scene.
     */

    public Scene()
    {
        this.lastUpdate = System.nanoTime();
        setRootNode(createDefaultRootNode());
        this.cameraNode = createDefaultCamera();
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
     * Creates and returns the default camera.
     *
     * @return The default camera
     */

    private Camera createDefaultCamera()
    {
        return new Camera();
    }


    /**
     * Sets the clear color. This is the color used for clearing the screen
     * before rendering a frame. The default clear color is black.
     *
     * @param clearColor
     *            The clear color to set. Must not be null.
     */

    public void setClearColor(final Color4f clearColor)
    {
        if (clearColor == null)
            throw new IllegalArgumentException("clearColor must be set");
        if (!this.clearColor.equals(clearColor))
        {
            this.clearColor = clearColor.asImmutable();
            // TODO Must reinit the scene rendering
        }
    }


    /**
     * Returns the current clear color.
     *
     * @return The clear color. Never null.
     */

    public Color4f getClearColor()
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
        // Calculate time delta
        final long now = System.nanoTime();
        final float delta = ((now - this.lastUpdate)) / 1000000000f;
        this.lastUpdate = now;

        // Initialize changed flag
        boolean changed = false;

        // Update nodes and update the changed-flag if needed
        if (this.rootNode != null)
            changed |= this.rootNode.update(delta);

        // Update animations if present
        if (this.animations != null && !this.animations.isEmpty())
        {
            for (final Animation animation : this.animations)
            {
                if (animation.getInputType() == AnimationInputType.TIME)
                {
                    animation.update(delta);
                    changed = true;
                }
            }
        }


        // Update textures and update the changed-flag if needed
        changed |= TextureManager.getInstance().update(delta);

        // Return true if scene was changed, false if not
        return changed;
    }


    /**
     * Sets the root scene node.
     *
     * @param rootNode
     *            The root scene node to set. Null for none.
     */

    public void setRootNode(final SceneNode rootNode)
    {
        if (rootNode != this.rootNode)
        {
            if (this.rootNode != null) this.rootNode.setScene(null);
            this.rootNode = rootNode;
            if (rootNode != null) rootNode.setScene(this);
        }
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
        final Color4f clearColor = this.clearColor;

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

        // Clean-up unused textures
        TextureManager.getInstance().cleanUp(gl);

        // Finish renderering
        gl.glFlush();
    }


    /**
     * Registers a node so it can be found with getElementById.
     *
     * @param node
     *            The node to register
     */

    void registerNode(final SceneNode node)
    {
        final String id = node.getId();
        if (id != null) this.nodes.put(id, node);
    }


    /**
     * Unregisters a node from the id-mapping.
     *
     * @param node
     *            The node to unregister
     */

    void unregisterNode(final SceneNode node)
    {
        final String id = node.getId();
        if (id != null) this.nodes.remove(id);
    }


    /**
     * Re-registers a node. Must be called when the ID of the node has changed.
     *
     * @param node
     *            The node to re-register
     * @param oldId
     *            The old id
     */

    void reregisterNode(final SceneNode node, final String oldId)
    {
        if (oldId != null) this.nodes.remove(oldId);
        final String id = node.getId();
        if (id != null) this.nodes.put(id, node);
    }


    /**
     * Returns the node with the specified id or null if not found.
     *
     * @param id
     *            The node id
     * @return The node or null if not found
     */

    public SceneNode getNodeById(final String id)
    {
        return this.nodes.get(id);
    }


    /**
     * Adds an animation.
     *
     * @param animation
     *            The animation to add
     */

    public void addAnimation(final Animation animation)
    {
        if (this.animations == null)
            this.animations = new ArrayList<Animation>();
        this.animations.add(animation);
    }


    /**
     * Removes an animation.
     *
     * @param animation
     *            The animation to remove.
     */

    public void removeAnimation(final Animation animation)
    {
        if (this.animations == null) return;
        this.animations.remove(animation);
    }


    /**
     * Returns the animation with the specified ID.
     *
     * @param id
     *            The animation ID.
     * @return The animation or null if not found.
     */

    public Animation getAnimationById(final String id)
    {
        for (final Animation animation : this.animations)
            if (id.equals(animation.getId())) return animation;
        return null;
    }
}
