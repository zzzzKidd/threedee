/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ailis.threedee.assets.reader.assets.ColladaAssetsReader;
import de.ailis.threedee.assets.reader.material.TDBMaterialReader;
import de.ailis.threedee.assets.reader.mesh.TDBMeshReader;
import de.ailis.threedee.exceptions.AssetNotFoundException;
import de.ailis.threedee.exceptions.UnknownAssetFormatException;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.animation.Animation;
import de.ailis.threedee.scene.textures.ImageTexture;


/**
 * In this container all used assets are collected. It is some kind of cache and
 * auto-loader.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Assets
{
    /** The logger. */
    private static final Log log = LogFactory.getLog(Assets.class);

    /** The loaded textures. */
    private final Map<String, Texture> textures = new HashMap<String, Texture>();

    /** The loaded materials. */
    private final Map<String, Material> materials = new HashMap<String, Material>();

    /** The loaded meshes. */
    private final Map<String, Mesh> meshes = new HashMap<String, Mesh>();

    /** The loaded scenes. */
    private final Map<String, Scene> scenes = new HashMap<String, Scene>();

    /** The loaded animations. */
    private final Map<String, Animation> animations = new HashMap<String, Animation>();

    /** If assets should be loaded automatically when requested. */
    private boolean autoLoad;

    /** The asset provider to use. */
    private AssetProvider assetProvider;


    /**
     * Constructs a new Assets container which uses a ClasspathAssetProvider and
     * has auto-loading enabled.
     */

    public Assets()
    {
        this(new ClasspathAssetProvider());
    }


    /**
     * Constructs a new Assets container using the specified asset provider and
     * has auto-loading enabled.
     *
     * @param provider
     *            The asset provider.
     */

    public Assets(final AssetProvider provider)
    {
        this(provider, true);
    }


    /**
     * Constructs an new Assets container with the specified provider and
     * auto-loading setting.
     *
     * @param provider
     *            The asset provider.
     * @param autoLoad
     *            If assets should be loaded automatically when requested. If
     *            false then exceptions are thrown when unloaded assets are
     *            requested.
     */

    public Assets(final AssetProvider provider, final boolean autoLoad)
    {
        this.assetProvider = provider;
        this.autoLoad = autoLoad;
    }


    /**
     * Returns the texture with the specified ID.
     *
     * @param id
     *            The texture ID.
     * @return The texture. Never null.
     * @throws AssetNotFoundException
     *             When texture was not found.
     */

    public Texture getTexture(final String id)
    {
        final Texture texture = this.textures.get(id);
        if (texture == null)
        {
            if (this.autoLoad) return loadTexture(id);
            throw new AssetNotFoundException("Texture not found: " + id);
        }
        return texture;
    }


    /**
     * Loads the texture with the specified id.
     *
     * @param id
     *            The texture id
     * @return The loaded texture. Never null.
     * @throws AssetNotFoundException
     *             When texture was not found.
     */

    private Texture loadTexture(final String id)
    {
        if (!this.assetProvider.exists(AssetType.TEXTURE, id))
            throw new AssetNotFoundException("Texture not found: " + id);
        final Texture texture = new ImageTexture(id);
        addTexture(texture);
        return texture;
    }


    /**
     * Returns all loaded textures.
     *
     * @return The loaded textures. Never null. May be empty.
     */

    public Collection<Texture> getTextures()
    {
        return Collections.unmodifiableCollection(this.textures.values());
    }


    /**
     * Adds a texture.
     *
     * @param texture
     *            The texture to add
     */

    public void addTexture(final Texture texture)
    {
        this.textures.put(texture.getId(), texture);
    }


    /**
     * Removes the specified texture.
     *
     * @param texture
     *            The texture to remove.
     */

    public void removeTexture(final Texture texture)
    {
        removeTexture(texture.getId());
    }


    /**
     * Removes the specified texture.
     *
     * @param id
     *            The ID of the texture to remove.
     */

    public void removeTexture(final String id)
    {
        this.textures.remove(id);
    }


    /**
     * Removes all textures.
     */

    public void removeTextures()
    {
        this.textures.clear();
    }


    /**
     * Returns the material with the specified ID.
     *
     * @param id
     *            The material ID.
     * @return The material. Never null.
     * @throws AssetNotFoundException
     *             When material was not found.
     */

    public Material getMaterial(final String id)
    {
        final Material material = this.materials.get(id);
        if (material == null)
        {
            if (this.autoLoad) loadMaterial(id);
            throw new AssetNotFoundException("Material not found: " + id);
        }
        return material;
    }


    /**
     * Loads the material with the specified id.
     *
     * @param id
     *            The material id
     * @throws AssetNotFoundException
     *             When material was not found.
     */

    private void loadMaterial(final String id)
    {
        final AssetInputStream stream = this.assetProvider
            .openInputStream(AssetType.MATERIAL, id);
        try
        {
            final AssetFormat format = stream.getFormat();
            switch (format)
            {
                case TDB:
                    log.trace("Started loading material " + id);
                    addMaterial(new TDBMaterialReader(id).read(stream, this));
                    log.trace("Finished loading material " + id);
                    break;

                default:
                    throw new UnknownAssetFormatException(
                        "Unknown material format: " + format);
            }
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (final IOException e)
            {
                // Ignored
            }
        }
    }


    /**
     * Returns all loaded textures.
     *
     * @return The loaded textures. Never null. May be empty.
     */

    public Collection<Material> getMaterials()
    {
        return Collections.unmodifiableCollection(this.materials.values());
    }


    /**
     * Adds a material.
     *
     * @param material
     *            The material to add
     */

    public void addMaterial(final Material material)
    {
        this.materials.put(material.getId(), material);
    }


    /**
     * Removes the specified material.
     *
     * @param material
     *            The material to remove.
     */

    public void removeMaterial(final Material material)
    {
        removeMaterial(material.getId());
    }


    /**
     * Removes the specified material.
     *
     * @param id
     *            The ID of the material to remove.
     */

    public void removeMaterial(final String id)
    {
        this.materials.remove(id);
    }


    /**
     * Removes all materials.
     */

    public void removeMaterials()
    {
        this.materials.clear();
    }


    /**
     * Returns the meshes with the specified ID.
     *
     * @param id
     *            The meshes ID.
     * @return The meshes. Never null.
     * @throws AssetNotFoundException
     *             When meshes was not found.
     */

    public Mesh getMesh(final String id)
    {
        final Mesh meshes = this.meshes.get(id);
        if (meshes == null)
        {
            if (this.autoLoad) return loadMesh(id);
            throw new AssetNotFoundException("Mesh not found: " + id);
        }
        return meshes;
    }


    /**
     * Loads the meshes with the specified id.
     *
     * @param id
     *            The meshes id
     * @return The loaded mesh
     * @throws AssetNotFoundException
     *             When meshes was not found.
     */

    private Mesh loadMesh(final String id)
    {
        final AssetInputStream stream = this.assetProvider
            .openInputStream(AssetType.MESH, id);
        try
        {
            final AssetFormat format = stream.getFormat();
            Mesh mesh;
            switch (format)
            {
                case TDB:
                    log.trace("Started loading mesh " + id);
                    mesh = new TDBMeshReader(id).read(stream, this);
                    log.trace("Finished loading mesh " + id);
                    break;

                default:
                    throw new UnknownAssetFormatException(
                        "Unknown meshes format: " + format);
            }
            addMesh(mesh);
            return mesh;
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (final IOException e)
            {
                // Ignored
            }
        }
    }


    /**
     * Returns all loaded textures.
     *
     * @return The loaded textures. Never null. May be empty.
     */

    public Collection<Mesh> getMeshes()
    {
        return Collections.unmodifiableCollection(this.meshes.values());
    }


    /**
     * Adds a meshes.
     *
     * @param meshes
     *            The meshes to add
     */

    public void addMesh(final Mesh meshes)
    {
        this.meshes.put(meshes.getId(), meshes);
    }


    /**
     * Removes the specified meshes.
     *
     * @param meshes
     *            The meshes to remove.
     */

    public void removeMesh(final Mesh meshes)
    {
        removeMesh(meshes.getId());
    }


    /**
     * Removes the specified meshes.
     *
     * @param id
     *            The ID of the meshes to remove.
     */

    public void removeMesh(final String id)
    {
        this.meshes.remove(id);
    }


    /**
     * Removes all meshes.
     */

    public void removeMeshes()
    {
        this.meshes.clear();
    }


    /**
     * Returns the scene with the specified ID.
     *
     * @param id
     *            The scene ID.
     * @return The scene. Never null.
     * @throws AssetNotFoundException
     *             When scene was not found.
     */

    public Scene getScene(final String id)
    {
        final Scene scene = this.scenes.get(id);
        if (scene == null)
        {
            if (this.autoLoad) loadScene(id);
            throw new AssetNotFoundException("Scene not found: " + id);
        }
        return scene;
    }


    /**
     * Loads the scene with the specified id.
     *
     * @param id
     *            The scene id
     * @throws AssetNotFoundException
     *             When scene was not found.
     */

    private void loadScene(final String id)
    {
        final AssetInputStream stream = this.assetProvider
            .openInputStream(AssetType.SCENE, id);
        try
        {
            final AssetFormat format = stream.getFormat();
            switch (format)
            {
                default:
                    throw new UnknownAssetFormatException(
                        "Unknown scene format: " + format);
            }
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (final IOException e)
            {
                // Ignored
            }
        }
    }


    /**
     * Returns all loaded textures.
     *
     * @return The loaded textures. Never null. May be empty.
     */

    public Collection<Scene> getScenes()
    {
        return Collections.unmodifiableCollection(this.scenes.values());
    }


    /**
     * Adds a scene.
     *
     * @param scene
     *            The scene to add
     */

    public void addScene(final Scene scene)
    {
        this.scenes.put(scene.getId(), scene);
    }


    /**
     * Removes the specified scene.
     *
     * @param scene
     *            The scene to remove.
     */

    public void removeScene(final Scene scene)
    {
        removeScene(scene.getId());
    }


    /**
     * Removes the specified scene.
     *
     * @param id
     *            The ID of the scene to remove.
     */

    public void removeScene(final String id)
    {
        this.scenes.remove(id);
    }


    /**
     * Removes all scenes.
     */

    public void removeScenes()
    {
        this.scenes.clear();
    }


    /**
     * Returns the animation with the specified ID.
     *
     * @param id
     *            The animation ID.
     * @return The animation. Never null.
     * @throws AssetNotFoundException
     *             When animation was not found.
     */

    public Animation getAnimation(final String id)
    {
        final Animation animation = this.animations.get(id);
        if (animation == null)
        {
            if (this.autoLoad) loadAnimation(id);
            throw new AssetNotFoundException("Animation not found: " + id);
        }
        return animation;
    }


    /**
     * Loads the animation with the specified id.
     *
     * @param id
     *            The animation id
     * @throws AssetNotFoundException
     *             When animation was not found.
     */

    private void loadAnimation(final String id)
    {
        final AssetInputStream stream = this.assetProvider
            .openInputStream(AssetType.MATERIAL, id);
        try
        {
            final AssetFormat format = stream.getFormat();
            switch (format)
            {
                default:
                    throw new UnknownAssetFormatException(
                        "Unknown animation format: " + format);
            }
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (final IOException e)
            {
                // Ignored
            }
        }
    }


    /**
     * Returns all loaded textures.
     *
     * @return The loaded textures. Never null. May be empty.
     */

    public Collection<Animation> getAnimations()
    {
        return Collections.unmodifiableCollection(this.animations.values());
    }


    /**
     * Adds a animation.
     *
     * @param animation
     *            The animation to add
     */

    public void addAnimation(final Animation animation)
    {
        this.animations.put(animation.getId(), animation);
    }


    /**
     * Removes the specified animation.
     *
     * @param animation
     *            The animation to remove.
     */

    public void removeAnimation(final Animation animation)
    {
        removeAnimation(animation.getId());
    }


    /**
     * Removes the specified animation.
     *
     * @param id
     *            The ID of the animation to remove.
     */

    public void removeAnimation(final String id)
    {
        this.animations.remove(id);
    }


    /**
     * Removes all animations.
     */

    public void removeAnimations()
    {
        this.animations.clear();
    }


    /**
     * Loads assets.
     *
     * @param id
     *            The assets ID.
     */

    public void addAssets(final String id)
    {
        final AssetInputStream stream = this.assetProvider
            .openInputStream(AssetType.ASSETS, id);
        try
        {
            final AssetFormat format = stream.getFormat();
            switch (format)
            {
                case DAE:
                    log.trace("Started loading assets " + id);
                    new ColladaAssetsReader().read(stream, this);
                    log.trace("Finished loading assets " + id);
                    break;

                default:
                    throw new UnknownAssetFormatException(
                        "Unknown assets format: " + format);
            }
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (final IOException e)
            {
                // Ignored
            }
        }
    }


    /**
     * Checks if auto-loading is enabled.
     *
     * @return True if auto-loading is enabled, false if not.
     */

    public boolean isAutoLoad()
    {
        return this.autoLoad;
    }


    /**
     * Sets the auto-loading flag.
     *
     * @param autoLoad
     *            True to enable auto-loading, false to disable it.
     */

    public void setAutoLoad(final boolean autoLoad)
    {
        this.autoLoad = autoLoad;
    }


    /**
     * Returns the asset provider.
     *
     * @return The asset provider. Never null.
     */

    public AssetProvider getAssetProvider()
    {
        return this.assetProvider;
    }


    /**
     * Sets the asset provider.
     *
     * @param assetProvider
     *            The asset provider to set. Must not be null.
     */

    public void setAssetProvider(final AssetProvider assetProvider)
    {
        if (assetProvider == null)
            throw new IllegalArgumentException("assetProvider must not be null");
        this.assetProvider = assetProvider;
    }


    /**
     * Clears all assets.
     */

    public void clear()
    {
        removeAnimations();
        removeMaterials();
        removeMeshes();
        removeScenes();
        removeTextures();
    }
}
