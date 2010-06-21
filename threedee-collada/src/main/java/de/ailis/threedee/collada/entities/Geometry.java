/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A geometry.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Geometry implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The geometry id */
    private String id;

    /** The geometric element */
    private GeometricElement geometricElement;


    /**
     * Constructs a geometry without an id
     *
     * @param geometricElement
     *            The geometric element
     */

    public Geometry(final GeometricElement geometricElement)
    {
        this(null, geometricElement);
    }


    /**
     * Constructs a geometry with the given id.
     *
     * @param id
     *            The id
     * @param geometricElement
     *            The geometric element
     */

    public Geometry(final String id, final GeometricElement geometricElement)
    {
        this.id = id;
        this.geometricElement = geometricElement;
    }


    /**
     * Returns the geometry id.
     *
     * @return The geometry id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the geometry id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the geometric element.
     *
     * @return The geometric element. Never null.
     */

    public GeometricElement getGeometricElement()
    {
        return this.geometricElement;
    }


    /**
     * Sets the geometric element.
     *
     * @param geometricElement
     *            The geometric element to set. Must not be null
     */

    public void setGeometricElement(final GeometricElement geometricElement)
    {
        if (geometricElement == null)
            throw new IllegalArgumentException("geometricElement must be set");
        this.geometricElement = geometricElement;
    }


    /**
     * Checks if the geometric element is a mesh.
     *
     * @return True if geometric element is a mesh, false if not
     */

    public boolean isMesh()
    {
        return this.geometricElement instanceof ColladaMesh;
    }


    /**
     * Returns the mesh.
     *
     * @return The mesh
     * @throws IllegalStateException
     *             When geometric element is not a mesh
     */

    public ColladaMesh getMesh()
    {
        if (!isMesh())
            throw new IllegalStateException("Geometric element is not a mesh");
        return (ColladaMesh) this.geometricElement;
    }
}
