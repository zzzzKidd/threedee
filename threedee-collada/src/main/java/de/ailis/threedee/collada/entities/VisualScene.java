/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A visual scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class VisualScene implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The visual scene id */
    private String id;

    /** The list of child nodes */
    private final Nodes nodes = new Nodes();


    /**
     * Constructs an visual scene without an id
     */

    public VisualScene()
    {
        this(null);
    }


    /**
     * Constructs an visual scene with the given id.
     *
     * @param id
     *            The id
     */

    public VisualScene(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the visual scene id.
     *
     * @return The visual scene id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the visual scene id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }



    /**
     * Returns the list of child nodes.
     *
     * @return The list of child nodes
     */

    public Nodes getNodes()
    {
        return this.nodes;
    }
}
