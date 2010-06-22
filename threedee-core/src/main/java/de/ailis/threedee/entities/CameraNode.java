/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.rendering.opengl.GL;


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


    /**
     * Applies the camera to the specified viewport.
     *
     * @param viewport
     *            The viewport
     */

    public void apply(final Viewport viewport)
    {
        // Create some shortcuts
        final GL gl = viewport.getGL();
        final Camera camera = this.camera;

        // Setup the coordinate system
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        final Float aspectRatio = camera.getAspectRatio();
        gl.gluPerspective(camera.getFovY(), aspectRatio == null ? viewport
                .getAspectRatio() : aspectRatio.floatValue(),
                camera.getZNear(), camera.getZFar());

        // Set the viewport
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, viewport.getWidth(), viewport.getHeight());

        // Apply camera transformation
        gl.glPushMatrix();
        gl.glMultMatrix(getCameraTransform().getBuffer());
    }


    /**
     * Removes the camera from the specified viewport.
     *
     * @param viewport
     *            The viewport
     */

    public void remove(final Viewport viewport)
    {
        viewport.getGL().glPopMatrix();
    }
}
