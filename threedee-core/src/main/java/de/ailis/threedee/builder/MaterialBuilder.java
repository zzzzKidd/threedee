/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.builder;

import de.ailis.threedee.entities.Material;
import de.ailis.threedee.model.Color;


/**
 * Builds a material
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class MaterialBuilder
{
    /** Material ID */
    private String id;

    /** The emission color */
    private Color emissionColor;

    /** The ambient color */
    private Color ambientColor;

    /** The diffuse color */
    private Color diffuseColor;

    /** The diffuse texture */
    private String diffuseTexture;

    /** The specular color */
    private Color specularColor;

    /** The shininess */
    private float shininess;


    /**
     * Constructs a new material builder.
     */

    public MaterialBuilder()
    {
        reset();
    }


    /**
     * Resets the builder.
     *
     * @return This builder for chaining
     */

    public MaterialBuilder reset()
    {
        final Material defaultMaterial = Material.DEFAULT;
        this.ambientColor = defaultMaterial.getAmbientColor();
        this.emissionColor = defaultMaterial.getEmissionColor();
        this.diffuseColor = defaultMaterial.getDiffuseColor();
        this.specularColor = defaultMaterial.getSpecularColor();
        this.diffuseTexture = defaultMaterial.getDiffuseTexture();
        this.shininess = defaultMaterial.getShininess();
        return this;
    }


    /**
     * Sets the id.
     *
     * @param id
     *            The id to set
     * @return This builder for chaining
     */

    public MaterialBuilder setId(final String id)
    {
        this.id = id;
        return this;
    }


    /**
     * Sets the emissionColor.
     *
     * @param emissionColor
     *            The emissionColor to set
     * @return This builder for chaining
     */

    public MaterialBuilder setEmissionColor(final Color emissionColor)
    {
        this.emissionColor = emissionColor;
        return this;
    }


    /**
     * Sets the ambientColor.
     *
     * @param ambientColor
     *            The ambientColor to set
     * @return This builder for chaining
     */

    public MaterialBuilder setAmbientColor(final Color ambientColor)
    {
        this.ambientColor = ambientColor;
        return this;
    }


    /**
     * Sets the diffuseColor.
     *
     * @param diffuseColor
     *            The diffuseColor to set
     * @return This builder for chaining
     */

    public MaterialBuilder setDiffuseColor(final Color diffuseColor)
    {
        this.diffuseColor = diffuseColor;
        return this;
    }


    /**
     * Sets the diffuseTexture.
     *
     * @param diffuseTexture
     *            The diffuseTexture to set
     * @return This builder for chaining
     */

    public MaterialBuilder setDiffuseTexture(final String diffuseTexture)
    {
        this.diffuseTexture = diffuseTexture;
        return this;
    }


    /**
     * Sets the specularColor.
     *
     * @param specularColor
     *            The specularColor to set
     * @return This builder for chaining
     */

    public MaterialBuilder setSpecularColor(final Color specularColor)
    {
        this.specularColor = specularColor;
        return this;
    }


    /**
     * Sets the shininess.
     *
     * @param shininess
     *            The shininess to set
     * @return This builder for chaining
     */

    public MaterialBuilder setShininess(final float shininess)
    {
        this.shininess = shininess;
        return this;
    }

    /**
     * Builds the material. The builder stays untouched so you can continue
     * changing values and building additional materials with it. To reset
     * the builder use the reset() method.
     *
     * @return The build material
     */

    public Material build()
    {
        return new Material(this.id, this.ambientColor, this.diffuseColor,
                this.specularColor, this.emissionColor, this.shininess,
                this.diffuseTexture);
    }
}
