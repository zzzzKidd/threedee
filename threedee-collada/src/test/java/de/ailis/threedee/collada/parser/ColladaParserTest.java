/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.collada.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import de.ailis.threedee.collada.entities.AmbientLight;
import de.ailis.threedee.collada.entities.COLLADA;
import de.ailis.threedee.collada.entities.ColladaCamera;
import de.ailis.threedee.collada.entities.ColladaColor;
import de.ailis.threedee.collada.entities.ColladaDirectionalLight;
import de.ailis.threedee.collada.entities.ColladaLight;
import de.ailis.threedee.collada.entities.ColladaMaterial;
import de.ailis.threedee.collada.entities.ColladaMesh;
import de.ailis.threedee.collada.entities.ColladaScene;
import de.ailis.threedee.collada.entities.ColladaSpotLight;
import de.ailis.threedee.collada.entities.ColorOrTexture;
import de.ailis.threedee.collada.entities.CommonProfile;
import de.ailis.threedee.collada.entities.CommonTechnique;
import de.ailis.threedee.collada.entities.CommonTechniques;
import de.ailis.threedee.collada.entities.DataArray;
import de.ailis.threedee.collada.entities.DataSource;
import de.ailis.threedee.collada.entities.DataSources;
import de.ailis.threedee.collada.entities.Effect;
import de.ailis.threedee.collada.entities.Geometry;
import de.ailis.threedee.collada.entities.Image;
import de.ailis.threedee.collada.entities.InstanceCamera;
import de.ailis.threedee.collada.entities.InstanceCameras;
import de.ailis.threedee.collada.entities.InstanceGeometries;
import de.ailis.threedee.collada.entities.InstanceGeometry;
import de.ailis.threedee.collada.entities.InstanceLight;
import de.ailis.threedee.collada.entities.InstanceLights;
import de.ailis.threedee.collada.entities.InstanceMaterial;
import de.ailis.threedee.collada.entities.InstanceMaterials;
import de.ailis.threedee.collada.entities.InstanceVisualScene;
import de.ailis.threedee.collada.entities.LibraryCameras;
import de.ailis.threedee.collada.entities.LibraryEffects;
import de.ailis.threedee.collada.entities.LibraryGeometries;
import de.ailis.threedee.collada.entities.LibraryImages;
import de.ailis.threedee.collada.entities.LibraryLights;
import de.ailis.threedee.collada.entities.LibraryMaterials;
import de.ailis.threedee.collada.entities.LibraryVisualScenes;
import de.ailis.threedee.collada.entities.MatrixTransformation;
import de.ailis.threedee.collada.entities.Node;
import de.ailis.threedee.collada.entities.Nodes;
import de.ailis.threedee.collada.entities.Optic;
import de.ailis.threedee.collada.entities.PerspectiveOptic;
import de.ailis.threedee.collada.entities.Phong;
import de.ailis.threedee.collada.entities.Polygons;
import de.ailis.threedee.collada.entities.PrimitiveGroups;
import de.ailis.threedee.collada.entities.Primitives;
import de.ailis.threedee.collada.entities.PrimitivesType;
import de.ailis.threedee.collada.entities.Profiles;
import de.ailis.threedee.collada.entities.Semantic;
import de.ailis.threedee.collada.entities.SharedInput;
import de.ailis.threedee.collada.entities.SharedInputs;
import de.ailis.threedee.collada.entities.Texture;
import de.ailis.threedee.collada.entities.Transformation;
import de.ailis.threedee.collada.entities.Transformations;
import de.ailis.threedee.collada.entities.UnsharedInput;
import de.ailis.threedee.collada.entities.UnsharedInputs;
import de.ailis.threedee.collada.entities.Vertices;
import de.ailis.threedee.collada.entities.VisualScene;


/**
 * Tests the ColladaParser class.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ColladaParserTest
{
    /** The test collada document */
    private static COLLADA collada;


    /**
     * Parses the test collada file.
     *
     * @throws IOException
     *             When test collada file could not be read
     */

    @BeforeClass
    public static void parseCollada() throws IOException
    {
        final InputStream stream = ColladaParserTest.class.getClassLoader()
                .getResourceAsStream("cup.dae");
        try
        {
            collada = new ColladaParser().parse(stream);
        }
        finally
        {
            stream.close();
        }
    }


    /**
     * Tests the library images.
     */

    @Test
    public void testLibraryImages()
    {
        final LibraryImages images = collada.getLibraryImages();
        assertEquals(3, images.size());

        Image image = images.get("invalid");
        assertNull(image);

        image = images.get("photo-image");
        assertNotNull(image);
        assertEquals("photo.jpeg", image.getURI().getPath());

        image = images.get("coffee-image");
        assertNotNull(image);
        assertEquals("coffee.jpeg", image.getURI().getPath());

        image = images.get("ground-image");
        assertNotNull(image);
        assertEquals("ground.jpeg", image.getURI().getPath());
    }


    /**
     * Tests the library materials.
     */

    @Test
    public void testLibraryMaterials()
    {
        final LibraryMaterials materials = collada.getLibraryMaterials();
        assertEquals(4, materials.size());

        ColladaMaterial material = materials.get("invalid");
        assertNull(material);

        material = materials.get("photo");
        assertNotNull(material);
        assertEquals("photo-fx", material.getEffectURI().getFragment());

        material = materials.get("coffee");
        assertNotNull(material);
        assertEquals("coffee-fx", material.getEffectURI().getFragment());

        material = materials.get("ground");
        assertNotNull(material);
        assertEquals("ground-fx", material.getEffectURI().getFragment());

        material = materials.get("cup");
        assertNotNull(material);
        assertEquals("cup-fx", material.getEffectURI().getFragment());
    }


    /**
     * Tests the library effects.
     */

    @Test
    public void testLibraryEffects()
    {
        final LibraryEffects effects = collada.getLibraryEffects();
        assertEquals(4, effects.size());

        Effect effect = effects.get("invalid");
        assertNull(effect);

        effect = effects.get("photo-fx");
        assertNotNull(effect);
        final Profiles profiles = effect.getProfiles();
        assertNotNull(profiles);
        assertEquals(1, profiles.size());
        final CommonProfile profile = profiles.getCommonProfile();
        assertNotNull(profile);
        final CommonTechniques techniques = profile.getTechniques();
        assertEquals(1, techniques.size());
        final CommonTechnique technique = techniques.get(0);
        assertNull(technique.getBlinn());
        final Phong phong = technique.getPhong();
        assertNotNull(phong);
        ColorOrTexture colorOrTexture = phong.getEmission();
        assertTrue(colorOrTexture.isColor());
        assertFalse(colorOrTexture.isTexture());
        ColladaColor color = colorOrTexture.getColor();
        assertNotNull(color);
        assertEquals(0, color.getRed(), 0.001f);
        assertEquals(0, color.getGreen(), 0.001f);
        assertEquals(0, color.getBlue(), 0.001f);
        assertEquals(1, color.getAlpha(), 0.001f);

        colorOrTexture = phong.getDiffuse();
        assertTrue(colorOrTexture.isTexture());
        assertFalse(colorOrTexture.isColor());
        final Texture texture = colorOrTexture.getTexture();
        assertNotNull(texture);
        assertEquals("photo-image", texture.getTexture());
        assertEquals("CHANNEL0", texture.getTexcoord());

        color = phong.getAmbient().getColor();
        assertEquals(0.2f, color.getRed(), 0.001f);
        assertEquals(0.2f, color.getGreen(), 0.001f);
        assertEquals(0.2f, color.getBlue(), 0.001f);
        assertEquals(1f, color.getAlpha(), 0.001f);

        color = phong.getSpecular().getColor();
        assertEquals(0.2f, color.getRed(), 0.001f);
        assertEquals(0.2f, color.getGreen(), 0.001f);
        assertEquals(0.2f, color.getBlue(), 0.001f);
        assertEquals(1f, color.getAlpha(), 0.001f);

        color = phong.getReflective().getColor();
        assertEquals(0f, color.getRed(), 0.001f);
        assertEquals(0f, color.getGreen(), 0.001f);
        assertEquals(0f, color.getBlue(), 0.001f);
        assertEquals(1f, color.getAlpha(), 0.001f);

        color = phong.getTransparent().getColor();
        assertEquals(0f, color.getRed(), 0.001f);
        assertEquals(0f, color.getGreen(), 0.001f);
        assertEquals(0f, color.getBlue(), 0.001f);
        assertEquals(1f, color.getAlpha(), 0.001f);

        assertEquals(20f, phong.getShininess(), 0.001f);
        assertEquals(1f, phong.getReflectivity(), 0.001f);
        assertEquals(0f, phong.getTransparency(), 0.001f);

        effect = effects.get("coffee-fx");
        assertNotNull(effect);

        effect = effects.get("ground-fx");
        assertNotNull(effect);

        effect = effects.get("cup-fx");
        assertNotNull(effect);
    }


    /**
     * Tests the library geometries.
     */

    @Test
    public void testLibraryGeometries()
    {
        final LibraryGeometries geometries = collada.getLibraryGeometries();
        assertEquals(1, geometries.size());

        Geometry geometry = geometries.get("invalid");
        assertNull(geometry);

        geometry = geometries.get("cup_3500_polys_photo_and_ground-lib");
        assertNotNull(geometry);
        assertTrue(geometry.isMesh());
        final ColladaMesh mesh = geometry.getMesh();
        assertNotNull(mesh);
        final DataSources sources = mesh.getSources();
        assertEquals(3, sources.size());

        final DataSource source = sources
                .get("cup_3500_polys_photo_and_ground-lib-Position");
        final DataArray data = source.getData();
        assertNotNull(data);
        assertEquals("cup_3500_polys_photo_and_ground-lib-Position-array", data
                .getId());
        assertEquals(6228, data.getCount());
        assertTrue(data.isFloatData());
        final float[] array = data.getFloatData();
        assertEquals(6228, array.length);
        assertEquals(1.091872f, array[0], 0.000001f);
        assertEquals(-120.000005f, array[6227], 0.000001f);

        final Vertices vertices = mesh.getVertices();
        assertNotNull(vertices);
        final UnsharedInputs inputs = vertices.getInputs();
        assertEquals(1, inputs.size());
        assertTrue(inputs.contains(Semantic.POSITION));
        assertFalse(inputs.contains(Semantic.NORMAL));
        final UnsharedInput position = inputs.get(Semantic.POSITION);
        assertNotNull(position);
        assertEquals("cup_3500_polys_photo_and_ground-lib-Position", position
                .getSource().getFragment());

        final PrimitiveGroups groups = mesh.getPrimitiveGroups();
        assertEquals(4, groups.size());
        final Primitives primitives = groups.get(3);
        assertEquals(PrimitivesType.POLYGONS, primitives.getPrimitivesType());
        assertEquals("cup", primitives.getMaterial());
        assertEquals(3698, primitives.getCount());
        final int[][] indices = ((Polygons) primitives).getIndices();
        assertEquals(3698, indices.length);
        assertEquals(9, indices[0].length);
        assertEquals(1138, indices[0][0]);
        assertEquals(-1, indices[0][8]);
        assertEquals(1014, indices[3697][0]);
        assertEquals(12089, indices[3697][7]);
        final SharedInputs polyInputs = primitives.getInputs();
        assertEquals(3, polyInputs.size());
        final SharedInput polyInput = polyInputs.get(Semantic.TEXCOORD);
        assertEquals(Semantic.TEXCOORD, polyInput.getSemantic());
        assertEquals(2, polyInput.getOffset());
        assertEquals(Integer.valueOf(0), polyInput.getSet());
        assertEquals("cup_3500_polys_photo_and_ground-lib-UV0", polyInput
                .getSource().getFragment());
    }


    /**
     * Tests the library lights.
     */

    @Test
    public void testLibraryLights()
    {
        final LibraryLights lights = collada.getLibraryLights();
        assertEquals(3, lights.size());

        ColladaLight light = lights.get("invalid");
        assertNull(light);

        light = lights.get("spotLight");
        assertNotNull(light);
        assertTrue(light instanceof ColladaSpotLight);
        final ColladaSpotLight spotLight = (ColladaSpotLight) light;
        assertEquals(Float.valueOf(45f), spotLight.getFalloffAngle());
        ColladaColor color = light.getColor();
        assertEquals(1f, color.getRed(), 0.001);
        assertEquals(0.5f, color.getGreen(), 0.001);
        assertEquals(0.25f, color.getBlue(), 0.001);
        assertEquals(1f, color.getAlpha(), 0.001);

        light = lights.get("Light-lib");
        assertNotNull(light);
        assertTrue(light instanceof ColladaDirectionalLight);
        color = light.getColor();
        assertEquals(1f, color.getRed(), 0.001);
        assertEquals(1f, color.getGreen(), 0.001);
        assertEquals(1f, color.getBlue(), 0.001);
        assertEquals(1f, color.getAlpha(), 0.001);

        light = lights.get("SceneAmbient-lib");
        assertNotNull(light);
        assertTrue(light instanceof AmbientLight);
        color = light.getColor();
        assertEquals(0.4f, color.getRed(), 0.001);
        assertEquals(0.4f, color.getGreen(), 0.001);
        assertEquals(0.4f, color.getBlue(), 0.001);
        assertEquals(1f, color.getAlpha(), 0.001);
    }


    /**
     * Tests the library cameras.
     */

    @Test
    public void testLibraryCameras()
    {
        final LibraryCameras cameras = collada.getLibraryCameras();
        assertEquals(1, cameras.size());

        ColladaCamera camera = cameras.get("invalid");
        assertNull(camera);

        camera = cameras.get("Camera-lib");
        assertNotNull(camera);
        final Optic optic = camera.getOptic();
        assertNotNull(optic);
        assertTrue(optic instanceof PerspectiveOptic);
        final PerspectiveOptic perspective = (PerspectiveOptic) optic;
        assertEquals(Float.valueOf(45.239729f), perspective.getXfov());
        assertEquals(Float.valueOf(1f), perspective.getAspectRatio());
        assertNull(perspective.getYfov());
        assertEquals(10f, perspective.getZnear(), 0.001f);
        assertEquals(4000f, perspective.getZfar(), 0.001f);
    }


    /**
     * Tests the library visual scenes.
     */

    @Test
    public void testLibraryVisualScenes()
    {
        final LibraryVisualScenes visualScenes = collada
                .getLibraryVisualScenes();
        assertEquals(1, visualScenes.size());

        VisualScene scene = visualScenes.get("invalid");
        assertNull(scene);

        scene = visualScenes.get("RootNode");
        assertNotNull(scene);
        final Nodes nodes = scene.getNodes();
        assertEquals(3, nodes.size());

        Node node = nodes.get(0);
        assertEquals("cup_3500_polys_photo_and_ground", node.getId());
        assertEquals(0, node.getNodes().size());

        final Transformations transformations = node.getTransformations();
        assertEquals(1, transformations.size());
        final Transformation transformation = transformations.get(0);
        assertTrue(transformation instanceof MatrixTransformation);
        final MatrixTransformation matrix = (MatrixTransformation) transformation;
        final float[] values = matrix.getValues();
        assertEquals(1f, values[0], 0.001f);
        assertEquals(0f, values[1], 0.001f);
        assertEquals(0f, values[2], 0.001f);
        assertEquals(0f, values[3], 0.001f);
        assertEquals(0f, values[4], 0.001f);
        assertEquals(1f, values[5], 0.001f);
        assertEquals(0f, values[6], 0.001f);
        assertEquals(0f, values[7], 0.001f);
        assertEquals(0f, values[8], 0.001f);
        assertEquals(0f, values[9], 0.001f);
        assertEquals(1f, values[10], 0.001f);
        assertEquals(0f, values[11], 0.001f);
        assertEquals(0f, values[12], 0.001f);
        assertEquals(0f, values[13], 0.001f);
        assertEquals(0f, values[14], 0.001f);
        assertEquals(1f, values[15], 0.001f);

        final InstanceGeometries geometries = node.getInstanceGeometries();
        assertEquals(1, geometries.size());
        final InstanceGeometry geometry = geometries.get(0);
        assertEquals("cup_3500_polys_photo_and_ground-lib", geometry.getURL()
                .getFragment());

        final InstanceMaterials materials = geometry.getInstanceMaterials();
        assertEquals(4, materials.size());
        final InstanceMaterial material = materials.get(3);
        assertEquals("cup", material.getSymbol());
        assertEquals("cup", material.getTarget().getFragment());

        node = nodes.get("Camera");
        final InstanceCameras cameras = node.getInstanceCameras();
        assertEquals(1, cameras.size());
        final InstanceCamera camera = cameras.get(0);
        assertEquals("Camera-lib", camera.getURL().getFragment());

        node = nodes.get("Light");
        final InstanceLights lights = node.getInstanceLights();
        assertEquals(1, lights.size());
        final InstanceLight light = lights.get(0);
        assertEquals("Light-lib", light.getURL().getFragment());
    }


    /**
     * Tests the scene.
     */

    @Test
    public void testScene()
    {
        final ColladaScene scene = collada.getScene();
        assertNotNull(scene);
        final InstanceVisualScene instance = scene.getInstanceVisualScene();
        assertNotNull(instance);
        assertEquals("RootNode", instance.getURL().getFragment());
    }
}
