/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.math.Matrix4f;


/**
 * A camera node.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Camera extends SceneNode
{
    /** Cached camera transformation */
    private final Matrix4f cameraTransform = Matrix4f.identity();

    /** If cached camera transformation is valid */
    private final boolean cameraTransformValid = false;


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