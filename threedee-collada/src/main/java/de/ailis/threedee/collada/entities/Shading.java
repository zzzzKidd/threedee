/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * Base class for fixed shading information.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Shading implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The emission color */
    private ColorOrTexture emission;

    /** The ambient color */
    private ColorOrTexture ambient;

    /** The diffuse color */
    private ColorOrTexture diffuse;

    /** The specular color */
    private ColorOrTexture specular;

    /** The reflective color */
    private ColorOrTexture reflective;

    /** The transparent color */
    private ColorOrTexture transparent;

    /** The shininess */
    private Float shininess;

    /** The reflectivity */
    private Float reflectivity;

    /** The transparency */
    private Float transparency;

    /** The index of refraction */
    private Float indexOfRefraction;


    /**
     * Returns the emission color.
     *
     * @return The emission color or null if not specified
     */

    public ColorOrTexture getEmission()
    {
        return this.emission;
    }


    /**
     * Sets the emission color.
     *
     * @param emission
     *            The emission color to set. Null for unspecified
     */

    public void setEmission(final ColorOrTexture emission)
    {
        this.emission = emission;
    }


    /**
     * Returns the ambient color.
     *
     * @return The ambient color or null if not specified
     */

    public ColorOrTexture getAmbient()
    {
        return this.ambient;
    }


    /**
     * Sets the ambient color.
     *
     * @param ambient
     *            The ambient color to set. Null for unspecified
     */

    public void setAmbient(final ColorOrTexture ambient)
    {
        this.ambient = ambient;
    }


    /**
     * Returns the diffuse color.
     *
     * @return The diffuse color or null if not specified
     */

    public ColorOrTexture getDiffuse()
    {
        return this.diffuse;
    }


    /**
     * Sets the diffuse color.
     *
     * @param diffuse
     *            The diffuse color to set. Null for unspecified
     */

    public void setDiffuse(final ColorOrTexture diffuse)
    {
        this.diffuse = diffuse;
    }


    /**
     * Returns the specular color.
     *
     * @return The specular color or null if not specified
     */

    public ColorOrTexture getSpecular()
    {
        return this.specular;
    }


    /**
     * Sets the specular color.
     *
     * @param specular
     *            The specular color to set. Null for unspecified
     */

    public void setSpecular(final ColorOrTexture specular)
    {
        this.specular = specular;
    }


    /**
     * Returns the reflective color.
     *
     * @return The reflective color or null if not specified
     */

    public ColorOrTexture getReflective()
    {
        return this.reflective;
    }


    /**
     * Sets the reflective color.
     *
     * @param reflective
     *            The reflective color to set. Null for unspecified
     */

    public void setReflective(final ColorOrTexture reflective)
    {
        this.reflective = reflective;
    }


    /**
     * Returns the transparent color.
     *
     * @return The transparent color or null if not specified
     */

    public ColorOrTexture getTransparent()
    {
        return this.transparent;
    }


    /**
     * Sets the transparent color.
     *
     * @param transparent
     *            The transparent color to set. Null for unspecified
     */

    public void setTransparent(final ColorOrTexture transparent)
    {
        this.transparent = transparent;
    }


    /**
     * Returns the shininess.
     *
     * @return The shininess or null if not specified
     */

    public Float getShininess()
    {
        return this.shininess;
    }


    /**
     * Sets the shininess.
     *
     * @param shininess
     *            The shininess to set. Null for unspecified
     */

    public void setShininess(final Float shininess)
    {
        this.shininess = shininess;
    }


    /**
     * Returns the reflectivity.
     *
     * @return The reflectivity or null if not specified
     */

    public Float getReflectivity()
    {
        return this.reflectivity;
    }


    /**
     * Sets the reflectivity.
     *
     * @param reflectivity
     *            The reflectivity to set. Null for unspecified
     */

    public void setReflectivity(final Float reflectivity)
    {
        this.reflectivity = reflectivity;
    }


    /**
     * Returns the transparency.
     *
     * @return The transparency or null if not specified
     */

    public Float getTransparency()
    {
        return this.transparency;
    }


    /**
     * Sets the transparency.
     *
     * @param transpareancy
     *            The transparency to set. Null for unspecified
     */

    public void setTransparency(final Float transpareancy)
    {
        this.transparency = transpareancy;
    }


    /**
     * Returns the index of refraction.
     *
     * @return The index of refraction or null if not specified
     */

    public Float getIndexOfRefraction()
    {
        return this.indexOfRefraction;
    }


    /**
     * Sets the index of refraction.
     *
     * @param indexOfRefraction
     *            The index of refraction to set. Null for unspecified
     */

    public void setIndexOfRefraction(final Float indexOfRefraction)
    {
        this.indexOfRefraction = indexOfRefraction;
    }
}
