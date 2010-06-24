/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLSurfaceView;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.scene.Scene;


/**
 * A GL surface view renderer to render a threedee scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneRenderer implements GLSurfaceView.Renderer
{
    /** The scene to render */
    private Scene scene;

    /** The view */
    private final SceneSurfaceView view;

    /** The GL context */
    private final AndroidGL gl;

    /** The viewport */
    private final Viewport viewport;


    /**
     * Constructs the scene renderer.
     *
     * @param view
     *            The view
     */

    public SceneRenderer(final SceneSurfaceView view)
    {
        this.scene = new Scene();
        this.view = view;
        this.gl = new AndroidGL();
        this.viewport = new Viewport(this.gl);
    }


    /**
     * Returns the scene.
     *
     * @return The scene
     */

    public Scene getScene()
    {
        return this.scene;
    }


    /**
     * @see GLSurfaceView.Renderer#onSurfaceCreated(GL10, EGLConfig)
     */

    public void onSurfaceCreated(final GL10 gl, final EGLConfig config)
    {
        this.gl.setGL((GL11) gl);
        this.viewport.init();
    }


    /**
     * @see GLSurfaceView.Renderer#onSurfaceChanged(GL10, int, int)
     */

    public void onSurfaceChanged(final GL10 gl, final int width,
            final int height)
    {
        this.gl.setGL((GL11) gl);
        this.viewport.setSize(width, height);
    }


    /**
     * @see GLSurfaceView.Renderer#onDrawFrame(GL10)
     */

    public void onDrawFrame(final GL10 gl)
    {
        final boolean changed = this.scene.update();
        this.scene.render(this.viewport);
        if (changed) this.view.requestRender();
    }


    /**
     * Sets the scene.
     *
     * @param scene
     *            The scene to set
     */

    public void setScene(final Scene scene)
    {
        this.scene = scene;
    }
}
