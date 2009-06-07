/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.math

import java.awt.Graphics2D

/**
 * A node in the scene.
 */

@serializable
class Scene
{
    /** The root node of the scene */
    var rootNode: Node

    def render(buffer: Graphics2D)
    {
        renderNode(rootNode, buffer, Matrix4d.identity)
    }

    private def renderNode(node: Node, buffer: Graphics2D, transform: Matrix4d)
    {
        
    }
}
