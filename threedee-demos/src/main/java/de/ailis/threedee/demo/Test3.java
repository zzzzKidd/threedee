/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.entities.Camera;
import de.ailis.threedee.entities.CameraNode;
import de.ailis.threedee.entities.Color;
import de.ailis.threedee.entities.LightInstance;
import de.ailis.threedee.entities.Mesh;
import de.ailis.threedee.entities.MeshInstance;
import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.entities.SpotLight;
import de.ailis.threedee.jogl.swing.SceneCanvas;
import de.ailis.threedee.model.Cube;


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

        final Mesh cube = Cube.buildCube(1f, 1f, 1f);

        final int size = 50;
        for (int x = -size; x < size; x++)
        {
            for (int z = -size; z < size; z++)
            {
                final SceneNode node = new SceneNode();
                node.translateZ(z * 2);
                node.translateX(x * 2);
                root.appendChild(node);
                node.addMesh(new MeshInstance(cube));
            }
        }

        final SpotLight spotLight = new SpotLight(Color.BLACK, Color.RED, Color.RED);
        final LightInstance light = new LightInstance(spotLight);
        final SceneNode lightNode = new SceneNode();
        lightNode.addLight(light);
        lightNode.translateZ(50f);
        lightNode.translateY(250f);
        lightNode.translateX(50f);
        lightNode.rotateX((float) Math.toRadians(-90f));
//        lightNode.getPhysics().getVelocity().setZ(10);
        root.appendChild(lightNode);
        root.enableLight(light);

        final SpotLight spotLight2 = new SpotLight(Color.BLACK, Color.BLUE, Color.BLUE);
        final LightInstance light2 = new LightInstance(spotLight2);
        final SceneNode lightNode2 = new SceneNode();
        lightNode2.addLight(light2);
        lightNode2.translateZ(50f);
        lightNode2.translateY(250f);
        lightNode2.translateX(-50f);
        lightNode2.rotateX((float) Math.toRadians(-90f));
//        lightNode.getPhysics().getVelocity().setZ(10);
        root.appendChild(lightNode2);
        root.enableLight(light2);

        final SpotLight spotLight3 = new SpotLight(Color.BLACK, Color.GREEN, Color.GREEN);
        final LightInstance light3 = new LightInstance(spotLight3);
        final SceneNode lightNode3 = new SceneNode();
        lightNode3.addLight(light3);
        lightNode3.translateZ(-50f);
        lightNode3.translateY(250f);
        lightNode3.translateX(0f);
        lightNode3.rotateY((float) Math.toRadians(-5f));
        lightNode3.rotateX((float) Math.toRadians(-90f));
        //lightNode3.getPhysics().getSpin().setX((float) Math.toRadians(180f));
        root.appendChild(lightNode3);
        root.enableLight(light3);



       final Camera camera = new Camera();
        final CameraNode cameraNode = new CameraNode(camera);
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
