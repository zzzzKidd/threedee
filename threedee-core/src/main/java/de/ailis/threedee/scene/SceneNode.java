/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.ailis.threedee.entities.Viewport;
import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.math.Transformable;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.physics.Physics;
import de.ailis.threedee.properties.NodeProperty;
import de.ailis.threedee.rendering.opengl.GL;


/**
 * A scene node. Can be used directly to create invisible group nodes or can be
 * extended to implement other node types.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public abstract class SceneNode implements Iterable<SceneNode>, Transformable
{
    /** The parent node. Can be null if there is none. */
    private SceneNode parentNode;

    /** The next sibling node. Can be null if there is none. */
    private SceneNode nextSibling;

    /** The previous sibling node. Can be null if there is none. */
    private SceneNode previousSibling;

    /** The first child node. Can be null if there is none. */
    private SceneNode firstChild;

    /** The last child node. Can be null if there is none. */
    private SceneNode lastChild;

    /** The transformation of this node */
    private Matrix4f transform = Matrix4f.identity();

    /** Cached scene transformation of this node */
    private final Matrix4f sceneTransform = Matrix4f.identity();

    /** If cached scene transformation is valid */
    private boolean sceneTransformValid = false;

    /** The physics of this node. */
    private final Physics physics = new Physics();

    /** The lights which should illuminate this tree branch */
    private List<Light> lights;

    /** The node properties */
    private List<NodeProperty> properties;


    /**
     * Constructs a new scene node.
     */

    public SceneNode()
    {
        // Empty
    }


    /**
     * Appends the specified node as a child node to this node. If the node was
     * previously connected to a different parent node then it is first
     * disconnected from this parent.
     *
     * @param node
     *            The node to append to this node
     */

    public final void appendChild(final SceneNode node)
    {
        if (node == null)
            throw new IllegalArgumentException("node must not be null");
        if (node == this)
            throw new IllegalArgumentException(
                    "node can not be a child of itself");

        // Remove from old parent if there is one
        final SceneNode oldParent = node.parentNode;
        if (oldParent != null) oldParent.removeChild(node);

        // Append the child
        node.previousSibling = this.lastChild;
        if (this.lastChild != null) this.lastChild.nextSibling = node;
        this.lastChild = node;
        if (this.firstChild == null) this.firstChild = node;
        node.parentNode = this;
    }


    /**
     * Returns the first child node of this node. If the node has no child nodes
     * then null is returned.
     *
     * @return The first child node or null if there are no child nodes
     */

    public final SceneNode getFirstChild()
    {
        return this.firstChild;
    }


    /**
     * Returns the last child node of this node. If the node has no child nodes
     * then null is returned.
     *
     * @return The last child node or null if there are no child nodes
     */

    public final SceneNode getLastChild()
    {
        return this.lastChild;
    }


    /**
     * Returns the next sibling of this node. If the node is the last child node
     * of its parent then null is returned because there can't be a next
     * sibling.
     *
     * @return The next sibling of this node or null if there is no next sibling
     */

    public final SceneNode getNextSibling()
    {
        return this.nextSibling;
    }


    /**
     * Returns the parent node. If the node has no root node yet then null is
     * returned.
     *
     * @return The parent node or null if there is none
     */

    public final SceneNode getParentNode()
    {
        return this.parentNode;
    }


    /**
     * Returns the previous sibling of this node. If the node is the first child
     * node of its parent then null is returned because there can't be a
     * previous sibling.
     *
     * @return The previous sibling of this node or null if there is no previous
     *         sibling
     */

    public final SceneNode getPreviousSibling()
    {
        return this.previousSibling;
    }


    /**
     * Checks if this node has child nodes.
     *
     * @return True if this node has child nodes, false if not
     */

    public final boolean hasChildNodes()
    {
        return this.firstChild != null;
    }


    /**
     * Inserts a new child node before the specified reference node. If the new
     * node was already connected to a parent then it is disconnected from this
     * parent first.
     *
     * @param newNode
     *            The new node to insert
     * @param referenceNode
     *            The reference node
     */

    public final void insertBefore(final SceneNode newNode,
            final SceneNode referenceNode)
    {
        if (newNode == null)
            throw new IllegalArgumentException("newNode must not be null");
        if (referenceNode == null)
            throw new IllegalArgumentException("referenceNode must not be null");
        if (newNode == this)
            throw new IllegalArgumentException(
                    "newNode can not be a child of itself");

        // Verify that reference node is our child
        if (referenceNode.parentNode != this)
            throw new IllegalArgumentException(
                    "Reference node is not my child node");

        // Remove from old parent if there is one
        final SceneNode oldParent = newNode.parentNode;
        if (oldParent != null) oldParent.removeChild(newNode);

        // Insert the node
        final SceneNode oldPrevious = referenceNode.getPreviousSibling();
        if (oldPrevious == null)
            this.firstChild = newNode;
        else
            oldPrevious.nextSibling = newNode;
        referenceNode.previousSibling = newNode;
        newNode.previousSibling = oldPrevious;
        newNode.nextSibling = referenceNode;
        newNode.parentNode = this;
    }


    /**
     * Removes the specified child node from this node.
     *
     * @param node
     *            The node to remove
     */

    public final void removeChild(final SceneNode node)
    {
        if (node == null)
            throw new IllegalArgumentException("node must not be null");

        // Verify that node is our child
        if (node.parentNode != this)
            throw new IllegalArgumentException("node is not my child node");

        // Remove node from linked list
        final SceneNode next = node.nextSibling;
        final SceneNode prev = node.previousSibling;
        if (next != null) next.previousSibling = prev;
        if (prev != null) prev.nextSibling = next;

        // Correct first/last reference
        if (node == this.firstChild) this.firstChild = next;
        if (node == this.lastChild) this.lastChild = prev;

        // Remove all references from node
        node.parentNode = null;
        node.nextSibling = null;
        node.previousSibling = null;
    }


    /**
     * Replaces the specified old node with the specified new node. If the new
     * node was already connected to a parent then it is disconnected from this
     * parent first.
     *
     * @param oldNode
     *            The old node to be replaced by the new one
     * @param newNode
     *            The new node to replace the old one
     */

    public final void replaceChild(final SceneNode oldNode,
            final SceneNode newNode)
    {
        if (newNode == null)
            throw new IllegalArgumentException("node must not be null");
        if (oldNode == null)
            throw new IllegalArgumentException("node must not be null");
        if (newNode == this)
            throw new IllegalArgumentException(
                    "node can not be a child of itself");

        // Verify that old node is our child
        if (oldNode.parentNode != this)
            throw new IllegalArgumentException("node is not my child node");

        // new node is the same as the old node then do nothing
        if (newNode == oldNode) return;

        final SceneNode next = oldNode.nextSibling;
        removeChild(oldNode);
        if (next == null)
            appendChild(newNode);
        else
            insertBefore(newNode, next);
    }


    /**
     * Returns an iterator for all child nodes.
     *
     * @return The iterator
     */

    public final Iterator<SceneNode> iterator()
    {
        return new SceneNodeIterator(this.firstChild);
    }


    /**
     * Updates the node with the specified time delta. Default implementation is
     * executing the connected node updaters and calling the update method of
     * all child nodes.
     *
     * @param delta
     *            The time elapsed since the last scene update (in seconds)
     * @return True if the scene needs to be rendered again, false if not
     */

    public boolean update(final float delta)
    {
        // Invalidate scene transformation cache
        this.sceneTransformValid = false;

        boolean changed = false;
        if (this.physics != null) changed |= this.physics.update(this, delta);

        for (final SceneNode childNode : this)
            changed |= childNode.update(delta);

        return changed;
    }


    /**
     * @see Transformable#addTransform(Matrix4f)
     */

    public void addTransform(final Matrix4f transform)
    {
        if (transform == null)
            throw new IllegalArgumentException("transform must not be null");
        this.transform = this.transform.multiply(transform);
    }


    /**
     * @see Transformable#rotate(Vector3f, float)
     */

    public void rotate(final Vector3f v, final float r)
    {
        this.transform = this.transform.rotate(v, r);
    }


    /**
     * @see Transformable#rotateX(float)
     */

    public void rotateX(final float r)
    {
        this.transform = this.transform.rotateX(r);
    }


    /**
     * @see Transformable#rotateY(float)
     */

    public void rotateY(final float r)
    {
        this.transform = this.transform.rotateY(r);
    }


    /**
     * @see Transformable#rotateZ(float)
     */

    public void rotateZ(final float r)
    {
        this.transform = this.transform.rotateZ(r);
    }


    /**
     * @see Transformable#scale(float, float, float)
     */

    public void scale(final float sx, final float sy, final float sz)
    {
        this.transform = this.transform.scale(sx, sy, sz);
    }


    /**
     * @see Transformable#scale(float)
     */

    public void scale(final float s)
    {
        this.transform = this.transform.scale(s);
    }


    /**
     * @see Transformable#scaleX(float)
     */

    public void scaleX(final float s)
    {
        this.transform = this.transform.scaleX(s);
    }


    /**
     * @see Transformable#scaleY(float)
     */

    public void scaleY(final float s)
    {
        this.transform = this.transform.scaleY(s);
    }


    /**
     * @see Transformable#scaleZ(float)
     */

    public void scaleZ(final float s)
    {
        this.transform = this.transform.scaleZ(s);
    }


    /**
     * @see Transformable#translate(float, float, float)
     */

    public void translate(final float tx, final float ty, final float tz)
    {
        this.transform = this.transform.translate(tx, ty, tz);
    }


    /**
     * @see Transformable#translateX(float)
     */

    public void translateX(final float t)
    {
        this.transform = this.transform.translateX(t);
    }


    /**
     * @see Transformable#translateY(float)
     */

    public void translateY(final float t)
    {
        this.transform = this.transform.translateY(t);
    }


    /**
     * @see Transformable#translateZ(float)
     */

    public void translateZ(final float t)
    {
        this.transform = this.transform.translateZ(t);
    }


    /**
     * @see Transformable#getTransform()
     */

    public final Matrix4f getTransform()
    {
        return this.transform;
    }


    /**
     * Returns the scene transformation of this node. This is the
     * transformation matrix of the node relative to the scene.
     *
     * @return The scene transformation
     */

    public final Matrix4f getSceneTransform()
    {
        // If a cached scene transformation is present then use that
        if (this.sceneTransformValid) return this.sceneTransform;

        // If node has no parent node then the local transformation is the
        // scene transformation
        if (this.parentNode == null)
        {
            this.sceneTransform.set(this.transform);
        }
        else
        {
            // Calculate the scene transformation by multiplying the parent
            // scene transformation with the local transformation
            this.sceneTransform.set(this.parentNode.getSceneTransform());
            if (!this.transform.isIdentity())
                this.sceneTransform.multiply(this.transform);
        }

        // Mark scene transform cache as valid
        this.sceneTransformValid = true;

        // Return The scene transformation
        return this.sceneTransform;
    }


    /**
     * @see Transformable#setTransform(Matrix4f)
     */

    public final void setTransform(final Matrix4f transform)
    {
        if (transform == null)
            throw new IllegalArgumentException("transform must not be null");
        this.transform = transform;
    }


    /**
     * Returns the physics of this scene node.
     *
     * @return The physics or null if none set yet
     */

    public Physics getPhysics()
    {
        return this.physics;
    }


    /**
     * Adds a node property.
     *
     * @param property
     *            The property to add
     */

    public void addProperty(final NodeProperty property)
    {
        if (this.properties == null)
            this.properties = new ArrayList<NodeProperty>();
        this.properties.add(property);
    }


    /**
     * Returns node properties. May be empty if no node properties have been
     * set.
     *
     * @return The node properties
     */

    public List<NodeProperty> getProperties()
    {
        return this.properties;
    }


    /**
     * Removes a node property.
     *
     * @param property
     *            The property to remove
     */

    public void removeProperty(final NodeProperty property)
    {
        if (this.properties == null) return;
        this.properties.remove(property);
    }


    /**
     * @see de.ailis.threedee.math.Transformable#transform(de.ailis.threedee.math.Matrix4f)
     */

    @Override
    public void transform(final Matrix4f m)
    {
        this.transform.multiply(m);
    }


    /**
     * @see de.ailis.threedee.math.Transformable#transform(float[])
     */

    @Override
    public void transform(final float... m)
    {
        this.transform.multiply(m);
    }


    /**
     * Recursively applies light transformation.
     *
     * @param gl
     *            The GL context
     * @param node
     *            The scene node the light node should illuminate
     * @param light
     *            The light node
     */

    private void applyLightTransform(final GL gl, final SceneNode node,
            final SceneNode light)
    {
        // Create some shortcuts
        final SceneNode parentNode = light.getParentNode();

        // Apply next parent transform
        if (parentNode != null && parentNode != node)
            applyLightTransform(gl, node, parentNode);

        // Apply current transform
        gl.glMultMatrix(light.getTransform().getBuffer());
    }


    /**
     * Renders the node with all its properties and lights and child nodes.
     *
     * @param viewport
     *            The viewport
     */

    public void renderAll(final Viewport viewport)
    {
        // Get some shortcuts
        final GL gl = viewport.getGL();
        final List<NodeProperty> properties = this.properties;
        final Matrix4f transform = this.transform;

        boolean identity;

        // Apply node properties
        if (properties != null) for (final NodeProperty property : properties)
            property.apply(gl);

        // If transformation is used then apply it
        identity = transform.isIdentity();
        if (!identity)
        {
            gl.glPushMatrix();
            gl.glMultMatrix(transform.getBuffer());
        }

        // Apply lights
        if (this.lights != null)
        {
            for (final Light light : this.lights)
            {
                gl.glPushMatrix();
                applyLightTransform(gl, this, light);
                light.apply(viewport);
                gl.glPopMatrix();
            }
        }

        // Renders the node itself
        render(viewport);

        // Render the node and the child nodes
        for (final SceneNode childNode : this)
            childNode.renderAll(viewport);

        // Remove lights
        if (this.lights != null) for (final Light light : this.lights)
            light.remove(viewport);

        // If transformation is used then reset the old transformation
        if (!identity) gl.glPopMatrix();

        // Remove node properties
        if (properties != null) for (final NodeProperty property : properties)
            property.remove(gl);
    }


    /**
     * Renders the node itself.
     *
     * @param viewport
     *            The viewport
     */

    protected void render(final Viewport viewport)
    {
        // Empty
    }


    /**
     * Adds a light so it illuminates this node and all child nodes
     * (not the parent nodes).
     *
     * @param light
     *            The light to add
     */

    public void addLight(final Light light)
    {
        if (this.lights == null) this.lights = new ArrayList<Light>();
        this.lights.add(light);
    }


    /**
     * Removes a light so it no longer illuminates this node and its child
     * nodes.
     *
     * @param light
     *            The light to remove
     */

    public void removeLight(final Light light)
    {
        this.lights.remove(light);
    }
}
