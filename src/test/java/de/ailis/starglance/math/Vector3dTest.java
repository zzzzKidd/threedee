/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Vector3d;


/**
 * Tests the Vector3d class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Vector3dTest
{
    /**
     * Tests access to the vector components
     */

    @Test
    public void testComponents()
    {
        final Vector3d v = new Vector3d(1, 2, 3);
        assertEquals(1d, v.getX(), 0.01);
        assertEquals(2d, v.getY(), 0.01);
        assertEquals(3d, v.getZ(), 0.01);
    }


    /**
     * Tests access to the vector components
     */

    @Test
    public void testToString()
    {
        assertEquals("[1.0,2.0,3.0]", new Vector3d(1, 2, 3).toString());
    }


    /**
     * Tests the addition of vectors)
     */

    @Test
    public void testAdd()
    {
        assertEquals(new Vector3d(3, 5, 7), new Vector3d(1, 2, 3)
            .add(new Vector3d(2, 3, 4)));
    }


    /**
     * Tests the subtraction of vectors)
     */

    @Test
    public void testSub()
    {
        assertEquals(new Vector3d(-1, -2, -3), new Vector3d(1, 2, 3)
            .sub(new Vector3d(2, 4, 6)));
    }


    /**
     * Tests multiplying a vector with a factor
     */

    @Test
    public void testMul()
    {
        assertEquals(new Vector3d(2, 4, 6), new Vector3d(1, 2, 3).multiply(2));
    }


    /**
     * Tests dividing a vector by a factor
     */

    @Test
    public void testDiv()
    {
        assertEquals(new Vector3d(1d / 2d, 2d / 2d, 3d / 2d), new Vector3d(1,
            2, 3).divide(2));
    }


    /**
     * Tests the dot product of two Vectors.
     */

    @Test
    public void testDot()
    {
        assertEquals(1d * 2d + 2d * 3d + 3d * 4d, new Vector3d(1, 2, 3)
            .multiply(new Vector3d(2, 3, 4)), 0.01);
    }


    /**
     * Tests the cross product of two Vectors.
     */

    @Test
    public void testCross()
    {
        assertEquals(new Vector3d(-2, 3, -1), new Vector3d(1, 2, 4)
            .cross(new Vector3d(2, 3, 5)));
    }


    /**
     * Tests calculating the vector length.
     */

    @Test
    public void testLength()
    {
        assertEquals(Math.sqrt(1d + 2d * 2d + 3d * 3d), new Vector3d(1, 2, 3)
            .getLength(), 0.01);
        assertEquals(0d, new Vector3d(0, 0, 0).getLength(), 0.01);
        assertEquals(1d, new Vector3d(-1, 0, 0).getLength(), 0.01);
    }


    /**
     * Tests conversion to the unit vector.
     */

    @Test
    public void testToUnit()
    {
        assertEquals(new Vector3d(1, 0, 0), new Vector3d(123, 0, 0).toUnit());
        assertEquals(new Vector3d(0, 1, 0), new Vector3d(0, 234, 0).toUnit());
        assertEquals(new Vector3d(0, 0, 1), new Vector3d(0, 0, 345).toUnit());
        assertEquals(new Vector3d(-1, 0, 0), new Vector3d(-123, 0, 0).toUnit());
        assertEquals(new Vector3d(0, -1, 0), new Vector3d(0, -234, 0).toUnit());
        assertEquals(new Vector3d(0, 0, -1), new Vector3d(0, 0, -345).toUnit());
    }


    /**
     * Tests calculating the angle between two vectors
     */

    @Test
    public void testAngle()
    {
        assertEquals(Math.toRadians(45), new Vector3d(0, 123, 0)
            .getAngle(new Vector3d(50, 50, 0)), 0.01);
        assertEquals(Math.toRadians(45), new Vector3d(1, 1, 0)
            .getAngle(new Vector3d(0, 1, 0)), 0.01);
    }


    /**
     * Tests multiplying the vector with some test matrices
     */

    @Test
    public void testMatrixMultiply()
    {
        assertEquals(new Vector3d(2, 4, 6), new Vector3d(1, 2, 3)
            .multiply(Matrix4d.scaling(2)));
        assertEquals(new Vector3d(2, 4, 6), new Vector3d(1, 2, 3)
            .multiply(Matrix4d.translation(1, 2, 3)));
        Vector3d v =
            new Vector3d(0, 1, 0).multiply(Matrix4d.rotationX(Math
                .toRadians(90)));
        assertEquals(0, v.getX(), 0.01);
        assertEquals(0, v.getY(), 0.01);
        assertEquals(1, v.getZ(), 0.01);
        v =
            new Vector3d(0, 1, 0).multiply(Matrix4d.rotationZ(Math
                .toRadians(90)));
        assertEquals(-1, v.getX(), 0.01);
        assertEquals(0, v.getY(), 0.01);
        assertEquals(0, v.getZ(), 0.01);
    }

}
