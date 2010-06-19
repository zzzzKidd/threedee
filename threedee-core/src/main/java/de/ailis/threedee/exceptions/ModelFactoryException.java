/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.exceptions;


/**
 * Thrown when something goes wrong in the model factory.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class ModelFactoryException extends ParserException
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;


    /**
     * Constructor
     */

    public ModelFactoryException()
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

    public ModelFactoryException(final String detailMessage, final Throwable throwable)
    {
        super(detailMessage, throwable);
    }


    /**
     * Constructor
     *
     * @param detailMessage
     *            The detailed error message
     */

    public ModelFactoryException(final String detailMessage)
    {
        super(detailMessage);
    }
}
