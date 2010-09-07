/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl;

import java.awt.BorderLayout;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JComponent;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

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
    Scene scene;

    /** The animator thread */
    Animator animator;

    /** The GL interface */
    JoGL gl;

    /** The renderer */
    Viewport viewport;

    /** The current touch adapter. */
    private SceneTouchAdapter touchAdapter;


    /**
     * Constructs a new scene canvas with an empty scene.
     */

    public SceneCanvas()
    {
        this(new Scene("scene"));
    }


    /**
     * Constructs a new scene canvas with the specified scene.
     *
     * @param scene
     *            The scene to display. Null to start without a scene.
     */

    public SceneCanvas(final Scene scene)
    {
        setLayout(new BorderLayout());
        final GLCapabilities capabilities = new GLCapabilities();
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(4);
        this.canvas = new GLCanvas(capabilities);
        final Viewport viewport = this.viewport = new Viewport(new JoGL(
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
                final Scene scene = getScene();
                if (scene == null) return;
                scene.render(viewport);
                final boolean animate = scene.update();
                if (animate && !SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.start();
                else if (!animate && SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.stop();
            }
        });
        setScene(scene);
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
     * Sets a new scene.
     *
     * @param scene
     *            The scene to render. Null to unset.
     */

    public void setScene(final Scene scene)
    {
        // Do nothing if scene has not changed
        if (scene == this.scene) return;

        // Uninstall old touch adapter if needed
        if (this.touchAdapter != null)
        {
            this.canvas.removeMouseListener(this.touchAdapter);
            this.canvas.removeMouseMotionListener(this.touchAdapter);
            this.touchAdapter = null;
        }

        this.scene = scene;

        // Install new touch adapter
        if (scene != null)
        {
            this.touchAdapter = new SceneTouchAdapter(scene, this.viewport);
            this.canvas.addMouseListener(this.touchAdapter);
            this.canvas.addMouseMotionListener(this.touchAdapter);
        }
    }


    /**
     * Requests rendering
     */

    public void requestRender()
    {
        if (!this.animator.isAnimating()) this.animator.start();
    }
}
