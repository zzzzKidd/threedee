/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.model

import de.ailis.starglance.math.Vector3d


/**
 * A cube model
 */

@serializable
class Cube(sizeX: Double, sizeY: Double, sizeZ: Double) extends Model
{
    /** The vertices of the cube */
    private val vertices = Array(new Vector3d(-sizeX, sizeY, sizeZ),
                                 new Vector3d(-sizeX, sizeY, -sizeZ),
                                 new Vector3d(sizeX, sizeY, -sizeZ),
                                 new Vector3d(sizeX, sizeY, sizeZ),
                                 new Vector3d(-sizeX, -sizeY, sizeZ),
                                 new Vector3d(-sizeX, -sizeY, -sizeZ),
                                 new Vector3d(sizeX, -sizeY, -sizeZ),
                                 new Vector3d(sizeX, -sizeY, sizeZ))

    /** The areas of the cube */
    private val areas = Array(new Area(0, 1, 2, 3),
                              new Area(7, 3, 2, 6),
                              new Area(4, 7, 6, 5),
                              new Area(1, 0, 4, 5),
                              new Area(0, 3, 7, 4),
                              new Area(6, 3, 1, 5))                                 
}
