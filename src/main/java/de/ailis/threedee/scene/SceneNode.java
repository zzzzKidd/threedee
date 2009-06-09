/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Transformable;
import de.ailis.threedee.scene.rendering.PolygonBuffer;
import de.ailis.threedee.scene.updater.NodeUpdater;


/**
 * A scene node. Can be used directly to create invisible group nodes or can be
 * extended to implement other node types.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SceneNode implements Iterable<SceneNode>, Transformable
{
    /** The list of connected node updaters */
    private final List<NodeUpdater> updaters = new ArrayList<NodeUpdater>();

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
    private Matrix4d transform = Matrix4d.identity();


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
            throw new IllegalArgumentException(
                "referenceNode must not be null");
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
     * @see java.lang.Iterable#iterator()
     */

    @Override
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
     *            The time elapsed since the last scene update (in nanoseconds)
     */

    public void update(final long delta)
    {
        for (final NodeUpdater updater: this.updaters)
        {
            updater.update(this, delta);
        }

        for (final SceneNode childNode: this)
        {
            childNode.update(delta);
        }
    }


    /**
     * Renders the node. Default implementation is doing nothing except calling
     * the render method of all child nodes.
     * 
     * @param buffer
     *            The polygon buffer
     * @param transform
     *            The current transformation
     */

    public void render(final PolygonBuffer buffer, final Matrix4d transform)
    {
        for (final SceneNode childNode: this)
        {
            childNode.render(buffer, transform.multiply(childNode
                .getTransform()));
        }
    }


    /**
     * @see Transformable#addTransform(Matrix4d)
     */

    @Override
    public void addTransform(final Matrix4d transform)
    {
        if (transform == null)
            throw new IllegalArgumentException("transform must not be null");
        this.transform = this.transform.multiply(transform);
    }


    /**
     * @see Transformable#rotateX(double)
     */

    @Override
    public void rotateX(final double r)
    {
        this.transform = this.transform.rotateX(r);
    }


    /**
     * @see Transformable#rotateY(double)
     */

    @Override
    public void rotateY(final double r)
    {
        this.transform = this.transform.rotateY(r);
    }


    /**
     * @see Transformable#rotateZ(double)
     */

    @Override
    public void rotateZ(final double r)
    {
        this.transform = this.transform.rotateZ(r);
    }


    /**
     * @see Transformable#scale(double, double, double)
     */

    @Override
    public void scale(final double sx, final double sy, final double sz)
    {
        this.transform = this.transform.scale(sx, sy, sz);
    }


    /**
     * @see Transformable#scaleX(double)
     */

    @Override
    public void scaleX(final double s)
    {
        this.transform = this.transform.scaleX(s);
    }


    /**
     * @see Transformable#scaleY(double)
     */

    @Override
    public void scaleY(final double s)
    {
        this.transform = this.transform.scaleY(s);
    }


    /**
     * @see Transformable#scaleZ(double)
     */

    @Override
    public void scaleZ(final double s)
    {
        this.transform = this.transform.scaleZ(s);
    }


    /**
     * @see Transformable#subTransform(Matrix4d)
     */

    @Override
    public void subTransform(final Matrix4d transform)
    {
        if (transform == null)
            throw new IllegalArgumentException("transform must not be null");
        this.transform = this.transform.divide(transform);
    }


    /**
     * @see Transformable#translate(double, double, double)
     */

    @Override
    public void translate(final double tx, final double ty, final double tz)
    {
        this.transform = this.transform.translate(tx, ty, tz);
    }


    /**
     * @see Transformable#translateX(double)
     */

    @Override
    public void translateX(final double t)
    {
        this.transform = this.transform.translateX(t);
    }


    /**
     * @see Transformable#translateY(double)
     */

    @Override
    public void translateY(final double t)
    {
        this.transform = this.transform.translateY(t);
    }


    /**
     * @see Transformable#translateZ(double)
     */

    @Override
    public void translateZ(final double t)
    {
        this.transform = this.transform.translateZ(t);
    }


    /**
     * Returns the effective transformation of this node by recursively
     * traversing the path up the root node and multiplying all found
     * transformations. Depending on the complexity of your scene graph this is
     * time-consuming so thing twice if you really need it.
     * 
     * @return The effective transformation
     */

    public final Matrix4d getEffectiveTransform()
    {
        if (this.parentNode != null)
            return this.parentNode.getEffectiveTransform().multiply(
                this.transform);
        else
            return this.transform;
    }


    /**
     * @see Transformable#getTransform()
     */

    public final Matrix4d getTransform()
    {
        return this.transform;
    }


    /**
     * @see Transformable#setTransform(Matrix4d)
     */

    public final void setTransform(final Matrix4d transform)
    {
        if (transform == null)
            throw new IllegalArgumentException("transform must not be null");
        this.transform = transform;
    }


    /**
     * Adds the specified node updater to this node.
     * 
     * @param updater
     *            The updater to add
     */

    public final void addUpdater(final NodeUpdater updater)
    {
        this.updaters.add(updater);
    }


    /**
     * Removes the specified node updater from this node.
     * 
     * @param updater
     *            The updater to remove
     */

    public final void removeUpdater(final NodeUpdater updater)
    {
        this.updaters.remove(updater);
    }
}
