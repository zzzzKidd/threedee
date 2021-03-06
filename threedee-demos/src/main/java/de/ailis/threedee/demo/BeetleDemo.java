/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.jogl.SceneCanvas;


/**
 * Loads a VW beetle from a Wavefront OBJ file and displays it.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class BeetleDemo
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
        final SceneCanvas canvas = new SceneCanvas();
        canvas.setPreferredSize(new Dimension(1024, 768));
        frame.getContentPane().add(canvas);
        //final Scene scene = canvas.getScene();
//        final SceneNode root = scene.getRootNode();

//        // Load the assets
//        final Assets assets = new Assets(new ClasspathAssetProvider());
//        assets.addAssets("beetle");
//        Model model = assets.getModel("beetle");
//
//        // Append model to scene
//        root.appendChild(model);
//        model.getPhysics().getSpinVelocity().setY(0.3f);
//
//        // Append a directional light
//        final Light light = new DirectionalLight();
//        root.appendChild(light);
//        root.addLight(light);
//
//        // Set the camera
//        final Camera camera = new Camera();
//        camera.translateY(2.5f);
//        camera.rotateX(-0.2f);
//        camera.translateZ(30f);
//        scene.setCameraNode(camera);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
