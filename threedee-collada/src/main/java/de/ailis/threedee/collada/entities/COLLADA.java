/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * The root entity of a collada document
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class COLLADA implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The library images */
    private final LibraryImages images = new LibraryImages();

    /** The library materials */
    private final LibraryMaterials materials = new LibraryMaterials();

    /** The library effects */
    private final LibraryEffects effects = new LibraryEffects();

    /** The library geometries */
    private final LibraryGeometries geometries = new LibraryGeometries();

    /** The library animations */
    private final LibraryAnimations animations = new LibraryAnimations();

    /** The library lights */
    private final LibraryLights lights = new LibraryLights();

    /** The library cameras */
    private final LibraryCameras cameras = new LibraryCameras();

    /** The library visual scenes */
    private final LibraryVisualScenes visualScenes = new LibraryVisualScenes();

    /** The scene */
    private ColladaScene scene;


    /**
     * Returns the library images.
     *
     * @return The library images
     */

    public LibraryImages getLibraryImages()
    {
        return this.images;
    }


    /**
     * Returns the library materials.
     *
     * @return The library materials
     */

    public LibraryMaterials getLibraryMaterials()
    {
        return this.materials;
    }


    /**
     * Returns the library effects.
     *
     * @return The library effects
     */

    public LibraryEffects getLibraryEffects()
    {
        return this.effects;
    }


    /**
     * Returns the library geometries.
     *
     * @return The library geometries
     */

    public LibraryGeometries getLibraryGeometries()
    {
        return this.geometries;
    }


    /**
     * Returns the library animations.
     *
     * @return The library animations
     */

    public LibraryAnimations getLibraryAnimations()
    {
        return this.animations;
    }


    /**
     * Returns the library lights.
     *
     * @return The library lights
     */

    public LibraryLights getLibraryLights()
    {
        return this.lights;
    }


    /**
     * Returns the library cameras.
     *
     * @return The library cameras
     */

    public LibraryCameras getLibraryCameras()
    {
        return this.cameras;
    }


    /**
     * Returns the library visual scenes.
     *
     * @return The library visual scenes
     */

    public LibraryVisualScenes getLibraryVisualScenes()
    {
        return this.visualScenes;
    }


    /**
     * Returns the scene.
     *
     * @return The scene
     */

    public ColladaScene getScene()
    {
        return this.scene;
    }


    /**
     * Sets the scene.
     *
     * @param scene
     *            The scene to set
     */

    public void setScene(final ColladaScene scene)
    {
        this.scene = scene;
    }
}
