/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.assets.AssetProvider;
import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.ClasspathAssetProvider;
import de.ailis.threedee.jogl.SceneCanvas;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.properties.Lighting;


/**
 * Displays the COLLADA Transform Test scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaLogo
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
        // Load assets
        final AssetProvider provider = new ClasspathAssetProvider();
        final Assets assets = new Assets(provider);
        assets.addAssets("collada");

        // Get the scene
        final Scene scene = assets.getScenes().iterator().next();

        // Create the frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas();
        canvas.setScene(scene);
        canvas.setPreferredSize(new Dimension(640, 480));
        frame.getContentPane().add(canvas);

        // Setup the camera (Because the scene doesn't have one)
        final Camera camera = new Camera();
        camera.translateZ(5f);
        scene.getRootNode().appendChild(camera);
        scene.setCameraNode(camera);

        // Disable lighting (No lights in scene)
        scene.getRootNode().addProperty(new Lighting(false));

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
