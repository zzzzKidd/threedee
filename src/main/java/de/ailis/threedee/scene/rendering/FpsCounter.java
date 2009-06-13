/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.rendering;


/**
 * Counts frames per second.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class FpsCounter
{
    /** The last time a result was calculated */
    private long lastResult;
    
    /** The last calculated FPS value */
    private int fps;
    
    /** The current FPS counter */
    private int counter;
    
    
    /**
     * This method must be called each time a frame is drawn.
     */
    
    public void frame()
    {
        final long now = System.currentTimeMillis();
        this.counter++;
        if (this.lastResult == 0)
        {
            this.lastResult = now;
        }
        else if (this.lastResult + 1000 < now)
        {
            this.lastResult = now;
            this.fps = this.counter;
            this.counter = 0;
        }
    }
    
    
    /**
     * Returns the number of frames per second.
     * 
     * @return The number of frames per second
     */
    
    public int getFps()
    {
        return this.fps;
    }
}
