/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


/**
 * Generic OpenGL interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface GL
{
    /** Points */
    public static final int GL_POINTS = 0x0;

    /** Lines */
    public static final int GL_LINES = 0x1;

    /** Line loop */
    public static final int GL_LINE_LOOP = 0x2;

    /** Line strip */
    public static final int GL_LINE_STRIP = 0x3;

    /** Triangles */
    public static final int GL_TRIANGLES = 0x4;

    /** Triangle strip */
    public static final int GL_TRIANGLE_STRIP = 0x5;

    /** Triangle fan */
    public static final int GL_TRIANGLE_FAN = 0x6;

    /** Quads */
    public static final int GL_QUADS = 0x7;

    /** Quad strip */
    public static final int GL_QUAD_STRIP = 0x8;

    /** Polygon */
    public static final int GL_POLYGON = 0x9;

    /** Depth buffer */
    public static final int GL_DEPTH_BUFFER_BIT = 0x100;

    /** Never */
    public static final int GL_NEVER = 0x200;

    /** Less */
    public static final int GL_LESS = 0x201;

    /** Equal */
    public static final int GL_EQUAL = 0x202;

    /** Lesser or equal */
    public static final int GL_LEQUAL = 0x203;

    /** Greater / */
    public static final int GL_GREATER = 0x204;

    /** Not equal */
    public static final int GL_NOTEQUAL = 0x205;

    /** Greater or equal */
    public static final int GL_GEQUAL = 0x206;

    /** Always */
    public static final int GL_ALWAYS = 0x207;

    /** Source color */
    public static final int GL_SRC_COLOR = 0x300;

    /** One minus source color */
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x301;

    /** Source alpha */
    public static final int GL_SRC_ALPHA = 0x302;

    /** One minus source alpha */
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x303;

    /** Destination alpha */
    public static final int GL_DST_ALPHA = 0x304;

    /** One minus destination alpha */
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x305;

    /** Destination color */
    public static final int GL_DST_COLOR = 0x306;

    /** One minus destination color */
    public static final int GL_ONE_MINUS_DST_COLOR = 0x307;

    /** Source alpha saturate */
    public static final int GL_SRC_ALPHA_SATURATE = 0x308;

    /** Front */
    public static final int GL_FRONT = 0x0404;

    /** Front and back face */
    public static final int GL_FRONT_AND_BACK = 0x408;

    /** Point smooth */
    public static final int GL_POINT_SMOOTH = 0xb10;

    /** Point size */
    public static final int GL_POINT_SIZE = 0xb11;

    /** Line smooth */
    public static final int GL_LINE_SMOOTH = 0xb20;

    /** Line width */
    public static final int GL_LINE_WIDTH = 0xb21;

    /** Polygon smooth */
    public static final int GL_POLYGON_SMOOTH = 0xb41;

    /** Lighting */
    public static final int GL_LIGHTING = 0xb50;

    /** Light model local viewer */
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 0xb51;

    /** Light model two side */
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 0xb52;

    /** Light model ambient */
    public static final int GL_LIGHT_MODEL_AMBIENT = 0xb53;

    /** Color material */
    public static final int GL_COLOR_MATERIAL = 0x0B57;

    /** Dither */
    public static final int GL_DITHER = 0xbd0;

    /** Depth test */
    public static final int GL_DEPTH_TEST = 0xb71;

    /** Blend */
    public static final int GL_BLEND = 0xbe2;

    /** Perspective correction hint */
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 0xc50;

    /** Maximum lights */
    public static final int GL_MAX_LIGHTS = 0xd31;

    /** Texture 2D */
    public static final int GL_TEXTURE_2D = 0xde1;

    /** Don't care */
    public static final int GL_DONT_CARE = 0x1100;

    /** Fastest */
    public static final int GL_FASTEST = 0x1101;

    /** Nicest */
    public static final int GL_NICEST = 0x1102;

    /** Ambient color */
    public static final int GL_AMBIENT = 0x1200;

    /** Diffuse color */
    public static final int GL_DIFFUSE = 0x1201;

    /** Specular color */
    public static final int GL_SPECULAR = 0x1202;

    /** Position */
    public static final int GL_POSITION = 0x1203;

    /** Spot direction */
    public static final int GL_SPOT_DIRECTION = 0x1204;

    /** Spot exponent */
    public static final int GL_SPOT_EXPONENT = 0x1205;

    /** Spot cut-off */
    public static final int GL_SPOT_CUTOFF = 0x1206;

    /** Unsigned byte */
    public static final int GL_UNSIGNED_BYTE = 0x1401;

    /** Unsigned short */
    public static final int GL_UNSIGNED_SHORT = 0x1403;

    /** Unsigned integer */
    public static final int GL_UNSIGNED_INT = 0x1405;

    /** Float */
    public static final int GL_FLOAT = 0x1406;

    /** Emission color */
    public static final int GL_EMISSION = 0x1600;

    /** Shininess */
    public static final int GL_SHININESS = 0x1601;

    /** Ambient and diffuse */
    public static final int GL_AMBIENT_AND_DIFFUSE = 0x1602;

    /** Model view */
    public static final int GL_MODELVIEW = 0x1700;

    /** Projection */
    public static final int GL_PROJECTION = 0x1701;

    /** RGB */
    public static final int GL_RGB = 0x1907;

    /** RGBA */
    public static final int GL_RGBA = 0x1908;

    /** Flat */
    public static final int GL_FLAT = 0x1d00;

    /** Smooth */
    public static final int GL_SMOOTH = 0x1d01;

    /** Nearest */
    public static final int GL_NEAREST = 0x2600;

    /** Linear */
    public static final int GL_LINEAR = 0x2601;

    /** Nearest mipmap nearest */
    public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;

    /** Linear mipmap nearest */
    public static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;

    /** Nearest mipmap linear */
    public static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;

    /** Linear mipmap linear */
    public static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;

    /** Texture mag filter */
    public static final int GL_TEXTURE_MAG_FILTER = 0x2800;

    /** Texture min filter */
    public static final int GL_TEXTURE_MIN_FILTER = 0x2801;

    /** Color buffer */
    public static final int GL_COLOR_BUFFER_BIT = 0x4000;

    /** Light 0 */
    public static final int GL_LIGHT0 = 0x4000;

    /** Light 1 */
    public static final int GL_LIGHT1 = 0x4001;

    /** Light 2 */
    public static final int GL_LIGHT2 = 0x4002;

    /** Light 3 */
    public static final int GL_LIGHT3 = 0x4003;

    /** Light 4 */
    public static final int GL_LIGHT4 = 0x4004;

    /** Light 5 */
    public static final int GL_LIGHT5 = 0x4005;

    /** Light 6 */
    public static final int GL_LIGHT6 = 0x4006;

    /** Light 7 */
    public static final int GL_LIGHT7 = 0x4007;

    /** Vertex array */
    public static final int GL_VERTEX_ARRAY = 0x8074;

    /** Normal array */
    public static final int GL_NORMAL_ARRAY = 0x8075;

    /** Texture coordinate array */
    public static final int GL_TEXTURE_COORD_ARRAY = 0x8078;

    /** Multisample */
    public static final int GL_MULTISAMPLE = 0x809d;

    /** Light model color control */
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL = 0x81f8;

    /** Texture Max Anisotropy */
    public static final int GL_TEXTURE_MAX_ANISOTROPY_EXT = 0x84fe;

    /** Max Texture Max Anisotropy */
    public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT = 0x84ff;

    /** Repeat */
    public static final int GL_REPEAT = 0x2901;

    /** Texture wrap S */
    public static final int GL_TEXTURE_WRAP_S = 0x2802;

    /** Texture wrap T */
    public static final int GL_TEXTURE_WRAP_T = 0x2803;


    /**
     * Define an array of colors.
     *
     * @param size
     *            Specifies the number of components per color. Must be 3 or 4.
     * @param stride
     *            Specifies the byte offset between consecutive colors. If
     *            stride is 0, the colors are understood to be tightly packed in
     *            the array.
     * @param pointer
     *            The float buffer containing the color components
     */

    public void glColorPointer(int size, int stride, FloatBuffer pointer);


    /**
     * Specify material parameters for the lighting model.
     *
     * @param face
     *            Specifies which face or faces are being updated. Must be one
     *            of GL_FRONT, GL_BACK, or GL_FRONT_AND_BACK.
     * @param pname
     *            Specifies the material parameter of the face or faces that is
     *            being updated. Must be one of GL_AMBIENT, GL_DIFFUSE,
     *            GL_SPECULAR, GL_EMISSION, GL_SHININESS,
     *            GL_AMBIENT_AND_DIFFUSE, or GL_COLOR_INDEXES.
     * @param params
     *            Specifies a pointer to the value or values that pname will be
     *            set to.
     */

    public void glMaterial(final int face, final int pname,
            final FloatBuffer params);


    /**
     * Specify material parameters for the lighting model.
     *
     * @param face
     *            Specifies which face or faces are being updated. Must be one
     *            of GL_FRONT, GL_BACK, or GL_FRONT_AND_BACK.
     * @param pname
     *            Specifies the single-valued material parameter of the face or
     *            faces that is being updated. Must be GL_SHININESS.
     * @param param
     *            Specifies the value that parameter GL_SHININESS will be set
     *            to.
     */

    public void glMaterialf(final int face, final int pname, float param);


    /**
     * Enable a client-side capability.
     *
     * @param cap
     *            Specifies the capability to enable. Symbolic constants
     *            GL_COLOR_ARRAY, GL_EDGE_FLAG_ARRAY, GL_FOG_COORD_ARRAY,
     *            GL_INDEX_ARRAY, GL_NORMAL_ARRAY, GL_SECONDARY_COLOR_ARRAY,
     *            GL_TEXTURE_COORD_ARRAY, and GL_VERTEX_ARRAY are accepted.
     */

    public void glEnableClientState(int cap);


    /**
     * Enable a client-side capability.
     *
     * @param cap
     *            Specifies the capability to enable. Symbolic constants
     *            GL_COLOR_ARRAY, GL_EDGE_FLAG_ARRAY, GL_FOG_COORD_ARRAY,
     *            GL_INDEX_ARRAY, GL_NORMAL_ARRAY, GL_SECONDARY_COLOR_ARRAY,
     *            GL_TEXTURE_COORD_ARRAY, and GL_VERTEX_ARRAY are accepted.
     */

    public void glDisableClientState(int cap);


    /**
     * Define an array of vertex data.
     *
     * @param size
     *            Specifies the number of coordinates per vertex. Must be 2, 3,
     *            or 4. The initial value is 4.
     * @param stride
     *            Specifies the byte offset between consecutive vertices. If
     *            stride is 0, the vertices are understood to be tightly packed
     *            in the array. The initial value is 0.
     * @param pointer
     *            Specifies a pointer to the first coordinate of the first
     *            vertex in the array. The initial value is 0.
     */

    public void glVertexPointer(int size, int stride, FloatBuffer pointer);


    /**
     * Define an array of normals.
     *
     * @param stride
     *            Specifies the byte offset between consecutive normals. If
     *            stride is 0, the normals are understood to be tightly packed
     *            in the array. The initial value is 0.
     * @param pointer
     *            Specifies a pointer to the first coordinate of the first
     *            normal in the array. The initial value is 0.
     */

    public void glNormalPointer(int stride, FloatBuffer pointer);


    /**
     * Define an array of texture coordinates.
     *
     * @param size
     *            Specifies the number of coordinates per array element. Must be
     *            1, 2, 3, or 4. The initial value is 4.
     * @param stride
     *            Specifies the byte offset between consecutive texture
     *            coordinate sets. If stride is 0, the array elements are
     *            understood to be tightly packed. The initial value is 0.
     * @param pointer
     *            Specifies a pointer to the first coordinate of the first
     *            texture coordinate set in the array. The initial value is 0.
     */

    public void glTexCoordPointer(int size, int stride, FloatBuffer pointer);


    /**
     * Render primitives from array data
     *
     * @param mode
     *            Specifies what kind of primitives to render. Symbolic
     *            constants GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES,
     *            GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, GL_TRIANGLES,
     *            GL_QUAD_STRIP, GL_QUADS, and GL_POLYGON are accepted.
     * @param type
     *            Specifies the type of the values in indices. Must be one of
     *            GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, or GL_UNSIGNED_INT.
     * @param indices
     *            Specifies a pointer to the location where the indices are
     *            stored.
     */

    public void glDrawElements(int mode, int type, Buffer indices);


    /**
     * Push current matrix stack
     */

    public void glPushMatrix();


    /**
     * Multiply the current matrix with the specified matrix
     *
     * @param m
     *            The matrix to multiply the current matrix with
     */

    public void glMultMatrix(FloatBuffer m);


    /**
     * Pop current matrix stack.
     */

    public void glPopMatrix();


    /**
     * Clear buffers to preset values
     *
     * @param mask
     *            Bitwise OR of masks that indicate the buffers to be cleared.
     *            The four masks are GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT,
     *            GL_ACCUM_BUFFER_BIT, and GL_STENCIL_BUFFER_BIT.
     */

    public void glClear(int mask);


    /**
     * Specify which matrix is the current matrix
     *
     * @param mode
     *            Specifies which matrix stack is the target for subsequent
     *            matrix operations. Three values are accepted: GL_MODELVIEW,
     *            GL_PROJECTION, and GL_TEXTURE. The initial value is
     *            GL_MODELVIEW. Additionally, if the ARB_imaging extension is
     *            supported, GL_COLOR is also accepted.
     */

    public void glMatrixMode(int mode);


    /**
     * Replace the current matrix with the identity matrix
     */

    public void glLoadIdentity();


    /**
     * Force execution of GL commands in finite time.
     */

    public void glFlush();


    /**
     * Specify clear values for the color buffers.
     *
     * @param red
     *            The red component
     * @param green
     *            The green component
     * @param blue
     *            The blue component
     * @param alpha
     *            The alpha component
     */

    public void glClearColor(float red, float green, float blue, float alpha);


    /**
     * Select flat or smooth shading
     *
     * @param mode
     *            Specifies a symbolic value representing a shading technique.
     *            Accepted values are GL_FLAT and GL_SMOOTH. The initial value
     *            is GL_SMOOTH.
     */

    public void glShadeModel(int mode);


    /**
     * Specify the clear value for the depth buffer
     *
     * @param depth
     *            Specifies the depth value used when the depth buffer is
     *            cleared. The initial value is 1.
     */

    public void glClearDepth(double depth);


    /**
     * Enable server-side GL capabilities.
     *
     * @param cap
     *            Specifies a symbolic constant indicating a GL capability.
     */

    public void glEnable(int cap);


    /**
     * Disable server-side GL capabilities.
     *
     * @param cap
     *            Specifies a symbolic constant indicating a GL capability.
     */

    public void glDisable(int cap);


    /**
     * Specify the value used for depth buffer comparisons.
     *
     * @param func
     *            Specifies the depth comparison function. Symbolic constants
     *            GL_NEVER, GL_LESS, GL_EQUAL, GL_LEQUAL, GL_GREATER,
     *            GL_NOTEQUAL, GL_GEQUAL, and GL_ALWAYS are accepted. The
     *            initial value is GL_LESS.
     */

    public void glDepthFunc(int func);


    /**
     * Specify implementation-specific hints
     *
     * @param target
     *            Specifies a symbolic constant indicating the behavior to be
     *            controlled. GL_FOG_HINT, GL_GENERATE_MIPMAP_HINT,
     *            GL_LINE_SMOOTH_HINT, GL_PERSPECTIVE_CORRECTION_HINT,
     *            GL_POINT_SMOOTH_HINT, GL_POLYGON_SMOOTH_HINT,
     *            GL_TEXTURE_COMPRESSION_HINT, and
     *            GL_FRAGMENT_SHADER_DERIVATIVE_HINT are accepted.
     * @param mode
     *            Specifies a symbolic constant indicating the desired behavior.
     *            GL_FASTEST, GL_NICEST, and GL_DONT_CARE are accepted.
     */

    public void glHint(int target, int mode);


    /**
     * Specify pixel arithmetic
     *
     * @param sfactor
     *            Specifies how the red, green, blue, and alpha source blending
     *            factors are computed. The following symbolic constants are
     *            accepted: GL_ZERO, GL_ONE, GL_SRC_COLOR,
     *            GL_ONE_MINUS_SRC_COLOR, GL_DST_COLOR, GL_ONE_MINUS_DST_COLOR,
     *            GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_DST_ALPHA,
     *            GL_ONE_MINUS_DST_ALPHA, GL_CONSTANT_COLOR,
     *            GL_ONE_MINUS_CONSTANT_COLOR, GL_CONSTANT_ALPHA,
     *            GL_ONE_MINUS_CONSTANT_ALPHA, and GL_SRC_ALPHA_SATURATE. The
     *            initial value is GL_ONE.
     * @param dfactor
     *            Specifies how the red, green, blue, and alpha destination
     *            blending factors are computed. The following symbolic
     *            constants are accepted: GL_ZERO, GL_ONE, GL_SRC_COLOR,
     *            GL_ONE_MINUS_SRC_COLOR, GL_DST_COLOR, GL_ONE_MINUS_DST_COLOR,
     *            GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_DST_ALPHA,
     *            GL_ONE_MINUS_DST_ALPHA. GL_CONSTANT_COLOR,
     *            GL_ONE_MINUS_CONSTANT_COLOR, GL_CONSTANT_ALPHA, and
     *            GL_ONE_MINUS_CONSTANT_ALPHA. The initial value is GL_ZERO.
     */

    public void glBlendFunc(int sfactor, int dfactor);


    /**
     * Set light source parameters.
     *
     * @param light
     *            Specifies a light. The number of lights depends on the
     *            implementation, but at least eight lights are supported. They
     *            are identified by symbolic names of the form GL_LIGHT i, where
     *            i ranges from 0 to the value of GL_MAX_LIGHTS - 1.
     *
     * @param pname
     *            Specifies a single-valued light source parameter for light.
     *            GL_SPOT_EXPONENT, GL_SPOT_CUTOFF, GL_CONSTANT_ATTENUATION,
     *            GL_LINEAR_ATTENUATION, and GL_QUADRATIC_ATTENUATION are
     *            accepted.
     *
     * @param param
     *            Specifies the value that parameter pname of light source light
     *            will be set to.
     */

    public void glLight(int light, int pname, FloatBuffer param);


    /**
     * Set the viewport.
     *
     * @param x
     *            Specify the left edge of the viewport rectangle, in pixels.
     *            The initial value is 0.
     * @param y
     *            Specify the top edge of the viewport rectangle, in pixels. The
     *            initial value is 0
     * @param width
     *            Specify the width of the viewport.
     * @param height
     *            Specify the height of the viewport.
     */

    public void glViewport(int x, int y, int width, int height);


    /**
     * Set up a perspective projection matrix
     *
     * @param fovy
     *            Specifies the field of view angle, in degrees, in the y
     *            direction.
     * @param aspect
     *            Specifies the aspect ratio that determines the field of view
     *            in the x direction. The aspect ratio is the ratio of x (width)
     *            to y (height).
     * @param zNear
     *            Specifies the distance from the viewer to the near clipping
     *            plane (always positive).
     * @param zFar
     *            Specifies the distance from the viewer to the far clipping
     *            plane (always positive).
     */

    public void gluPerspective(float fovy, float aspect, float zNear, float zFar);


    /**
     * Bind a named texture to a texturing target.
     *
     * @param target
     *            Specifies the target to which the texture is bound. Must be
     *            either GL_TEXTURE_1D, GL_TEXTURE_2D, GL_TEXTURE_3D, or
     *            GL_TEXTURE_CUBE_MAP.
     * @param texture
     *            Specifies the name of a texture.
     */

    public void glBindTexture(int target, int texture);


    /**
     * Delete named textures
     *
     * @param textures
     *            Specifies an array of textures to be deleted.
     */

    public void glDeleteTextures(IntBuffer textures);


    /**
     * Generate texture names
     *
     * @param textures
     *            Specifies an array in which the generated texture names are
     *            stored.
     */

    public void glGenTextures(IntBuffer textures);


    /**
     * Set texture parameters
     *
     * @param target
     *            Specifies the target texture, which must be either
     *            GL_TEXTURE_1D, GL_TEXTURE_2D, GL_TEXTURE_3D, or
     *            GL_TEXTURE_CUBE_MAP.
     * @param pname
     *            Specifies the symbolic name of a single-valued texture
     *            parameter. pname can be one of the following:
     *            GL_TEXTURE_MIN_FILTER, GL_TEXTURE_MAG_FILTER,
     *            GL_TEXTURE_MIN_LOD, GL_TEXTURE_MAX_LOD, GL_TEXTURE_BASE_LEVEL,
     *            GL_TEXTURE_MAX_LEVEL, GL_TEXTURE_WRAP_S, GL_TEXTURE_WRAP_T,
     *            GL_TEXTURE_WRAP_R, GL_TEXTURE_PRIORITY,
     *            GL_TEXTURE_COMPARE_MODE, GL_TEXTURE_COMPARE_FUNC,
     *            GL_DEPTH_TEXTURE_MODE, or GL_GENERATE_MIPMAP.
     * @param param
     *            Specifies the value of pname.
     */

    public void glTexParameteri(int target, int pname, int param);


    /**
     * Set texture parameters
     *
     * @param target
     *            Specifies the target texture, which must be either
     *            GL_TEXTURE_1D, GL_TEXTURE_2D, GL_TEXTURE_3D, or
     *            GL_TEXTURE_CUBE_MAP.
     * @param pname
     *            Specifies the symbolic name of a single-valued texture
     *            parameter. pname can be one of the following:
     *            GL_TEXTURE_MIN_FILTER, GL_TEXTURE_MAG_FILTER,
     *            GL_TEXTURE_MIN_LOD, GL_TEXTURE_MAX_LOD, GL_TEXTURE_BASE_LEVEL,
     *            GL_TEXTURE_MAX_LEVEL, GL_TEXTURE_WRAP_S, GL_TEXTURE_WRAP_T,
     *            GL_TEXTURE_WRAP_R, GL_TEXTURE_PRIORITY,
     *            GL_TEXTURE_COMPARE_MODE, GL_TEXTURE_COMPARE_FUNC,
     *            GL_DEPTH_TEXTURE_MODE, or GL_GENERATE_MIPMAP.
     * @param param
     *            Specifies the value of pname.
     */

    public void glTexParameterf(int target, int pname, float param);


    /**
     * Specify a two-dimensional texture image
     *
     * @param target
     *            Specifies the target texture. Must be GL_TEXTURE_2D,
     *            GL_PROXY_TEXTURE_2D, GL_TEXTURE_CUBE_MAP_POSITIVE_X,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, or GL_PROXY_TEXTURE_CUBE_MAP.
     * @param level
     *            Specifies the level-of-detail number. Level 0 is the base
     *            image level. Level n is the nth mipmap reduction image.
     * @param stream
     *            The stream to read the image from
     * @param border
     *            Specifies the width of the border. Must be either 0 or 1.
     */

    public void glTexImage2D(int target, int level, InputStream stream,
            int border);


    /**
     * Specify a two-dimensional texture image.
     *
     * @param target
     *            Specifies the target texture. Must be GL_TEXTURE_2D,
     *            GL_PROXY_TEXTURE_2D, GL_TEXTURE_CUBE_MAP_POSITIVE_X,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, or GL_PROXY_TEXTURE_CUBE_MAP.
     * @param level
     *            Specifies the level-of-detail number. Level 0 is the base
     *            image level. Level n is the nth mipmap reduction image.
     * @param internalFormat
     *            Specifies the number of color components in the texture.
     *            Must be 1, 2, 3, or 4, or one of the following symbolic
     *            constants:
     *            GL_ALPHA,
     *            GL_ALPHA4,
     *            GL_ALPHA8,
     *            GL_ALPHA12,
     *            GL_ALPHA16,
     *            GL_COMPRESSED_ALPHA,
     *            GL_COMPRESSED_LUMINANCE,
     *            GL_COMPRESSED_LUMINANCE_ALPHA,
     *            GL_COMPRESSED_INTENSITY,
     *            GL_COMPRESSED_RGB,
     *            GL_COMPRESSED_RGBA,
     *            GL_DEPTH_COMPONENT,
     *            GL_DEPTH_COMPONENT16,
     *            GL_DEPTH_COMPONENT24,
     *            GL_DEPTH_COMPONENT32,
     *            GL_LUMINANCE,
     *            GL_LUMINANCE4,
     *            GL_LUMINANCE8,
     *            GL_LUMINANCE12,
     *            GL_LUMINANCE16,
     *            GL_LUMINANCE_ALPHA,
     *            GL_LUMINANCE4_ALPHA4,
     *            GL_LUMINANCE6_ALPHA2,
     *            GL_LUMINANCE8_ALPHA8,
     *            GL_LUMINANCE12_ALPHA4,
     *            GL_LUMINANCE12_ALPHA12,
     *            GL_LUMINANCE16_ALPHA16,
     *            GL_INTENSITY,
     *            GL_INTENSITY4,
     *            GL_INTENSITY8,
     *            GL_INTENSITY12,
     *            GL_INTENSITY16,
     *            GL_R3_G3_B2,
     *            GL_RGB,
     *            GL_RGB4,
     *            GL_RGB5,
     *            GL_RGB8,
     *            GL_RGB10,
     *            GL_RGB12,
     *            GL_RGB16,
     *            GL_RGBA,
     *            GL_RGBA2,
     *            GL_RGBA4,
     *            GL_RGB5_A1,
     *            GL_RGBA8,
     *            GL_RGB10_A2,
     *            GL_RGBA12,
     *            GL_RGBA16,
     *            GL_SLUMINANCE,
     *            GL_SLUMINANCE8,
     *            GL_SLUMINANCE_ALPHA,
     *            GL_SLUMINANCE8_ALPHA8,
     *            GL_SRGB,
     *            GL_SRGB8,
     *            GL_SRGB_ALPHA, or
     *            GL_SRGB8_ALPHA8.
     * @param width
     *            Specifies the width of the texture image including the border
     *            if any. If the GL version does not support non-power-of-two
     *            sizes, this value must be 2^n+2(border) for some integer n.
     *            All implementations support texture images that are at least
     *            64 texels wide.
     * @param height
     *            Specifies the height of the texture image including the border
     *            if any. If the GL version does not support non-power-of-two
     *            sizes, this value must be 2^m+2(border) for some integer m.
     *            All implementations support texture images that are at least
     *            64 texels high.
     * @param border
     *            Specifies the width of the border. Must be either 0 or 1.
     * @param format
     *            Specifies the format of the pixel data.
     *            The following symbolic values are accepted:
     *            GL_COLOR_INDEX,
     *            GL_RED,
     *            GL_GREEN,
     *            GL_BLUE,
     *            GL_ALPHA,
     *            GL_RGB,
     *            GL_BGR,
     *            GL_RGBA,
     *            GL_BGRA,
     *            GL_LUMINANCE, and
     *            GL_LUMINANCE_ALPHA.
     * @param type
     *            Specifies the data type of the pixel data.
     *            The following symbolic values are accepted:
     *            GL_UNSIGNED_BYTE,
     *            GL_BYTE,
     *            GL_BITMAP,
     *            GL_UNSIGNED_SHORT,
     *            GL_SHORT,
     *            GL_UNSIGNED_INT,
     *            GL_INT,
     *            GL_FLOAT,
     *            GL_UNSIGNED_BYTE_3_3_2,
     *            GL_UNSIGNED_BYTE_2_3_3_REV,
     *            GL_UNSIGNED_SHORT_5_6_5,
     *            GL_UNSIGNED_SHORT_5_6_5_REV,
     *            GL_UNSIGNED_SHORT_4_4_4_4,
     *            GL_UNSIGNED_SHORT_4_4_4_4_REV,
     *            GL_UNSIGNED_SHORT_5_5_5_1,
     *            GL_UNSIGNED_SHORT_1_5_5_5_REV,
     *            GL_UNSIGNED_INT_8_8_8_8,
     *            GL_UNSIGNED_INT_8_8_8_8_REV,
     *            GL_UNSIGNED_INT_10_10_10_2, and
     *            GL_UNSIGNED_INT_2_10_10_10_REV.
     * @param data
     *            Specifies a pointer to the image data in memory.
     */

    public void glTexImage2D(final int target, final int level,
            final int internalFormat, final int width, final int height,
            final int border, final int format, final int type,
            final ByteBuffer data);


    /**
     * Set the lighting model parameters.
     *
     * @param pname
     *            Specifies a lighting model parameter. GL_LIGHT_MODEL_AMBIENT,
     *            GL_LIGHT_MODEL_COLOR_CONTROL, GL_LIGHT_MODEL_LOCAL_VIEWER, and
     *            GL_LIGHT_MODEL_TWO_SIDE are accepted.
     * @param params
     *            Specifies a pointer to the value or values that params will be
     *            set to.
     */

    public void glLightModelfv(final int pname, final FloatBuffer params);


    /**
     * Return the value or values of a selected parameter
     *
     * @param pname
     *            pecifies the parameter value to be returned. The symbolic
     *            constants in the list below are accepted.
     * @param params
     *            Returns the value or values of the specified parameter.
     */

    public void glGetFloatv(int pname, FloatBuffer params);


    /**
     * Test whether a capability is enabled.
     *
     * @param cap
     *            Specifies a symbolic constant indicating a GL capability.
     * @return True if cap is an enabled capability and false otherwise
     */

    public boolean glIsEnabled(int cap);


    /**
     * Set light source parameters
     *
     * @param light
     *            Specifies a light. The number of lights depends on the
     *            implementation, but at least eight lights are supported. They
     *            are identified by symbolic names of the form GL_LIGHT i, where
     *            i ranges from 0 to the value of GL_MAX_LIGHTS - 1.
     * @param pname
     *            Specifies a light. The number of lights depends on the
     *            implementation, but at least eight lights are supported. They
     *            are identified by symbolic names of the form GL_LIGHT i, where
     *            i ranges from 0 to the value of GL_MAX_LIGHTS - 1.
     * @param param
     *            Specifies the value that parameter pname of light source light
     *            will be set to.
     */

    public void glLightf(int light, int pname, float param);


    /**
     * Return the value or values of a selected parameter
     *
     * @param pname
     *            Specifies the parameter value to be returned. The symbolic
     *            constants in the list below are accepted.
     * @param params
     *            Returns the value or values of the specified parameter.
     */

    public void glGetIntegerv(int pname, IntBuffer params);


    /**
     * Multiply the current matrix by a translation matrix.
     *
     * @param x
     *            Specify the x coordinates of a translation vector.
     * @param y
     *            Specify the y coordinates of a translation vector.
     * @param z
     *            Specify the z coordinates of a translation vector.
     */

    public void glTranslate(final float x, final float y, final float z);


    /**
     * Cause a material color to track the current color
     *
     * @param face
     *            Specifies whether front, back, or both front and back
     *            material parameters should track the current color. Accepted
     *            values are GL_FRONT, GL_BACK, and GL_FRONT_AND_BACK.
     *            The initial value is GL_FRONT_AND_BACK.
     * @param mode
     *            Specifies which of several material parameters track the
     *            current color. Accepted values are GL_EMISSION, GL_AMBIENT,
     *            GL_DIFFUSE, GL_SPECULAR, and GL_AMBIENT_AND_DIFFUSE.
     *            The initial value is GL_AMBIENT_AND_DIFFUSE.
     */

    public void glColorMaterial(int face, int mode);
}
