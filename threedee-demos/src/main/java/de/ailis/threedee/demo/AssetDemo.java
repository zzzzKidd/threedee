/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.ClasspathAssetProvider;
import de.ailis.threedee.collada.ColladaAssetsReader;
import de.ailis.threedee.jogl.SceneCanvas;


/**
 * Shows how to create and display assets.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AssetDemo
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
        // Create the assets library
        final Assets assets = new Assets(new ClasspathAssetProvider());

        new ColladaAssetsReader().read(assets, AssetDemo.class.getResourceAsStream("/cup.dae"));
        System.out.println(assets.getMaterials());
        System.out.println(assets.getMeshes());
        System.out.println(assets.getScenes());

        // Create the frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas(assets.getScene("RootNode"));
        canvas.setPreferredSize(new Dimension(640, 480));
        frame.getContentPane().add(canvas);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
