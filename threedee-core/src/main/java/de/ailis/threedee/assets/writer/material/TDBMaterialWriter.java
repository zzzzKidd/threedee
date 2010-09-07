/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.writer.material;

import java.io.IOException;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.Material;
import de.ailis.threedee.assets.Texture;
import de.ailis.threedee.assets.writer.TDBWriter;


/**
 * Material writer for TDB files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TDBMaterialWriter extends TDBWriter<Material> implements
    MaterialWriter
{
    /**
     * Constructor
     */

    public TDBMaterialWriter()
    {
        super((byte) 1);
    }


    /**
     * @see TDBWriter#writeAsset(Asset)
     */

    @Override
    protected void writeAsset(final Material material) throws IOException
    {
        this.writer.writeColor4f(material.getAmbientColor());
        this.writer.writeColor4f(material.getDiffuseColor());
        this.writer.writeColor4f(material.getSpecularColor());
        this.writer.writeColor4f(material.getEmissionColor());
        this.writer.writeFloat(material.getShininess());
        final Texture texture = material.getDiffuseTexture();
        if (texture == null)
            this.writer.writeByte(0);
        else
        {
            final String id = texture.getId();
            this.writer.writeByte(id.getBytes("UTF-8").length);
            this.writer.writeString(id);
        }
    }
}
