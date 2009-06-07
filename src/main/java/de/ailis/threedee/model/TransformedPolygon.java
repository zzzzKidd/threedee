/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;


/**
 * A transformed polygon. It extends a normal polygon but adds a property which
 * contains the average Z coordinate of the polygon which is needed for polygon
 * sorting. A transformed polygon always needs a material because it has no
 * longer a connection to a model.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class TransformedPolygon extends Polygon implements
    Comparable<TransformedPolygon>
{
    /** Serial version UID */
    private static final long serialVersionUID = -6939574579761214928L;

    /** The cached average z coordinate */
    private final double averageZ;


    /**
     * Constructs a new polygon with a polygon-specific material.
     * 
     * @param material
     *            The material used by this polygon. Must not be null.
     * @param averageZ
     *            The average Z index for Z sorting
     * @param vertices
     *            The referenced vertices
     */

    public TransformedPolygon(final Material material, final double averageZ,
        final int... vertices)
    {
        super(material, vertices);
        this.averageZ = averageZ;
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final TransformedPolygon o)
    {
        return -Double.compare(this.averageZ, o.averageZ);
    }
}
