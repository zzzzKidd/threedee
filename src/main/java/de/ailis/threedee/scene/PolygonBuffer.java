/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Vector3d;
import de.ailis.threedee.model.Material;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.Polygon;
import de.ailis.threedee.model.TransformedPolygon;


/**
 * The polygon buffer is a container for all polygons which needs to be drawn.
 * It provides several add methods to place polygons in the buffer. The buffer
 * applies the transformations and also performs Z-sorting and clipping.
 * 
 * TODO Implement clipping
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class PolygonBuffer
{
    /** The vertices in the buffer */
    private final List<Vector3d> vertices = new ArrayList<Vector3d>();

    /** The polygons in the buffer (Sorted by Z coordinate) */
    private final List<TransformedPolygon> polygons =
        new ArrayList<TransformedPolygon>();

    /** The maximum polygon size */
    private int maxPolySize = 0;


    /**
     * Clears the buffer so it can be reused.
     */

    public void clear()
    {
        this.vertices.clear();
        this.polygons.clear();
        this.maxPolySize = 0;
    }


    /**
     * Transforms the vertices of the specified model with the specified
     * transformation matrix and returns the list with transformed vertices.
     * 
     * @param model
     *            The model to add
     * @param transform
     *            The transformation matrix to use
     * @return The list with transformed vertices
     */

    private List<Vector3d> getTransformedVertices(final Model model,
        final Matrix4d transform)
    {
        final int max = model.countVertices();
        final List<Vector3d> vertices = new ArrayList<Vector3d>(max);

        // Transform the vertices and return them
        for (int i = 0; i < max; i++)
            vertices.add(model.getVertex(i).multiply(transform));
        return vertices;
    }


    /**
     * Adds the specified model to the polygon buffer.
     * 
     * @param model
     *            The model to add
     * @param transform
     *            The transformation matrix to use
     */

    public void add(final Model model, final Matrix4d transform)
    {
        // Get the transformed vertices
        final List<Vector3d> transformedVertices =
            getTransformedVertices(model, transform);

        // Create the transformed polygons and add them to the buffer
        boolean added = false;
        final int vertexOffset = this.vertices.size();
        for (int i = 0, max = model.countPolygons(); i < max; i++)
        {
            final Polygon polygon = model.getPolygon(i);

            // Perform back-face culling
            if (true)
            {
                if (polygon.getNormal(transformedVertices).multiply(
                    transformedVertices.get(polygon.getVertex(0))) > 0)
                    continue;
            }

            // So we use at least one polygon. So add the transformed vertices
            // to the buffer
            if (!added)
            {
                added = true;
                this.vertices.addAll(transformedVertices);
            }

            final int vertexCount = polygon.countVertices();
            if (vertexCount > this.maxPolySize)
                this.maxPolySize = vertexCount;
            final int[] vertices = new int[vertexCount];

            double averageZ = 0;
            for (int v = 0; v < vertexCount; v++)
            {
                final int vertex = polygon.getVertex(v);
                final int newVertex = vertex + vertexOffset;
                vertices[v] = newVertex;
                averageZ += this.vertices.get(newVertex).getZ();
            }
            averageZ /= vertexCount;
            this.polygons.add(new TransformedPolygon(model
                .getMaterial(polygon), averageZ, vertices));
        }
    }


    /**
     * Renders the polygon buffer.
     * 
     * @param g
     *            The graphics context
     * @param width
     *            The output width in pixels
     * @param height
     *            The output height in pixels
     */

    public void render(final Graphics2D g, final int width, final int height)
    {
        final int[] x = new int[this.maxPolySize];
        final int[] y = new int[this.maxPolySize];

        // Precision factor to smooth out rounding errors when converting double
        // coordinates to integer screen coordinates
        final int precisionFactor = 10;
        final double eyeDistance = 0.5; // Eye is 0.5 meters from screen
        final double screenInMeters = 0.38;
        final double screenInPixels = Math.min(width, height);
        final double dpm = screenInPixels / screenInMeters;
        final double factor = eyeDistance * dpm * precisionFactor;

        // Do Z-sorting
        Collections.sort(this.polygons);

        // Remember the old transformation and then apply the screen center
        // transformation and a special "precision scaling". This scaling
        // smoothes out rounding errors which occur because Java2D can only
        // draw with integer values instead of floats.
        final AffineTransform oldTransform = g.getTransform();
        g.translate(width / 2, height / 2);
        g.scale(1.0 / precisionFactor, 1.0 / precisionFactor);
        g.setStroke(new BasicStroke(precisionFactor));

        // Draw the polygons
        for (final Polygon polygon: this.polygons)
        {
            final int vertices = polygon.countVertices();
            for (int v = 0; v < vertices; v++)
            {
                final Vector3d vertex =
                    this.vertices.get(polygon.getVertex(v));

                final double dx = vertex.getX();
                final double dy = vertex.getY();
                final double dz = vertex.getZ();

                x[v] = (int) Math.round(dx * factor / dz);
                y[v] = (int) Math.round(-dy * factor / dz);
            }


            final Vector3d normal = polygon.getNormal(this.vertices);

            double colorFactor = 0;
            if (true)
            {
                final Vector3d light = new Vector3d(0.5, 0.5, -0.5);
                final double angle = Math.toDegrees(light.getAngle(normal));
                colorFactor = Math.max(colorFactor, 1 - (angle / 90));
                colorFactor = Math.max(colorFactor, 0.2);
            }
            else
                colorFactor = 1;

            final Material material = polygon.getMaterial();
            Color color = material.getColor();
            color =
                new Color((int) (colorFactor * color.getRed()),
                    (int) (colorFactor * color.getGreen()),
                    (int) (colorFactor * color.getBlue()));

            g.setColor(color);
            g.fillPolygon(x, y, vertices);

            // display normals
            if (false)
            {
                g.setColor(Color.YELLOW);
                final Vector3d center = polygon.getCenter(this.vertices);
                final Vector3d normalEnd = normal.add(center);
                final int cx = (int) (center.getX() * factor / center.getZ());
                final int cy = (int) (-center.getY() * factor / center.getZ());
                final int cx2 =
                    (int) (normalEnd.getX() * factor / normalEnd.getZ());
                final int cy2 =
                    (int) (-normalEnd.getY() * factor / normalEnd.getZ());
                g.drawLine(cx, cy, cx2, cy2);
            }
        }

        g.setTransform(oldTransform);
    }
}
