/*
 * Copyright (C) 2010 IP Labs GmbH (http://www.iplabs.de/)
 * All rights reserved.
 */

package de.ailis.threedee.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import de.ailis.threedee.scene.Scene;


/**
 * A surface view which displays a Draydel scene.
 *
 * @author Klaus Reimer (k.reimer@iplabs.de)
 */

public class SceneSurfaceView extends GLSurfaceView
{
    /** The scene renderer */
    private SceneRenderer renderer;


    /**
     * Constructor
     *
     * @param context
     *            The content context
     */

    public SceneSurfaceView(final Context context)
    {
        super(context);
        init();
    }


    /**
     * Constructor
     *
     * @param context
     *            The content context
     * @param attrs
     *            The attributes
     */

    public SceneSurfaceView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }


    /**
     * Initializes the view.
     */

    private void init()
    {
        this.renderer = new SceneRenderer(this);
        setRenderer(this.renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setOnTouchListener(new SceneTouchAdapter(getScene()));
    }


    /**
     * Returns the scene.
     *
     * @return The scene
     */

    public Scene getScene()
    {
        return this.renderer.getScene();
    }


    /**
     * Sets the scene.
     *
     * @param scene
     *            The scene to set
     */

    public void setScene(final Scene scene)
    {
        this.renderer.setScene(scene);
        setOnTouchListener(new SceneTouchAdapter(getScene()));
    }
}
