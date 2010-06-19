/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Class-path stream provider.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class FileResourceProvider implements ResourceProvider
{
    /** The base directory for relative paths */
    private final File baseDir;


    /**
     * Constructs a new file resource provider.
     */

    public FileResourceProvider()
    {
        this.baseDir = null;
    }


    /**
     * Constructs a new file resource provider.
     *
     * @param baseDir
     *            The base directory for relative paths
     */

    public FileResourceProvider(final String baseDir)
    {
        this(new File(baseDir));
    }


    /**
     * Constructs a new file resource provider.
     *
     * @param baseDir
     *            The base directory for relative paths
     */

    public FileResourceProvider(final File baseDir)
    {
        this.baseDir = baseDir;
    }


    /**
     * @see ResourceProvider#openForRead(String)
     */

    @Override
    public InputStream openForRead(final String filename)
            throws FileNotFoundException
    {
        File file = new File(filename);
        if (!file.isAbsolute() && this.baseDir != null)
            file = new File(this.baseDir, filename);
        return new FileInputStream(file);
    }


    /**
     * @see ResourceProvider#openForWrite(String)
     */

    @Override
    public OutputStream openForWrite(final String filename)
            throws FileNotFoundException
    {
        return new FileOutputStream(filename);
    }
}
