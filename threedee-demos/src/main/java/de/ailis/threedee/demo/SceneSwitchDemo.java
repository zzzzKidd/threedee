/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ailis.threedee.assets.AssetProvider;
import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.ClasspathAssetProvider;
import de.ailis.threedee.jogl.SceneCanvas;


/**
 * Displays the Duck demo scene.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneSwitchDemo
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

        // Create the frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the canvas component displaying the scene
        final SceneCanvas canvas = new SceneCanvas();
        canvas.setPreferredSize(new Dimension(640, 480));
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        // Create the button panel
        final JPanel buttonPanel = new JPanel();
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Create the buttons
        JButton button = new JButton("None");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                assets.clear();
                canvas.setScene(null);
            }
        });
        buttonPanel.add(button);

        button = new JButton("Duck");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                assets.clear();
                assets.addAssets("duck");
                canvas.setScene(assets.getScenes().iterator().next());
            }
        });
        buttonPanel.add(button);

        button = new JButton("Transform_Test");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                assets.clear();
                assets.addAssets("Transform_Test");
                canvas.setScene(assets.getScenes().iterator().next());
            }
        });
        buttonPanel.add(button);

        // Display the frame
        frame.pack();
        frame.setVisible(true);

    }
}
