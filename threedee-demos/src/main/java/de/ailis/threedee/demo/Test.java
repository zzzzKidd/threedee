/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.entities.Asset;
import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.jogl.swing.SceneCanvas;
import de.ailis.threedee.reader.AssetReader;


/**
 * Simple demo showing a cup.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Test
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
        final Asset asset = AssetReader.read("dae/BikeFromXSI.dae", new ClasspathResourceProvider());

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas(asset.getActiveScene());
        canvas.setPreferredSize(new Dimension(512, 512));
        frame.getContentPane().add(canvas);

        // Create the scene
        final Scene scene = canvas.getScene();
        final SceneNode root = scene.getRootNode();

//        final Camera camera = new Camera();
//        camera.translate(54, 522, 1078);
//        camera.rotateX((float) Math.toDegrees(-27.1));
//        root.appendChild(camera);
//        scene.setCameraNode(camera);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
