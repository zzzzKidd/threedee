/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.collada.reader.ColladaSceneReader;
import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.jogl.swing.SceneCanvas;


/**
 * Simple demo showing a cup.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Test2
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

        // Load the asset
        //final String filename = "lightscene_trippled.dae";
//        final String filename = "lightscene_with_lights.dae";
        final String filename = "cup.dae";
        final Scene scene = new ColladaSceneReader(
                new ClasspathResourceProvider()).read(filename);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas(scene);
        canvas.setPreferredSize(new Dimension(1024, 768));
        frame.getContentPane().add(canvas);

        final SceneNode root = scene.getRootNode();
/*

        final PointLight spotLight = new PointLight(Color.BLACK, Color.BLACK, Color.WHITE);
        final LightInstance light = new LightInstance(spotLight);
        final SceneNode lightNode = new SceneNode();
        lightNode.addLight(light);
        lightNode.translateZ(0f);
        lightNode.translateY(50f);
        //lightNode.rotateX((float) Math.toRadians(-90f));
        lightNode.getPhysics().getVelocity().setZ(20);
        root.appendChild(lightNode);
        root.enableLight(light);
*/


/*
       final Camera camera = new Camera();
        final CameraNode cameraNode = new CameraNode(camera);
        cameraNode.rotateX((float) Math.toRadians(-65f));
        cameraNode.translateZ(440f);
        cameraNode.translateX(10f);
        scene.getRootNode().appendChild(cameraNode);
        scene.setCameraNode(cameraNode);
*/

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
