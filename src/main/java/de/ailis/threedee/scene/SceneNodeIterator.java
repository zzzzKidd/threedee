/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.ailis.threedee.entities.SceneNode;


/**
 * A scene node iterator.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SceneNodeIterator implements Iterator<SceneNode>
{
    /** The current node */
    private SceneNode node;


    /**
     * Constructs a new node iterator starting with the specified node.
     *
     * @param node
     *            The starting node of the iterator
     */

    public SceneNodeIterator(final SceneNode node)
    {
        this.node = node;
    }


    /**
     * @see java.util.Iterator#hasNext()
     */

    public boolean hasNext()
    {
        return this.node != null;
    }


    /**
     * @see java.util.Iterator#next()
     */

    public SceneNode next()
    {
        final SceneNode node = this.node;
        if (node == null) throw new NoSuchElementException();
        this.node = this.node.getNextSibling();
        return node;
    }


    /**
     * @see java.util.Iterator#remove()
     */

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}