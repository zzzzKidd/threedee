/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.model.Color;
import de.ailis.threedee.support.IdChangeListener;
import de.ailis.threedee.support.Identifiable;


/**
 * Abstract base class for lights.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Light implements Identifiable<Light>
{
    /** The light id */
    private String id;

    /** The ambient color of the light */
    private Color ambientColor = Color.BLACK;

    /** The specular color of the light */
    private Color specularColor = Color.WHITE;

    /** The diffuse color of the light */
    private Color diffuseColor = Color.WHITE;

    /** The id change listeners */
    private final List<IdChangeListener<Light>> idChangeListener = new ArrayList<IdChangeListener<Light>>();


    /**
     * Creates a new light with default colors (White).
     */

    public Light()
    {
        this(Color.BLACK, Color.WHITE, Color.WHITE);
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public Light(final Color color)
    {
        this(Color.BLACK, color, color);
    }


    /**
     * Constructs a new light with the specified colors.
     *
     * @param ambientColor
     *            The ambient color
     * @param specularColor
     *            The specular color
     * @param diffuseColor
     *            The diffuse color
     */

    public Light(final Color ambientColor, final Color specularColor,
            final Color diffuseColor)
    {
        this.ambientColor = ambientColor;
        this.specularColor = specularColor;
        this.diffuseColor = diffuseColor;
    }


    /**
     * Returns the ambient color.
     *
     * @return The ambient color
     */

    public Color getAmbientColor()
    {
        return this.ambientColor;
    }


    /**
     * Sets the ambient color.
     *
     * @param ambientColor
     *            The ambient color to set
     */

    public void setAmbientColor(final Color ambientColor)
    {
        this.ambientColor = ambientColor;
    }


    /**
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color getSpecularColor()
    {
        return this.specularColor;
    }


    /**
     * Sets the specular color.
     *
     * @param specularColor
     *            The specularColor to set
     */

    public void setSpecularColor(final Color specularColor)
    {
        this.specularColor = specularColor;
    }


    /**
     * Returns the diffuse color.
     *
     * @return The diffuse color
     */

    public Color getDiffuseColor()
    {
        return this.diffuseColor;
    }


    /**
     * Sets the diffuse color.
     *
     * @param diffuseColor
     *            The diffuse color to set
     */

    public void setDiffuseColor(final Color diffuseColor)
    {
        this.diffuseColor = diffuseColor;
    }


    /**
     * @see de.ailis.threedee.support.Identifiable#addIdChangeListener(de.ailis.threedee.support.IdChangeListener)
     */

    @Override
    public void addIdChangeListener(final IdChangeListener<Light> listener)
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
    public void removeIdChangeListener(final IdChangeListener<Light> listener)
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
        for (final IdChangeListener<Light> listener : this.idChangeListener)
        {
            listener.idChanged(this, oldId);
        }
    }


    /**
     * Sets the light color. This sets the ambient, diffuse and specular
     * color to the same value.
     *
     * @param color
     *            The color to set
     */

    public void setColor(final Color color)
    {
        setAmbientColor(Color.BLACK);
        setDiffuseColor(color);
        setSpecularColor(Color.BLACK);
    }
}