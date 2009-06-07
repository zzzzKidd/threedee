/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.math


/**
 * A matrix with 4 x 4 elements.
 */

@serializable
final class Matrix4d(val m00: Double, val m01: Double, val m02: Double, val m03: Double,
                     val m10: Double, val m11: Double, val m12: Double, val m13: Double,
                     val m20: Double, val m21: Double, val m22: Double, val m23: Double,
                     val m30: Double, val m31: Double, val m32: Double, val m33: Double)
{
    /** The cached determinant */
    @transient
    private var determinantCache = Math.NaN_DOUBLE;

    /** The cached inverse matrix */
    @transient
    private var invertCache: Matrix4d = null;

    
    /**
     * @see java.lang.equals(Object)
     */

    override def equals(m: Any): Boolean =
    {
        m match
        {
            case m: Matrix4d =>
                m00 == m.m00 && m01 == m.m01 && m02 == m.m02 && m03 == m.m03 &&
                m10 == m.m10 && m11 == m.m11 && m12 == m.m12 && m13 == m.m13 &&
                m20 == m.m20 && m21 == m.m21 && m22 == m.m22 && m23 == m.m23 &&
                m30 == m.m30 && m31 == m.m31 && m32 == m.m32 && m33 == m.m33
            case _ => false
        }
    }


    /**
     * @see java.lang.hashCode
     */
    
    override def hashCode: Int =
    {
        41 *
        (41 *
         (41 *
          (41 *
           (41 *
            (41 *
             (41 *
              (41 *
               (41 *
                (41 *
                 (41 *
                  (41 *
                   (41 *
                    (41 *
                     (41 *
                      (41 +
                       m00.hashCode) +
                      m01.hashCode) +
                     m02.hashCode) +
                    m03.hashCode) +
                   m10.hashCode) +
                  m11.hashCode) +
                 m12.hashCode) +
                m13.hashCode) +
               m20.hashCode) +
              m21.hashCode) +
             m22.hashCode) +
            m23.hashCode) +
           m30.hashCode) +
          m31.hashCode) +
         m32.hashCode) +
        m33.hashCode
    }


    /**
     * @see java.lang.toString
     */

    override def toString: String =
    {
        "[[" + m00 + "," + m01 + "," + m02 + "," + m03 +"]," +
        "[" + m10 + "," + m11 + "," + m12 + "," + m13 +"]," +
        "[" + m20 + "," + m21 + "," + m22 + "," + m23 +"]," +
        "[" + m30 + "," + m31 + "," + m32 + "," + m33 +"]]"
    }


    /**
     * Adds the specified matrix to the current matrix and returns the result.
     *
     * @param m
     *            The matrix to add to the current matrix
     * @return The resulting matrix
     */

    def + (m: Matrix4d): Matrix4d =
    {
        new Matrix4d(m00 + m.m00, m01 + m.m01, m02 + m.m02, m03 + m.m03,
                     m10 + m.m10, m11 + m.m11, m12 + m.m12, m13 + m.m13,
                     m20 + m.m20, m21 + m.m21, m22 + m.m22, m23 + m.m23,
                     m30 + m.m30, m31 + m.m31, m32 + m.m32, m33 + m.m33)
    }


    /**
     * Subtracts the specified matrix from the current matrix and returns the
     * result.
     *
     * @param m
     *            The matrix to subtract from the current matrix
     * @return The resulting matrix
     */

    def - (m: Matrix4d): Matrix4d =
    {
        new Matrix4d(m00 - m.m00, m01 - m.m01, m02 - m.m02, m03 - m.m03,
                     m10 - m.m10, m11 - m.m11, m12 - m.m12, m13 - m.m13,
                     m20 - m.m20, m21 - m.m21, m22 - m.m22, m23 - m.m23,
                     m30 - m.m30, m31 - m.m31, m32 - m.m32, m33 - m.m33)
    }


    /**
     * Multiplies the current matrix with the specified matrix and returns the
     * result.
     *
     * @param m
     *            The matrix to multiply the current matrix with
     * @return The resulting matrix
     */

    def * (m: Matrix4d): Matrix4d =
    {
        new Matrix4d(m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30,
                     m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31,
                     m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32,
                     m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33,

                     m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30,
                     m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31,
                     m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32,
                     m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33,

                     m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30,
                     m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31,
                     m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32,
                     m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33,

                     m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30,
                     m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31,
                     m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32,
                     m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33)
    }


    /**
     * Multiplies the matrix with the specified scale factor and returns the
     * result.
     *
     * @param f
     *            The scale factor
     * @return The resulting matrix
     */

    def * (f: Double): Matrix4d =
    {
        new Matrix4d(m00 * f, m01 * f, m02 * f, m03 * f,
                     m10 * f, m11 * f, m12 * f, m13 * f,
                     m20 * f, m21 * f, m22 * f, m23 * f,
                     m30 * f, m31 * f, m32 * f, m33 * f)
    }


    /**
     * Multiplies this matrix with the specified vector and returns the
     * resulting vector.
     *
     * @param v
     *            The vector to multiply this matrix with
     * @return The resulting vector
     */

    def * (v: Vector3d): Vector3d =
    {
        new Vector3d(m00 * v.x + m01 * v.y + m02 * v.z + m03,
                     m10 * v.x + m11 * v.y + m12 * v.z + m13,
                     m20 * v.x + m21 * v.y + m22 * v.z + m23)
    }


    /**
     * Divides the matrix with the specified scale factor and returns the
     * result.
     *
     * @param f
     *            The scale factor
     * @return The resulting matrix
     */

    def / (f: Double): Matrix4d =
    {
        new Matrix4d(m00 / f, m01 / f, m02 / f, m03 / f,
                     m10 / f, m11 / f, m12 / f, m13 / f,
                     m20 / f, m21 / f, m22 / f, m23 / f,
                     m30 / f, m31 / f, m32 / f, m33 / f)
    }


    /**
     * Returns the determinant of the matrix. It is cached in a transient
     * field for speed optimization.
     *
     * @return The determinant of the matrix
     */

    def determinant: Double =
    {
        if (determinantCache == Math.NaN_DOUBLE)
        {
            determinantCache =
            m03 * m12 * m21 * m30 - m02 * m13 * m21 * m30 -
            m03 * m11 * m22 * m30 + m01 * m13 * m22 * m30 +
            m02 * m11 * m23 * m30 - m01 * m12 * m23 * m30 -
            m03 * m12 * m20 * m31 + m02 * m13 * m20 * m31 +
            m03 * m10 * m22 * m31 - m00 * m13 * m22 * m31 -
            m02 * m10 * m23 * m31 + m00 * m12 * m23 * m31 +
            m03 * m11 * m20 * m32 - m01 * m13 * m20 * m32 -
            m03 * m10 * m21 * m32 + m00 * m13 * m21 * m32 +
            m01 * m10 * m23 * m32 - m00 * m11 * m23 * m32 -
            m02 * m11 * m20 * m33 + m01 * m12 * m20 * m33 +
            m02 * m10 * m21 * m33 - m00 * m12 * m21 * m33 -
            m01 * m10 * m22 * m33 + m00 * m11 * m22 * m33;
        }
        determinantCache
    }


    /**
     * Returns the inverse matrix of the current matrix. The result is cached
     * in a transient field for speed optimization.
     *
     * @return The inverse matrix
     */

    def invert: Matrix4d =
    {
        if (invertCache == null)
        {
            invertCache = new Matrix4d(
                m12 * m23 * m31 - m13 * m22 * m31 + m13 * m21 * m32 - m11 * m23 * m32 - m12 * m21 * m33 + m11 * m22 * m33,
                  m03 * m22 * m31 - m02 * m23 * m31 - m03 * m21 * m32 + m01 * m23 * m32 + m02 * m21 * m33 - m01 * m22 * m33,
                  m02 * m13 * m31 - m03 * m12 * m31 + m03 * m11 * m32 - m01 * m13 * m32 - m02 * m11 * m33 + m01 * m12 * m33,
                  m03 * m12 * m21 - m02 * m13 * m21 - m03 * m11 * m22 + m01 * m13 * m22 + m02 * m11 * m23 - m01 * m12 * m23,

                  m13 * m22 * m30 - m12 * m23 * m30 - m13 * m20 * m32 + m10 * m23 * m32 + m12 * m20 * m33 - m10 * m22 * m33,
                  m02 * m23 * m30 - m03 * m22 * m30 + m03 * m20 * m32 - m00 * m23 * m32 - m02 * m20 * m33 + m00 * m22 * m33,
                  m03 * m12 * m30 - m02 * m13 * m30 - m03 * m10 * m32 + m00 * m13 * m32 + m02 * m10 * m33 - m00 * m12 * m33,
                  m02 * m13 * m20 - m03 * m12 * m20 + m03 * m10 * m22 - m00 * m13 * m22 - m02 * m10 * m23 + m00 * m12 * m23,

                  m11 * m23 * m30 - m13 * m21 * m30 + m13 * m20 * m31 - m10 * m23 * m31 - m11 * m20 * m33 + m10 * m21 * m33,
                  m03 * m21 * m30 - m01 * m23 * m30 - m03 * m20 * m31 + m00 * m23 * m31 + m01 * m20 * m33 - m00 * m21 * m33,
                  m01 * m13 * m30 - m03 * m11 * m30 + m03 * m10 * m31 - m00 * m13 * m31 - m01 * m10 * m33 + m00 * m11 * m33,
                  m03 * m11 * m20 - m01 * m13 * m20 - m03 * m10 * m21 + m00 * m13 * m21 + m01 * m10 * m23 - m00 * m11 * m23,

                  m12 * m21 * m30 - m11 * m22 * m30 - m12 * m20 * m31 + m10 * m22 * m31 + m11 * m20 * m32 - m10 * m21 * m32,
                  m01 * m22 * m30 - m02 * m21 * m30 + m02 * m20 * m31 - m00 * m22 * m31 - m01 * m20 * m32 + m00 * m21 * m32,
                  m02 * m11 * m30 - m01 * m12 * m30 - m02 * m10 * m31 + m00 * m12 * m31 + m01 * m10 * m32 - m00 * m11 * m32,
                  m01 * m12 * m20 - m02 * m11 * m20 + m02 * m10 * m21 - m00 * m12 * m21 - m01 * m10 * m22 + m00 * m11 * m22) /
            determinant
        }
        invertCache
    }
}


object Matrix4d
{
    /** The identity matrix */
    val identity = new Matrix4d(1, 0, 0, 0,
                                0, 1, 0, 0,
                                0, 0, 1, 0,
                                0, 0, 0, 1)
    /**
     * Returns a matrix for rotation around the X axis.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A X rotation matrix
     */
    
    def rotationX(angle: Double): Matrix4d =
    {
        val s = Math.sin(angle)
        val c = Math.cos(angle)
        new Matrix4d(1, 0,  0, 0,
                     0, c, -s, 0,
                     0, s,  c, 0,
                     0, 0,  0, 1)
    }


    /**
     * Returns a matrix for rotation around the Y axis.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A Y rotation matrix
     */

    def rotationY(angle: Double): Matrix4d =
    {
        val s = Math.sin(angle)
        val c = Math.cos(angle)
        new Matrix4d(c,  0, s, 0,
                     0,  1, 0, 0,
                     -s, 0, c, 0,
                     0,  0, 0, 1)
    }


    /**
     * Returns a matrix for rotation around the Z axis.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return A Z rotation matrix
     */

    def rotationZ(angle: Double): Matrix4d =
    {
        val s = Math.sin(angle)
        val c = Math.cos(angle)
        new Matrix4d(c, -s, 0, 0,
                     s,  c, 0, 0,
                     0,  0, 1, 0,
                     0,  0, 0, 1)
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on all
     * axis.
     *
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    def scale(f: Double): Matrix4d =
    {
        new Matrix4d(f, 0, 0, 0,
                     0, f, 0, 0,
                     0, 0, f, 0,
                     0, 0, 0, 1)
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

    def scale(sx: Double, sy: Double, sz: Double): Matrix4d =
    {
        new Matrix4d(sx,  0,  0, 0,
                     0, sy,  0, 0,
                     0,  0, sz, 0,
                     0,  0,  0, 1)
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the
     * X axis.
     *
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    def scaleX(f: Double): Matrix4d =
    {
        new Matrix4d(f, 0, 0, 0,
                     0, 1, 0, 0,
                     0, 0, 1, 0,
                     0, 0, 0, 1)
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the
     * Y axis.
     *
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    def scaleY(f: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, 0,
                     0, f, 0, 0,
                     0, 0, 1, 0,
                     0, 0, 0, 1)
    }


    /**
     * Returns a scale matrix for scaling by the specified factor on the
     * Z axis.
     *
     * @param f
     *            The scale factor
     * @return The scale matrix
     */

    def scaleZ(f: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, 0,
                     0, 1, 0, 0,
                     0, 0, f, 0,
                     0, 0, 0, 1)
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

    def translation(tx: Double, ty: Double, tz: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, tx,
                     0, 1, 0, ty,
                     0, 0, 1, tz,
                     0, 0, 0,  1)
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the X axis.
     *
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    def translationX(t: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, t,
                     0, 1, 0, 0,
                     0, 0, 1, 0,
                     0, 0, 0, 1)
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the Y axis.
     *
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    def translationY(t: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, 0,
                     0, 1, 0, t,
                     0, 0, 1, 0,
                     0, 0, 0, 1)
    }


    /**
     * Returns a translation matrix for translating by the specified delta on
     * the Z axis.
     *
     * @param t
     *            The translation delta
     * @return The translation matrix
     */

    def translationZ(t: Double): Matrix4d =
    {
        new Matrix4d(1, 0, 0, 0,
                     0, 1, 0, 0,
                     0, 0, 1, t,
                     0, 0, 0, 1)
    }
}
