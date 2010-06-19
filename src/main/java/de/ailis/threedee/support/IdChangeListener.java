/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.support;


/**
 * Listener interface for ID changes.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface IdChangeListener<T>
{
    /**
     * Called when the ID of the monitored identifiable changed.
     *
     * @param identifiable
     *            The identifiable that has changed its id
     * @param oldId
     *            The old id
     */

    public void idChanged(T identifiable, String oldId);
}
