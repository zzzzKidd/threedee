/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.exceptions.ModelFactoryException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.model.reader.ModelReader;


/**
 * Factory for loading models.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public final class ModelFactory
{
    /** The loaded models */
    private final Map<String, Model> models = new HashMap<String, Model>();

    /** The resource provider used for loading the models */
    private final ResourceProvider resourceProvider;


    /**
     * Private constructor to prevent instantiation.
     *
     * @param resourceProvider
     *            The resource provider used for loading the models
     */

    public ModelFactory(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Returns the model with the specified name.
     *
     * @param name
     *            The model name
     * @return The model
     */

    public Model getModel(final String name)
    {
        Model model = this.models.get(name);
        if (model == null)
        {
            try
            {
                model = ModelReader.read(name, this.resourceProvider);
            }
            catch (final IOException e)
            {
                throw new ModelFactoryException("Unable to read model '" + name
                        + "': " + e, e);
            }
            this.models.put(name, model);
        }
        return model;
    }
}
