/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Library;


/**
 * The profiles of an effect.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Profiles extends Library<Profile>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Returns the first common profile if there is one. May return null when no
     * common profile was found.
     *
     * @return The first common profile or null if not found.
     */

    public CommonProfile getCommonProfile()
    {
        for (final Profile profile : this)
            if (profile instanceof CommonProfile)
                return (CommonProfile) profile;
        return null;
    }
}
