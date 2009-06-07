/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.io.Serializable;

import de.ailis.threedee.math.Vector3d;


/**
 * A model
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface Model extends Serializable
{
    /**
     * Returns the number of vertices used in this model.
     * 
     * @return The number of vertices used in this model
     */

    public int countVertices();


    /**
     * Returns the vertex with the specified index.
     * 
     * @param index
     *            The index
     * @return The vertex
     */

    public Vector3d getVertex(int index);


    /**
     * Returns the number of polygons used in this model.
     * 
     * @return The number of polygons used in this model
     */

    public int countPolygons();


    /**
     * Returns the polygon with the specified index.
     * 
     * @param index
     *            The index
     * @return The polygon
     */

    public Polygon getPolygon(int index);


    /**
     * Returns the material of the model.
     * 
     * @return The material of the model. Never null
     */

    public Material getMaterial();


    /**
     * Returns the material of the specified polygon. If the polygon has no
     * specific material then the models material is returned.
     * 
     * @param polygon
     *            The polygon
     * @return The material of the polygon. Never null
     */

    public Material getMaterial(final Polygon polygon);

}
