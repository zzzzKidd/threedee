/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.math;


/**
 * Classes implementing this interface provide many methods for handling
 * transformations.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface Transformable
{
    /**
     * Sets a new transformation matrix.
     * 
     * @param transform
     *            The transformation matrix to set
     */

    public void setTransform(final Matrix4d transform);


    /**
     * Returns the current transformation matrix.
     * 
     * @return The current transformation matrix
     */

    public Matrix4d getTransform();


    /**
     * Adds the specified transformation to the current transformation.
     * 
     * @param transform
     *            The transformation matrix to multiply the current
     *            transformation matrix with
     */

    public void addTransform(final Matrix4d transform);


    /**
     * Subtracts the specified transformation from the current transformation.
     * This is the same as adding the inverse of the transformation matrix.
     * 
     * @param transform
     *            The transformation matrix to divide the current transformation
     *            matrix with
     */

    public void subTransform(final Matrix4d transform);


    /**
     * Translates the current transformation matrix by the specified deltas.
     * 
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     */

    public void translate(final double tx, final double ty, final double tz);


    /**
     * Translates the current transformation matrix by the specified X delta.
     * 
     * @param t
     *            The X translation delta
     */

    public void translateX(final double t);


    /**
     * Translates the current transformation matrix by the specified Y delta.
     * 
     * @param t
     *            The Y translation delta
     */

    public void translateY(final double t);


    /**
     * Translates the current transformation matrix by the specified Z delta.
     * 
     * @param t
     *            The Z translation delta
     */

    public void translateZ(final double t);


    /**
     * Scales the current transformation matrix by the specified factors.
     * 
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     */

    public void scale(final double sx, final double sy, final double sz);


    /**
     * Scales the current transformation by the specified factor on all three
     * axis.
     * 
     * @param s
     *            The scale factor
     */

    public void scale(final double s);


    /**
     * Scales the current transformation matrix by the specified X factor.
     * 
     * @param s
     *            The X scale factor
     */

    public void scaleX(final double s);


    /**
     * Scales the current transformation matrix by the specified Y factor.
     * 
     * @param s
     *            The Y scale factor
     */

    public void scaleY(final double s);


    /**
     * Scales the current transformation matrix by the specified Z factor.
     * 
     * @param s
     *            The Z scale factor
     */

    public void scaleZ(final double s);


    /**
     * Rotates the current transformation matrix by the specified angle around
     * the X axis.
     * 
     * @param r
     *            The X rotation angle in clock-wise RAD.
     */

    public void rotateX(final double r);


    /**
     * Rotates the current transformation matrix by the specified angle around
     * the Y axis.
     * 
     * @param r
     *            The Y rotation angle in clock-wise RAD.
     */

    public void rotateY(final double r);


    /**
     * Rotates the current transformation matrix by the specified angle around
     * the Z axis.
     * 
     * @param r
     *            The Z rotation angle in clock-wise RAD.
     */

    public void rotateZ(final double r);
}
