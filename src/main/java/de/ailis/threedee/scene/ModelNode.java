/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.scene.rendering.PolygonBuffer;


/**
 * A node which draws a model.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ModelNode extends SceneNode
{
    /** The model */
    private final Model model;


    /**
     * Constructs a new model node.
     * 
     * @param model
     *            The model
     */

    public ModelNode(final Model model)
    {
        this.model = model;
    }
    

    /**
     * @see SceneNode#render(PolygonBuffer, Matrix4d)
     */

    @Override
    public void render(final PolygonBuffer buffer, final Matrix4d transform)
    {
        buffer.add(this.model, transform);
        super.render(buffer, transform);
    }
}
