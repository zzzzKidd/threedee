/*
 * Copyright (C) 2010 IP Labs GmbH (http://www.iplabs.de/)
 * All rights reserved.
 */

package de.ailis.threedee.jogl;

import de.ailis.threedee.exceptions.ParserException;


/**
 * Thrown when something goes wrong with a canvas.
 *
 * @author Klaus Reimer (k.reimer@iplabs.de)
 * @version $Revision: 84727 $
 */

public class CanvasException extends ParserException
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;


    /**
     * Constructor
     */

    public CanvasException()
    {
        super();
    }


    /**
     * Constructor
     *
     * @param detailMessage
     *            The detailed error message
     * @param throwable
     *            The forwarded exception
     */

    public CanvasException(final String detailMessage, final Throwable throwable)
    {
        super(detailMessage, throwable);
    }


    /**
     * Constructor
     *
     * @param detailMessage
     *            The detailed error message
     */

    public CanvasException(final String detailMessage)
    {
        super(detailMessage);
    }
}
