/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.model

import de.ailis.starglance.math.Vector3d


/**
 * This trait must be implemented by all classes which represent models.
 */

trait Model
{
    /**
     * Returns the number of vertices of this model
     */

    def vertices: Int


    /**
     * Returns the number of areas of this model
     */

    def areas: Int


    /**
     * Returns the vertex with the specified index.
     *
     * @param index
     *            The index
     * @return The vertex
     */

    def vertex(index: int): Vector3d


    /**
     * Returns the area with the specified index.
     *
     * @param index
     *            The index
     * @param The area
     */

    def area(index: Int): Area
}
