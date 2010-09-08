/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.AssetType;
import de.ailis.threedee.events.SceneListener;
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

public class Scene extends Asset
{
    /** The root scene node */
    private SceneNode rootNode;

    /** The used camera */
    private Camera cameraNode;

    /** The list with scene listeners */
    private List<SceneListener> sceneListeners;

    /** The ID-to-node mapping */
    private final Map<String, SceneNode> nodes = new HashMap<String, SceneNode>();

    /** The list with animations */
    private List<Animation> animations = null;

    /** The viewport this scene is currently connected to. */
    private Viewport viewport;


    /**
     * Constructs a new scene.
     *
     * @param id
     *            The asset ID
     */

    public Scene(final String id)
    {
        super(id, AssetType.SCENE);
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
     * Updates the scene. Call this regularly to enable animations.
     *
     * @param delta
     *            The time elapsed since the last scene update (in seconds)
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean update(final float delta)
    {
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
                    changed |= animation.update(delta);
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
     * Renders the scene.
     *
     * @param viewport
     *            The viewport
     */

    public void render(final Viewport viewport)
    {
        // Only process camera and nodes when a root node is set
        final SceneNode rootNode = this.rootNode;
        if (rootNode != null)
        {
            // Apply camera transformation if camera is present
            if (this.cameraNode != null) this.cameraNode.apply(viewport);

            // Render root node
            rootNode.renderAll(viewport);

            // Remove camera transformation if camera is present
            if (this.cameraNode != null) this.cameraNode.remove(viewport);
        }
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
        if (this.animations == null) return null;
        for (final Animation animation : this.animations)
            if (id.equals(animation.getId())) return animation;
        return null;
    }


    /**
     * Adds a node listener.
     *
     * @param listener
     *            The node listener to add
     */

    public void addSceneListener(final SceneListener listener)
    {
        if (this.sceneListeners == null)
            this.sceneListeners = new ArrayList<SceneListener>();
        this.sceneListeners.add(listener);
    }


    /**
     * Removes a node listener.
     *
     * @param listener
     *            The node listener to remove
     */

    public void removeSceneListener(final SceneListener listener)
    {
        if (this.sceneListeners == null) return;
        this.sceneListeners.remove(listener);
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "Scene " + getId();
    }


    /**
     * Fires the sceneRemovedFromViewport event.
     */

    private void fireSceneRemovedFromViewport()
    {
        if (this.sceneListeners == null) return;
        for (final SceneListener listener : this.sceneListeners)
            listener.sceneRemovedFromViewport();
    }


    /**
     * Fires the sceneInsertedIntoViewport event.
     */

    private void fireSceneInsertedIntoViewport()
    {
        if (this.sceneListeners == null) return;
        for (final SceneListener listener : this.sceneListeners)
            listener.sceneInsertedIntoViewport();
    }


    /**
     * Sets the viewport. This is called internally, don't do it yourself.
     *
     * @param viewport
     *            The viewport to set.
     */

    public void setViewport(final Viewport viewport)
    {
        // Do nothing if viewport has not changed
        if (viewport == this.viewport) return;

        // Detach from old viewport
        if (this.viewport != null)
        {
            final Viewport oldViewport = this.viewport;
            fireSceneRemovedFromViewport();
            this.viewport = null;
            oldViewport.setScene(null);
        }

        // Attach to new viewport
        this.viewport = viewport;
        if (viewport != null)
        {
            viewport.setScene(this);
            fireSceneInsertedIntoViewport();
        }
    }


    /**
     * Returns the viewport this scene is currently connected to.
     *
     * @return The viewport. May be null if scene is not displayed.
     */

    public Viewport getViewport()
    {
        return this.viewport;
    }


    /**
     * Checks if scene is currently displayed in a viewport.
     *
     * @return True if scene has a viewport, false if not.
     */

    public boolean hasViewport()
    {
        return this.viewport != null;
    }
}
