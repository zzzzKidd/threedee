/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * A mesh.
 *
 * TODO Enforce vertices and at least one datasource (Using a builder I think)
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaMesh implements GeometricElement
{
    /** Serial version UID */
    private static final long serialVersionUID = 3726911679563958092L;

    /** The sources */
    private final DataSources sources = new DataSources();

    /** The vertices */
    private Vertices vertices;

    /** The primitive groups */
    private final PrimitiveGroups primitiveGroups = new PrimitiveGroups();


    /**
     * Returns the sources.
     *
     * @return The sources
     */

    public DataSources getSources()
    {
        return this.sources;
    }


    /**
     * Returns the vertices.
     *
     * @return The vertices or null if not yet set
     */

    public Vertices getVertices()
    {
        return this.vertices;
    }


    /**
     * Sets the vertices.
     *
     * @param vertices
     *            The vertices to set
     */

    public void setVertices(final Vertices vertices)
    {
        this.vertices = vertices;
    }


    /**
     * Returns the primitive groups.
     *
     * @return The primitives groups. Never null. May be empty
     */

    public PrimitiveGroups getPrimitiveGroups()
    {
        return this.primitiveGroups;
    }
}
