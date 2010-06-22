/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.physics;

import de.ailis.threedee.math.Vector3f;


/**
 * Acceleration physics. An acceleration object is used in some other
 * physic contexts like spin or velocity. The unit of acceleration depends
 * on the context. For a spin the acceleration unit is degree per square-second
 * for example.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Acceleration extends Vector3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;
}
