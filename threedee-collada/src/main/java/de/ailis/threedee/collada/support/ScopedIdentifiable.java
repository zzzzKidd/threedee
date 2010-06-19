/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.support;



/**
 * A identifiable which is also identifiable in its parent scope.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface ScopedIdentifiable extends Identifiable
{
    /**
     * Returns the scoped ID.
     *
     * @return The scoped ID
     */

    public String getScopedId();
}
