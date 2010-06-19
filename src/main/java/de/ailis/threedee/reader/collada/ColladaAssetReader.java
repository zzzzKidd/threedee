/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.reader.collada;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import de.ailis.threedee.entities.Asset;
import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.reader.AssetReader;


/**
 * ColladaReader
 *
 * @author k
 */

public class ColladaAssetReader extends AssetReader
{
    /**
     * Constructor
     *
     * @param resourceProvider
     *            The resource provider
     */

    public ColladaAssetReader(final ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }


    /**
     * @see de.ailis.threedee.model.reader.ModelReader#read(java.io.InputStream)
     */

    @Override
    public Asset read(final InputStream stream) throws IOException
    {
        try
        {
            final ColladaParser colladaParser = new ColladaParser();
            final SAXParser parser = SAXParserFactory.newInstance()
                    .newSAXParser();
            parser.parse(stream, colladaParser);
            return colladaParser.getAsset();
        }
        catch (final ParserConfigurationException e)
        {
            throw new ReaderException("Unable to create SAX parser: " + e, e);
        }
        catch (final SAXException e)
        {
            throw new ReaderException("Unable to parse collada document: " + e,
                    e);
        }
    }
}
