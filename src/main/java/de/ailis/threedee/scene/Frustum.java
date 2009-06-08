/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import de.ailis.threedee.math.Line;
import de.ailis.threedee.math.Plane;
import de.ailis.threedee.math.Vector3d;


/**
 * Frustum
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Frustum
{
    /** The left plane */
    private final Plane left;

    /** The right plane */
    private final Plane right;

    /** The top plane */
    private final Plane top;

    /** The bottom plane */
    private final Plane bottom;

    /** The near plane */
    private final Plane near;


    /**
     * Constructs a new frustum for the specified screen size and scale factor.
     * 
     * @param width
     *            The screen width in pixels
     * @param height
     *            The screen height in pixels
     * @param scale
     *            The scale factor. This is the factor you multiply the X and Y
     *            coordinates with before dividing them by the Z coordinate to
     *            project the 3D coordinates to 2D
     */

    public Frustum(final int width, final int height, final double scale)
    {
        final double xAngle = Math.atan2(width / 2 - 5, scale) - 0.0001;
        final double yAngle = Math.atan2(height / 2 - 5, scale) - 0.0001;
        final double sh = Math.sin(xAngle);
        final double sv = Math.sin(yAngle);
        final double ch = Math.cos(xAngle);
        final double cv = Math.cos(yAngle);

        this.left = new Plane(new Vector3d(ch, 0, sh), 0);
        this.right = new Plane(new Vector3d(-ch, 0, sh), 0);
        this.top = new Plane(new Vector3d(0, -cv, sv), 0);
        this.bottom = new Plane(new Vector3d(0, cv, sv), 0);
        this.near = new Plane(new Vector3d(0, 0, 1), -10);
    }


    /**
     * Returns the left clipping plane.
     * 
     * @return The left clipping plane
     */

    public Plane getLeft()
    {
        return this.left;
    }


    /**
     * Returns the right clipping plane.
     * 
     * @return The right clipping plane
     */

    public Plane getRight()
    {
        return this.right;
    }


    /**
     * Returns the top clipping plane.
     * 
     * @return The top clipping plane
     */

    public Plane getTop()
    {
        return this.top;
    }


    /**
     * Returns the bottom clipping plane.
     * 
     * @return The bottom clipping plane
     */

    public Plane getBottom()
    {
        return this.bottom;
    }


    /**
     * Returns the near clipping plane.
     * 
     * @return The near clipping plane
     */

    public Plane getNear()
    {
        return this.near;
    }


    /**
     * Clips the specified line. Returns null if the line was completely clipped
     * away
     * 
     * @param line
     *            The line to clip
     * @return The clipped line or null if the line was completely clipped away
     */

    public Line clip(final Line line)
    {
        Line clippedLine = line.clip(this.left);
        if (clippedLine == null) return null;
        clippedLine = clippedLine.clip(this.right);
        if (clippedLine == null) return null;
        clippedLine = clippedLine.clip(this.top);
        if (clippedLine == null) return null;
        clippedLine = clippedLine.clip(this.bottom);
        if (clippedLine == null) return null;
        clippedLine = clippedLine.clip(this.near);
        if (clippedLine == null) return null;
        return clippedLine;
    }
}
