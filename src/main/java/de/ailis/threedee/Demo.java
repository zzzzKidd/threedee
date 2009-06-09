/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.reader.TDOReader;
import de.ailis.threedee.scene.CameraNode;
import de.ailis.threedee.scene.LightNode;
import de.ailis.threedee.scene.ModelNode;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.light.PointLight;
import de.ailis.threedee.scene.updater.KeyboardUpdater;


/**
 * ThreeDee demonstration.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Demo
{
    /**
     * Executes the application.
     * 
     * @param args
     *            The command line arguments
     * @throws IOException 
     */

    public static void main(final String[] args) throws IOException
    {
        final JFrame frame = new JFrame("ThreeDee Demo");
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Scene scene = new Scene();
        //final Cube cube = new Cube(1.25, 1.25, 1.25);
        //final ModelNode node = new ModelNode(cube);
        //final ModelNode node2 = new ModelNode(cube);
        //final ModelNode node3 = new ModelNode(cube);
        //node.setTransform(node.getTransform().translate(0, 0, 0));
        //scene.setRootNode(node);
        //node2.translateX(2.5);
        //node.appendChild(node2);
        //node3.translateX(-2.5);
        //node.appendChild(node3);
        

        //node.addUpdater(new RollUpdater(Math.toRadians(11.25)));
        //node.addUpdater(new YawUpdater(Math.toRadians(11.25)));        
        //node2.addUpdater(new PitchUpdater(Math.toRadians(22.5)));
        //node3.addUpdater(new PitchUpdater(Math.toRadians(-45)));
        
        final SceneNode root = new SceneNode();
        scene.setRootNode(root);
        //scene.setGlobalAmbient(Color.BLACK);
        
        final Model model = TDOReader.read(Demo.class.getResourceAsStream("/worcem.tdo"));
        final SceneNode shipNode = new SceneNode();
        final ModelNode modelNode = new ModelNode(model);
        modelNode.rotateY(Math.toRadians(180));
       // modelNode.addUpdater(new YawUpdater(Math.toRadians(22.5)));
        shipNode.appendChild(modelNode);
        root.appendChild(shipNode);
        

        final CameraNode camera = new CameraNode();
        camera.translate(0, 0, -35);
        root.appendChild(camera);
        
        final KeyboardUpdater keyboardUpdater = new KeyboardUpdater();
        
        shipNode.addUpdater(keyboardUpdater);
        frame.addKeyListener(keyboardUpdater);
        
        
        final PointLight light1 = new PointLight(Color.BLUE);
        final LightNode lightNode1 = new LightNode(light1);
        lightNode1.translate(5, 5, 0);
        root.appendChild(lightNode1);

        final PointLight light2 = new PointLight(Color.GREEN);
        final LightNode lightNode2 = new LightNode(light2);
        lightNode2.translate(-5, 5, 0);
        root.appendChild(lightNode2);

        frame.add(new ThreeDeePanel(scene, camera), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
