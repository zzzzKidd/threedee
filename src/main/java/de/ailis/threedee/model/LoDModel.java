/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import de.ailis.threedee.math.Matrix4d;


/**
 * A model with enabled Level-of-Detail.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface LoDModel extends Model
{
    /**
     * Prepares the Level-of-Detail of the model.
     * 
     * @param transform
     *            The transformation which is going to be applied to the model
     *            to display it
     * @param factor
     *            The perspective scale factor
     */

    public void prepareLoD(final Matrix4d transform, double factor);
}
