/*
 * Copyright (C) 2010 IP Labs GmbH (http://www.iplabs.de/)
 * All rights reserved.
 */

package de.ailis.threedee.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import de.ailis.gramath.Color4f;
import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.rendering.ViewComponent;
import de.ailis.threedee.scene.Scene;


/**
 * A surface view which displays a Draydel scene.
 *
 * @author Klaus Reimer (k.reimer@iplabs.de)
 */

public class SceneSurfaceView extends GLSurfaceView implements ViewComponent
{
    /** The scene renderer */
    private SceneRenderer renderer;

    /** The list of touch listeners */
    private final List<TouchListener> touchListeners = new ArrayList<TouchListener>();


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

        setOnTouchListener(new SceneTouchAdapter(this));
    }


    /**
     * Fires the touch down event.
     *
     * @param event
     *            The event object
     */

    void touchDown(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchDown(event);
        }
    }


    /**
     * Fires the touch move event.
     *
     * @param event
     *            The event object
     */

    void touchMove(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchMove(event);
        }
    }


    /**
     * Fires the touch release event.
     *
     * @param event
     *            The event object
     */

    void touchRelease(final TouchEvent event)
    {
        for (final TouchListener touchListener : this.touchListeners)
        {
            touchListener.touchRelease(event);
        }
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#addTouchListener(de.ailis.threedee.events.TouchListener)
     */

    @Override
    public void addTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.add(touchListener);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#removeTouchListener(de.ailis.threedee.events.TouchListener)
     */

    @Override
    public void removeTouchListener(final TouchListener touchListener)
    {
        this.touchListeners.remove(touchListener);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#setScene(de.ailis.threedee.scene.Scene)
     */

    @Override
    public void setScene(final Scene scene)
    {
        this.renderer.getViewport().setScene(scene);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#getScene()
     */

    @Override
    public Scene getScene()
    {
        return this.renderer.getViewport().getScene();
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#setClearColor(de.ailis.gramath.Color4f)
     */

    @Override
    public void setClearColor(final Color4f clearColor)
    {
        this.renderer.getViewport().setClearColor(clearColor);
    }


    /**
     * @see de.ailis.threedee.rendering.ViewComponent#getClearColor()
     */

    @Override
    public Color4f getClearColor()
    {
        return this.renderer.getViewport().getClearColor();
    }
}
