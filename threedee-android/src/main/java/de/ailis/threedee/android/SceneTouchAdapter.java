/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import de.ailis.threedee.events.TouchEvent;


/**
 * Translates mouse events into touch events for the connected scene renderer.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneTouchAdapter implements OnTouchListener
{
    /** The scene */
    private final SceneSurfaceView sceneRenderer;


    /**
     * Constructs a new scene touch adapter.
     *
     * @param scene
     *            The scene
     */

    public SceneTouchAdapter(final SceneSurfaceView scene)
    {
        this.sceneRenderer = scene;
    }


    /**
     * @see android.view.View.OnTouchListener#onTouch(android.view.View,
     *      android.view.MotionEvent)
     */

    @Override
    public boolean onTouch(final View v, final MotionEvent event)
    {
        for (int i = 0, max = event.getPointerCount(); i < max; i++)
        {
            final int id = event.getPointerId(i);
            final int x = (int) event.getX(i) - v.getWidth() / 2;
            final int y = v.getHeight() / 2 - (int) event.getY(i);
            final TouchEvent touchEvent = new TouchEvent(id, x, y);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    this.sceneRenderer.touchDown(touchEvent);
                    break;

                case MotionEvent.ACTION_MOVE:
                    this.sceneRenderer.touchMove(touchEvent);
                    break;

                case MotionEvent.ACTION_UP:
                    this.sceneRenderer.touchRelease(touchEvent);
                    break;
            }
        }

        return true;
    }
}
