/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.opengl.GL;


/**
 * A scene node. Can be used directly to create invisible group nodes or can be
 * extended to implement other node types.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ModelNode extends SceneNode
{
    /** The cached model to render */
    private final Model model;

    /** If normals should be displayed */
    private final boolean showNormals = false;

    /** If bounds should be displayed */
    private final boolean showBounds = false;

    /** If group bounds should be displayed */
    private final boolean showGroupBounds = false;

    /** Offset for rendering the model relative to the node */
    private final Vector3f modelOffset = new Vector3f();


    /**
     * Constructs a new model node.
     *
     * @param model
     *            The model to render
     */

    public ModelNode(final Model model)
    {
        this.model = model;
        centerModel();
    }


    /**
     * Returns the model which is displayed in this node.
     *
     * @return The model to display
     */

    public Model getModel()
    {
        return this.model;
    }


    /**
     * Returns the model offset.
     *
     * @return The model offset
     */

    public Vector3f getModelOffset()
    {
        return this.modelOffset;
    }


    /**
     * Modifies the model offset so it the node center is in the middle of
     * the model.
     */

    public void centerModel()
    {
        this.modelOffset.set(this.model.getBounds().getCenter()).invert();
    }


    /**
     * @see SceneNode#preRender(GL)
     */

    protected void preRender(final GL gl)
    {
        if (this.model != null)
        {
            final boolean useOffset = !this.modelOffset.isEmpty();
            if (useOffset)
                gl.glTranslate(this.modelOffset.getX(), this.modelOffset.getY(),
                        this.modelOffset.getZ());
            this.model.render(gl);
            if (this.showNormals) this.model.renderNormals(gl);
            if (this.showBounds)
                this.model.renderBounds(gl, this.showGroupBounds);
            if (useOffset)
                gl.glTranslate(-this.modelOffset.getX(), -this.modelOffset.getY(),
                        -this.modelOffset.getZ());
        }
    }
}