/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.physics;

import de.ailis.threedee.math.Vector3f;


/**
 * Deceleration physics. A deceleration object is used in some other
 * physic contexts like spin or velocity. The unit of deceleration depends
 * on the context. For a spin the deceleration unit is degree per square-second
 * for example.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Deceleration extends Vector3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;
}
