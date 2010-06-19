/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.support;

import java.io.Serializable;



/**
 * A image.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface Identifiable extends Serializable
{
    /**
     * Returns the ID.
     *
     * @return The ID
     */

    public String getId();
}
