/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.support.IdChangeListener;
import de.ailis.threedee.support.Identifiable;


/**
 * A camera
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Camera implements Identifiable<Camera>
{
    /** The camera id */
    private String id;

    /** The id change listeners */
    private final List<IdChangeListener<Camera>> idChangeListener = new ArrayList<IdChangeListener<Camera>>();


    /**
     * Creates a new light with default colors (White).
     */

    public Camera()
    {
        // Empty
    }

    /**
     * Constructs a new camera with the given id.
     *
     * @param id
     *            The camera id
     */

    public Camera(final String id)
    {
        this.id = id;
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#addIdChangeListener(de.ailis.threedee.support.IdChangeListener)
     */

    @Override
    public void addIdChangeListener(final IdChangeListener<Camera> listener)
    {
        this.idChangeListener.add(listener);
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#getId()
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#removeIdChangeListener(de.ailis.threedee.support.IdChangeListener)
     */

    @Override
    public void removeIdChangeListener(final IdChangeListener<Camera> listener)
    {
        this.idChangeListener.remove(listener);
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#setId(java.lang.String)
     */

    @Override
    public void setId(final String id)
    {
        final String oldId = this.id;
        this.id = id;
        idChanged(oldId);
    }


    /**
     * Informs listeners about a changed id.
     *
     * @param oldId
     *            The old id
     */

    private void idChanged(final String oldId)
    {
        for (final IdChangeListener<Camera> listener : this.idChangeListener)
        {
            listener.idChanged(this, oldId);
        }
    }
}