/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * A touch event.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TouchEvent
{
    /** The ID of a tracked touch */
    private final int id;

    /** X coordinate of the touch */
    private final int x;

    /** Y coordinate of the touch */
    private final int y;


    /***
     * Constructs a new touch event.
     *
     * @param id
     *            The touch ID
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     */

    public TouchEvent(final int id, final int x, final int y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }


    /**
     * Returns the touch id.
     *
     * @return The touch id
     */

    public int getId()
    {
        return this.id;
    }


    /**
     * Returns the x coordinate.
     *
     * @return The x coordinate
     */

    public int getX()
    {
        return this.x;
    }


    /**
     * Returns the y coordinate.
     *
     * @return The y coordinate
     */

    public int getY()
    {
        return this.y;
    }
}
