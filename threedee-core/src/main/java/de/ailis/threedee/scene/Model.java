/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.lang.ref.SoftReference;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import de.ailis.threedee.builder.MeshBuilder;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.rendering.BoundsRenderer;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.scene.model.Material;
import de.ailis.threedee.scene.model.Mesh;
import de.ailis.threedee.scene.model.MeshPolygons;
import de.ailis.threedee.textures.Texture;
import de.ailis.threedee.textures.TextureCache;


/**
 * A scene node. Can be used directly to create invisible group nodes or can be
 * extended to implement other node types.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Model extends SceneNode
{
    /** The cached model to render */
    private final Mesh mesh;

    /** The cached normal mesh */
    private SoftReference<Mesh> normalMesh;

    /** If normals should be displayed */
    private final boolean showNormals = true;

    /** If bounds should be displayed */
    private final boolean showBounds = false;

    /** If group bounds should be displayed */
    private final boolean showGroupBounds = false;

    /** Offset for rendering the model relative to the node */
    private final Vector3f modelOffset = new Vector3f();

    /** The bound materials */
    private final Material[] materials;

    /** The last used diffuse texture */
    private Texture diffuseTexture;


    /**
     * Constructor
     *
     * @param mesh
     *            The mesh to display
     */

    public Model(final Mesh mesh)
    {
        this.mesh = mesh;
        this.materials = new Material[mesh.getMaterials().length];
    }


    /**
     * Binds a material to a material id.
     *
     * @param id
     *            The material id
     * @param material
     *            The material to bind to the id
     */

    public void bindMaterial(final String id, final Material material)
    {
        final int index = getMaterialIndex(id);
        if (index == -1) return;
        this.materials[index] = material;
    }


    /**
     * Returns the material for the specified material index. If no material
     * was bound to this index or the index is invalid then the default
     * material is returned.
     *
     * @param index
     *            The material index
     * @return The material. Never null
     */

    public Material getMaterial(final int index)
    {
        if (index >= this.materials.length) return Material.DEFAULT;
        final Material material = this.materials[index];
        if (material == null) return Material.DEFAULT;
        return material;
    }


    /**
     * Returns the material index of the material with the specified id.
     * Returns -1 if no material with this id was found.
     *
     * @param id
     *            The material id
     * @return The material index or -1 if not found
     */

    private int getMaterialIndex(final String id)
    {
        final String[] indices = this.mesh.getMaterials();
        for (int index = indices.length - 1; index >= 0; --index)
            if (indices[index].equals(id)) return index;
        return -1;
    }


    /**
     * Returns the mesh.
     *
     * @return The mesh
     */

    public Mesh getMesh()
    {
        return this.mesh;
    }


    /**
     * Returns the model offset.
     *
     * @return The model offset
     */

    public Vector3f getModelOffset()
    {
        return this.modelOffset;
    }


    /**
     * Modifies the model offset so it the node center is in the middle of
     * the model.
     */

    public void centerModel()
    {
        // TODO Implement me
        // this.modelOffset.set(this.mesh.getBounds().getCenter()).invert();
    }


    /**
     * @see SceneNode#render(Viewport)
     */

    @Override
    protected void render(final Viewport viewport)
    {
        final GL gl = viewport.getGL();

        for (final MeshPolygons polygons : this.mesh.getPolygons())
            renderMeshPolygon(gl, polygons);

        // Render the mesh bounds if requested
        if (this.showBounds) renderBounds(gl);

        // Render mesh normals if requested
        if (this.showNormals) renderNormals(gl);
    }


    /**
     * Renders the mesh normals.
     *
     * @param gl
     *            The GL context
     */

    private void renderNormals(final GL gl)
    {
        final boolean oldLighting = gl.glIsEnabled(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_LIGHTING);

        for (final MeshPolygons polygons : getNormalMesh().getPolygons())
            renderMeshPolygon(gl, polygons);

        if (oldLighting) gl.glEnable(GL.GL_LIGHTING);
    }


    /**
     * Renders the specified mesh polygons.
     *
     * @param gl
     *            The GL context
     * @param polygons
     *            The mesh polygons
     */

    private void renderMeshPolygon(final GL gl, final MeshPolygons polygons)
    {
        // Create some shortcuts
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
                : getMaterial(materialIndex);
        applyMaterial(gl, material);

        // Draw polygons
        gl.glDrawElements(mode, GL.GL_UNSIGNED_SHORT, indices);

        // Reset GL state
        removeMaterial(gl, material);
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
     * @param gl
     *            The GL context
     * @param material
     *            The material to apply
     */

    private void applyMaterial(final GL gl, final Material material)
    {
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
     * @param gl
     *            The GL context
     * @param material
     *            The material to remove
     */

    private void removeMaterial(final GL gl, final Material material)
    {
        if (this.diffuseTexture != null)
        {
            this.diffuseTexture.unbind(gl);
            this.diffuseTexture = null;
        }
    }


    /**
     * Renders the bounds of the mesh.
     *
     * @param gl
     *            The GL context
     */

    private void renderBounds(final GL gl)
    {
        final boolean oldLighting = gl.glIsEnabled(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_LIGHTING);

        BoundsRenderer.render(gl, this.mesh.getBounds());
        if (this.showGroupBounds)
            for (final MeshPolygons polygons : this.mesh.getPolygons())
                BoundsRenderer.render(gl, polygons.getBounds());

        if (oldLighting) gl.glEnable(GL.GL_LIGHTING);
    }


    /**
     * Builds and returns the normal mesh for this model.
     *
     * @return The normal mesh
     */

    public Mesh getNormalMesh()
    {
        // If there is a cached normal mesh then use this one
        if (this.normalMesh != null)
        {
            final Mesh normalMesh = this.normalMesh.get();
            if (normalMesh != null) return normalMesh;
        }

        // Build the normal mesh
        final MeshBuilder builder = new MeshBuilder();
        for (final MeshPolygons polygons : this.mesh.getPolygons())
        {
            // Do nothing if mesh has no normals
            if (!polygons.hasNormals()) continue;

            // Calculate a scale factor for the normals
            final float scale = polygons.getBounds().getSize() / 50;

            final FloatBuffer normals = polygons.getNormals();
            final FloatBuffer vertices = polygons.getVertices();
            while (vertices.hasRemaining())
            {
                final Vector3f a = new Vector3f(vertices.get(), vertices.get(),
                        vertices.get());
                final Vector3f b = new Vector3f(normals.get(), normals.get(),
                        normals.get());
                b.multiply(scale);
                b.add(a);
                builder.addElement(2, builder.addVertex(a), builder
                        .addVertex(b));
            }
        }
        final Mesh normalMesh = builder.build();

        // Cache normal mesh and return it
        this.normalMesh = new SoftReference<Mesh>(normalMesh);
        return normalMesh;
    }
}