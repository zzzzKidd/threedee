/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;


/**
 * Asset.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Asset
{
    /** The meshes */
    private final Meshes meshes = new Meshes();

    /** The images library */
    private final Images images = new Images();

    /** The materials */
    private final Materials materials = new Materials();

    /** The scenes */
    private final Scenes scenes = new Scenes();

    /** The lights */
    private final Lights lights = new Lights();

    /** The cameras */
    private final Cameras cameras = new Cameras();

    /** The active scene */
    private Scene activeScene;


    /**
     * Returns the geometries library.
     *
     * @return The geometries library. Never null. May be empty
     */

    public Meshes getMeshes()
    {
        return this.meshes;
    }


    /**
     * Returns the images library.
     *
     * @return The images library. Never null. May be empty
     */

    public Images getImages()
    {
        return this.images;
    }


    /**
     * Returns the materials library.
     *
     * @return The materials library. Never null. May be empty
     */

    public Materials getMaterials()
    {
        return this.materials;
    }


    /**
     * Returns the lights.
     *
     * @return The lights. Never null. May be empty
     */

    public Lights getLights()
    {
        return this.lights;
    }


    /**
     * Returns the cameras.
     *
     * @return The cameras. Never null. May be empty
     */

    public Cameras getCameras()
    {
        return this.cameras;
    }


    /**
     * Returns the scenes.
     *
     * @return The scenes. Never null. May be empty
     */

    public Scenes getScenes()
    {
        return this.scenes;
    }


    /**
     * Sets the active scene.
     *
     * @param scene
     *            The active scene to set
     */

    public void setActiveScene(final Scene scene)
    {
        if (!this.scenes.contains(scene))
            throw new IllegalArgumentException(
                    "Specified scene is not in scene list");
        this.activeScene = scene;
    }


    /**
     * Returns the active scene.
     *
     * @return The active scene
     */

    public Scene getActiveScene()
    {
        return this.activeScene;
    }
}
