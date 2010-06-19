/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;





/**
 * A common profile
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class CommonProfile extends Profile
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The techniques */
    private final CommonTechniques techniques = new CommonTechniques();


    /**
     * Returns the techniques.
     *
     * @return The techniques
     */

    public CommonTechniques getTechniques()
    {
        return this.techniques;
    }
}
