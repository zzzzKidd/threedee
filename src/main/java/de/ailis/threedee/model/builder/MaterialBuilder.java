/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.builder;

import de.ailis.threedee.model.Color;
import de.ailis.threedee.model.Material;


/**
 * This builder builds Material objects.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class MaterialBuilder
{
    /** The material name */
    private String name;

    /** The ambient color */
    private Color ambientColor;

    /** The diffuse color */
    private Color diffuseColor;

    /** The texture */
    private String texture;

    /** The specular color */
    private Color specularColor;

    /** The emission color */
    private Color emissionColor;

    /** The shininess */
    private float shininess;

    /** The alpha value */
    private float alpha;


    /**
     * Constructor
     */

    public MaterialBuilder()
    {
        reset();
    }


    /**
     * Resets the builder to default values.
     */

    public void reset()
    {
        final Material defMat = Material.DEFAULT;
        this.name = defMat.getId();
        this.ambientColor = defMat.getAmbientColor();
        this.specularColor = defMat.getSpecularColor();
        this.emissionColor = defMat.getEmissionColor();
        this.diffuseColor = defMat.getDiffuseColor();
        this.shininess = defMat.getShininess();
        this.alpha = 1;
        this.texture = null;
    }


    /**
     * Sets the material name
     *
     * @param name
     *            The material name to set
     */

    public void setId(final String id)
    {
        this.name = this.name;
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
     * Sets the texture.
     *
     * @param texture
     *            The texture to set
     */

    public void setTexture(final String texture)
    {
        this.texture = texture;
    }


    /**
     * Returns the texture.
     *
     * @return The texture
     */

    public String getTexture()
    {
        return this.texture;
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
     *            The specular color to set
     */

    public void setSpecularColor(final Color specularColor)
    {
        this.specularColor = specularColor;
    }


    /**
     * Returns the emission color.
     *
     * @return The emission color
     */

    public Color getEmissionColor()
    {
        return this.emissionColor;
    }


    /**
     * Sets the emission color.
     *
     * @param emissionColor
     *            The emission color to set
     */

    public void setEmissionColor(final Color emissionColor)
    {
        this.emissionColor = emissionColor;
    }


    /**
     * Returns the shininess.
     *
     * @return The shininess
     */

    public float getShininess()
    {
        return this.shininess;
    }


    /**
     * Sets the shininess.
     *
     * @param shininess
     *            The shininess to set
     */

    public void setShininess(final float shininess)
    {
        this.shininess = shininess;
    }


    /**
     * Returns the material name.
     *
     * @return The material name
     */

    public String getName()
    {
        return this.name;
    }


    /**
     * Sets the alpha value.
     *
     * @param alpha
     *            The alpha value
     */

    public void setAlpha(final float alpha)
    {
        this.alpha = alpha;
    }


    /**
     * Returns the alpha value.
     *
     * @return The alpha value
     */

    public float getAlpha()
    {
        return this.alpha;
    }


    /**
     * Builds the material and returns it.
     *
     * @return The material
     */

    public Material build()
    {
        return new Material(this.name,
                this.ambientColor.applyAlpha(this.alpha), this.diffuseColor
                        .applyAlpha(this.alpha), this.specularColor
                        .applyAlpha(this.alpha), this.emissionColor
                        .applyAlpha(this.alpha), this.shininess,
                this.texture);
    }
}
