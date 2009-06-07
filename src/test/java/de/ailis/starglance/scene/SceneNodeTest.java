/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.scene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.scene.SceneNode;


/**
 * Tests the SceneNode class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SceneNodeTest
{
    /**
     * Tests empty node.
     */

    @Test
    public void testEmpty()
    {
        final SceneNode node = new SceneNode();
        assertNull(node.getFirstChild());
        assertNull(node.getLastChild());
        assertNull(node.getParentNode());
        assertNull(node.getNextSibling());
        assertNull(node.getPreviousSibling());
        assertEquals(Matrix4d.identity(), node.getTransform());
        assertFalse(node.hasChildNodes());
    }


    /**
     * Tests appendChild method with one child
     */

    @Test
    public void testAppendOneChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of parent
        assertNull(child1.getPreviousSibling());
        assertSame(child1, parent.getFirstChild());
        assertSame(child1, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests appendChild method with two children.
     */

    @Test
    public void testAppendTwoChildren()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertNull(child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child2, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests appending the first child again as the third child (Becoming the
     * new second child because the first child is removed)
     */

    @Test
    public void testAppendFirstChildAsThirdChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);

        // Move first node to the end
        parent.appendChild(child1);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertSame(child2, child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child1, child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of parent node
        assertSame(child2, parent.getFirstChild());
        assertSame(child1, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }

    
    /**
     * Tests sanity checks on appendChild method.
     */

    @Test
    public void testAppendChildChecks()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        
        // Tests passing null as child
        try
        {
            parent.appendChild(null);
            fail("node of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }

        // Tests passing parent as new child
        try
        {
            parent.appendChild(parent);
            fail("Using parent as newNode must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
    }

    
    /**
     * Tests insertBefore method where insert position is also the first
     * position.
     */

    @Test
    public void testInsertBeforeAtFirst()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.insertBefore(child2, child1);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertSame(child2, child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child1, child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of parent node
        assertSame(child2, parent.getFirstChild());
        assertSame(child1, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests insertBefore method where insert position is also between the first
     * and last position.
     */

    @Test
    public void testInsertBeforeAtMiddle()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        final SceneNode child2 = new SceneNode();
        parent.insertBefore(child2, child3);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child3, child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child2, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests insertBefore method inserting the last child in front of the first
     * child.
     */

    @Test
    public void testInsertBeforeLastChildAsFirst()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);

        // Move last node to the beginning
        parent.insertBefore(child2, child1);

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertSame(child2, child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child1, child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of parent node
        assertSame(child2, parent.getFirstChild());
        assertSame(child1, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }
    
    
    /**
     * Tests sanity checks on insertBefore method.
     */

    @Test
    public void testInsertBeforeChecks()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child = new SceneNode();
        final SceneNode realChild = new SceneNode();
        parent.appendChild(realChild);
        
        // Tests passing null as newNode
        try
        {
            parent.insertBefore(null, realChild);
            fail("newNode of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
        
        // Tests passing null as referenceNode
        try
        {
            parent.insertBefore(child, null);
            fail("referenceNode of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
        
        // Tests passing a non-child as referenceNode
        try
        {
            parent.insertBefore(child, child);
            fail("referenceNode which is not a child must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }

        // Tests passing parent as newNode
        try
        {
            parent.insertBefore(parent, realChild);
            fail("Using parent as newNode must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
    }

    
    /**
     * Tests removeChild method by removing the middle child.
     */

    @Test
    public void testRemoveMiddleChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        // Remove the child
        parent.removeChild(child2);
        
        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child3, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertNull(child2.getParentNode());
        assertNull(child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child1, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }

    
    /**
     * Tests removeChild method by removing the first child.
     */

    @Test
    public void testRemoveFirstChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        // Remove the child
        parent.removeChild(child1);
        
        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertNull(child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child3, child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child2, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child2, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }

    
    /**
     * Tests removeChild method by removing the last child.
     */

    @Test
    public void testRemoveLastChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        // Remove the child
        parent.removeChild(child3);
        
        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertNull(child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertNull(child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertNull(child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child2, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests removeChild method by removing all children.
     */

    @Test
    public void testRemoveAllChildren()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        // Remove the child
        parent.removeChild(child3);
        parent.removeChild(child1);
        parent.removeChild(child2);
        
        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertNull(child1.getParentNode());
        assertNull(child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertNull(child2.getParentNode());
        assertNull(child2.getNextSibling());
        assertNull(child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertNull(child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertNull(child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertNull(parent.getFirstChild());
        assertNull(parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertFalse(parent.hasChildNodes());
    }

    
    /**
     * Tests sanity checks on removeChild method.
     */

    @Test
    public void testRemoveChildChecks()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        
        // Tests passing null as child
        try
        {
            parent.removeChild(null);
            fail("node of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }

        // Tests passing non-existent child
        try
        {
            parent.removeChild(parent);
            fail("node which is not a child must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
    }

    
    /**
     * Tests replaceChild method by replacing the middle child
     */

    @Test
    public void testReplaceMiddleChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode oldChild = new SceneNode();
        parent.appendChild(oldChild);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        final SceneNode child2 = new SceneNode();        
        parent.replaceChild(oldChild, child2);
        
        // Validate state of replaced child node
        assertNull(oldChild.getFirstChild());
        assertNull(oldChild.getLastChild());
        assertNull(oldChild.getParentNode());
        assertNull(oldChild.getNextSibling());
        assertNull(oldChild.getPreviousSibling());
        assertFalse(oldChild.hasChildNodes());

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child3, child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child2, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests replaceChild method by replacing the first child
     */

    @Test
    public void testReplaceFirstChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode oldChild = new SceneNode();
        parent.appendChild(oldChild);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode child3 = new SceneNode();
        parent.appendChild(child3);
        
        final SceneNode child1 = new SceneNode();        
        parent.replaceChild(oldChild, child1);
        
        // Validate state of replaced child node
        assertNull(oldChild.getFirstChild());
        assertNull(oldChild.getLastChild());
        assertNull(oldChild.getParentNode());
        assertNull(oldChild.getNextSibling());
        assertNull(oldChild.getPreviousSibling());
        assertFalse(oldChild.hasChildNodes());

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child3, child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child2, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests replaceChild method by replacing the last child
     */

    @Test
    public void testReplaceLastChild()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child1 = new SceneNode();
        parent.appendChild(child1);
        final SceneNode child2 = new SceneNode();
        parent.appendChild(child2);
        final SceneNode oldChild = new SceneNode();
        parent.appendChild(oldChild);
        
        final SceneNode child3 = new SceneNode();        
        parent.replaceChild(oldChild, child3);
        
        // Validate state of replaced child node
        assertNull(oldChild.getFirstChild());
        assertNull(oldChild.getLastChild());
        assertNull(oldChild.getParentNode());
        assertNull(oldChild.getNextSibling());
        assertNull(oldChild.getPreviousSibling());
        assertFalse(oldChild.hasChildNodes());

        // Validate state of first child node
        assertNull(child1.getFirstChild());
        assertNull(child1.getLastChild());
        assertSame(parent, child1.getParentNode());
        assertSame(child2, child1.getNextSibling());
        assertNull(child1.getPreviousSibling());
        assertFalse(child1.hasChildNodes());

        // Validate state of second child node
        assertNull(child2.getFirstChild());
        assertNull(child2.getLastChild());
        assertSame(parent, child2.getParentNode());
        assertSame(child3, child2.getNextSibling());
        assertSame(child1, child2.getPreviousSibling());
        assertFalse(child2.hasChildNodes());

        // Validate state of third child node
        assertNull(child3.getFirstChild());
        assertNull(child3.getLastChild());
        assertSame(parent, child3.getParentNode());
        assertNull(child3.getNextSibling());
        assertSame(child2, child3.getPreviousSibling());
        assertFalse(child3.hasChildNodes());

        // Validate state of parent node
        assertSame(child1, parent.getFirstChild());
        assertSame(child3, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }


    /**
     * Tests replaceChild method by replacing a node with itself.
     */

    @Test
    public void testReplaceSame()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child = new SceneNode();
        parent.appendChild(child);
        parent.replaceChild(child, child);
        
        // Validate state of first child node
        assertNull(child.getFirstChild());
        assertNull(child.getLastChild());
        assertSame(parent, child.getParentNode());
        assertNull(child.getNextSibling());
        assertNull(child.getPreviousSibling());
        assertFalse(child.hasChildNodes());

        // Validate state of parent node
        assertSame(child, parent.getFirstChild());
        assertSame(child, parent.getLastChild());
        assertNull(parent.getParentNode());
        assertNull(parent.getNextSibling());
        assertNull(parent.getPreviousSibling());
        assertTrue(parent.hasChildNodes());
    }

    
    /**
     * Tests sanity checks on replace child.
     */

    @Test
    public void testReplaceChildChecks()
    {
        // Create the nodes
        final SceneNode parent = new SceneNode();
        final SceneNode child = new SceneNode();
        final SceneNode realChild = new SceneNode();
        parent.appendChild(realChild);
        
        // Tests passing null as oldNode
        try
        {
            parent.replaceChild(null, child);
            fail("oldNode of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
        
        // Tests passing null as newNode
        try
        {
            parent.replaceChild(realChild, null);
            fail("newNode of null must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }

        
        // Tests passing a non-child as oldNode
        try
        {
            parent.replaceChild(child, child);
            fail("oldNode which is not a child must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }

        // Tests passing parent as new child
        try
        {
            parent.replaceChild(realChild, parent);
            fail("Using parent as newNode must throw exception");
        }
        catch (final IllegalArgumentException e)
        {
            // Fine
        }
    }
}
