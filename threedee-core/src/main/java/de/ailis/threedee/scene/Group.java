/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;



/**
 * A node group.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Group extends SceneNode
{
    /**
     * @see java.lang.Object#clone()
     */

    @Override
    public Group clone()
    {
        final Group group = new Group();
        group.setTransform(getTransform());
        SceneNode child = getFirstChild();
        while (child != null)
        {
            group.appendChild(child.clone());
            child = child.getNextSibling();
        }
        return group;
    }
}
