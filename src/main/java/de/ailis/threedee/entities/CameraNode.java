/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.math.Matrix4f;


/**
 * A instance of a light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class CameraNode extends SceneNode
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The camera */
    private final Camera camera;

    /** Cached camera transformation */
    private final Matrix4f cameraTransform = Matrix4f.identity();

    /** If cached camera transformation is valid */
    private final boolean cameraTransformValid = false;


    /**
     * Constructor
     *
     * @param camera
     *            The camera to instance
     */

    public CameraNode(final Camera camera)
    {
        this.camera = camera;
    }


    /**
     * Returns the camera.
     *
     * @return The camera
     */

    public Camera getCamera()
    {
        return this.camera;
    }


    /**
     * Returns the camera transformation.
     *
     * @return The camera transformation
     */

    public Matrix4f getCameraTransform()
    {
        // If a cached scene transformation is present then use that
        if (this.cameraTransformValid) return this.cameraTransform;

        return this.cameraTransform.set(getSceneTransform()).invert();
    }
}
