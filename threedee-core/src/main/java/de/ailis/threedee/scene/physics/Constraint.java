/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.physics;

import de.ailis.threedee.math.Coord3f;


/**
 * A constraint.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Constraint extends Coord3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param initial
     *            The initial value
     */

    public Constraint(final float initial)
    {
        super(initial, initial, initial);
    }


    /**
     * Constructor
     *
     * @param initialX
     *            The initial X value
     * @param initialY
     *            The initial Y value
     * @param initialZ
     *            The initial Z value
     */

    public Constraint(final float initialX, final float initialY,
            final float initialZ)
    {
        super(initialX, initialY, initialZ);
    }
}
