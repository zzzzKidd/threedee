/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ailis.threedee.RenderOptions;
import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Vector3d;
import de.ailis.threedee.model.Material;
import de.ailis.threedee.model.Model;
import de.ailis.threedee.model.Polygon;
import de.ailis.threedee.scene.light.Light;
import de.ailis.threedee.scene.light.PointLight;
import de.ailis.threedee.scene.light.TransformedLight;


/**
 * The polygon buffer is a container for all polygons which needs to be drawn.
 * It provides several add methods to place polygons in the buffer. The buffer
 * applies the transformations and also performs Z-sorting and clipping.
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

    /** The polygons in the buffer (Sorted by Z coordinate) */
    private final List<TransformedLight> lights =
        new ArrayList<TransformedLight>();

    /** The maximum polygon size */
    private int maxPolySize = 0;

    /** The global ambient color */
    private Color globalAmbient = Color.DARK_GRAY;

    /** The render options */
    private RenderOptions renderOptions;
    
    /** Counts frames per second */
    private final FpsCounter fpsCounter = new FpsCounter();    


    /**
     * Clears the buffer so it can be reused.
     */

    public void clear()
    {
        this.vertices.clear();
        this.polygons.clear();
        this.lights.clear();
        this.maxPolySize = 0;
    }


    /**
     * Sets the global ambient color.
     * 
     * @param globalAmbient
     *            The global ambient color to set
     */

    public void setGlobalAmbient(final Color globalAmbient)
    {
        this.globalAmbient = globalAmbient;
    }


    /**
     * Sets the render options.
     * 
     * @param renderOptions
     *            The render options to set
     */

    public void setRenderOptions(final RenderOptions renderOptions)
    {
        this.renderOptions = renderOptions;
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
        // Pull render options into local variables
        final boolean backfaceCulling = this.renderOptions.isBackfaceCulling();

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
            if (backfaceCulling)
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
     * Adds the specified light to the polygon buffer.
     * 
     * @param light
     *            The light to add
     * @param transform
     *            The transformation matrix to use
     */

    public void add(final Light light, final Matrix4d transform)
    {
        this.lights.add(new TransformedLight(light, transform));
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
        // Pull render options in local variables
        final boolean displayNormals = this.renderOptions.isDisplayNormals();
        final boolean lighting = this.renderOptions.isLighting();
        final boolean solid = this.renderOptions.isSolid();
        final boolean antiAliasing = this.renderOptions.isAntiAliasing();
        final boolean debugInfo = this.renderOptions.isDebugInfo();

        final int[] x = new int[this.maxPolySize + 5];
        final int[] y = new int[this.maxPolySize + 5];

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
        if (precisionFactor > 1)
            g.scale(1.0 / precisionFactor, 1.0 / precisionFactor);
        g.setStroke(new BasicStroke(precisionFactor));

        // Set the default color and enable anti-aliasing if needed
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing
            ? RenderingHints.VALUE_ANTIALIAS_ON
            : RenderingHints.VALUE_ANTIALIAS_OFF);

        // Generate the view frustum for clipping the polygons
        final Frustum frustum =
            new Frustum(width, height, factor / precisionFactor);

        // Draw the polygons
        for (final Polygon polygon: this.polygons)
        {
            // Clip the polygon with the frustum
            final Polygon clippedPolygon =
                frustum.clip(polygon, this.vertices);

            // If polygon was clipped completely away then skip it
            if (clippedPolygon == null) continue;

            // Project the 3D vertices into 2D coordinates and fill the x and y
            // arrays with them which will be used by the fillPolygon() method
            // to draw it
            final int vertexCount = clippedPolygon.countVertices();
            for (int v = 0; v < vertexCount; v++)
            {
                final Vector3d vertex =
                    this.vertices.get(clippedPolygon.getVertex(v));

                final double dx = vertex.getX();
                final double dy = vertex.getY();
                final double dz = vertex.getZ();

                x[v] = (int) Math.round(dx * factor / dz);
                y[v] = (int) Math.round(-dy * factor / dz);
            }

            if (solid)
            {
                g.setColor(lighting ? calcPolygonColor(polygon) : polygon
                    .getMaterial().getDiffuse());
                g.fillPolygon(x, y, vertexCount);
            }
            else
                g.drawPolygon(x, y, vertexCount);

            // Display normals if needed
            if (displayNormals)
            {
                final Vector3d normal = polygon.getNormal(this.vertices);
                final Color oldColor = g.getColor();
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
                g.setColor(oldColor);
            }
        }

        // Restore the original transformation
        g.setTransform(oldTransform);

        // Draw debug info if needed
        if (debugInfo) drawDebugInfo(g, width, height);
    }


    /**
     * Draws debug info to the screen.
     * 
     * @param g
     *            The graphics context to draw to
     * @param width
     *            The screen width
     * @param height
     *            The screen height
     */

    private void drawDebugInfo(final Graphics2D g, final int width, final int height)
    {
        this.fpsCounter.frame();

        final String text = "Frames/s: " + this.fpsCounter.getFps();
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(text, 5, 15);
    }


    /**
     * Calculates the polygon color according to the material and the light
     * sources.
     * 
     * TODO Implement specular color
     * 
     * @param polygon
     *            The polygon
     * @return The calculated polygon color
     */

    private Color calcPolygonColor(final Polygon polygon)
    {
        // Get the material from the polygon
        final Material material = polygon.getMaterial();

        // Get the position (The center) and the normal from the polygon
        final Vector3d position = polygon.getCenter(this.vertices);
        final Vector3d normal = polygon.getNormal(this.vertices);

        // Get the color components of the global ambient color
        final float[] globalAmbient =
            this.globalAmbient.getRGBColorComponents(new float[3]);

        // Get the ambient, diffuse and emissive color components of the
        // material
        final float[] ambient =
            material.getAmbient().getRGBColorComponents(new float[3]);
        final float[] diffuse =
            material.getDiffuse().getRGBColorComponents(new float[3]);
        final float[] emissive =
            material.getEmissive().getRGBColorComponents(new float[3]);

        // We store the resulting color components here:
        final float[] result = new float[3];

        // We store the added diffuse color components here:
        final float[] addedDiffuse = new float[] { 0, 0, 0 };

        // Now we cycle through all color components and calculate the values
        for (int i = 0; i < 3; i++)
        {
            // Calculate the ambient color value
            ambient[i] = ambient[i] * globalAmbient[i];

            // Iterate over all registered light sources and process them
            for (final TransformedLight transLight: this.lights)
            {
                final Light light = transLight.getLight();
                if (light instanceof PointLight)
                {
                    final PointLight pointLight = (PointLight) light;
                    final float lightColor = pointLight.getColorComponent(i);

                    final Vector3d lightPosition = transLight.getPosition();
                    final Vector3d L = lightPosition.sub(position).toUnit();
                    final float diffuseLight =
                        (float) Math.max(normal.multiply(L), 0);

                    addedDiffuse[i] += diffuse[i] * lightColor * diffuseLight;
                }
            }

            result[i] =
                Math.min(ambient[i] + emissive[i] + addedDiffuse[i], 1f);
        }

        return new Color(result[0], result[1], result[2]);

        // float3 P = position.xyz;
        // float3 N = normal;

        // float3 emissive = Ke;
        // float3 ambient = Ka * globalAmbient;

        // float3 L = normalize(lightPosition - P);
        // float diffuseLight = max(dot(N, L), 0);
        // float3 diffuse = Kd * lightColor * diffuseLight;

        // float3 V = normalize(eyePosition - P);
        // float3 H = normalize(L + V);
        // float specularLight = pow(max(dot(N, H), 0), shininess);

        // if (diffuseLight <= 0) specularLight = 0;
        // float3 specular = Ks * lightColor * specularLight;

        // color.xyz = emissive + ambient + diffuse + specular;
    }
}
