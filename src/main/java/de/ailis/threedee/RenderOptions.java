/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee;


/**
 * This class defines how rendering is performed in a ThreeDeePanel.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class RenderOptions
{
    /** If normals should be rendered */
    private boolean displayNormals = false;

    /** If lighting is enabled */
    private boolean lighting = true;

    /** If polygons should be filled */
    private boolean solid = true;

    /** If anti-aliasing should be used */
    private boolean antiAliasing = true;
    
    /** If backface culling should be performed */
    private boolean backfaceCulling = true;
    

    /**
     * Enables or disables the display of normals.
     * 
     * @param displayNormals
     *            True to display normals, false to hide them
     */

    public void setDisplayNormals(final boolean displayNormals)
    {
        this.displayNormals = displayNormals;
    }


    /**
     * Checks if displaying normals is enabled. Default: disabled.
     * 
     * @return True if displaying normals is enabled, false if not
     */

    public boolean isDisplayNormals()
    {
        return this.displayNormals;
    }


    /**
     * Checks if lighting is enabled.
     * 
     * @return True if lighting is enabled, false if not
     */

    public boolean isLighting()
    {
        return this.lighting;
    }


    /**
     * Checks if polygons should be rendered solid.
     * 
     * @return True if polygons are rendered solid, false if not
     */

    public boolean isSolid()
    {
        return this.solid;
    }


    /**
     * Enables or disables the lighting. Default: enabled.
     *
     * @param lighting
     *            True to enable lighting, false to disable it
     */
    
    public void setLighting(final boolean lighting)
    {
        this.lighting = lighting;
    }


    /**
     * Enables or disables solid drawing of polygons. Default: enabled.
     *
     * @param solid
     *            True to draw solid polygons, false to draw only the edges
     */
    
    public void setSolid(final boolean solid)
    {
        this.solid = solid;
    }


    /**
     * Checks if anti-aliasing is enabled or not.
     *
     * @return True if anti-aliasing is enabled, false of not
     */
    
    public boolean isAntiAliasing()
    {
        return this.antiAliasing;
    }


    /**
     * Enables or disables anti-aliasing. Default: enabled.
     *
     * @param antiAliasing
     *            True to enable anti-aliasing, false to disable it
     */
    
    public void setAntiAliasing(final boolean antiAliasing)
    {
        this.antiAliasing = antiAliasing;
    }


    /**
     * Checks if backface culling is enabled or not.
     *
     * @return True if backface culling is enabled, false if not
     */
    
    public boolean isBackfaceCulling()
    {
        return this.backfaceCulling;
    }


    /**
     * Enables or disables backface culling. Default: enabled.
     *
     * @param backfaceCulling
     *            True to enable backface culling, false to disable it.
     */
    
    public void setBackfaceCulling(final boolean backfaceCulling)
    {
        this.backfaceCulling = backfaceCulling;
    }
}
