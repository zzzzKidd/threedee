/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;


/**
 * A instance of a mesh.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class MeshInstance
{
    /** The mesh */
    private final Mesh mesh;

    /** The bound materials */
    private final Material[] materials;


    /**
     * Constructor
     *
     * @param mesh
     *            The mesh to instance
     */

    public MeshInstance(final Mesh mesh)
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
}
