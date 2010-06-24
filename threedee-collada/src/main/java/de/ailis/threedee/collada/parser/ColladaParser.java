/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import de.ailis.threedee.collada.entities.COLLADA;
import de.ailis.threedee.exceptions.ParserException;


/**
 * Parses a collada file.
 *
 * @author Klaus Reimer <k@ailis.de>
 */

public class ColladaParser
{
    /**
     * Parses a collada document from the specified input stream and returns
     * it.
     *
     * @param stream
     *            The stream from which to read the collada document
     * @return The collada document
     * @throws ParserException
     *             When collada document could not be read
     */

    public COLLADA parse(final InputStream stream)
    {
        try
        {
            final ColladaHandler handler = new ColladaHandler();
            //final SAXParser parser = SAXParserFactory.newInstance()
                    //.newSAXParser();
            final XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(stream));
//            LogFactory.getLog(ColladaParser.class).info(parser.getProperty("http://xml.org/sax/features/namespaces"));
            //System.exit(0);
            //parser.parse(stream, handler);
            return handler.getCOLLADA();
        }
        catch (final IOException e)
        {
            throw new ParserException("Unable to read collada document: " + e,
                    e);
        }
        catch (final SAXException e)
        {
            throw new ParserException("Unable to parse collada document: " + e,
                    e);
        }
    }
}
