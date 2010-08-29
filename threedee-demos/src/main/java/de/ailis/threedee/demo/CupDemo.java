/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import de.ailis.gramath.ImmutableColor4f;
import de.ailis.gramath.ImmutableVector3f;
import de.ailis.gramath.Vector3f;
import de.ailis.threedee.collada.reader.ColladaSceneReader;
import de.ailis.threedee.events.TouchEvent;
import de.ailis.threedee.events.TouchListener;
import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.jogl.SceneCanvas;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Group;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.lights.PointLight;
import de.ailis.threedee.scene.physics.Physics;


/**
 * Simple demo showing a cup.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class CupDemo
{
    /**
     * Main method.
     *
     * @param args
     *            Command line arguments
     * @throws IOException
     *             When IO error occurs
     */

    public static void main(final String args[]) throws IOException
    {
        // Create the frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas(new ColladaSceneReader(new ClasspathResourceProvider()).read("cup.dae"));
        canvas.setPreferredSize(new Dimension(512, 512));
        frame.getContentPane().add(canvas);

        // Create the scene
        final Scene scene = canvas.getScene();
        final SceneNode root = scene.getRootNode();

        // Add a light
        final PointLight light = new PointLight();
        light.translate(-50, 50, 50);
        root.appendChild(light);
        root.addLight(light);

        // Add another light
        final PointLight light2 = new PointLight();
        light2.setDiffuseColor(new ImmutableColor4f(0.5f, 0.5f, 0.5f, 1));
        light2.translate(50, 50, -50);
        root.appendChild(light2);
        root.addLight(light2);

        // Add another light
        final PointLight light3 = new PointLight();
        light3.setDiffuseColor(new ImmutableColor4f(0.5f, 0.5f, 0.5f, 1));
        light3.translate(0, 50, -50);
        root.appendChild(light3);
        root.addLight(light3);

        // Create an orbiter node
        final SceneNode orbitter = new Group();
        root.appendChild(orbitter);

        final SceneNode evaluator = new Group();
        orbitter.appendChild(evaluator);
        //evaluator.translateZ(0.2f);

        // Setup the camera
        //final OrbitCamera orbitCamera = new OrbitCamera();
        final Camera camera = new Camera();
        camera.translateZ(20.5f);
        //camera.translateY(0.1f);
        //camera.rotateX(-0.5f);
        evaluator.appendChild(camera);
//        scene.setCamera(orbitCamera);
        scene.setCameraNode(camera);

        // Add another light
        final PointLight camLight = new PointLight();
        camLight.setDiffuseColor(new ImmutableColor4f(0.5f, 0.5f, 0.5f, 1));
        camLight.translate(50, 50, -50);
        camera.appendChild(camLight);
        root.addLight(camLight);

        // Let the cube spin slowly
        //cupNode.getPhysics().getSpin().setX(0.5f).setY(0.25f);
        final Physics physics = orbitter.getPhysics();
        //physics.getSpin().setZ(0.01f);
        physics.getSpinDeceleration().set(5f, 5f, 5f);

        final Physics physics2 = evaluator.getPhysics();
        physics2.getSpinDeceleration().set(5f, 5f, 5f);

        scene.addTouchListener(new TouchListener()
        {
            private Timer timer;

            int lastX;

            int x;

            int lastY;

            int y;

            float speed;


            @Override
            public void touchRelease(final TouchEvent event)
            {
                this.timer.cancel();
                this.timer.purge();
            }

            @Override
            public void touchMove(final TouchEvent event)
            {
                this.x = event.getX();
                this.y = event.getY();
            }

            @Override
            public void touchDown(final TouchEvent event)
            {
                this.x = event.getX();
                this.y = event.getY();
                this.lastX = this.x;
                this.lastY = this.y;
                this.timer = new Timer();
                this.timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        int diff = x - lastX;
                        final float speedY = (float) diff * 25 / 100;
                        lastX = x;
                        diff = lastY - y;
                        final float speedX = (float) diff * 25 / 100;
                        lastY = y;

                        final Vector3f v = new ImmutableVector3f(speedX, speedY, 0);

                        //v.multiply(cupNode.getSceneTransform().copy().invert());
                        //physics.getSpin().set(v.getX(), v.getY(), v.getZ());

                        physics.getSpinVelocity().setY(-speedY);
                        physics2.getSpinVelocity().setX(-speedX);

//                        physics.getSpin().setX(speedX);
                        //physics.getSpin().setY(speedY);
                        canvas.requestRender();
                    }
                }, 25, 25);
            }
        });

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
