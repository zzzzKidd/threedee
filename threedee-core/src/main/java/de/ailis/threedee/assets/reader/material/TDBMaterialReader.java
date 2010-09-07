/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader.material;

import java.io.IOException;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.Material;
import de.ailis.threedee.assets.reader.TDBReader;


/**
 * Material reader for TDB files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TDBMaterialReader extends TDBReader<Material> implements
    MaterialReader
{
    /**
     * Constructor
     *
     * @param id
     *            The ID for the read asset.
     */

    public TDBMaterialReader(final String id)
    {
        super(id, (byte) 1, (byte) 1);
    }


    /**
     * Reads and returns the material.
     *
     * @param assets
     *            Assets for referencing textures.
     * @return The material
     * @throws IOException
     *             When read fails
     */

    @Override
    protected Material readAsset(final Assets assets) throws IOException
    {
        final Color4f ambientColor = this.reader.readColor4f();
        final Color4f diffuseColor = this.reader.readColor4f();
        final Color4f specularColor = this.reader.readColor4f();
        final Color4f emissionColor = this.reader.readColor4f();
        final float shininess = this.reader.readFloat();
        final int len = this.reader.readByte();
        final String texture = len == 0 ? null : this.reader.readString(len);
        return new Material(this.id, ambientColor, diffuseColor, specularColor,
                emissionColor, shininess, assets.getTexture(texture), true);
    }
}
