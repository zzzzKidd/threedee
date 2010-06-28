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

public class Node implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The visual scene id */
    private String id;

    /** The node transformations */
    private final Transformations transformations = new Transformations();

    /** The list of child nodes */
    private final Nodes nodes = new Nodes();

    /** The instance geometries */
    private final InstanceGeometries instanceGeometries = new InstanceGeometries();

    /** The instance lights */
    private final InstanceLights instanceLights = new InstanceLights();

    /** The instance cameras */
    private final InstanceCameras instanceCameras = new InstanceCameras();


    /**
     * Constructs an visual scene without an id
     */

    public Node()
    {
        this(null);
    }


    /**
     * Constructs an visual scene with the given id.
     *
     * @param id
     *            The id
     */

    public Node(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the visual scene id.
     *
     * @return The visual scene id
     */

    @Override
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
     * Returns the list of transformations.
     *
     * @return The list of transformations. Never null. May be empty
     */

    public Transformations getTransformations()
    {
        return this.transformations;
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


    /**
     * Returns the instance geometries.
     *
     * @return The instance geometries
     */

    public InstanceGeometries getInstanceGeometries()
    {
        return this.instanceGeometries;
    }


    /**
     * Returns the instance lights.
     *
     * @return The instance lights
     */

    public InstanceLights getInstanceLights()
    {
        return this.instanceLights;
    }


    /**
     * Returns the instance cameras.
     *
     * @return The instance cameras
     */

    public InstanceCameras getInstanceCameras()
    {
        return this.instanceCameras;
    }
}
