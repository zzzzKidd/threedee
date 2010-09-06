/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.model;

import de.ailis.gramath.Color4f;
import de.ailis.gramath.ImmutableColor4f;


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

    /** If material is influenced by light. */
    private final boolean lighting;

    /** The emission color */
    private final Color4f emissionColor;

    /** The ambient color */
    private final Color4f ambientColor;

    /** The diffuse color */
    private final Color4f diffuseColor;

    /** The diffuse texture */
    private final String diffuseTexture;

    /** The specular color */
    private final Color4f specularColor;

    /** The shininess */
    private final float shininess;


    /**
     * Constructs the default material.
     */

    private Material()
    {
        this("DEFAULT", new ImmutableColor4f(0.2f, 0.2f, 0.2f, 1f),
                new ImmutableColor4f(0.8f, 0.8f, 0.8f, 1f), Color4f.BLACK,
            Color4f.BLACK, 0, null, false);
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
     * @param lighting
     *            If material is influenced by lighting or not.
     */

    public Material(final String id, final Color4f ambientColor,
            final Color4f diffuseColor, final Color4f specularColor,
            final Color4f emissionColor, final float shininess,
            final String diffuseTexture, final boolean lighting)
    {
        this.id = id;
        this.ambientColor = new ImmutableColor4f(ambientColor);
        this.diffuseColor = new ImmutableColor4f(diffuseColor);
        this.diffuseTexture = diffuseTexture;
        this.specularColor = new ImmutableColor4f(specularColor);
        this.emissionColor = new ImmutableColor4f(emissionColor);
        this.shininess = shininess;
        this.lighting = lighting;
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

    public Color4f getEmissionColor()
    {
        return this.emissionColor;
    }


    /**
     * Returns the ambient color.
     *
     * @return The ambient color
     */

    public Color4f getAmbientColor()
    {
        return this.ambientColor;
    }


    /**
     * Returns the diffuse color.
     *
     * @return The diffuse color
     */

    public Color4f getDiffuseColor()
    {
        return this.diffuseColor;
    }


    /**
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color4f getSpecularColor()
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
     * Checks if material is influenced by lighting.
     *
     * @return True if influenced by lighting, false if not.
     */

    public boolean getLighting()
    {
        return this.lighting;
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
