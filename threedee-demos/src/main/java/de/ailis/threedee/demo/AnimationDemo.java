/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import de.ailis.gramath.Matrix4f;
import de.ailis.gramath.MutableMatrix4f;
import de.ailis.threedee.jogl.SceneCanvas;
import de.ailis.threedee.sampling.Interpolation;
import de.ailis.threedee.sampling.Sampler;
import de.ailis.threedee.sampling.SamplerValue;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Light;
import de.ailis.threedee.scene.Model;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.animation.Animation;
import de.ailis.threedee.scene.animation.TransformAnimation;
import de.ailis.threedee.scene.lights.DirectionalLight;
import de.ailis.threedee.scene.model.MeshFactory;


/**
 * Loads a VW beetle from a Wavefront OBJ file and displays it.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AnimationDemo
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
        canvas.setPreferredSize(new Dimension(512, 512));
        frame.getContentPane().add(canvas);
        final Scene scene = canvas.getScene();
        final SceneNode root = scene.getRootNode();

        // Create a cube model
        final Model model = new Model(MeshFactory.createBox("box", 1, 1, 1));
        root.appendChild(model);

        // Add a simple animation
        final Sampler<Matrix4f> sampler = new Sampler<Matrix4f>();
        for (int i = 0; i <= 360; i += 10)
        {
            sampler.addSample((float) i / 90, new SamplerValue<Matrix4f>(MutableMatrix4f.identity().translateZ(0 * -i / 10).rotateY((float) Math.PI * i / 180).rotateX((float) Math.PI * i / 180), Interpolation.LINEAR));
        }
        final Animation animation = new TransformAnimation("animation", sampler);
        animation.addNode(model);
        scene.addAnimation(animation);

        // Append a directional light
        final Light light = new DirectionalLight();
        root.appendChild(light);
        root.addLight(light);

        // Set the camera
        final Camera camera = new Camera();
        camera.translateZ(10f);
        scene.setCameraNode(camera);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
