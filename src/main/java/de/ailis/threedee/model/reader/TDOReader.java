/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model.reader;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.math.Vector3d;
import de.ailis.threedee.model.CustomModel;
import de.ailis.threedee.model.Material;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.Polygon;


/**
 * TDOReader
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class TDOReader
{
    public static Model read(final InputStream stream) throws IOException
    {
        final CustomModel model = new CustomModel();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        final List<String> vertexNames = new ArrayList<String>();
        String line;
        int mode = 0;
        while ((line = reader.readLine()) != null)
        {
            if (line.equals("[Vertexes]"))
                mode = 1;
            else if (line.equals("[Planes]"))
                mode = 2;
            else
            {
                switch (mode)
                {
                    case 1:
                        final String[] parts = line.split("=");
                        if (parts.length < 2) continue;
                        final String name = parts[0].trim();
                        final String[] coords = parts[1].trim().split("[ ]+");
                        final double x = Double.parseDouble(coords[0]);
                        final double y = Double.parseDouble(coords[1]);
                        final double z = -Double.parseDouble(coords[2]);
                        model.addVertex(new Vector3d(x, y, z));
                        vertexNames.add(name);
                        break;
                        
                    case 2:
                        final String[] parts2 = line.split("=");
                        if (parts2.length < 2) continue;
                        final String name2 = parts2[0].trim();
                        final String[] parts3 = parts2[1].trim().split("[ ]+");
                        final String color = parts3[parts3.length - 1];
                        final Color matColor = new Color(Integer.parseInt(color.substring(1), 16));
                        Color emissive;
                        if (matColor.equals(Color.WHITE))
                            emissive = Color.BLACK;
                        else
                            emissive = matColor;
                        final int[] vertices = new int[parts3.length - 1];
                        for (int i = 0; i < parts3.length - 1; i++)
                        {
                            vertices[i] = vertexNames.indexOf(parts3[i].trim());
                        }
                        model.addPolygon(new Polygon(new Material(matColor, matColor, emissive), vertices));
                        break;
                }
            }
                
        }
        return model;
    }
}
