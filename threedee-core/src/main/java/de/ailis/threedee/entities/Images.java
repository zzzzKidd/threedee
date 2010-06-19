/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.entities.support.Library;


/**
 * Images library. This is simply a map from image id to image filename.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Images extends ArrayList<Image> implements
        Library<Image>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The index */
    private final Map<String, Image> index;


    /**
     * Constructor
     */

    public Images()
    {
        this.index = new HashMap<String, Image>();
    }


    /**
     * @see ArrayList#add(Object)
     */

    @Override
    public boolean add(final Image image)
    {
        final String id = image.getId();
        if (id != null)
        {
            if (this.index.containsKey(id))
                throw new RuntimeException(
                        "There is already a image with id " + id
                                + " in the library");
            this.index.put(id, image);
        }
        return super.add(image);
    }


    /**
     * @see Library#get(String)
     */

    public Image get(final String id)
    {
        return this.index.get(id);
    }


    /**
     * @see Library#contains(String)
     */

    public boolean contains(final String id)
    {
        return this.index.containsKey(id);
    }
}
