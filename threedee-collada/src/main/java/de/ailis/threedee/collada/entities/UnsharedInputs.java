/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.util.ArrayList;


/**
 * A list of unshared inputs
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class UnsharedInputs extends ArrayList<UnsharedInput>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Returns the entry with the specified semantic. May return null if
     * entry was not found.
     *
     * @param semantic
     *            The semantic
     * @return The entry or null if not found
     */

    public UnsharedInput get(final Semantic semantic)
    {
        for (final UnsharedInput input : this)
            if (semantic == input.getSemantic()) return input;
        return null;
    }


    /**
     * Checks if an input with the specified semantic exists.
     *
     * @param semantic
     *            The semantic to look for
     * @return True if input exists, false if not
     */

    public boolean contains(final Semantic semantic)
    {
        for (final UnsharedInput input : this)
            if (semantic == input.getSemantic()) return true;
        return false;
    }
}
