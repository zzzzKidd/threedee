/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import de.ailis.threedee.scene.CameraNode;
import de.ailis.threedee.scene.Scene;


/**
 * A JPanel which displays a scene.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ThreeDeePanel extends JPanel implements Runnable
{
    /** Serial version UID */
    private static final long serialVersionUID = 4634446551768489838L;

    /** The animator thread */
    private Thread animator;

    /** For stopping the animation */
    private volatile boolean running = false;

    /** The image for double-buffering */
    private VolatileImage buffer;

    /** The graphics context for drawing on the buffer image */
    private Graphics2D context;

    /**
     * The camera to use for rendering the scene. If null then a fixed default
     * camera at position 0,0,0 looking in direction 0,0,1 is used
     */
    private CameraNode camera;

    /** The scene */
    private Scene scene;

    /** The last game update (Nanoseconds) */
    private long lastUpdate = System.nanoTime();

    /** The render options */
    private final RenderOptions renderOptions = new RenderOptions();


    /**
     * Constructs a new ThreeDee panel without a scene and camera. You must at
     * least specify a scene with setScene() to see anything.
     */

    public ThreeDeePanel()
    {
        this(null, null);
    }


    /**
     * Constructs a new ThreeDee panel using the specified scene and a fixed
     * default camera at position 0,0,0 looking in direction 0,0,1.
     * 
     * @param scene
     *            The scene
     */

    public ThreeDeePanel(final Scene scene)
    {
        this(scene, null);
    }


    /**
     * Constructs a new ThreeDee panel using the specified scene and camera
     * node.
     * 
     * @param scene
     *            The scene
     * @param camera
     *            The camera node
     */

    public ThreeDeePanel(final Scene scene, final CameraNode camera)
    {
        this.scene = scene;
        this.camera = camera;

        // We ignore repaints because we do active painting
        setIgnoreRepaint(true);
    }


    /**
     * @see javax.swing.JComponent#addNotify()
     */

    @Override
    public void addNotify()
    {
        super.addNotify();
        startGame();
    }


    /**
     * Initialize and start the thread
     */

    private void startGame()
    {
        if (this.animator == null || !this.running)
        {
            this.animator = new Thread(this);
            this.animator.start();
        }
    }


    /**
     * Called by the user to stop execution.
     */

    public void stopGame()
    {
        this.running = false;
    }


    /**
     * @see java.lang.Runnable#run()
     */

    @Override
    public void run()
    {
        this.running = true;
        while (this.running)
        {
            gameUpdate();
            gameRender();
            paintScreen();
            /*
             * try { Thread.sleep(10); } catch (final InterruptedException e) {
             * // Ignored }
             */
        }
    }


    /**
     * Updates the game state
     */

    private void gameUpdate()
    {
        // If no scene is set then do nothing
        if (this.scene == null) return;

        final long now = System.nanoTime();
        this.scene.update(now - this.lastUpdate);
        this.lastUpdate = now;
    }


    /**
     * Renders the game
     */

    private void gameRender()
    {
        final int width = getWidth();
        final int height = getHeight();

        // If window size is 0 then do nothing
        if (width == 0 || height == 0) return;

        // Create/Re-create the buffer if needed
        if (this.buffer == null
            || this.buffer.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE
            || this.buffer.getWidth() != width
            || this.buffer.getHeight() != height)
        {
            this.buffer = createVolatileImage(width, height);
            this.context = this.buffer.createGraphics();
        }

        // Clear the buffer
        this.context.setColor(Color.BLACK);
        this.context.fillRect(0, 0, width, height);

        // Render the scene if one is set
        if (this.scene != null)
            this.scene.render(this.context, width, height, this.renderOptions,
                this.camera);
    }


    /**
     * Actively paints the screen.
     */

    public void paintScreen()
    {
        // If buffer is not yet created then do nothing
        if (this.buffer == null) return;

        // If buffer is no longer valid then do nothing
        final int validate = this.buffer.validate(getGraphicsConfiguration());
        if (validate == VolatileImage.IMAGE_RESTORED
            || validate == VolatileImage.IMAGE_INCOMPATIBLE) return;

        // Draw the buffer to the screen
        this.getGraphics().drawImage(this.buffer, 0, 0, this);
        
        // Sync the display (needed on some systems)
        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * Sets the scene. If set to null then nothing is rendered in this panel.
     * 
     * @param scene
     *            The scene to use.
     */

    public void setScene(final Scene scene)
    {
        this.scene = scene;
    }


    /**
     * Sets the camera node. If set to null then a fixed default camera at
     * position 0,0,0 looking in direction 0,0,1 is used.
     * 
     * @param camera
     *            The camera node to set
     */

    public void setCamera(final CameraNode camera)
    {
        this.camera = camera;
    }


    /**
     * Returns the currently displayed scene. May return null if no scene is
     * currently displayed.
     * 
     * @return The currently displayed scene. Maybe null.
     */

    public Scene getScene()
    {
        return this.scene;
    }


    /**
     * Returns the current camera node. May return null if no specific camera
     * was set and a fixed default camera at position 0,0,0 looking in direction
     * 0,0,1 is used.
     * 
     * @return The current camera node. Maybe null.
     */

    public CameraNode getCamera()
    {
        return this.camera;
    }


    /**
     * Returns the render options of this 3D panel.
     * 
     * @return The render options
     */

    public RenderOptions getRenderOptions()
    {
        return this.renderOptions;
    }
}
