/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.model

import java.awt.Color


/**
 * An area
 */

@serializable
class Area(ids: Int*)
{
    /** The color of the area */
    var color: Color = Color.WHITE;

    
    /**
     * Returns the number of vertex ids this area is referencing.
     *
     * @return The number of vertex ids
     */

    def vertexIds: Int = ids.length


    /**
     * Returns the vertex id with the specified index.
     *
     * @param index
     *            The inde4x
     * @return The vertex id
     */
    
    def vertexId(index: Int): Int = ids(index)
}
