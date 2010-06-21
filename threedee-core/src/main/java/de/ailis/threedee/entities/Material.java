/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;



/**
 * A material.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Material
{
    /** The default material */
    public static final Material DEFAULT = new Material();

    /** Material ID */
    private final String id;

    /** The emission color */
    private final Color emissionColor;

    /** The ambient color */
    private final Color ambientColor;

    /** The diffuse color */
    private final Color diffuseColor;

    /** The diffuse texture */
    private final String diffuseTexture;

    /** The specular color */
    private final Color specularColor;

    /** The shininess */
    private final float shininess;


    /**
     * Constructs the default material.
     */

    private Material()
    {
        this("DEFAULT", new Color(0.2f, 0.2f, 0.2f),
                new Color(0.8f, 0.8f, 0.8f), Color.BLACK, Color.BLACK, 0, null);
    }


    /**
     * Constructs a material
     *
     * @param id
     *            The material name
     * @param ambientColor
     *            The ambient color
     * @param diffuseColor
     *            The diffuse color
     * @param specularColor
     *            The specular color
     * @param emissionColor
     *            The emission color
     * @param shininess
     *            The shininess
     * @param diffuseTexture
     *            The diffuse texture
     */

    public Material(final String id, final Color ambientColor,
            final Color diffuseColor, final Color specularColor,
            final Color emissionColor, final float shininess,
            final String diffuseTexture)
    {
        this.id = id;
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.diffuseTexture = diffuseTexture;
        this.specularColor = specularColor;
        this.emissionColor = emissionColor;
        this.shininess = shininess;
    }


    /**
     * Returns the material id.
     *
     * @return The material id
     */

    public String getId()
    {
        return this.id;
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
     * Returns the ambient color.
     *
     * @return The ambient color
     */

    public Color getAmbientColor()
    {
        return this.ambientColor;
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
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color getSpecularColor()
    {
        return this.specularColor;
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
     * Returns the diffuseTexture.
     *
     * @return The diffuseTexture
     */

    public String getDiffuseTexture()
    {
        return this.diffuseTexture;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "Effect [id=" + this.id + ", emission=" + this.emissionColor
                + ", ambient=" + this.ambientColor + ", diffuse="
                + this.diffuseColor + ", specular=" + this.specularColor
                + ", shininess=" + this.shininess + "]";
    }
}
