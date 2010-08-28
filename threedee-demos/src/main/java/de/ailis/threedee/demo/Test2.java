/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.collada.reader.ColladaSceneReader;
import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.jogl.SceneCanvas;
import de.ailis.threedee.scene.Scene;


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
        //final String filename = "cup.dae";
        //final String filename = "test.dae";
        //final String filename = "lightscene_with_FBX_thirdparty.dae";
//        final String filename = "lightscene_ascii_3cups.dae";
        final String filename = "weapon.dae";
        final Scene scene = new ColladaSceneReader(
                new ClasspathResourceProvider()).read(filename);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas(scene);
        canvas.setPreferredSize(new Dimension(512, 512));
        frame.getContentPane().add(canvas);

        // Display the frame
        frame.pack();
        frame.setVisible(true);

/*
        final Model box = new Model(MeshFactory.createBox(1, 1, 1));
        final Model model = ((Model) scene.getRootNode().getFirstChild().getFirstChild());
        for (final MeshPolygons polygons: model.getMesh().getPolygons())
        {
            System.out.println(polygons.getIndexCount());
        }*/
//model.setShowBounds(true);
//model.setShowNormals(true);
        //scene.getRootNode().appendChild(box);

        /*
        final Camera camera = new Camera();
        scene.getRootNode().appendChild(camera);
        camera.rotateX((float) Math.PI * 4);
        camera.translateZ(10);
        scene.setCameraNode(camera);
*/
        //System.out.println(scene.getRootNode());

//        scene.getNodeById("box").getPhysics().getSpin().setX(0.5f).setY(0.75f);
    }
}
