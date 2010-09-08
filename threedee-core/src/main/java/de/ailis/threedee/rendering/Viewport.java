/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering;

import de.ailis.gramath.Color4f;
import de.ailis.gramath.MutableColor4f;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.textures.TextureManager;


/**
 * Viewport
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Viewport
{
    /** The viewport width */
    private int width;

    /** The viewport height */
    private int height;

    /** The GL interface */
    private final GL gl;

    /** The aspect ratio (width/height) */
    private float aspectRatio;

    /** The currently displayed scene. null if none. */
    private Scene scene;

    /** The view component. */
    private final ViewComponent viewComponent;

    /** The color used to clear the screen */
    private final MutableColor4f clearColor = new MutableColor4f(0, 0, 0, 1);

    /** The last update time (Nanosecond timestamp) */
    private long lastUpdate;


    /**
     * Constructs a new viewport.
     *
     * @param viewComponent
     *            The view component
     * @param gl
     *            The OpenGL context
     */

    public Viewport(final ViewComponent viewComponent, final GL gl)
    {
        this.viewComponent = viewComponent;
        this.gl = gl;
        this.lastUpdate = System.nanoTime();
    }


    /**
     * Initializes the viewport.
     */

    public void init()
    {
        // Create some shortcuts
        final GL gl = this.gl;

        // Set the color used for clearing the screen before rendering a frame
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearDepth(1f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glEnable(GL.GL_DITHER);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // Perform implementation specific initialization
        gl.init();

        // gl.glEnable(GL.GL_COLOR_MATERIAL);
        // gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);

        // gl.glDisable(GL.GL_POINT_SMOOTH);
        // gl.glEnable(GL.GL_MULTISAMPLE);

        // Enable Anisotropic filtering if available
        // gl.glTexParameteri(GL.GL_TEXTURE_2D,
        // GL.GL_TEXTURE_MAX_ANISOTROPY_EXT, 4);

        // Initialize the texture manager.
        TextureManager.getInstance().clear(gl);
    }


    /**
     * Renders the viewport.
     */

    public void render()
    {
        // Get the clear color
        final Color4f clearColor = this.clearColor;

        // Clear the viewport
        this.gl.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor
                .getBlue(), clearColor.getAlpha());
        this.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Draw the scene if present
        if (this.scene != null) this.scene.render(this);

        // Clean-up unused textures
        TextureManager.getInstance().cleanUp(this.gl);

        // Finish renderering
        this.gl.glFlush();
    }


    /**
     * Updates the viewport.
     *
     * @return True if viewport must be updated again because it is animated.
     *         False if viewport may not be updated again because nothing will
     *         change anyway.
     */

    public boolean update()
    {
        // Calculate time delta
        final long now = System.nanoTime();
        final float delta = ((now - this.lastUpdate)) / 1000000000f;
        this.lastUpdate = now;

        // Do nothing more if no scene is set
        if (this.scene == null) return false;

        // Update the scene
        return this.scene.update(delta);
    }


    /**
     * Sets the viewport size.
     *
     * @param width
     *            The viewport width
     * @param height
     *            The viewport height
     */

    public void setSize(final int width, final int height)
    {
        this.width = width;
        this.height = height;
        this.aspectRatio = (float) width / (float) height;
    }


    /**
     * Returns the viewport width.
     *
     * @return The viewport width
     */

    public int getWidth()
    {
        return this.width;
    }


    /**
     * Returns the viewport height.
     *
     * @return The viewport height
     */

    public int getHeight()
    {
        return this.height;
    }


    /**
     * Returns the GL interface.
     *
     * @return The GL interface
     */

    public GL getGL()
    {
        return this.gl;
    }


    /**
     * Returns the aspect ratio (width/height).
     *
     * @return The aspect ratio
     */

    public float getAspectRatio()
    {
        return this.aspectRatio;
    }


    /**
     * Sets the scene to display.
     *
     * @param scene
     *            The scene to display. Null for none.
     */

    public void setScene(final Scene scene)
    {
        if (scene != this.scene)
        {
            // Detach old scene
            if (this.scene != null)
            {
                final Scene oldScene = this.scene;
                this.scene = null;
                oldScene.setViewport(null);
            }

            // Attach new scene
            this.scene = scene;
            if (scene != null) scene.setViewport(this);

            // Render the view.
            this.viewComponent.requestRender();
        }
    }

    /**
     * Returns the currently displayed scene.
     *
     * @return The currently display scene. Null if none.
     */

    public Scene getScene()
    {
        return this.scene;
    }


    /**
     * Sets the clear color. This is the color used for clearing the screen
     * before rendering the scene. The default clear color is black.
     *
     * @param clearColor
     *            The clear color to set. Must not be null.
     */

    public void setClearColor(final Color4f clearColor)
    {
        if (clearColor == null)
            throw new IllegalArgumentException("clearColor must be set");
        this.clearColor.set(clearColor);
        this.viewComponent.requestRender();
    }


    /**
     * Returns the current clear color.
     *
     * @return The clear color. Never null.
     */

    public Color4f getClearColor()
    {
        return this.clearColor.asImmutable();
    }
}
