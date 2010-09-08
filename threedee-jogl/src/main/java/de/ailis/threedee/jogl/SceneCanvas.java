/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JComponent;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.rendering.ViewComponent;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.scene.Scene;


/**
 * Swing component to display a scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneCanvas extends JComponent implements ViewComponent
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The canvas component */
    private final GLCanvas canvas;

    /** The animator thread */
    Animator animator;

    /** The GL interface */
    JoGL gl;

    /** The renderer */
    Viewport viewport;

    /** The current touch adapter. */
    private final SceneTouchAdapter touchAdapter;

    /** The list of touch listeners */
    private final List<TouchListener> touchListeners = new ArrayList<TouchListener>();


    /**
     * Constructs a new scene canvas.
     */

    public SceneCanvas()
    {
        setLayout(new BorderLayout());
        final GLCapabilities capabilities = new GLCapabilities();
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(4);
        this.canvas = new GLCanvas(capabilities);
        final Viewport viewport = this.viewport = new Viewport(this, new JoGL(
                this.canvas.getGL(), new GLU()));
        this.animator = new FPSAnimator(this.canvas, 75);
        add(this.canvas);

        // Install GL event listener
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
                viewport.render();
                final boolean animate = viewport.update();
                if (animate && !SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.start();
                else if (!animate && SceneCanvas.this.animator.isAnimating())
                    SceneCanvas.this.animator.stop();
            }
        });

        this.touchAdapter = new SceneTouchAdapter(this);
        this.canvas.addMouseListener(this.touchAdapter);
        this.canvas.addMouseMotionListener(this.touchAdapter);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#getScene()
     */

    @Override
    public Scene getScene()
    {
        return this.viewport.getScene();
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#setScene(de.ailis.threedee.scene.Scene)
     */
    @Override
    public void setScene(final Scene scene)
    {
        this.viewport.setScene(scene);
    }


    /**
     * Requests rendering
     */

    @Override
    public void requestRender()
    {
        if (!this.animator.isAnimating()) this.animator.start();
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#setClearColor(de.ailis.gramath.Color4f)
     */

    @Override
    public void setClearColor(final Color4f clearColor)
    {
        this.viewport.setClearColor(clearColor);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#getClearColor()
     */

    @Override
    public Color4f getClearColor()
    {
        return this.viewport.getClearColor();
    }


    /**
     * Returns the viewport.
     *
     * @return The viewport
     */

    Viewport getViewport()
    {
        return this.viewport;
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#addTouchListener(de.ailis.threedee.events.TouchListener)
     */

    @Override
    public void addTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.add(touchListener);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#removeTouchListener(de.ailis.threedee.events.TouchListener)
     */

    @Override
    public void removeTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.remove(touchListener);
    }


    /**
     * Fires the touch down event.
     *
     * @param event
     *            The event object
     */

    void touchDown(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchDown(event);
        }
    }


    /**
     * Fires the touch move event.
     *
     * @param event
     *            The event object
     */

    void touchMove(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchMove(event);
        }
    }


    /**
     * Fires the touch release event.
     *
     * @param event
     *            The event object
     */

    void touchRelease(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchRelease(event);
        }
    }
}
