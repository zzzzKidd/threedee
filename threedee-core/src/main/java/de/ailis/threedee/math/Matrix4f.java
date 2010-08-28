/*
 * Copyright (C) 2010 Klaus Reimer (k@ailis.de)
 * See LICENSE.txt for licensing information
 */

package de.ailis.threedee.math;

import java.nio.FloatBuffer;
import java.util.Arrays;

import de.ailis.threedee.utils.BufferUtils;


/**
 * A 4x4 transformation matrix using floats. For speed reasons this class is
 * mutable and you are encouraged to use it accordingly to prevent excessive
 * garbage collection.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Matrix4f
{
    /** The internal array representation of the matrix */
    private final float[] m;

    /** The buffer representation of the matrix */
    private final FloatBuffer buffer;


    /**
     * Creates a new uninitialized matrix.
     */

    private Matrix4f()
    {
        this.m = new float[16];
        this.buffer = BufferUtils.createDirectFloatBuffer(16);
    }


    /**
     * Constructs a new matrix with the specified values.
     *
     * @param values
     *            The matrix values (Must have at least 16 values)
     */

    public Matrix4f(final float[] values)
    {
        this(values, 0);
    }


    /**
     * Constructs a new matrix with values read from the specified array
     * beginning at the given index.
     *
     * @param values
     *            The matrix values (Must have at least index+16 values)
     * @param index
     *            The start index
     */

    public Matrix4f(final float[] values, final int index)
    {
        this();
        for (int i = 0; i < 16; i++)
        {
            this.m[i] = values[index + i];
        }
    }


    /**
     * Returns the float buffer used to store the matrix. This buffer can be
     * used for GL commands.
     *
     * @return The float buffer representing the matrix
     */

    public FloatBuffer getBuffer()
    {
        this.buffer.put(this.m).rewind();
        return this.buffer;
    }


    /**
     * Returns the matrix array.
     *
     * @return The matrix array
     */

    public float[] getArray()
    {
        return this.m;
    }


    /**
     * Sets the values of the matrix.
     *
     * @param values
     *            Up to 16 float values to set the matrix to
     * @return This matrix for chaining
     */

    public Matrix4f set(final float... values)
    {
        for (int i = Math.min(16, values.length) - 1; i >= 0; i--)
            this.m[i] = values[i];
        return this;
    }


    /**
     * Sets a value at a specific column and row.
     *
     * @param column
     *            The column index
     * @param row
     *            The row index
     * @param value
     *            The value to set
     * @return This matrix for chaining
     */

    public Matrix4f setValue(final int column, final int row, final float value)
    {
        this.m[column * 4 + row] = value;
        return this;
    }


    /**
     * Returns the value at the specified column and row.
     *
     * @param column
     *            The column index
     * @param row
     *            The row index
     * @return The value
     */

    public float getValue(final int column, final int row)
    {
        return this.m[column * 4 + row];
    }


    /**
     * Copies the values of the specified matrix into this matrix.
     *
     * @param src
     *            The source matrix
     * @return This matrix (NOT the source matrix) for chaining
     */

    public Matrix4f set(final Matrix4f src)
    {
        return this.set(src.m);
    }


    /**
     * Returns a copy of this matrix.
     *
     * @return The copiy of the matrix
     */

    public Matrix4f copy()
    {
        return new Matrix4f().set(this);
    }


    /**
     * Copies the values of this matrix into the specified matrix.
     *
     * @param dest
     *            The source matrix
     * @return This matrix (NOT the destination matrix) for chaining
     */

    public Matrix4f copy(final Matrix4f dest)
    {
        dest.set(this.m);
        return this;
    }


    /**
     * Multiplies this matrix with the specified matrix.
     *
     * @param matrix
     *            The matrix to multiply this one with
     * @return This matrix for chaining
     */

    public Matrix4f multiply(final Matrix4f matrix)
    {
        return multiply(matrix.m);
    }


    /**
     * Multiplies this matrix with the specified matrix.
     *
     * @param b
     *            The matrix to multiply this one with. Must have 16 arguments
     *            in the order m00, m01, m02, m03, m10, ...
     * @return This matrix for chaining
     */

    public Matrix4f multiply(final float... b)
    {
        final float[] a = this.m;
        return set(a[0] * b[0] + a[4] * b[1] + a[8] * b[2] + a[12] * b[3], a[1]
                * b[0] + a[5] * b[1] + a[9] * b[2] + a[13] * b[3], a[2] * b[0]
                + a[6] * b[1] + a[10] * b[2] + a[14] * b[3], a[3] * b[0] + a[7]
                * b[1] + a[11] * b[2] + a[15] * b[3], a[0] * b[4] + a[4] * b[5]
                + a[8] * b[6] + a[12] * b[7], a[1] * b[4] + a[5] * b[5] + a[9]
                * b[6] + a[13] * b[7], a[2] * b[4] + a[6] * b[5] + a[10] * b[6]
                + a[14] * b[7], a[3] * b[4] + a[7] * b[5] + a[11] * b[6]
                + a[15] * b[7], a[0] * b[8] + a[4] * b[9] + a[8] * b[10]
                + a[12] * b[11], a[1] * b[8] + a[5] * b[9] + a[9] * b[10]
                + a[13] * b[11], a[2] * b[8] + a[6] * b[9] + a[10] * b[10]
                + a[14] * b[11], a[3] * b[8] + a[7] * b[9] + a[11] * b[10]
                + a[15] * b[11], a[0] * b[12] + a[4] * b[13] + a[8] * b[14]
                + a[12] * b[15], a[1] * b[12] + a[5] * b[13] + a[9] * b[14]
                + a[13] * b[15], a[2] * b[12] + a[6] * b[13] + a[10] * b[14]
                + a[14] * b[15], a[3] * b[12] + a[7] * b[13] + a[11] * b[14]
                + a[15] * b[15]);
    }


    /**
     * Returns a new identity matrix.
     *
     * @return The new identity matrix
     */

    public static Matrix4f identity()
    {
        return new Matrix4f().setIdentity();
    }


    /**
     * Returns a new rotation matrix around the specified vector.
     *
     * @param v
     *            The vector to rotate around
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return The new rotation matrix
     */

    public static Matrix4f rotation(final Vector3f v, final float angle)
    {
        return new Matrix4f().setRotation(v, angle);
    }


    /**
     * Returns a new X rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return The new identity matrix
     */

    public static Matrix4f rotationX(final float angle)
    {
        return new Matrix4f().setRotationX(angle);
    }


    /**
     * Returns a new Y rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return The new identity matrix
     */

    public static Matrix4f rotationY(final float angle)
    {
        return new Matrix4f().setRotationY(angle);
    }


    /**
     * Returns a new Z rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return The new identity matrix
     */

    public static Matrix4f rotationZ(final float angle)
    {
        return new Matrix4f().setRotationZ(angle);
    }


    /**
     * Returns a new translation matrix.
     *
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     * @return The new translation matrix
     */

    public static Matrix4f translation(final float tx, final float ty,
            final float tz)
    {
        return new Matrix4f().setTranslation(tx, ty, tz);
    }


    /**
     * Returns a new X translation matrix.
     *
     * @param t
     *            The translation delta
     * @return The new translation matrix
     */

    public static Matrix4f translationX(final float t)
    {
        return new Matrix4f().setTranslationX(t);
    }


    /**
     * Returns a new Y translation matrix.
     *
     * @param t
     *            The translation delta
     * @return The new translation matrix
     */

    public static Matrix4f translationY(final float t)
    {
        return new Matrix4f().setTranslationY(t);
    }


    /**
     * Returns a new Z translation matrix.
     *
     * @param t
     *            The translation delta
     * @return The new translation matrix
     */

    public static Matrix4f translationZ(final float t)
    {
        return new Matrix4f().setTranslationZ(t);
    }


    /**
     * Returns a new scale matrix.
     *
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     * @return The new scale matrix
     */

    public static Matrix4f scaling(final float sx, final float sy,
            final float sz)
    {
        return new Matrix4f().setScaling(sx, sy, sz);
    }


    /**
     * Returns a new scale matrix.
     *
     * @param s
     *            The scale factor for all axes
     * @return The new scale matrix
     */

    public static Matrix4f scaling(final float s)
    {
        return new Matrix4f().setScaling(s);
    }


    /**
     * Returns a new X scale matrix.
     *
     * @param s
     *            The scale factor
     * @return The new scale matrix
     */

    public static Matrix4f scalingX(final float s)
    {
        return new Matrix4f().setScalingX(s);
    }


    /**
     * Returns a new Y scale matrix.
     *
     * @param s
     *            The scale factor
     * @return The new scale matrix
     */

    public static Matrix4f scalingY(final float s)
    {
        return new Matrix4f().setScalingY(s);
    }


    /**
     * Returns a new Z scale matrix.
     *
     * @param s
     *            The scale factor
     * @return The new scale matrix
     */

    public static Matrix4f scalingZ(final float s)
    {
        return new Matrix4f().setScalingZ(s);
    }


    /**
     * Resets the matrix to an identity matrix.
     *
     * @return This matrix for chaining
     */

    public Matrix4f setIdentity()
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a rotation matrix around the specified vector.
     *
     * @param v
     *            The vector to rotate around
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return This matrix for chaining
     */

    public Matrix4f setRotation(final Vector3f v, final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        final float x = v.getX();
        final float y = v.getY();
        final float z = v.getZ();
        final float x2 = x * x;
        final float y2 = y * y;
        final float z2 = z * z;
        final float t = 1 - c;
        final float tYZ = t * y * z;
        final float tXY = t * x * y;
        final float sZ = s * z;
        final float sY = s * y;
        final float sX = s * x;
        return set(t * x2 + c, tXY + sZ, t * x * z - sY, 0, tXY - sZ, t * y2
                + c, tYZ + sX, 0, tXY + sY, tYZ - sX, t * z2 + c, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a X rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return This matrix for chaining
     */

    public Matrix4f setRotationX(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return set(1, 0, 0, 0, 0, c, s, 0, 0, -s, c, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a Y rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return This matrix for chaining
     */

    public Matrix4f setRotationY(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return set(c, 0, -s, 0, 0, 1, 0, 0, s, 0, c, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a Z rotation matrix.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD.
     * @return This matrix for chaining
     */

    public Matrix4f setRotationZ(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return set(c, s, 0, 0, -s, c, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a translation matrix.
     *
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     * @return This matrix for chaining
     */

    public Matrix4f setTranslation(final float tx, final float ty,
            final float tz)
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, tx, ty, tz, 1);
    }


    /**
     * Sets the matrix to a X translation matrix.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f setTranslationX(final float t)
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, t, 0, 0, 1);
    }


    /**
     * Sets the matrix to a Y translation matrix.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f setTranslationY(final float t)
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, t, 0, 1);
    }


    /**
     * Sets the matrix to a Z translation matrix.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f setTranslationZ(final float t)
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, t, 1);
    }


    /**
     * Sets the matrix to a scaling matrix.
     *
     * @param s
     *            The scale factor used on all axes
     * @return This matrix for chaining
     */

    public Matrix4f setScaling(final float s)
    {
        return set(s, 0, 0, 0, 0, s, 0, 0, 0, 0, s, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a scaling matrix.
     *
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     * @return This matrix for chaining
     */

    public Matrix4f setScaling(final float sx, final float sy, final float sz)
    {
        return set(sx, 0, 0, 0, 0, sy, 0, 0, 0, 0, sz, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a X scaling matrix.
     *
     * @param s
     *            The scale factor
     * @return This matrix for chaining
     */

    public Matrix4f setScalingX(final float s)
    {
        return set(s, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a Y scaling matrix.
     *
     * @param s
     *            The scale factor
     * @return This matrix for chaining
     */

    public Matrix4f setScalingY(final float s)
    {
        return set(1, 0, 0, 0, 0, s, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Sets the matrix to a Z scaling matrix.
     *
     * @param s
     *            The scale factor
     * @return This matrix for chaining
     */

    public Matrix4f setScalingZ(final float s)
    {
        return set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, s, 0, 0, 0, 0, 1);
    }


    /**
     * Rotates the matrix by the given angle around the given vector.
     *
     * @param v
     *            The vector to rotate around
     * @param angle
     *            The rotation angle in anti-clock-wise RAD
     * @return This matrix for chaining
     */

    public Matrix4f rotate(final Vector3f v, final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        final float x = v.getX();
        final float y = v.getY();
        final float z = v.getZ();
        final float x2 = x * x;
        final float y2 = y * y;
        final float z2 = z * z;
        final float t = 1 - c;
        final float tYZ = t * y * z;
        final float tXY = t * x * y;
        final float sZ = s * z;
        final float sY = s * y;
        final float sX = s * x;
        return multiply(t * x2 + c, tXY + sZ, t * x * z - sY, 0, tXY - sZ, t
                * y2 + c, tYZ + sX, 0, tXY + sY, tYZ - sX, t * z2 + c, 0, 0, 0,
                0, 1);
    }


    /**
     * Rotates the matrix by the given angle around the X axes.
     *
     * @param angle
     *            The rotation angle in anti-clock-wise RAD
     * @return This matrix for chaining
     */

    public Matrix4f rotateX(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return multiply(1, 0, 0, 0, 0, c, s, 0, 0, -s, c, 0, 0, 0, 0, 1);
    }


    /**
     * Rotates the matrix by the given angle around the Y axes.
     *
     * @param angle
     *            The rotation angle in clock-wise DEG
     * @return This matrix for chaining
     */

    public Matrix4f rotateY(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return multiply(c, 0, -s, 0, 0, 1, 0, 0, s, 0, c, 0, 0, 0, 0, 1);
    }


    /**
     * Rotates the matrix by the given angle around the Z axes.
     *
     * @param angle
     *            The rotation angle in clock-wise DEG
     * @return This matrix for chaining
     */

    public Matrix4f rotateZ(final float angle)
    {
        final float s = (float) Math.sin(angle);
        final float c = (float) Math.cos(angle);
        return multiply(c, s, 0, 0, -s, c, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Translates the matrix on all axes.
     *
     * @param tx
     *            The X translation delta
     * @param ty
     *            The Y translation delta
     * @param tz
     *            The Z translation delta
     * @return This matrix for chaining
     */

    public Matrix4f translate(final float tx, final float ty, final float tz)
    {
        return multiply(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, tx, ty, tz, 1);
    }


    /**
     * Translates the matrix on the X axes.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f translateX(final float t)
    {
        return multiply(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, t, 0, 0, 1);
    }


    /**
     * Translates the matrix on the Y axes.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f translateY(final float t)
    {
        return multiply(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, t, 0, 1);
    }


    /**
     * Translates the matrix on the Z axes.
     *
     * @param t
     *            The translation delta
     * @return This matrix for chaining
     */

    public Matrix4f translateZ(final float t)
    {
        return multiply(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, t, 1);
    }


    /**
     * Scales matrix with the given scale factor on all axes.
     *
     * @param s
     *            The scale factor
     * @return The matrix for chaining
     */

    public Matrix4f scale(final float s)
    {
        return multiply(s, 0, 0, 0, 0, s, 0, 0, 0, 0, s, 0, 0, 0, 0, 1);
    }


    /**
     * Scales the matrix with the given scale factors.
     *
     * @param sx
     *            The X scale factor
     * @param sy
     *            The Y scale factor
     * @param sz
     *            The Z scale factor
     * @return The matrix for chaining
     */

    public Matrix4f scale(final float sx, final float sy, final float sz)
    {
        return multiply(sx, 0, 0, 0, 0, sy, 0, 0, 0, 0, sz, 0, 0, 0, 0, 1);
    }


    /**
     * Scales matrix with the given scale factor on the X axes.
     *
     * @param s
     *            The scale factor
     * @return The matrix for chaining
     */

    public Matrix4f scaleX(final float s)
    {
        return multiply(s, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Scales matrix with the given scale factor on the Y axes.
     *
     * @param s
     *            The scale factor
     * @return The matrix for chaining
     */

    public Matrix4f scaleY(final float s)
    {
        return multiply(1, 0, 0, 0, 0, s, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    /**
     * Scales matrix with the given scale factor on the Z axes.
     *
     * @param s
     *            The scale factor
     * @return The matrix for chaining
     */

    public Matrix4f scaleZ(final float s)
    {
        return multiply(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, s, 0, 0, 0, 0, 1);
    }


    /**
     * Divides the matrix with the specified scale factor.
     *
     * @param f
     *            The scale factor
     * @return This matrix for chaining
     */

    public Matrix4f divide(final double f)
    {
        final float[] m = this.m;
        m[0] /= f;
        m[1] /= f;
        m[2] /= f;
        m[3] /= f;
        m[4] /= f;
        m[5] /= f;
        m[6] /= f;
        m[7] /= f;
        m[8] /= f;
        m[9] /= f;
        m[10] /= f;
        m[11] /= f;
        m[12] /= f;
        m[13] /= f;
        m[14] /= f;
        m[15] /= f;
        return this;
    }


    /**
     * Returns the determinant of the matrix. It is cached in a transient field
     * for speed optimization.
     *
     * @return The determinant of the matrix
     */

    public float getDeterminant()
    {
        final float[] m = this.m;
        return m[3] * m[6] * m[9] * m[12] - m[2] * m[7] * m[9] * m[12] - m[3]
                * m[5] * m[10] * m[12] + m[1] * m[7] * m[10] * m[12] + m[2]
                * m[5] * m[11] * m[12] - m[1] * m[6] * m[11] * m[12] - m[3]
                * m[6] * m[8] * m[13] + m[2] * m[7] * m[8] * m[13] + m[3]
                * m[4] * m[10] * m[13] - m[0] * m[7] * m[10] * m[13] - m[2]
                * m[4] * m[11] * m[13] + m[0] * m[6] * m[11] * m[13] + m[3]
                * m[5] * m[8] * m[14] - m[1] * m[7] * m[8] * m[14] - m[3]
                * m[4] * m[9] * m[14] + m[0] * m[7] * m[9] * m[14] + m[1]
                * m[4] * m[11] * m[14] - m[0] * m[5] * m[11] * m[14] - m[2]
                * m[5] * m[8] * m[15] + m[1] * m[6] * m[8] * m[15] + m[2]
                * m[4] * m[9] * m[15] - m[0] * m[6] * m[9] * m[15] - m[1]
                * m[4] * m[10] * m[15] + m[0] * m[5] * m[10] * m[15];
    }


    /**
     * Inverts this matrix.
     *
     * @return This matrix for chaining
     */

    public Matrix4f invert()
    {
        final float[] m = this.m;
        return set(
                m[6] * m[11] * m[13] - m[7] * m[10] * m[13] + m[7] * m[9]
                        * m[14] - m[5] * m[11] * m[14] - m[6] * m[9] * m[15]
                        + m[5] * m[10] * m[15],

                m[3] * m[10] * m[13] - m[2] * m[11] * m[13] - m[3] * m[9]
                        * m[14] + m[1] * m[11] * m[14] + m[2] * m[9] * m[15]
                        - m[1] * m[10] * m[15],

                m[2] * m[7] * m[13] - m[3] * m[6] * m[13] + m[3] * m[5] * m[14]
                        - m[1] * m[7] * m[14] - m[2] * m[5] * m[15] + m[1]
                        * m[6] * m[15],

                m[3] * m[6] * m[9] - m[2] * m[7] * m[9] - m[3] * m[5] * m[10]
                        + m[1] * m[7] * m[10] + m[2] * m[5] * m[11] - m[1]
                        * m[6] * m[11],

                m[7] * m[10] * m[12] - m[6] * m[11] * m[12] - m[7] * m[8]
                        * m[14] + m[4] * m[11] * m[14] + m[6] * m[8] * m[15]
                        - m[4] * m[10] * m[15],

                m[2] * m[11] * m[12] - m[3] * m[10] * m[12] + m[3] * m[8]
                        * m[14] - m[0] * m[11] * m[14] - m[2] * m[8] * m[15]
                        + m[0] * m[10] * m[15],

                m[3] * m[6] * m[12] - m[2] * m[7] * m[12] - m[3] * m[4] * m[14]
                        + m[0] * m[7] * m[14] + m[2] * m[4] * m[15] - m[0]
                        * m[6] * m[15],

                m[2] * m[7] * m[8] - m[3] * m[6] * m[8] + m[3] * m[4] * m[10]
                        - m[0] * m[7] * m[10] - m[2] * m[4] * m[11] + m[0]
                        * m[6] * m[11],

                m[5] * m[11] * m[12] - m[7] * m[9] * m[12] + m[7] * m[8]
                        * m[13] - m[4] * m[11] * m[13] - m[5] * m[8] * m[15]
                        + m[4] * m[9] * m[15],

                m[3] * m[9] * m[12] - m[1] * m[11] * m[12] - m[3] * m[8]
                        * m[13] + m[0] * m[11] * m[13] + m[1] * m[8] * m[15]
                        - m[0] * m[9] * m[15],

                m[1] * m[7] * m[12] - m[3] * m[5] * m[12] + m[3] * m[4] * m[13]
                        - m[0] * m[7] * m[13] - m[1] * m[4] * m[15] + m[0]
                        * m[5] * m[15],

                m[3] * m[5] * m[8] - m[1] * m[7] * m[8] - m[3] * m[4] * m[9]
                        + m[0] * m[7] * m[9] + m[1] * m[4] * m[11] - m[0]
                        * m[5] * m[11],

                m[6] * m[9] * m[12] - m[5] * m[10] * m[12] - m[6] * m[8]
                        * m[13] + m[4] * m[10] * m[13] + m[5] * m[8] * m[14]
                        - m[4] * m[9] * m[14],

                m[1] * m[10] * m[12] - m[2] * m[9] * m[12] + m[2] * m[8]
                        * m[13] - m[0] * m[10] * m[13] - m[1] * m[8] * m[14]
                        + m[0] * m[9] * m[14],

                m[2] * m[5] * m[12] - m[1] * m[6] * m[12] - m[2] * m[4] * m[13]
                        + m[0] * m[6] * m[13] + m[1] * m[4] * m[14] - m[0]
                        * m[5] * m[14],

                m[1] * m[6] * m[8] - m[2] * m[5] * m[8] + m[2] * m[4] * m[9]
                        - m[0] * m[6] * m[9] - m[1] * m[4] * m[10] + m[0]
                        * m[5] * m[10]).divide(getDeterminant());
    }


    /**
     * Checks if this matrix is a identity matrix.
     *
     * @return True if matrix is identity matrix, false if not
     */

    public boolean isIdentity()
    {
        final float[] m = this.m;
        return m[0] == 1 && m[4] == 0 && m[8] == 0 && m[12] == 0 && m[1] == 0
                && m[5] == 1 && m[9] == 0 && m[13] == 0 && m[2] == 0
                && m[6] == 0 && m[10] == 1 && m[14] == 0 && m[3] == 0
                && m[7] == 0 && m[11] == 0 && m[15] == 1;
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.m);
        return result;
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Matrix4f other = (Matrix4f) obj;
        if (!Arrays.equals(this.m, other.m)) return false;
        return true;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        final float[] m = this.m;
        return "[[ " + m[0] + ", " + m[1] + ", " + m[2] + ", " + m[3] + "], "
                + "[ " + m[4] + ", " + m[5] + ", " + m[6] + ", " + m[7] + "], "
                + "[ " + m[8] + ", " + m[9] + ", " + m[10] + ", " + m[11]
                + "], " + "[ " + m[12] + ", " + m[13] + ", " + m[14] + ", "
                + m[15] + "]]";
    }

    /**
     * Transpose the matrix.
     *
     * @return This matrix for chaining
     */

    public Matrix4f transpose()
    {
        final float[] m = this.m;
        return this.set(
            m[0], m[4], m[8], m[12],
            m[1], m[5], m[9], m[13],
            m[2], m[6], m[10], m[14],
            m[3], m[7], m[11], m[15]);
    }
}
