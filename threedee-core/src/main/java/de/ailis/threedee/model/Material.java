/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.entities.Color;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.textures.Texture;
import de.ailis.threedee.textures.TextureCache;


/**
 * A material.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 37 $
 */

public class Material
{
    /** The default material */
    public static final Material DEFAULT = new Material();

    /** The material id */
    private final String id;

    /** The ambient color */
    private final Color ambientColor;

    /** The diffuse color */
    private final Color diffuseColor;

    /** The texture filename */
    private final String textureName;

    /** The texture */
    private Texture texture;

    /** The specular color */
    private final Color specularColor;

    /** The emission color */
    private final Color emissionColor;

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
     * @param texture
     *            The diffuse texture
     */

    public Material(final String id, final Color ambientColor,
            final Color diffuseColor, final Color specularColor,
            final Color emissionColor, final float shininess,
            final String texture)
    {
        this.id = id;
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.textureName = texture;
        this.specularColor = specularColor;
        this.emissionColor = emissionColor;
        this.shininess = shininess;
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
     * Returns the emission color.
     *
     * @return The emission color
     */

    public Color getEmissionColor()
    {
        return this.emissionColor;
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
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color getSpecularColor()
    {
        return this.specularColor;
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
     * Returns the diffuse texture.
     *
     * @return The diffuse texture
     */

    public String getTextureName()
    {
        return this.textureName;
    }


    /**
     * Checks if model has a texture.
     *
     * @return True if model has a texture, false if not
     */

    public boolean hasTexture()
    {
        return this.textureName != null;
    }


    /**
     * Renders the material to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void apply(final GL gl)
    {
        if (this.textureName != null)
        {
            if (this.texture == null || this.texture.isReleased()) this.texture = TextureCache.getInstance().getTexture(this.textureName);
            this.texture.bind(gl);
        }
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, this.specularColor
                .getBuffer());
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, this.diffuseColor
                .getBuffer());
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, this.ambientColor
                .getBuffer());
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, this.emissionColor
                .getBuffer());
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, this.shininess);
    }


    /**
     * Removes the material from the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void remove(final GL gl)
    {
        if (this.texture != null) this.texture.unbind(gl);
    }
}