/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.rendering.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import de.ailis.threedee.entities.CameraNode;
import de.ailis.threedee.entities.Color;
import de.ailis.threedee.entities.DirectionalLight;
import de.ailis.threedee.entities.Light;
import de.ailis.threedee.entities.LightInstance;
import de.ailis.threedee.entities.Material;
import de.ailis.threedee.entities.MeshInstance;
import de.ailis.threedee.entities.MeshPolygons;
import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.entities.SpotLight;
import de.ailis.threedee.exceptions.LightException;
import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.properties.NodeProperty;
import de.ailis.threedee.rendering.Renderer;
import de.ailis.threedee.textures.Texture;
import de.ailis.threedee.textures.TextureCache;
import de.ailis.threedee.utils.BufferUtils;


/**
 * Renderer for OpenGL backend.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class GLRenderer implements Renderer
{
    /** The GL context to use */
    private final GL gl;

    /** The camera matrix */
    private final Matrix4f cameraMatrix = Matrix4f.identity();

    /** If scene must be re-initialized */
    private boolean reinit = true;

    /** The scene width */
    private int width;

    /** The scene height */
    private int height;

    /** The last used diffuse texture */
    private Texture diffuseTexture;

    /** The next free light index */
    private int nextIndex = GL.GL_LIGHT0;

    /** The maximum light index */
    private int maxLightIndex = GL.GL_LIGHT7;

    /** Position for a directional light */
    private final FloatBuffer directionalLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(1).put(0).rewind();

    /** Position for a point light */
    private final FloatBuffer pointLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(0).put(1).rewind();

    /** Direction for a spot light */
    private final FloatBuffer spotLightDirection = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(-1).put(1).rewind();


    /**
     * Constructs a new GL renderer.
     *
     * @param gl
     *            The GL context to use
     */

    public GLRenderer(final GL gl)
    {
        this.gl = gl;
    }


    /**
     * @see de.ailis.threedee.rendering.Renderer#initScene(de.ailis.threedee.entities.Scene)
     */

    @Override
    public void initScene(final Scene scene)
    {
        // Create some shortcuts
        final GL gl = this.gl;
        final Color clearColor = scene.getClearColor();

        // Set the color used for clearing the screen before rendering a frame
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor
                .getBlue(), clearColor.getAlpha());
        gl.glClearDepth(1f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glEnable(GL.GL_DITHER);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // gl.glEnable(GL.GL_COLOR_MATERIAL);
        // gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);

        // gl.glDisable(GL.GL_POINT_SMOOTH);
        // gl.glEnable(GL.GL_MULTISAMPLE);

        // Enable Anisotropic filtering if available
        // gl.glTexParameteri(GL.GL_TEXTURE_2D,
        // GL.GL_TEXTURE_MAX_ANISOTROPY_EXT, 4);

        // Remember that initialization is done
        this.reinit = false;
    }


    /**
     * @see de.ailis.threedee.rendering.Renderer#setSize(int, int)
     */

    @Override
    public void setSize(final int width, final int height)
    {
        // Create some shortcuts
        final GL gl = this.gl;

        // Setup the coordinate system
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100000f);

        // Set the viewport
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);

        // Remember the scene size
        this.width = width;
        this.height = height;
    }


    /**
     * @see de.ailis.threedee.rendering.Renderer#render(de.ailis.threedee.entities.Scene)
     */

    @Override
    public void render(final Scene scene)
    {
        // Create some shortcuts
        final GL gl = this.gl;
        final CameraNode cameraNode = scene.getCameraNode();
        final SceneNode rootNode = scene.getRootNode();

        // Initialize the scene if needed
        if (this.reinit) initScene(scene);

        // Clear the color and depth buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        if (rootNode != null)
        {
            // Apply camera transformation if camera is present
            if (cameraNode != null)
            {
                gl.glPushMatrix();
                this.cameraMatrix.set(cameraNode.getCameraTransform());
                gl.glMultMatrix(this.cameraMatrix.getBuffer());
            }

            // Render root node
            renderSceneNode(rootNode);

            // Remove camera transformation if camera is present
            if (cameraNode != null) gl.glPopMatrix();
        }

        // Flush the scene
        gl.glFlush();
    }


    /**
     * Renders a scene node.
     *
     * @param node
     *            The node to render
     */

    public void renderSceneNode(final SceneNode node)
    {
        // Get some shortcuts
        final GL gl = this.gl;
        final List<NodeProperty> properties = node.getProperties();
        final List<LightInstance> lights = node.getEnabledLights();
        final List<MeshInstance> meshes = node.getMeshes();
        final Matrix4f transform = node.getTransform();

        boolean identity;

        // Apply node properties
        if (properties != null) for (final NodeProperty property : properties)
            property.apply(gl);

        // If transformation is used then apply it
        identity = transform.isIdentity();
        if (!identity)
        {
            gl.glPushMatrix();
            gl.glMultMatrix(transform.getBuffer());
        }

        // Apply lights
        if (lights != null)
        {
            for (final LightInstance light : lights)
            {
                gl.glPushMatrix();
                applyLightTransform(node, light.getSceneNode());
                applyLight(light);
                gl.glPopMatrix();
            }
        }

        // Render meshes
        if (meshes != null) for (final MeshInstance mesh : meshes)
            renderMesh(mesh);

        // Render the node and the child nodes
        for (final SceneNode childNode : node)
            renderSceneNode(childNode);

        // Remove lights
        if (lights != null) for (final LightInstance light : lights)
            removeLight(light);

        // If transformation is used then reset the old transformation
        if (!identity) gl.glPopMatrix();

        // Remove node properties
        if (properties != null) for (final NodeProperty property : properties)
            property.remove(gl);
    }


    /**
     * Recursively applies light transformation.
     *
     * @param node
     *            The scene node the light node should illuminate
     * @param light
     *            The light node
     */

    private void applyLightTransform(final SceneNode node, final SceneNode light)
    {
        // Create some shortcuts
        final GL gl = this.gl;
        final SceneNode parentNode = light.getParentNode();

        // Apply next parent transform
        if (parentNode != null && parentNode != node)
            applyLightTransform(node, parentNode);

        // Apply current transform
        gl.glMultMatrix(light.getTransform().getBuffer());
    }


    /**
     * Renders the specified mesh.
     *
     * @param mesh
     *            The mesh to render
     */

    public void renderMesh(final MeshInstance mesh)
    {
        for (final MeshPolygons polygons : mesh.getMesh().getPolygons())
            renderMeshPolygon(mesh, polygons);
    }


    /**
     * Renders the specified mesh polygons.
     *
     * @param mesh
     *            The mesh
     * @param polygons
     *            The mesh polygons
     */

    private void renderMeshPolygon(final MeshInstance mesh,
            final MeshPolygons polygons)
    {
        // Create some shortcuts
        final GL gl = this.gl;
        final FloatBuffer vertices = polygons.getVertices();
        final FloatBuffer normals = polygons.getNormals();
        final FloatBuffer texCoords = polygons.getTexCoords();
        final ShortBuffer indices = polygons.getIndices();
        final int materialIndex = polygons.getMaterial();
        final int mode = getPolygonMode(polygons.getSize());

        // Set vertex pointer
        gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, 0, vertices);

        // Set normal pointer (if normals are used)
        if (normals != null)
        {
            gl.glEnableClientState(GL.GL_NORMAL_ARRAY);
            gl.glNormalPointer(0, normals);
        }

        // Set texture coordinate pointer (if used)
        if (texCoords != null)
        {
            gl.glEnableClientState(GL.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, 0, texCoords);
        }

        // Apply material
        final Material material = (materialIndex == -1) ? Material.DEFAULT
                : mesh.getMaterial(materialIndex);
        applyMaterial(material);

        // Draw polygons
        gl.glDrawElements(mode, GL.GL_UNSIGNED_SHORT, indices);

        // Reset GL state
        removeMaterial(material);
        if (texCoords != null)
            gl.glDisableClientState(GL.GL_TEXTURE_COORD_ARRAY);
        if (normals != null) gl.glDisableClientState(GL.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
    }


    /**
     * Returns the polygon mode for the specified polygon size.
     *
     * @param size
     *            The polygon size (1-3)
     * @return The polygon Mode (GL_POINTS, GL_LINES, GL_TRIANGLES)
     */

    private int getPolygonMode(final int size)
    {
        switch (size)
        {
            case 1:
                return GL.GL_POINTS;

            case 2:
                return GL.GL_LINES;

            case 3:
                return GL.GL_TRIANGLES;

            default:
                throw new IllegalArgumentException("Invalid polygon size: "
                        + size);
        }
    }


    /**
     * Applies the material to the GL context.
     *
     * @param material
     *            The material to apply
     */

    private void applyMaterial(final Material material)
    {
        final GL gl = this.gl;
        final String diffuseTexture = material.getDiffuseTexture();

        if (diffuseTexture != null)
        {
            final Texture texture = this.diffuseTexture = TextureCache
                    .getInstance().getTexture(diffuseTexture);
            texture.bind(gl);
        }
        else

            this.diffuseTexture = null;

        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, material
                .getSpecularColor().getBuffer());
        if (this.diffuseTexture == null)
            gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, material
                    .getDiffuseColor().getBuffer());
        else
            gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, Color.WHITE
                    .getBuffer());
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, material
                .getAmbientColor().getBuffer());
        gl.glMaterial(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, material
                .getEmissionColor().getBuffer());
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, material
                .getShininess());
    }


    /**
     * Removes the material from the GL context.
     *
     * @param material
     *            The material to remove
     */

    private void removeMaterial(final Material material)
    {
        if (this.diffuseTexture != null)
        {
            this.diffuseTexture.unbind(this.gl);
            this.diffuseTexture = null;
        }
    }


    /**
     * Returns the width.
     *
     * @return The width
     */

    public int getWidth()
    {
        return this.width;
    }


    /**
     * Returns the height.
     *
     * @return The height
     */

    public int getHeight()
    {
        return this.height;
    }


    /**
     * Returns the light index.
     *
     * @param light
     *            The light instance
     * @return The light index
     */

    protected int getLightIndex(final LightInstance light)
    {
        // If light has already a light id then return this one
        int id = light.getLightId();
        if (id >= 0) return id;

        // Get the maximum number of lights once
        if (this.maxLightIndex == -1)
        {
            final IntBuffer buffer = BufferUtils.createDirectIntegerBuffer(1);
            this.gl.glGetIntegerv(GL.GL_MAX_LIGHTS, buffer);
            this.maxLightIndex = buffer.get(0);
        }

        // Check if there is a light index available
        if (this.nextIndex >= this.maxLightIndex)
            throw new LightException("Too many lights active (Max is "
                    + this.maxLightIndex + ")");

        // Claim light index and return it
        id = this.nextIndex++;
        light.setLightId(id);
        return id;
    }


    /**
     * Applies the specified light.
     *
     * @param lightInstance
     *            The light to apply
     */

    private void applyLight(final LightInstance lightInstance)
    {
        final GL gl = this.gl;

        final Light light = lightInstance.getLight();
        final int index = getLightIndex(lightInstance);
        if (light instanceof DirectionalLight)
            gl.glLight(index, GL.GL_POSITION, this.directionalLightPosition);
        else
            gl.glLight(index, GL.GL_POSITION, this.pointLightPosition);
        if (light instanceof SpotLight)
        {
            gl.glLight(index, GL.GL_SPOT_DIRECTION, this.spotLightDirection);
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, 90f);
        }
        else
        {
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, 180f);
        }
        gl.glLight(index, GL.GL_AMBIENT, light.getAmbientColor().getBuffer());
        gl.glLight(index, GL.GL_DIFFUSE, light.getDiffuseColor().getBuffer());
        gl.glLight(index, GL.GL_SPECULAR, light.getSpecularColor().getBuffer());
        gl.glEnable(index);
    }


    /**
     * Removes the specified light
     *
     * @param light
     *            The light instance
     */

    private void removeLight(final LightInstance light)
    {
        this.gl.glDisable(light.getLightId());
        light.setLightId(-1);
        this.nextIndex--;
    }
}
