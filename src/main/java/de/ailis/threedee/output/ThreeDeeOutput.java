/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.output;

import de.ailis.threedee.scene.CameraNode;
import de.ailis.threedee.scene.Scene;


/**
 * Interface which must be implemented by all ThreeDee output classes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface ThreeDeeOutput
{
    /**
     * Sets the scene. If set to null then nothing is rendered in this panel.
     * 
     * @param scene
     *            The scene to use.
     */

    public void setScene(final Scene scene);


    /**
     * Sets the camera node. If set to null then a fixed default camera at
     * position 0,0,0 looking in direction 0,0,1 is used.
     * 
     * @param camera
     *            The camera node to set
     */

    public void setCamera(final CameraNode camera);


    /**
     * Returns the currently displayed scene. May return null if no scene is
     * currently displayed.
     * 
     * @return The currently displayed scene. Maybe null.
     */

    public Scene getScene();


    /**
     * Returns the current camera node. May return null if no specific camera
     * was set and a fixed default camera at position 0,0,0 looking in direction
     * 0,0,1 is used.
     * 
     * @return The current camera node. Maybe null.
     */

    public CameraNode getCamera();
    

    /**
     * Returns the render options of this 3D panel.
     * 
     * @return The render options
     */

    public RenderOptions getRenderOptions();
}