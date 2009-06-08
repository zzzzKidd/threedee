/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.math;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * A matrix with 4 x 4 entries of type double.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Matrix4d implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1831757793057781305L;

    /** The identity matrix */
    private final static Matrix4d IDENTITY =
        new Matrix4d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);

    /** The cached determinant */
    private transient double determinantCache = Double.NaN;

    /** The cached inverse matrix */
    private transient Matrix4d invertCache = null;

    /** The matrix entry 0;0 */
    private final double m00;

    /** The matrix entry 0;1 */
    private final double m01;

    /** The matrix entry 0;2 */
    private final double m02;

    /** The matrix entry 0;3 */
    private final double m03;

    /** The matrix entry 1;0 */
    private final double m10;

    /** The matrix entry 1;1 */
    private final double m11;

    /** The matrix entry 1;2 */
    private final double m12;

    /** The matrix entry 1;3 */
    private final double m13;

    /** The matrix entry 0;0 */
    private final double m20;

    /** The matrix entry 0;1 */
    private final double m21;

    /** The matrix entry 0;2 */
    private final double m22;

    /** The matrix entry 0;3 */
    private final double m23;

    /** The matrix entry 1;0 */
    private final double m30;

    /** The matrix entry 1;1 */
    private final double m31;

    /** The matrix entry 1;2 */
    private final double m32;

    /** The matrix entry 1;3 */
    private final double m33;

    /**
     * Constructs a new matrix with the specified entries.
     * 
     * @param m00
     *            The matrix entry 0;0
     * @param m01
     *            The matrix entry 0;1
     * @param m02
     *            The matrix entry 0;2
     * @param m03
     *            The matrix entry 0;3
     * @param m10
     *            The matrix entry 1;0
     * @param m11
     *            The matrix entry 1;1
     * @param m12
     *            The matrix entry 1;2
     * @param m13
     *            The matrix entry 1;3
     * @param m20
     *            The matrix entry 2;0
     * @param m21
     *            The matrix entry 2;1
     * @param m22
     *            The matrix entry 2;2
     * @param m23
     *            The matrix entry 2;3
     * @param m30
     *            The matrix entry 3;0
     * @param m31
     *            The matrix entry 3;1
     * @param m32
     *            The matrix entry 3;2
     * @param m33
     *            The matrix entry 3;3
     */

    public Matrix4d(final double m00, final double m01, final double m02,
        final double m03, final double m10, final double m11,
        final double m12, final double m13, final double m20,
        final double m21, final double m22, final double m23,
        final double m30, final double m31, final double m32, final double m33)
    {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }


    /**
     * Returns the identity matrix
     * 
     * @return The identity matrix
     */

    public static Matrix4d identity()
    {
        return IDENTITY;
    }


    /**
     * Returns a matrix for rotation around the X axis.
     * 
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A X rotation matrix
     */

    public static Matrix4d rotationX(final double angle)
    {
        final double s = Math.sin(angle);
        final double c = Math.cos(angle);
        return new Matrix4d(1, 0, 0, 0, 0, c, -s, 0, 0, s, c, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a matrix for rotation around the Y axis.
     * 
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A Y rotation matrix
     */

    public static Matrix4d rotationY(final double angle)
    {
        final double s = Math.sin(angle);
        final double c = Math.cos(angle);
        return new Matrix4d(c, 0, s, 0, 0, 1, 0, 0, -s, 0, c, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a matrix for rotation around the Z axis.
     * 
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A Z rotation matrix
     */

    public static Matrix4d rotationZ(final double angle)
    {
        final double s = Math.sin(angle);
        final double c = Math.cos(angle);
        return new Matrix4d(c, -s, 0, 0, s, c, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on all axis.
     * 
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    public static Matrix4d scaling(final double f)
    {
        return new Matrix4d(f, 0, 0, 0, 0, f, 0, 0, 0, 0, f, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a scale matrix for scaling by the specified factors.
     * 
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     * @return The scale matrix
     */

    public static Matrix4d scaling(final double sx, final double sy,
        final double sz)
    {
        return new Matrix4d(sx, 0, 0, 0, 0, sy, 0, 0, 0, 0, sz, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the X axis.
     * 
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    public static Matrix4d scalingX(final double f)
    {
        return new Matrix4d(f, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the Y axis.
     * 
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    public static Matrix4d scalingY(final double f)
    {
        return new Matrix4d(1, 0, 0, 0, 0, f, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the Z axis.
     * 
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    public static Matrix4d scalingZ(final double f)
    {
        return new Matrix4d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, f, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a translation matrix for translating by the specified deltas.
     * 
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     * @return The translation matrix
     */

    public static Matrix4d translation(final double tx, final double ty,
        final double tz)
    {
        return new Matrix4d(1, 0, 0, tx, 0, 1, 0, ty, 0, 0, 1, tz, 0, 0, 0, 1);
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the X axis.
     * 
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    public static Matrix4d translationX(final double t)
    {
        return new Matrix4d(1, 0, 0, t, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the Y axis.
     * 
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    public static Matrix4d translationY(final double t)
    {
        return new Matrix4d(1, 0, 0, 0, 0, 1, 0, t, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the Z axis.
     * 
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    public static Matrix4d translationZ(final double t)
    {
        return new Matrix4d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, t, 0, 0, 0, 1);
    }


    /**
     * Returns the matrix entry 0;0.
     * 
     * @return The matrix entry 0;0
     */

    public double getM00()
    {
        return this.m00;
    }


    /**
     * Returns the matrix entry 0;1.
     * 
     * @return The matrix entry 0;1
     */

    public double getM01()
    {
        return this.m01;
    }


    /**
     * Returns the matrix entry 0;2.
     * 
     * @return The matrix entry 0;2
     */

    public double getM02()
    {
        return this.m02;
    }


    /**
     * Returns the matrix entry 0;3.
     * 
     * @return The matrix entry 0;3
     */

    public double getM03()
    {
        return this.m03;
    }


    /**
     * Returns the matrix entry 1;0.
     * 
     * @return The matrix entry 1;0
     */

    public double getM10()
    {
        return this.m10;
    }


    /**
     * Returns the matrix entry 1;1.
     * 
     * @return The matrix entry 1;1
     */

    public double getM11()
    {
        return this.m11;
    }


    /**
     * Returns the matrix entry 1;2.
     * 
     * @return The matrix entry 1;2
     */

    public double getM12()
    {
        return this.m12;
    }


    /**
     * Returns the matrix entry 1;3.
     * 
     * @return The matrix entry 1;3
     */

    public double getM13()
    {
        return this.m13;
    }


    /**
     * Returns the matrix entry 2;0.
     * 
     * @return The matrix entry 2;0
     */

    public double getM20()
    {
        return this.m20;
    }


    /**
     * Returns the matrix entry 2;1.
     * 
     * @return The matrix entry 2;1
     */

    public double getM21()
    {
        return this.m21;
    }


    /**
     * Returns the matrix entry 2;2.
     * 
     * @return The matrix entry 2;2
     */

    public double getM22()
    {
        return this.m22;
    }


    /**
     * Returns the matrix entry 2;3.
     * 
     * @return The matrix entry 2;3
     */

    public double getM23()
    {
        return this.m23;
    }


    /**
     * Returns the matrix entry 3;0.
     * 
     * @return The matrix entry 3;0
     */

    public double getM30()
    {
        return this.m30;
    }


    /**
     * Returns the matrix entry 3;1.
     * 
     * @return The matrix entry 3;1
     */

    public double getM31()
    {
        return this.m31;
    }


    /**
     * Returns the matrix entry 3;2.
     * 
     * @return The matrix entry 3;2
     */

    public double getM32()
    {
        return this.m32;
    }


    /**
     * Returns the matrix entry 3;3.
     * 
     * @return The matrix entry 3;3
     */

    public double getM33()
    {
        return this.m33;
    }


    /**
     * @see Object#equals(Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Matrix4d that = (Matrix4d) obj;
        return new EqualsBuilder().append(this.m00, that.m00).append(this.m01,
            that.m01).append(this.m02, that.m02).append(this.m03, that.m03)
            .append(this.m10, that.m10).append(this.m11, that.m11).append(
                this.m12, that.m12).append(this.m13, that.m13).append(
                this.m20, that.m20).append(this.m21, that.m21).append(
                this.m22, that.m22).append(this.m23, that.m23).append(
                this.m30, that.m30).append(this.m31, that.m31).append(
                this.m32, that.m32).append(this.m33, that.m33).isEquals();
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m00).append(this.m01).append(
            this.m02).append(this.m03).append(this.m10).append(this.m11)
            .append(this.m12).append(this.m13).append(this.m20).append(
                this.m21).append(this.m22).append(this.m23).append(this.m20)
            .append(this.m31).append(this.m32).append(this.m33).toHashCode();
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "[[" + this.m00 + "," + this.m01 + "," + this.m02 + ","
            + this.m03 + "]," + "[" + this.m10 + "," + this.m11 + ","
            + this.m12 + "," + this.m13 + "]," + "[" + this.m20 + ","
            + this.m21 + "," + this.m22 + "," + this.m23 + "]," + "["
            + this.m30 + "," + this.m31 + "," + this.m32 + "," + this.m33
            + "]]";
    }


    /**
     * Adds the specified matrix to the current matrix and returns the result.
     * 
     * @param m
     *            The matrix to add to the current matrix
     * @return The resulting matrix
     */

    public Matrix4d add(final Matrix4d m)
    {
        return new Matrix4d(this.m00 + m.m00, this.m01 + m.m01, this.m02
            + m.m02, this.m03 + m.m03, this.m10 + m.m10, this.m11 + m.m11,
            this.m12 + m.m12, this.m13 + m.m13, this.m20 + m.m20, this.m21
                + m.m21, this.m22 + m.m22, this.m23 + m.m23, this.m30 + m.m30,
            this.m31 + m.m31, this.m32 + m.m32, this.m33 + m.m33);
    }


    /**
     * Subtracts the specified matrix from the current matrix and returns the
     * result.
     * 
     * @param m
     *            The matrix to subtract from the current matrix
     * @return The resulting matrix
     */

    public Matrix4d sub(final Matrix4d m)
    {
        return new Matrix4d(this.m00 - m.m00, this.m01 - m.m01, this.m02
            - m.m02, this.m03 - m.m03, this.m10 - m.m10, this.m11 - m.m11,
            this.m12 - m.m12, this.m13 - m.m13, this.m20 - m.m20, this.m21
                - m.m21, this.m22 - m.m22, this.m23 - m.m23, this.m30 - m.m30,
            this.m31 - m.m31, this.m32 - m.m32, this.m33 - m.m33);
    }


    /**
     * Multiplies the current matrix with the specified matrix and returns the
     * result.
     * 
     * @param m
     *            The matrix to multiply the current matrix with
     * @return The resulting matrix
     */

    public Matrix4d multiply(final Matrix4d m)
    {
        return new Matrix4d(this.m00 * m.m00 + this.m01 * m.m10 + this.m02
            * m.m20 + this.m03 * m.m30, this.m00 * m.m01 + this.m01 * m.m11
            + this.m02 * m.m21 + this.m03 * m.m31, this.m00 * m.m02 + this.m01
            * m.m12 + this.m02 * m.m22 + this.m03 * m.m32, this.m00 * m.m03
            + this.m01 * m.m13 + this.m02 * m.m23 + this.m03 * m.m33,

        this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20 + this.m13
            * m.m30, this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21
            + this.m13 * m.m31, this.m10 * m.m02 + this.m11 * m.m12 + this.m12
            * m.m22 + this.m13 * m.m32, this.m10 * m.m03 + this.m11 * m.m13
            + this.m12 * m.m23 + this.m13 * m.m33,

        this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20 + this.m23
            * m.m30, this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21
            + this.m23 * m.m31, this.m20 * m.m02 + this.m21 * m.m12 + this.m22
            * m.m22 + this.m23 * m.m32, this.m20 * m.m03 + this.m21 * m.m13
            + this.m22 * m.m23 + this.m23 * m.m33,

        this.m30 * m.m00 + this.m31 * m.m10 + this.m32 * m.m20 + this.m33
            * m.m30, this.m30 * m.m01 + this.m31 * m.m11 + this.m32 * m.m21
            + this.m33 * m.m31, this.m30 * m.m02 + this.m31 * m.m12 + this.m32
            * m.m22 + this.m33 * m.m32, this.m30 * m.m03 + this.m31 * m.m13
            + this.m32 * m.m23 + this.m33 * m.m33);
    }


    /**
     * Multiplies the matrix with the specified scale factor and returns the
     * result.
     * 
     * @param f
     *            The scale factor
     * @return The resulting matrix
     */

    public Matrix4d multiply(final double f)
    {
        return new Matrix4d(this.m00 * f, this.m01 * f, this.m02 * f, this.m03
            * f, this.m10 * f, this.m11 * f, this.m12 * f, this.m13 * f,
            this.m20 * f, this.m21 * f, this.m22 * f, this.m23 * f, this.m30
                * f, this.m31 * f, this.m32 * f, this.m33 * f);
    }


    /**
     * Multiplies this matrix with the specified vector and returns the
     * resulting vector.
     * 
     * @param v
     *            The vector to multiply this matrix with
     * @return The resulting vector
     */

    public Vector3d multiply(final Vector3d v)
    {
        final double x = v.getX();
        final double y = v.getY();
        final double z = v.getZ();
        return new Vector3d(this.m00 * x + this.m01 * y + this.m02 * z
            + this.m03, this.m10 * x + this.m11 * y + this.m12 * z + this.m13,
            this.m20 * x + this.m21 * y + this.m22 * z + this.m23);
    }


    /**
     * Translates the current matrix by the specified deltas and returns the
     * resulting matrix.
     * 
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     * @return The resulting matrix
     */

    public Matrix4d translate(final double tx, final double ty, final double tz)
    {
        return multiply(Matrix4d.translation(tx, ty, tz));
    }


    /**
     * Translates the current matrix by the specified X delta and returns the
     * resulting matrix.
     * 
     * @param t
     *            The X translation delta
     * @return The resulting matrix
     */

    public Matrix4d translateX(final double t)
    {
        return multiply(Matrix4d.translationX(t));
    }


    /**
     * Translates the current matrix by the specified Y delta and returns the
     * resulting matrix.
     * 
     * @param t
     *            The Y translation delta
     * @return The resulting matrix
     */

    public Matrix4d translateY(final double t)
    {
        return multiply(Matrix4d.translationY(t));
    }


    /**
     * Translates the current matrix by the specified Z delta and returns the
     * resulting matrix.
     * 
     * @param t
     *            The Z translation delta
     * @return The resulting matrix
     */

    public Matrix4d translateZ(final double t)
    {
        return multiply(Matrix4d.translationZ(t));
    }


    /**
     * Rotates the current matrix by the specified angle around the X axis and
     * returns the resulting matrix.
     * 
     * @param r
     *            The X rotation angle in clock-wise RAD
     * @return The resulting matrix
     */

    public Matrix4d rotateX(final double r)
    {
        return multiply(Matrix4d.rotationX(r));
    }


    /**
     * Rotates the current matrix by the specified angle around the Y axis and
     * returns the resulting matrix.
     * 
     * @param r
     *            The Y rotation angle in clock-wise RAD
     * @return The resulting matrix
     */

    public Matrix4d rotateY(final double r)
    {
        return multiply(Matrix4d.rotationY(r));
    }


    /**
     * Rotates the current matrix by the specified angle around the Z axis and
     * returns the resulting matrix.
     * 
     * @param r
     *            The Z rotation angle in clock-wise RAD
     * @return The resulting matrix
     */

    public Matrix4d rotateZ(final double r)
    {
        return multiply(Matrix4d.rotationZ(r));
    }


    /**
     * Scales the current matrix by the specified factors and returns the
     * resulting matrix.
     * 
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     * @return The resulting matrix
     */

    public Matrix4d scale(final double sx, final double sy, final double sz)
    {
        return multiply(Matrix4d.scaling(sx, sy, sz));
    }


    /**
     * Scales the current matrix by the specified X factor and returns the
     * resulting matrix.
     * 
     * @param s
     *            The X scale factor
     * @return The resulting matrix
     */

    public Matrix4d scaleX(final double s)
    {
        return multiply(Matrix4d.scalingX(s));
    }


    /**
     * Scales the current matrix by the specified Y factor and returns the
     * resulting matrix.
     * 
     * @param s
     *            The Y scale factor
     * @return The resulting matrix
     */

    public Matrix4d scaleY(final double s)
    {
        return multiply(Matrix4d.scalingY(s));
    }


    /**
     * Scales the current matrix by the specified Z factor and returns the
     * resulting matrix.
     * 
     * @param s
     *            The Z scale factor
     * @return The resulting matrix
     */

    public Matrix4d scaleZ(final double s)
    {
        return multiply(Matrix4d.scalingZ(s));
    }


    /**
     * Divides the matrix with the specified scale factor and returns the
     * result.
     * 
     * @param f
     *            The scale factor
     * @return The resulting matrix
     */

    public Matrix4d divide(final double f)
    {
        return new Matrix4d(this.m00 / f, this.m01 / f, this.m02 / f, this.m03
            / f, this.m10 / f, this.m11 / f, this.m12 / f, this.m13 / f,
            this.m20 / f, this.m21 / f, this.m22 / f, this.m23 / f, this.m30
                / f, this.m31 / f, this.m32 / f, this.m33 / f);
    }


    /**
     * Divides the current matrix with the specified matrix and returns the
     * result. Dividing is the same as multiplying with the inverse matrix.
     * 
     * @param m
     *            The matrix to divide the current matrix with
     * @return The resulting matrix
     */

    public Matrix4d divide(final Matrix4d m)
    {
        return multiply(m.invert());
    }


    /**
     * Returns the determinant of the matrix. It is cached in a transient field
     * for speed optimization.
     * 
     * @return The determinant of the matrix
     */

    public double getDeterminant()
    {
        if (Double.isNaN(this.determinantCache))
        {
            this.determinantCache =
                this.m03 * this.m12 * this.m21 * this.m30 - this.m02
                    * this.m13 * this.m21 * this.m30 - this.m03 * this.m11
                    * this.m22 * this.m30 + this.m01 * this.m13 * this.m22
                    * this.m30 + this.m02 * this.m11 * this.m23 * this.m30
                    - this.m01 * this.m12 * this.m23 * this.m30 - this.m03
                    * this.m12 * this.m20 * this.m31 + this.m02 * this.m13
                    * this.m20 * this.m31 + this.m03 * this.m10 * this.m22
                    * this.m31 - this.m00 * this.m13 * this.m22 * this.m31
                    - this.m02 * this.m10 * this.m23 * this.m31 + this.m00
                    * this.m12 * this.m23 * this.m31 + this.m03 * this.m11
                    * this.m20 * this.m32 - this.m01 * this.m13 * this.m20
                    * this.m32 - this.m03 * this.m10 * this.m21 * this.m32
                    + this.m00 * this.m13 * this.m21 * this.m32 + this.m01
                    * this.m10 * this.m23 * this.m32 - this.m00 * this.m11
                    * this.m23 * this.m32 - this.m02 * this.m11 * this.m20
                    * this.m33 + this.m01 * this.m12 * this.m20 * this.m33
                    + this.m02 * this.m10 * this.m21 * this.m33 - this.m00
                    * this.m12 * this.m21 * this.m33 - this.m01 * this.m10
                    * this.m22 * this.m33 + this.m00 * this.m11 * this.m22
                    * this.m33;
        }
        return this.determinantCache;
    }


    /**
     * Returns the inverse matrix of the current matrix. The result is cached in
     * a transient field for speed optimization.
     * 
     * @return The inverse matrix
     */

    public Matrix4d invert()
    {
        if (this.invertCache == null)
        {
            this.invertCache =
                new Matrix4d(this.m12 * this.m23 * this.m31 - this.m13
                    * this.m22 * this.m31 + this.m13 * this.m21 * this.m32
                    - this.m11 * this.m23 * this.m32 - this.m12 * this.m21
                    * this.m33 + this.m11 * this.m22 * this.m33,

                this.m03 * this.m22 * this.m31 - this.m02 * this.m23
                    * this.m31 - this.m03 * this.m21 * this.m32 + this.m01
                    * this.m23 * this.m32 + this.m02 * this.m21 * this.m33
                    - this.m01 * this.m22 * this.m33,

                this.m02 * this.m13 * this.m31 - this.m03 * this.m12
                    * this.m31 + this.m03 * this.m11 * this.m32 - this.m01
                    * this.m13 * this.m32 - this.m02 * this.m11 * this.m33
                    + this.m01 * this.m12 * this.m33,

                this.m03 * this.m12 * this.m21 - this.m02 * this.m13
                    * this.m21 - this.m03 * this.m11 * this.m22 + this.m01
                    * this.m13 * this.m22 + this.m02 * this.m11 * this.m23
                    - this.m01 * this.m12 * this.m23,

                this.m13 * this.m22 * this.m30 - this.m12 * this.m23
                    * this.m30 - this.m13 * this.m20 * this.m32 + this.m10
                    * this.m23 * this.m32 + this.m12 * this.m20 * this.m33
                    - this.m10 * this.m22 * this.m33,

                this.m02 * this.m23 * this.m30 - this.m03 * this.m22
                    * this.m30 + this.m03 * this.m20 * this.m32 - this.m00
                    * this.m23 * this.m32 - this.m02 * this.m20 * this.m33
                    + this.m00 * this.m22 * this.m33,

                this.m03 * this.m12 * this.m30 - this.m02 * this.m13
                    * this.m30 - this.m03 * this.m10 * this.m32 + this.m00
                    * this.m13 * this.m32 + this.m02 * this.m10 * this.m33
                    - this.m00 * this.m12 * this.m33,

                this.m02 * this.m13 * this.m20 - this.m03 * this.m12
                    * this.m20 + this.m03 * this.m10 * this.m22 - this.m00
                    * this.m13 * this.m22 - this.m02 * this.m10 * this.m23
                    + this.m00 * this.m12 * this.m23,

                this.m11 * this.m23 * this.m30 - this.m13 * this.m21
                    * this.m30 + this.m13 * this.m20 * this.m31 - this.m10
                    * this.m23 * this.m31 - this.m11 * this.m20 * this.m33
                    + this.m10 * this.m21 * this.m33,

                this.m03 * this.m21 * this.m30 - this.m01 * this.m23
                    * this.m30 - this.m03 * this.m20 * this.m31 + this.m00
                    * this.m23 * this.m31 + this.m01 * this.m20 * this.m33
                    - this.m00 * this.m21 * this.m33,

                this.m01 * this.m13 * this.m30 - this.m03 * this.m11
                    * this.m30 + this.m03 * this.m10 * this.m31 - this.m00
                    * this.m13 * this.m31 - this.m01 * this.m10 * this.m33
                    + this.m00 * this.m11 * this.m33,

                this.m03 * this.m11 * this.m20 - this.m01 * this.m13
                    * this.m20 - this.m03 * this.m10 * this.m21 + this.m00
                    * this.m13 * this.m21 + this.m01 * this.m10 * this.m23
                    - this.m00 * this.m11 * this.m23,

                this.m12 * this.m21 * this.m30 - this.m11 * this.m22
                    * this.m30 - this.m12 * this.m20 * this.m31 + this.m10
                    * this.m22 * this.m31 + this.m11 * this.m20 * this.m32
                    - this.m10 * this.m21 * this.m32,

                this.m01 * this.m22 * this.m30 - this.m02 * this.m21
                    * this.m30 + this.m02 * this.m20 * this.m31 - this.m00
                    * this.m22 * this.m31 - this.m01 * this.m20 * this.m32
                    + this.m00 * this.m21 * this.m32,

                this.m02 * this.m11 * this.m30 - this.m01 * this.m12
                    * this.m30 - this.m02 * this.m10 * this.m31 + this.m00
                    * this.m12 * this.m31 + this.m01 * this.m10 * this.m32
                    - this.m00 * this.m11 * this.m32,

                this.m01 * this.m12 * this.m20 - this.m02 * this.m11
                    * this.m20 + this.m02 * this.m10 * this.m21 - this.m00
                    * this.m12 * this.m21 - this.m01 * this.m10 * this.m22
                    + this.m00 * this.m11 * this.m22).divide(getDeterminant());
        }
        return this.invertCache;
    }
}
