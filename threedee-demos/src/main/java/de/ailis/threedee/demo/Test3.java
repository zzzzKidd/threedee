/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.jogl.SceneCanvas;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Model;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.lights.SpotLight;
import de.ailis.threedee.scene.model.Mesh;
import de.ailis.threedee.scene.model.MeshFactory;


/**
 * Simple demo showing a cup.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Test3
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

        final Scene scene = canvas.getScene();
        final SceneNode root = scene.getRootNode();

        final Mesh cube = MeshFactory.createBox(1, 1, 1);

        final int size = 50;
        for (int x = -size; x < size; x++)
        {
            for (int z = -size; z < size; z++)
            {
                final Model node = new Model(cube);
                node.translateZ(z * 2);
                node.translateX(x * 2);
                root.appendChild(node);
            }
        }

        final SpotLight lightNode = new SpotLight(Color4f.BLACK, Color4f.RED, Color4f.RED);
        lightNode.setCutOff(5);
        lightNode.translateZ(50f);
        lightNode.translateY(250f);
        lightNode.translateX(50f);
        lightNode.rotateX((float) Math.toRadians(-90f));
//        lightNode.getPhysics().getVelocity().setZ(10);
        root.appendChild(lightNode);
        root.addLight(lightNode);

        final SpotLight lightNode2 = new SpotLight(Color4f.BLACK, Color4f.BLUE, Color4f.BLUE);
        lightNode2.setCutOff(5);
        lightNode2.translateZ(50f);
        lightNode2.translateY(250f);
        lightNode2.translateX(-50f);
        lightNode2.rotateX((float) Math.toRadians(-90f));
//        lightNode.getPhysics().getVelocity().setZ(10);
        root.appendChild(lightNode2);
        root.addLight(lightNode2);

        final SpotLight lightNode3 = new SpotLight(Color4f.BLACK, Color4f.GREEN, Color4f.GREEN);
        lightNode3.setCutOff(5);
        lightNode3.translateZ(-50f);
        lightNode3.translateY(250f);
        lightNode3.translateX(0f);
        lightNode3.rotateY((float) Math.toRadians(-5f));
        lightNode3.rotateX((float) Math.toRadians(-90f));
        //lightNode3.getPhysics().getSpin().setX((float) Math.toRadians(180f));
        root.appendChild(lightNode3);
        root.addLight(lightNode3);



       final Camera cameraNode = new Camera();
        cameraNode.rotateX((float) Math.toRadians(-90f));
        cameraNode.translateZ(350f);
        cameraNode.translateY(0f);
        scene.getRootNode().appendChild(cameraNode);
        scene.setCameraNode(cameraNode);


        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
