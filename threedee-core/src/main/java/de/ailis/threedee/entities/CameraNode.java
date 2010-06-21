/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.rendering.Renderer;


/**
 * A instance of a light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class CameraNode extends SceneNode
{
    /** The camera */
    private Camera camera;

    /** Cached camera transformation */
    private final Matrix4f cameraTransform = Matrix4f.identity();

    /** If cached camera transformation is valid */
    private final boolean cameraTransformValid = false;

    /** If camera data is invalid */
    boolean invalid = true;

    /** The previous output width */
    private int oldWidth = 0;

    /** The previous output height */
    private int oldHeight = 0;

    /** The previous camera settings */
    private final Camera oldCamera = new Camera();


    /**
     * Constructor
     *
     * @param camera
     *            The camera to instance. Must not be null
     */

    public CameraNode(final Camera camera)
    {
        setCamera(camera);
    }


    /**
     * Returns the camera.
     *
     * @return The camera. Never null
     */

    public Camera getCamera()
    {
        return this.camera;
    }


    /**
     * Sets the camera.
     *
     * @param camera
     *            The camera to set. Must not be null
     */

    public void setCamera(final Camera camera)
    {
        if (camera == null)
            throw new IllegalArgumentException("camera must be set");
        this.camera = camera;
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

    public void preRender(final Renderer renderer)
    {
        final int newWidth = renderer.getWidth();
        final int newHeight = renderer.getHeight();

        // Check if camera settings has changed
        if (!this.camera.equals(this.oldCamera)
                || (this.camera.getAspectRatio() != null && (newWidth != this.oldWidth || newHeight != this.oldHeight)))
        {
            this.oldWidth = newWidth;
            this.oldHeight = newHeight;
            this.oldCamera.copyFrom(this.camera);
            this.invalid = true;
        }

        if (this.invalid)
        {
            renderer.renderCamera(this.camera);
            this.invalid = false;
        }
    }
}
