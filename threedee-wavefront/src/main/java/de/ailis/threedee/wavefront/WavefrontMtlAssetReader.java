
package de.ailis.threedee.wavefront;

/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import de.ailis.gramath.Color4f;
import de.ailis.gramath.ImmutableColor4f;
import de.ailis.threedee.assets.AssetFormat;
import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.Material;
import de.ailis.threedee.assets.reader.assets.AssetsReader;
import de.ailis.threedee.builder.MaterialBuilder;
import de.ailis.threedee.exceptions.AssetIOException;
import de.ailis.threedee.exceptions.ReaderException;


/**
 * This loader loads material assets from a WaveFront MTL file.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class WavefrontMtlAssetReader implements AssetsReader
{
    /** The assets. */
    Assets assets;


    /**
     * @see AssetsReader#read(Assets, InputStream)
     */

    @Override
    public void read(final Assets assets, final InputStream stream) throws AssetIOException
    {
        this.assets = assets;
        try
        {
            loadMaterials(stream);
        }
        catch (final IOException e)
        {
            throw new AssetIOException(e.toString(), e);
        }
    }


    /**
     * Load materials.
     *
     * @param stream
     *            The input stream
     * @throws IOException
     *             When material library could not be loaded
     */

    private void loadMaterials(final InputStream stream) throws IOException
    {
        MaterialBuilder builder = null;

        // Open a buffered reader to read the file line by line
        final BufferedReader reader = new BufferedReader(new InputStreamReader(
                stream));
        try
        {
            String line;
            int lineNo = 0;
            while ((line = reader.readLine()) != null)
            {
                // Ignore empty lines
                if (line.trim().length() == 0) continue;

                // Increase line number for usage in error messages
                lineNo++;

                // Split the line into tokens and parse the command from it
                final StringTokenizer tokenizer = new StringTokenizer(line, " ");
                final String command = tokenizer.nextToken();

                // Process commands

                // Process comment
                if ("#".equals(command)) continue;

                // Process material name
                if ("newmtl".equals(command))
                {
                    if (builder != null)
                    {
                        final Material material = builder.build();
                        this.assets.addMaterial(material);
                        builder.reset();
                    }
                    else
                        builder = new MaterialBuilder();
                    builder.setId(parseString(tokenizer));
                    continue;
                }

                if (builder == null)
                    throw new ReaderException("Unexpected command in line "
                            + lineNo + ": " + command);

                // Process the ambient color
                if ("Ka".equals(command))
                {
                    builder.setAmbientColor(parseColor(tokenizer));
                    continue;
                }

                // Process the diffuse color
                if ("Kd".equals(command))
                {
                    builder.setDiffuseColor(parseColor(tokenizer));
                    continue;
                }

                // Process the diffuse texture
                if ("map_Kd".equals(command))
                {
                    String filename = new File(parseString(tokenizer)).getName();
                    filename = filename.substring(0, filename.lastIndexOf('.'));
                    builder.setDiffuseTexture(this.assets.getTexture(filename));
                    continue;
                }

                // Process the specular color
                if ("Ks".equals(command))
                {
                    builder.setSpecularColor(parseColor(tokenizer));
                    continue;
                }

                // Process shininess
                if ("Ns".equals(command))
                {
                    builder.setShininess(parseFloat(tokenizer) * 128 / 1000);
                    continue;
                }

                // Process transparency
                /*
                 * TODO Implement me if ("d".equals(command)) {
                 * builder.setAlpha(parseFloat(tokenizer)); continue; }
                 */
            }
        }
        finally
        {
            reader.close();
        }

        if (builder != null)
        {
            final Material material = builder.build();
            this.assets.addMaterial(material);
            builder.reset();
        }
    }


    /**
     * Parses a float from the specified tokenizer and returns it.
     *
     * @param tokenizer
     *            The tokenizer pointing to the vector values
     * @return The parsed float number
     */

    private float parseFloat(final StringTokenizer tokenizer)
    {
        if (tokenizer.countTokens() != 1)
        {
            throw new ReaderException("1 float parameters expected but "
                    + tokenizer.countTokens() + " found");
        }

        try
        {
            return Float.parseFloat(tokenizer.nextToken());
        }
        catch (final NumberFormatException e)
        {
            throw new ReaderException("Conversion to float failed: "
                    + e.toString(), e);
        }
    }


    /**
     * Parses a color from the specified tokenizer and returns it.
     *
     * @param tokenizer
     *            The tokenizer pointing to the vector values
     * @return The parsed color
     */

    private Color4f parseColor(final StringTokenizer tokenizer)
    {
        if (tokenizer.countTokens() != 3)
        {
            throw new ReaderException("3 float parameters expected but only "
                    + tokenizer.countTokens() + " found");
        }

        try
        {
            return new ImmutableColor4f(Float.parseFloat(tokenizer.nextToken()), Float
                    .parseFloat(tokenizer.nextToken()), Float
                    .parseFloat(tokenizer.nextToken()), 1f);
        }
        catch (final NumberFormatException e)
        {
            throw new ReaderException("Conversion to float failed: "
                    + e.toString(), e);
        }
    }


    /**
     * Parses a string from the specified tokenizer and returns it.
     *
     * @param tokenizer
     *            The tokenizer pointing to the vector values
     * @return The string
     */

    private String parseString(final StringTokenizer tokenizer)
    {
        if (tokenizer.countTokens() != 1)
        {
            throw new ReaderException("1 string parameter expected but "
                    + tokenizer.countTokens() + " found");
        }
        return tokenizer.nextToken();
    }


    /**
     * @see de.ailis.threedee.assets.reader.assets.AssetsReader#getFormat()
     */

    @Override
    public AssetFormat getFormat()
    {
        return AssetFormat.MTL;
    }
}
