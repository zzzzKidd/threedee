/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.output.swing;

import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import de.ailis.threedee.output.RenderOptions;
import de.ailis.threedee.output.ThreeDeeOutput;
import de.ailis.threedee.scene.CameraNode;
import de.ailis.threedee.scene.Scene;


/**
 * ThreeDeeFrame
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ThreeDeeFrame extends JFrame implements Runnable, ThreeDeeOutput
{
    /** Serial version UID */
    private static final long serialVersionUID = 8446547221414937343L;

    /** The buffer strategy used by this frame */
    private volatile BufferStrategy bufferStrategy;

    /** The animator thread */
    private Thread animator;

    /** For stopping the animation */
    private volatile boolean running = false;

    /**
     * The camera to use for rendering the scene. If null then a fixed default
     * camera at position 0,0,0 looking in direction 0,0,1 is used
     */
    private volatile CameraNode camera;

    /** The scene */
    private volatile Scene scene;

    /** The last game update (Nanoseconds) */
    private long lastUpdate = System.nanoTime();

    /** The render options */
    private final RenderOptions renderOptions = new RenderOptions();

    /** If frame should be displayed in full screen mode */
    private boolean fullScreen = false;

    /** If page flipping should be used */
    private boolean pageFlip = false;


    /**
     * Constructs a new ThreeDee frame with the specified window title.
     * 
     * @param title
     *            The title to set
     */

    public ThreeDeeFrame(final String title)
    {
        super(title);

        // Ignore repaints because we do active painting
        setIgnoreRepaint(true);
    }


    /**
     * Intercept showing and hiding of the window so we can perform stuff like
     * switching video modes and fullscreen at the right time.
     * 
     * @see java.awt.Window#setVisible(boolean)
     */

    @Override
    public void setVisible(final boolean visible)
    {
        if (!isDisplayable() && this.fullScreen) setUndecorated(true);

        super.setVisible(visible);
        if (visible)
        {
            if (this.fullScreen)
            {
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().setFullScreenWindow(this);
            }
            try
            {
                createBufferStrategy(2, new BufferCapabilities(
                    new ImageCapabilities(true), new ImageCapabilities(true),
                    this.pageFlip ? BufferCapabilities.FlipContents.UNDEFINED
                        : null));
            }
            catch (final AWTException e)
            {
                throw new RuntimeException(e.toString(), e);
            }
            this.bufferStrategy = getBufferStrategy();
        }
        else
        {
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().setFullScreenWindow(null);
            this.bufferStrategy = null;
        }
    }


    /**
     * @see javax.swing.JComponent#addNotify()
     */

    @Override
    public void addNotify()
    {
        super.addNotify();
        startAnimator();
    }


    /**
     * Initialize and start the thread
     */

    private void startAnimator()
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

    public void stopAnimator()
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
            update();
            paintScreen();
            Thread.yield();
        }
    }

    /**
     * Updates the game state
     */

    private void update()
    {
        // If no scene is set then do nothing
        if (this.scene == null) return;

        final long now = System.nanoTime();
        this.scene.update(now - this.lastUpdate);
        this.lastUpdate = now;
    }


    /**
     * Paints the current frame onto the screen
     */

    private void paintScreen()
    {
        final Insets insets = getInsets();
        final int width = getWidth() - insets.right - insets.left;
        final int height = getHeight() - insets.bottom - insets.top;

        // If window size is 0 then do nothing
        if (width <= 0 || height <= 0) return;

        // If buffer strategy is not set yet then do nothing
        if (this.bufferStrategy == null) return;

        // Get the graphics context to drawn on. This sometimes fails for some
        // unknown reason. If it happens we ignore the painting of the frame
        final Graphics2D g;
        try
        {
            g = (Graphics2D) this.bufferStrategy.getDrawGraphics();
        }
        catch (final Exception e)
        {
            return;
        }

        // Transform the context so the 0/0 coordinate is the left/top corner.
        // If we don't do this here then it may be outside of the screen because
        // of the window decorations.
        g.translate(insets.left, insets.top);

        // Render the scene
        render(g, width, height);

        // Dispose the graphics context and flip the buffers
        g.dispose();
        if (!this.bufferStrategy.contentsLost())
        {
            this.bufferStrategy.show();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    /**
     * Renders the game
     * 
     * @param g
     *            The graphics context to render the scene onto
     * @param width
     *            The output width
     * @param height
     *            The output height
     */

    private void render(final Graphics2D g, final int width, final int height)
    {
        // Initialize the output buffer with blackness
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Render the scene if one is set
        if (this.scene != null)
            this.scene.render(g, width, height, this.renderOptions,
                this.camera);

    }


    /**
     * @see ThreeDeeOutput#setScene(Scene)
     */

    public void setScene(final Scene scene)
    {
        this.scene = scene;
    }


    /**
     * @see ThreeDeeOutput#getScene()
     */

    public Scene getScene()
    {
        return this.scene;
    }


    /**
     * @see ThreeDeeOutput#setCamera(CameraNode)
     */

    public void setCamera(final CameraNode camera)
    {
        this.camera = camera;
    }


    /**
     * @see ThreeDeeOutput#getCamera()
     */

    public CameraNode getCamera()
    {
        return this.camera;
    }


    /**
     * @see ThreeDeeOutput#getRenderOptions()
     */

    public RenderOptions getRenderOptions()
    {
        return this.renderOptions;
    }


    /**
     * Enables or disables page flipping. On some systems for some reason page
     * flipping is slower then buffer blitting so we provide the possibility to
     * disable page flipping here.
     * 
     * @param pageFlip
     *            True to enable page flipping, false to disable it
     */

    public void setPageFlip(final boolean pageFlip)
    {
        this.pageFlip = pageFlip;
        applyScreenChanges();
    }


    /**
     * Checks if page flipping is enabled or not.
     * 
     * @return True if page flipping is enabled, false if not
     */

    public boolean isPageFlip()
    {
        return this.pageFlip;
    }


    /**
     * Enables or disables full screen mode
     * 
     * @param fullScreen
     *            True to enable full screen mode, false to disable it
     */

    public void setFullScreen(final boolean fullScreen)
    {
        if (fullScreen != this.fullScreen)
        {
            this.fullScreen = fullScreen;
            applyScreenChanges();
        }
    }


    /**
     * Checks if full screen mode is enabled or not.
     * 
     * @return True if full screen mode is enabled, false if not
     */

    public boolean isFullScreen()
    {
        return this.fullScreen;
    }


    /**
     * Applies screen changes by hiding and showing the window again if it is
     * currently visible. This is needed because some screen settings can only
     * be applied when the window is not currently visible.
     */

    private void applyScreenChanges()
    {
        if (isVisible())
        {
            setVisible(false);
            setVisible(true);
        }
    }
}
