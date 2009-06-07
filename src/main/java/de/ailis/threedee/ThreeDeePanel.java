/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

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
    private BufferedImage buffer;

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
            repaint();
            try
            {
                Thread.sleep(20);
            }
            catch (final InterruptedException e)
            {
                // Ignored
            }
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

        if (width == 0 || height == 0) return;
        if (this.buffer == null || this.buffer.getWidth() != width
            || this.buffer.getHeight() != height)
        {
            this.buffer =
                new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            this.context = this.buffer.createGraphics();
        }
        
        this.context.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //this.context.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

        this.context.setColor(Color.BLACK);
        this.context.fillRect(0, 0, width, height);

        // Render the scene if one is set
        if (this.scene != null)
            this.scene.render(this.context, width, height, this.camera);
    }


    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */

    @Override
    public void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        if (this.buffer != null) g.drawImage(this.buffer, 0, 0, null);
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
}
