/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl.swing;

import java.awt.BorderLayout;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JComponent;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import de.ailis.threedee.jogl.opengl.GL;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.scene.Scene;


/**
 * Swing component to display a scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneCanvas extends JComponent
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The canvas component */
    private final GLCanvas canvas;

    /** The scene to display */
    final Scene scene;

    /** The animator thread */
    Animator animator;

    /** The GL interface */
    GL gl;

    /** The renderer */
    Viewport viewport;


    /**
     * Constructs a new scene canvas with an empty scene.
     */

    public SceneCanvas()
    {
        this(new Scene());
    }


    /**
     * Constructs a new scene canvas with the specified scene.
     *
     * @param scene
     *            The scene to display
     */

    public SceneCanvas(final Scene scene)
    {
        if (scene == null)
            throw new IllegalArgumentException("scene must be set");
        this.scene = scene;

        setLayout(new BorderLayout());
        final GLCapabilities capabilities = new GLCapabilities();
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(4);
        this.canvas = new GLCanvas(capabilities);
        final Viewport viewport = this.viewport = new Viewport(new GL(
                this.canvas.getGL(), new GLU()));
        this.animator = new FPSAnimator(this.canvas, 75);
        add(this.canvas);

        this.canvas.addGLEventListener(new GLEventListener()
        {

            @Override
            public void init(final GLAutoDrawable drawable)
            {
                viewport.init();
            }

            @Override
            public void reshape(final GLAutoDrawable drawable, final int x,
                    final int y, final int width, final int height)
            {
                viewport.setSize(width, height);
            }

            @Override
            public void displayChanged(final GLAutoDrawable drawable,
                    final boolean modeChanged, final boolean deviceChanged)
            {
                // Empty
            }

            @Override
            public void display(final GLAutoDrawable drawable)
            {
                scene.render(viewport);
                final boolean animate = scene.update();
                if (animate && !SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.start();
                else if (!animate && SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.stop();
            }
        });

        // Install mouse event handler
        final SceneTouchAdapter touchAdapter = new SceneTouchAdapter(
                this.scene, this.viewport);
        this.canvas.addMouseListener(touchAdapter);
        this.canvas.addMouseMotionListener(touchAdapter);
    }

    /**
     * Returns the scene displayed in this canvas.
     *
     * @return The displayed scene
     */

    public Scene getScene()
    {
        return this.scene;
    }


    /**
     * Requests rendering
     */

    public void requestRender()
    {
        if (!this.animator.isAnimating()) this.animator.start();
    }
}
