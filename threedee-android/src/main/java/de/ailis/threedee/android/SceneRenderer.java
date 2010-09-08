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


/**
 * A GL surface view renderer to render a threedee scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneRenderer implements GLSurfaceView.Renderer
{
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
        this.view = view;
        this.gl = new AndroidGL();
        this.viewport = new Viewport(view, this.gl);
    }


    /**
     * @see GLSurfaceView.Renderer#onSurfaceCreated(GL10, EGLConfig)
     */

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config)
    {
        this.gl.setGL((GL11) gl);
        this.viewport.init();
    }


    /**
     * @see GLSurfaceView.Renderer#onSurfaceChanged(GL10, int, int)
     */

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width,
            final int height)
    {
        this.gl.setGL((GL11) gl);
        this.viewport.setSize(width, height);
    }


    /**
     * @see GLSurfaceView.Renderer#onDrawFrame(GL10)
     */

    @Override
    public void onDrawFrame(final GL10 gl)
    {
        this.viewport.render();
        final boolean changed = this.viewport.update();
        if (changed) this.view.requestRender();
    }


    /**
     * Returns the viewport.
     *
     * @return The viewport.
     */

    public Viewport getViewport()
    {
        return this.viewport;
    }
}
