/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A animation.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaAnimation implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The animation id */
    private String id;

    /** The sub animations */
    private final ColladaAnimations animations = new ColladaAnimations();

    /** The sources */
    private final DataSources sources = new DataSources();

    /** The samplers */
    private final ColladaSamplers samplers = new ColladaSamplers();

    /** The channels */
    private final Channels channels = new Channels();


    /**
     * Constructs an animation without an id.
     */

    public ColladaAnimation()
    {
        this(null);
    }


    /**
     * Constructs an animation with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaAnimation(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the effect id.
     *
     * @return The effect id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the effect id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the profiles of this animation.
     *
     * @return The profiles
     */

    public ColladaAnimations getAnimations()
    {
        return this.animations;
    }


    /**
     * Returns the sources.
     *
     * @return The sources
     */

    public DataSources getSources()
    {
        return this.sources;
    }


    /**
     * Returns the samplers of this animation.
     *
     * @return The samplers
     */

    public ColladaSamplers getSamplers()
    {
        return this.samplers;
    }


    /**
     * Returns the channels of this animation.
     *
     * @return The channels
     */

    public Channels getChannels()
    {
        return this.channels;
    }
}
