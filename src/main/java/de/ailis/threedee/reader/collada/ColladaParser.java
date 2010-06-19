/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.reader.collada;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.ailis.threedee.builder.MaterialBuilder;
import de.ailis.threedee.builder.MeshBuilder;
import de.ailis.threedee.entities.AmbientLight;
import de.ailis.threedee.entities.Asset;
import de.ailis.threedee.entities.Camera;
import de.ailis.threedee.entities.CameraNode;
import de.ailis.threedee.entities.Cameras;
import de.ailis.threedee.entities.DirectionalLight;
import de.ailis.threedee.entities.Image;
import de.ailis.threedee.entities.Images;
import de.ailis.threedee.entities.Light;
import de.ailis.threedee.entities.LightInstance;
import de.ailis.threedee.entities.Lights;
import de.ailis.threedee.entities.Material;
import de.ailis.threedee.entities.Materials;
import de.ailis.threedee.entities.Mesh;
import de.ailis.threedee.entities.MeshInstance;
import de.ailis.threedee.entities.Meshes;
import de.ailis.threedee.entities.PointLight;
import de.ailis.threedee.entities.Scene;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.entities.Scenes;
import de.ailis.threedee.math.Vector3f;
import de.ailis.threedee.model.Color;
import de.ailis.threedee.utils.ChunkFloatReader;
import de.ailis.threedee.utils.ChunkIntReader;


/**
 * ColladaParser
 *
 * @author k
 */

public class ColladaParser extends DefaultHandler
{
    /** Enumeration of parser modes */
    private static enum ParserMode {
        /** Parser is at root level */
        ROOT,

        /** Parser is in COLLADA element */
        COLLADA,

        /** Parser is in lights library */
        LIBRARY_LIGHTS,

        /** Parser is in light element */
        LIGHT,

        /** Parser is in directional element */
        DIRECTIONAL_LIGHT,

        /** Parser is in point light element */
        POINT_LIGHT,

        /** Parser is in point light element */
        AMBIENT_LIGHT,

        /** Parser is in cameras library */
        LIBRARY_CAMERAS,

        /** Parser is in camera element */
        CAMERA,

        /** Parser is in images library */
        LIBRARY_IMAGES,

        /** Parser is in image element */
        IMAGE,

        /** Parser is in an init_from element */
        INIT_FROM,

        /** Parser is in materials library */
        LIBRARY_MATERIALS,

        /** Parser is in effect element */
        MATERIAL,

        /** Parser is in instance_effect element */
        INSTANCE_EFFECT,

        /** Parser is in effects library */
        LIBRARY_EFFECTS,

        /** Parser is in effect element */
        EFFECT,

        /** Parser is in effect emission element */
        EFFECT_EMISSION,

        /** Parser is in effect ambient element */
        EFFECT_AMBIENT,

        /** Parser is in effect diffuse element */
        EFFECT_DIFFUSE,

        /** Parser is in effect specular element */
        EFFECT_SPECULAR,

        /** Parser is in effect shininess element */
        EFFECT_SHININESS,

        /** Parser is in color element */
        COLOR,

        /** Parser is in texture element */
        TEXTURE,

        /** Parser is in geometries library */
        LIBRARY_GEOMETRIES,

        /** Parser is in geometry */
        GEOMETRY,

        /** Parser is in mesh */
        MESH,

        /** Parser is in source */
        SOURCE,

        /** Parser is in float array */
        FLOAT_ARRAY,

        /** Parser is in float value */
        FLOAT,

        /** Parser is in vertices */
        VERTICES,

        /** Parser is in triangles element */
        TRIANGLES,

        /** Parser is in p element of a triangles block */
        TRIANGLES_DATA,

        /** Parser is in polygons element */
        POLYGONS,

        /** Parser is in p element of a polygons block */
        POLYGONS_DATA,

        /** Parser is in polylist */
        POLYLIST,

        /** Parser is in vcount element */
        VCOUNT,

        /** Parser is in p element */
        POLYLIST_DATA,

        /** Parser is in visual scene library */
        LIBRARY_VISUAL_SCENES,

        /** Parser is in visual scene */
        VISUAL_SCENE,

        /** Parser is in a scene node */
        NODE,

        /** Parser is in instance geometry node */
        INSTANCE_GEOMETRY,

        /** Parser is in instance light node */
        INSTANCE_LIGHT,

        /** Parser is in instance camera node */
        INSTANCE_CAMERA,

        /** Parser is in bind_material element */
        BIND_MATERIAL,

        /** Parser is in instance_material element */
        INSTANCE_MATERIAL,

        /** Parser is in scene node */
        SCENE,

        /** Parser is in instance_visual_scene element */
        INSTANCE_VISUAL_SCENE,

        /** Parser is in translate element */
        TRANSLATE,

        /** Parser is in rotate element */
        ROTATE,

        /** Parser is in scale element */
        SCALE,

        /** Parser is in matrix element */
        MATRIX
    }

    /** The current parser mode */
    private ParserMode mode = ParserMode.ROOT;

    /** The previous parser mode */
    private ParserMode prevMode;

    /** The asset */
    private Asset asset;

    /** The meshes */
    private Meshes meshes;

    /** The images */
    private Images images;

    /** The materials */
    private Materials materials;

    /** The lights */
    private Lights lights;

    /** The current image */
    private Image image;

    /** The material builder */
    private final MaterialBuilder materialBuilder = new MaterialBuilder();

    /** The current color */
    private Color color;

    /** The current sources */
    private Map<String, Object> sources;

    /** Current source id */
    private String sourceId;

    /** The current float array */
    float[] floatArray;

    /** The current float array index */
    int floatArrayIndex;

    /** The current light */
    private Light light;

    /** The current light id */
    private String lightId;

    /** The current mesh */
    private Mesh mesh;

    /** The current mesh id */
    private String meshId;

    /** The current vertices id */
    private String verticesId;

    /** The current polygonCount */
    private int polygonCount;

    /** The vertices */
    private Map<String, float[]> vertices;

    /** The vertex normals */
    private Map<String, float[]> normals;

    /** The vertex offset */
    private int vertexOffset;

    /** The vertex data */
    private float[] vertexData;

    /** The texture coordinates data */
    private float[] texCoordData;

    /** The texture coordinates offset */
    private int texCoordOffset;

    /** The normal data */
    private float[] normalData;

    /** The normal offset */
    private int normalOffset;

    /** The vertex count array */
    int[] vcount;

    /** The current vcount index */
    int vcountIndex;

    /** The vertex count array */
    int[] polygonData;

    /** The current vcount index */
    int pIndex;

    /** The visual scenes library */
    private Scenes scenes;

    /** The current visual scene */
    private Scene scene;

    /** The current scene node */
    private SceneNode sceneNode;

    /** The model builder */
    private MeshBuilder meshBuilder;

    /** The current mesh instance */
    private MeshInstance meshInstance;

    /** The reader to read float chunks */
    private ChunkFloatReader chunkFloatReader;

    /** The reader to read int chunks */
    private ChunkIntReader chunkIntReader;

    /** The string builder used to read string chunks */
    private StringBuilder stringBuilder;

    /** The current camera */
    private Camera camera;

    /** The cameras */
    private Cameras cameras;

    /** The block size */
    private int blockSize;

    /** The mapping from material ids to effect ids */
    private final Map<String, String> materialEffectMap = new HashMap<String, String>();

    /** The current material id */
    private String materialId;


    /**
     * @see DefaultHandler#startElement(String, String, String, Attributes)
     */

    @Override
    public void startElement(final String uri, final String localName,
            final String qName, final Attributes attributes)
            throws SAXException
    {
        switch (this.mode)
        {
            case ROOT:
                if (qName.equals("COLLADA")) enterAsset();
                break;

            case COLLADA:
                if (qName.equals("library_images"))
                    enterLibraryImages();
                else if (qName.equals("library_materials"))
                    enterLibraryMaterials();
                else if (qName.equals("library_effects"))
                    enterLibraryEffects();
                else if (qName.equals("library_lights"))
                    enterLibraryLights();
                else if (qName.equals("library_cameras"))
                    enterLibraryCameras();
                else if (qName.equals("library_geometries"))
                    enterLibraryGeometries();
                else if (qName.equals("library_visual_scenes"))
                    enterLibraryVisualScenes();
                else if (qName.equals("scene")) enterScene();
                break;

            case LIBRARY_LIGHTS:
                if (qName.equals("light")) enterLight(attributes);
                break;

            case LIBRARY_CAMERAS:
                if (qName.equals("camera")) enterCamera(attributes);
                break;

            case LIGHT:
                if (qName.equals("directional"))
                    enterDirectionalLight();
                else if (qName.equals("point"))
                    enterPointLight();
                else if (qName.equals("ambient")) enterAmbientLight();
                break;

            case DIRECTIONAL_LIGHT:
            case POINT_LIGHT:
            case AMBIENT_LIGHT:
                if (qName.equals("color")) enterColor();
                break;

            case LIBRARY_IMAGES:
                if (qName.equals("image")) enterImage(attributes);
                break;

            case IMAGE:
                if (qName.equals("init_from")) enterImageInitFrom();
                break;

            case LIBRARY_MATERIALS:
                if (qName.equals("material")) enterMaterial(attributes);
                break;

            case MATERIAL:
                if (qName.equals("instance_effect"))
                    enterInstanceEffect(attributes);
                break;

            case LIBRARY_EFFECTS:
                if (qName.equals("effect")) enterEffect(attributes);
                break;

            case EFFECT:
                if (qName.equals("emission"))
                    enterEffectEmission();
                else if (qName.equals("ambient"))
                    enterEffectAmbient();
                else if (qName.equals("diffuse"))
                    enterEffectDiffuse();
                else if (qName.equals("specular"))
                    enterEffectSpecular();
                else if (qName.equals("shininess")) enterShininess();
                break;

            case EFFECT_EMISSION:
            case EFFECT_AMBIENT:
            case EFFECT_DIFFUSE:
            case EFFECT_SPECULAR:
                if (qName.equals("color")) enterColor();
                if (qName.equals("texture")) enterTexture(attributes);
                break;

            case EFFECT_SHININESS:
                if (qName.equals("float")) enterFloat();
                break;

            case LIBRARY_GEOMETRIES:
                if (qName.equals("geometry")) enterGeometry(attributes);
                break;

            case GEOMETRY:
                if (qName.equals("mesh"))
                    enterMesh();
                else if (qName.equals("convex_mesh"))
                    throw new RuntimeException("convex_mesh not supported");
                break;

            case MESH:
                if (qName.equals("source"))
                    enterSource(attributes);
                else if (qName.equals("vertices"))
                    enterVertices(attributes);
                else if (qName.equals("polylist"))
                    enterPolyList(attributes);
                else if (qName.equals("triangles"))
                    enterTriangles(attributes);
                else if (qName.equals("polygons")) enterPolygons(attributes);
                break;

            case SOURCE:
                if (qName.equals("float_array")) enterFloatArray(attributes);
                break;

            case VERTICES:
                if (qName.equals("input")) processVerticesInput(attributes);
                break;

            case POLYGONS:
                if (qName.equals("input"))
                    processPolygonInput(attributes);
                else if (qName.equals("p")) enterPolygonsData(attributes);
                break;

            case TRIANGLES:
                if (qName.equals("input"))
                    processPolygonInput(attributes);
                else if (qName.equals("p")) enterTrianglesData(attributes);
                break;

            case POLYLIST:
                if (qName.equals("input"))
                    processPolygonInput(attributes);
                else if (qName.equals("vcount"))
                    enterVCount(attributes);
                else if (qName.equals("p")) enterPolyListData(attributes);
                break;

            case LIBRARY_VISUAL_SCENES:
                if (qName.equals("visual_scene")) enterVisualScene(attributes);
                break;

            case VISUAL_SCENE:
                if (qName.equals("node")) enterNode(attributes);
                break;

            case NODE:
                if (qName.equals("instance_geometry"))
                    enterInstanceGeometry(attributes);
                else if (qName.equals("instance_light"))
                    enterInstanceLight(attributes);
                else if (qName.equals("instance_camera"))
                    enterInstanceCamera(attributes);
                else if (qName.equals("translate"))
                    enterTranslate();
                else if (qName.equals("rotate"))
                    enterRotate();
                else if (qName.equals("scale"))
                    enterScale();
                else if (qName.equals("matrix"))
                    enterMatrix();
                else if (qName.equals("node")) enterNode(attributes);
                break;

            case INSTANCE_GEOMETRY:
                if (qName.equals("bind_material")) enterBindMaterial();
                break;

            case BIND_MATERIAL:
                if (qName.equals("instance_material"))
                    enterInstanceMaterial(attributes);
                break;

            case SCENE:
                if (qName.equals("instance_visual_scene"))
                    enterInstanceVisualScene(attributes);
                break;

            default:
                // Ignored
        }
    }


    /**
     * Enters a instance_effect element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceEffect(final Attributes attributes)
    {
        final String effectId = attributes.getValue("url").trim().substring(1);
        this.materialEffectMap.put(this.materialId, effectId);
        this.mode = ParserMode.INSTANCE_EFFECT;
    }


    /**
     * Enters a translate element.
     */

    private void enterTranslate()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.TRANSLATE;
    }


    /**
     * Enters a scale element.
     */

    private void enterScale()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.SCALE;
    }


    /**
     * Enters a matrix element.
     */

    private void enterMatrix()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.MATRIX;
    }


    /**
     * Enters a rotate element.
     */

    private void enterRotate()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.ROTATE;
    }


    /**
     * Enters a instance_light element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceLight(final Attributes attributes)
    {
        final String id = attributes.getValue("url").substring(1);
        final LightInstance light = new LightInstance(this.lights.get(id));
        this.sceneNode.addLight(light);
        this.scene.getRootNode().enableLight(light);
        this.mode = ParserMode.INSTANCE_LIGHT;
    }


    /**
     * Enters a instance_camera element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceCamera(final Attributes attributes)
    {
        final String id = attributes.getValue("url").substring(1);
        final CameraNode node = new CameraNode(this.cameras.get(id));
        this.sceneNode.appendChild(node);
        this.scene.setCameraNode(node);
        this.mode = ParserMode.INSTANCE_CAMERA;
    }


    /**
     * Enters lights library element
     */

    private void enterLibraryLights()
    {
        this.mode = ParserMode.LIBRARY_LIGHTS;
    }


    /**
     * Enters cameras library element
     */

    private void enterLibraryCameras()
    {
        this.mode = ParserMode.LIBRARY_CAMERAS;
    }


    /**
     * Enters a light element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterLight(final Attributes attributes)
    {
        this.lightId = attributes.getValue("id");
        this.mode = ParserMode.LIGHT;
    }


    /**
     * Enters a camera element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterCamera(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.camera = new Camera(id);
        this.mode = ParserMode.CAMERA;
    }


    /**
     * Enters a directional light element.
     */

    private void enterDirectionalLight()
    {
        this.light = new DirectionalLight();
        this.light.setId(this.lightId);
        this.mode = ParserMode.DIRECTIONAL_LIGHT;
    }


    /**
     * Enters a point light element.
     */

    private void enterPointLight()
    {
        this.light = new PointLight();
        this.light.setId(this.lightId);
        this.mode = ParserMode.POINT_LIGHT;
    }


    /**
     * Enters a ambient light element.
     */

    private void enterAmbientLight()
    {
        this.light = new AmbientLight();
        this.light.setId(this.lightId);
        this.mode = ParserMode.AMBIENT_LIGHT;
    }


    /**
     * Enters instance_material element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceMaterial(final Attributes attributes)
    {
        final String symbol = attributes.getValue("symbol");
        final String target = attributes.getValue("target").substring(1);
        final Material material = this.materials.get(this.materialEffectMap
                .get(target));
        this.meshInstance.bindMaterial(symbol, material);
        this.mode = ParserMode.INSTANCE_MATERIAL;
    }


    /**
     * Enters bind_material element.
     */

    private void enterBindMaterial()
    {
        this.mode = ParserMode.BIND_MATERIAL;
    }


    /**
     * @see DefaultHandler#endElement(String, String, String)
     */

    @Override
    public void endElement(final String uri, final String localName,
            final String qName) throws SAXException
    {
        switch (this.mode)
        {
            case COLLADA:
                if (qName.equals("COLLADA")) leaveAsset();
                break;

            case LIBRARY_LIGHTS:
                if (qName.equals("library_lights")) leaveLibraryLights();
                break;

            case LIGHT:
                if (qName.equals("light")) leaveLight();
                break;

            case DIRECTIONAL_LIGHT:
                if (qName.equals("directional")) leaveDirectionalLight();
                break;

            case POINT_LIGHT:
                if (qName.equals("point")) leavePointLight();
                break;

            case AMBIENT_LIGHT:
                if (qName.equals("ambient")) leaveAmbientLight();
                break;

            case LIBRARY_CAMERAS:
                if (qName.equals("library_cameras")) leaveLibraryCameras();
                break;

            case CAMERA:
                if (qName.equals("camera")) leaveCamera();
                break;

            case LIBRARY_IMAGES:
                if (qName.equals("library_images")) leaveLibraryImages();
                break;

            case IMAGE:
                if (qName.equals("image")) leaveImage();
                break;

            case INIT_FROM:
                if (qName.equals("init_from")) leaveImageInitFrom();
                break;

            case TRANSLATE:
                if (qName.equals("translate")) leaveTranslate();
                break;

            case ROTATE:
                if (qName.equals("rotate")) leaveRotate();
                break;

            case SCALE:
                if (qName.equals("scale")) leaveScale();
                break;

            case MATRIX:
                if (qName.equals("matrix")) leaveMatrix();
                break;

            case LIBRARY_MATERIALS:
                if (qName.equals("library_materials")) leaveLibraryMaterials();
                break;

            case MATERIAL:
                if (qName.equals("material")) leaveMaterial();
                break;

            case INSTANCE_EFFECT:
                if (qName.equals("instance_effect")) leaveInstanceEffect();
                break;

            case LIBRARY_EFFECTS:
                if (qName.equals("library_effects")) leaveLibraryEffects();
                break;

            case EFFECT:
                if (qName.equals("effect")) leaveEffect();
                break;

            case EFFECT_EMISSION:
                if (qName.equals("emission")) leaveEffectEmission();
                break;

            case EFFECT_AMBIENT:
                if (qName.equals("ambient")) leaveEffectAmbient();
                break;

            case EFFECT_DIFFUSE:
                if (qName.equals("diffuse")) leaveEffectDiffuse();
                break;

            case EFFECT_SPECULAR:
                if (qName.equals("specular")) leaveEffectSpecular();
                break;

            case EFFECT_SHININESS:
                if (qName.equals("shininess")) leaveShininess();
                break;

            case COLOR:
                if (qName.equals("color")) leaveColor();
                break;

            case TEXTURE:
                if (qName.equals("texture")) leaveTexture();
                break;

            case LIBRARY_GEOMETRIES:
                if (qName.equals("library_geometries"))
                    leaveLibraryGeometries();
                break;

            case GEOMETRY:
                if (qName.equals("geometry")) leaveGeometry();
                break;

            case MESH:
                if (qName.equals("mesh")) leaveMesh();
                break;

            case SOURCE:
                if (qName.equals("source")) leaveSource();
                break;

            case FLOAT_ARRAY:
                if (qName.equals("float_array")) leaveFloatArray();
                break;

            case FLOAT:
                if (qName.equals("float")) leaveFloat();
                break;

            case VERTICES:
                if (qName.equals("vertices")) leaveVertices();
                break;

            case POLYGONS:
                if (qName.equals("polygons")) leavePolygons();
                break;

            case POLYGONS_DATA:
                if (qName.equals("p")) leavePolygonsData();
                break;

            case TRIANGLES:
                if (qName.equals("triangles")) leaveTriangles();
                break;

            case TRIANGLES_DATA:
                if (qName.equals("p")) leaveTrianglesData();
                break;

            case POLYLIST:
                if (qName.equals("polylist")) leavePolyList();
                break;

            case VCOUNT:
                if (qName.equals("vcount")) leaveVCount();
                break;

            case POLYLIST_DATA:
                if (qName.equals("p")) leavePolyListData();
                break;

            case LIBRARY_VISUAL_SCENES:
                if (qName.equals("library_visual_scenes"))
                    leaveLibraryVisualScenes();
                break;

            case VISUAL_SCENE:
                if (qName.equals("visual_scene")) leaveVisualScene();
                break;

            case NODE:
                if (qName.equals("node")) leaveNode();
                break;

            case INSTANCE_GEOMETRY:
                if (qName.equals("instance_geometry")) leaveInstanceGeometry();
                break;

            case INSTANCE_LIGHT:
                if (qName.equals("instance_light")) leaveInstanceLight();
                break;

            case INSTANCE_CAMERA:
                if (qName.equals("instance_camera")) leaveInstanceCamera();
                break;

            case BIND_MATERIAL:
                if (qName.equals("bind_material")) leaveBindMaterial();
                break;

            case INSTANCE_MATERIAL:
                if (qName.equals("instance_material")) leaveInstanceMaterial();
                break;

            case SCENE:
                if (qName.equals("scene")) leaveScene();
                break;

            case INSTANCE_VISUAL_SCENE:
                if (qName.equals("instance_visual_scene"))
                    leaveInstanceVisualScene();
                break;

            default:
                // Ignored
        }
    }


    /**
     * Leaves a translate element
     */

    private void leaveTranslate()
    {
        final String[] parts = this.stringBuilder.toString().trim().split(
                "\\s+");
        this.sceneNode.translate(Float.parseFloat(parts[0]), Float
                .parseFloat(parts[1]), Float.parseFloat(parts[2]));
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a rotate element
     */

    private void leaveRotate()
    {
        final String[] parts = this.stringBuilder.toString().trim().split(
                "\\s+");
        final Vector3f v = new Vector3f(Float.parseFloat(parts[0]), Float
                .parseFloat(parts[1]), Float.parseFloat(parts[2]));
        this.sceneNode.rotate(v, (float) Math.toRadians(Float
                .parseFloat(parts[3])));
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a scale element
     */

    private void leaveScale()
    {
        final String[] parts = this.stringBuilder.toString().trim().split(
                "\\s+");
        this.sceneNode.scale(Float.parseFloat(parts[0]), Float
                .parseFloat(parts[1]), Float.parseFloat(parts[2]));
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a matrix element
     */

    private void leaveMatrix()
    {
        final String[] parts = this.stringBuilder.toString().trim().split(
                "\\s+");
        this.sceneNode.transform(Float.parseFloat(parts[0]), Float
                .parseFloat(parts[4]), Float.parseFloat(parts[8]), Float
                .parseFloat(parts[12]), Float.parseFloat(parts[1]), Float
                .parseFloat(parts[5]), Float.parseFloat(parts[9]), Float
                .parseFloat(parts[13]), Float.parseFloat(parts[2]), Float
                .parseFloat(parts[6]), Float.parseFloat(parts[10]), Float
                .parseFloat(parts[14]), Float.parseFloat(parts[3]), Float
                .parseFloat(parts[7]), Float.parseFloat(parts[11]), Float
                .parseFloat(parts[15]));
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a instance light element.
     */

    private void leaveInstanceLight()
    {
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a instance camera element.
     */

    private void leaveInstanceCamera()
    {
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a directional light element.
     */

    private void leaveDirectionalLight()
    {
        this.light.setColor(this.color);
        this.mode = ParserMode.LIGHT;
    }


    /**
     * Leaves a point light element.
     */

    private void leavePointLight()
    {
        this.light.setColor(this.color);
        this.mode = ParserMode.LIGHT;
    }


    /**
     * Leaves a ambient light element.
     */

    private void leaveAmbientLight()
    {
        this.light.setAmbientColor(this.color);
        this.mode = ParserMode.LIGHT;
    }


    /**
     * Leaves a light element.
     */

    private void leaveLight()
    {
        this.lights.add(this.light);
        this.light = null;
        this.mode = ParserMode.LIBRARY_LIGHTS;
    }


    /**
     * Leaves a camera element.
     */

    private void leaveCamera()
    {
        this.cameras.add(this.camera);
        this.camera = null;
        this.mode = ParserMode.LIBRARY_CAMERAS;
    }


    /**
     * Leaves a library_lights element.
     */

    private void leaveLibraryLights()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Leaves a library_cameras element.
     */

    private void leaveLibraryCameras()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Leaves instance_material element.
     */

    private void leaveInstanceMaterial()
    {
        this.mode = ParserMode.BIND_MATERIAL;
    }


    /**
     * Leaves bind_material element.
     */

    private void leaveBindMaterial()
    {
        this.mode = ParserMode.INSTANCE_GEOMETRY;
    }


    /**
     * Enter the asset.
     */

    private void enterAsset()
    {
        this.asset = new Asset();
        this.meshes = this.asset.getMeshes();
        this.images = this.asset.getImages();
        this.materials = this.asset.getMaterials();
        this.lights = this.asset.getLights();
        this.cameras = this.asset.getCameras();
        this.scenes = this.asset.getScenes();
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Enters the library_images element.
     */

    private void enterLibraryImages()
    {
        this.mode = ParserMode.LIBRARY_IMAGES;
    }


    /**
     * Enters the library_materials element.
     */

    private void enterLibraryMaterials()
    {
        this.mode = ParserMode.LIBRARY_MATERIALS;
    }


    /**
     * Enters an image node.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterImage(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.image = new Image(id);
        this.mode = ParserMode.IMAGE;
    }


    /**
     * Enters a init_from element
     */

    private void enterImageInitFrom()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.INIT_FROM;
    }


    /**
     * Leaves an init_from element
     */

    private void leaveImageInitFrom()
    {
        this.image.setFilename(this.stringBuilder.toString());
        this.stringBuilder = null;
        this.mode = ParserMode.IMAGE;
    }


    /**
     * Leaves a image node.
     */

    private void leaveImage()
    {
        this.images.add(this.image);
        this.image = null;
        this.mode = ParserMode.LIBRARY_IMAGES;
    }


    /**
     * Leaves the library_images element.
     */

    private void leaveLibraryImages()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Enters the library_images element.
     */

    private void enterLibraryEffects()
    {
        this.mode = ParserMode.LIBRARY_EFFECTS;
    }


    /**
     * Enters an effect node.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterEffect(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.materialBuilder.reset().setId(id);
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Enters a material element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterMaterial(final Attributes attributes)
    {
        this.materialId = attributes.getValue("id");
        this.mode = ParserMode.MATERIAL;
    }


    /**
     * Enters an effect emission node.
     */

    private void enterEffectEmission()
    {
        this.mode = ParserMode.EFFECT_EMISSION;
    }


    /**
     * Enters a color node
     */

    private void enterColor()
    {
        this.stringBuilder = new StringBuilder();
        this.prevMode = this.mode;
        this.mode = ParserMode.COLOR;
    }


    /**
     * Enters a texture element.
     *
     * @param attributes
     *            The element attribtues
     */

    private void enterTexture(final Attributes attributes)
    {
        final String texture = attributes.getValue("texture");
        final String filename = this.images.get(texture).getFilename();
        this.materialBuilder.setDiffuseTexture(new File(filename).getName());

        this.prevMode = this.mode;
        this.mode = ParserMode.TEXTURE;
    }


    /**
     * Leaves a texture element.
     */

    private void leaveTexture()
    {
        this.mode = this.prevMode;
    }


    /**
     * Leaves a color node
     */

    private void leaveColor()
    {
        final String[] parts = this.stringBuilder.toString().trim().split(
                "\\s+");
        final float red = Float.parseFloat(parts[0]);
        final float green = Float.parseFloat(parts[1]);
        final float blue = Float.parseFloat(parts[2]);
        final float alpha = parts.length == 4 ? Float.parseFloat(parts[3]) : 1;
        this.color = new Color(red, green, blue, 1.0f);
        this.stringBuilder = null;
        this.mode = this.prevMode;
    }


    /**
     * Leaves an effect emission node.
     */

    private void leaveEffectEmission()
    {
        this.materialBuilder.setEmissionColor(this.color);
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Enters an effect ambient node.
     */

    private void enterEffectAmbient()
    {
        this.mode = ParserMode.EFFECT_AMBIENT;
    }


    /**
     * Leaves an effect ambient node.
     */

    private void leaveEffectAmbient()
    {
        this.materialBuilder.setAmbientColor(this.color);
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Enters an effect diffuse node.
     */

    private void enterEffectDiffuse()
    {
        this.mode = ParserMode.EFFECT_DIFFUSE;
    }


    /**
     * Leaves an effect diffuse node.
     */

    private void leaveEffectDiffuse()
    {
        this.materialBuilder.setDiffuseColor(this.color);
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Enters an effect specular node.
     */

    private void enterEffectSpecular()
    {
        this.mode = ParserMode.EFFECT_SPECULAR;
    }


    /**
     * Leaves an effect specular node.
     */

    private void leaveEffectSpecular()
    {
        this.materialBuilder.setSpecularColor(this.color);
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Enters a shininess node.
     */

    private void enterShininess()
    {
        this.stringBuilder = new StringBuilder();
        this.mode = ParserMode.EFFECT_SHININESS;
    }


    /**
     * Enters float node.
     */

    private void enterFloat()
    {
        this.prevMode = this.mode;
        this.mode = ParserMode.FLOAT;
    }


    /**
     * Leaves float node.
     */

    private void leaveFloat()
    {
        this.mode = this.prevMode;
    }


    /**
     * Leaves shininess node
     */

    private void leaveShininess()
    {
        this.materialBuilder.setShininess(Float.parseFloat(this.stringBuilder
                .toString()));
        this.stringBuilder = null;
        this.mode = ParserMode.EFFECT;
    }


    /**
     * Leaves a effect node.
     */

    private void leaveEffect()
    {
        this.materials.add(this.materialBuilder.build());
        this.mode = ParserMode.LIBRARY_EFFECTS;
    }


    /**
     * Leaves a material node.
     */

    private void leaveMaterial()
    {
        this.mode = ParserMode.LIBRARY_MATERIALS;
    }


    /**
     * Leaves a material node.
     */

    private void leaveInstanceEffect()
    {
        this.mode = ParserMode.MATERIAL;
    }


    /**
     * Leaves the library_images element.
     */

    private void leaveLibraryEffects()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Leaves the library_materials element.
     */

    private void leaveLibraryMaterials()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Enters a library_geometries element.
     */

    private void enterLibraryGeometries()
    {
        this.mode = ParserMode.LIBRARY_GEOMETRIES;
    }


    /**
     * Enters a geometry element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterGeometry(final Attributes attributes)
    {
        this.meshId = attributes.getValue("id");
        this.mode = ParserMode.GEOMETRY;
    }


    /**
     * Enters a mesh element.
     */

    private void enterMesh()
    {
        this.meshBuilder = new MeshBuilder();
        this.vertices = new HashMap<String, float[]>();
        this.normals = new HashMap<String, float[]>();
        this.sources = new HashMap<String, Object>();
        this.mode = ParserMode.MESH;
    }


    /**
     * Enters a source element
     *
     * @param attributes
     *            The element attributes
     */

    private void enterSource(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.sourceId = id;
        this.mode = ParserMode.SOURCE;
    }


    /**
     * Enters a floatArray element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterFloatArray(final Attributes attributes)
    {
        final int count = Integer.parseInt(attributes.getValue("count"));
        this.floatArray = new float[count];
        this.floatArrayIndex = 0;
        this.chunkFloatReader = new ChunkFloatReader()
        {
            @Override
            protected void valueFound(final float value)
            {
                ColladaParser.this.floatArray[ColladaParser.this.floatArrayIndex++] = value;
            }
        };
        this.mode = ParserMode.FLOAT_ARRAY;
    }


    /**
     * Leaves a floatArray element
     */

    private void leaveFloatArray()
    {
        this.chunkFloatReader.finish();
        this.chunkFloatReader = null;
        this.mode = ParserMode.SOURCE;
    }


    /**
     * Leaves source element.
     */

    private void leaveSource()
    {
        this.sources.put(this.sourceId, this.floatArray);
        this.floatArray = null;
        this.sourceId = null;
        this.mode = ParserMode.MESH;
    }


    /**
     * Enters vertices element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterVertices(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.verticesId = id;
        this.mode = ParserMode.VERTICES;
    }


    /**
     * Processes vertices/input element.
     *
     * @param attributes
     *            The element attributes
     */

    private void processVerticesInput(final Attributes attributes)
    {
        final String sourceId = attributes.getValue("source").substring(1);
        final String semantic = attributes.getValue("semantic");
        if (semantic.equals("POSITION"))
        {
            final Object source = this.sources.get(sourceId);
            if (!(source instanceof float[]))
                throw new RuntimeException(
                        "Only support float arrays as vertex input");
            this.vertices.put(this.verticesId, (float[]) source);
        }
        else if (semantic.equals("NORMAL"))
        {
            final Object source = this.sources.get(sourceId);
            if (!(source instanceof float[]))
                throw new RuntimeException(
                        "Only support float arrays as normal input");
            this.normals.put(this.verticesId, (float[]) source);
        }
    }


    /**
     * Leaves vertices element
     */

    private void leaveVertices()
    {
        this.mode = ParserMode.MESH;
    }


    /**
     * Enters a polygons element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterPolygons(final Attributes attributes)
    {
        this.polygonCount = Integer.parseInt(attributes.getValue("count"));
        this.meshBuilder.useMaterial(attributes.getValue("material"));
        this.vertexOffset = -1;
        this.texCoordOffset = -1;
        this.normalOffset = -1;
        this.blockSize = 0;
        this.mode = ParserMode.POLYGONS;
    }


    /**
     * Enters p element of a polygons block.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterPolygonsData(final Attributes attributes)
    {
        if (this.chunkIntReader == null)
        {
            int pCount, inputs = 0;
            if (this.vertexOffset >= 0) inputs++;
            if (this.texCoordOffset >= 0) inputs++;
            if (this.normalOffset >= 0) inputs++;
            pCount = this.polygonCount * 3 * inputs;
            this.polygonData = new int[pCount];
            this.pIndex = 0;
            this.chunkIntReader = new ChunkIntReader()
            {
                @Override
                protected void valueFound(final int value)
                {
                    ColladaParser.this.polygonData[ColladaParser.this.pIndex++] = value;
                }
            };
        }

        this.mode = ParserMode.POLYGONS_DATA;
    }


    /**
     * Leaves P element.
     */

    private void leavePolygonsData()
    {
        this.chunkIntReader.finish();
        this.mode = ParserMode.POLYGONS;
    }


    /**
     * Leaves a polygons element.
     */

    private void leavePolygons()
    {
        this.chunkIntReader.finish();
        this.chunkIntReader = null;

        // Setup some helper variables
        final int vertexOffset = this.vertexOffset;
        final int texCoordOffset = this.texCoordOffset;
        final int normalOffset = this.normalOffset;
        final boolean hasTexCoord = texCoordOffset >= 0;
        final boolean hasNormal = normalOffset >= 0;
        final MeshBuilder builder = this.meshBuilder;
        final int[] polygonData = this.polygonData;
        final float[] vertexData = this.vertexData;
        final float[] texCoordData = this.texCoordData;
        final float[] normalData = this.normalData;

        // Iterate over all polygons
        int i = 0;
        for (int p = 0; p < this.polygonCount; p++)
        {
            final int vcount = 3;

            // Iterate over all vertices of one polygon
            final int[] realVertexIndices = new int[vcount];
            final int[] realTexCoordIndices = hasTexCoord ? new int[vcount]
                    : null;
            final int[] realNormalIndices = hasNormal ? new int[vcount] : null;
            for (int v = 0; v < vcount; v++)
            {
                final int vertexIndex = polygonData[i + vertexOffset];
                final int texCoordIndex = hasTexCoord ? polygonData[i
                        + texCoordOffset] : -1;
                final int normalIndex = hasNormal ? polygonData[i
                        + normalOffset] : -1;

                if (hasTexCoord && texCoordIndex >= 0)
                {
                    final float x = texCoordData[texCoordIndex * 2];
                    final float y = texCoordData[texCoordIndex * 2 + 1];
                    realTexCoordIndices[v] = builder.addTexCoord(x, y);
                }

                if (hasNormal)
                {
                    final float x = normalData[normalIndex * 3];
                    final float y = normalData[normalIndex * 3 + 1];
                    final float z = normalData[normalIndex * 3 + 2];
                    realNormalIndices[v] = builder.addNormal(x, y, z);
                }

                final float x = vertexData[vertexIndex * 3];
                final float y = vertexData[vertexIndex * 3 + 1];
                final float z = vertexData[vertexIndex * 3 + 2];
                realVertexIndices[v] = builder.addVertex(x, y, z);

                // Go to next polygon data block
                i += this.blockSize;
            }

            builder.useTexCoords(realTexCoordIndices);
            builder.useNormals(realNormalIndices);
            builder.addElement(vcount, realVertexIndices);
        }

        this.polygonData = null;
        this.mode = ParserMode.MESH;
    }


    /**
     * Enters a triangles element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterTriangles(final Attributes attributes)
    {
        this.polygonCount = Integer.parseInt(attributes.getValue("count"));
        this.meshBuilder.useMaterial(attributes.getValue("material"));
        this.vertexOffset = -1;
        this.texCoordOffset = -1;
        this.normalOffset = -1;
        this.blockSize = 0;
        this.mode = ParserMode.TRIANGLES;
    }


    /**
     * Enters p element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterTrianglesData(final Attributes attributes)
    {
        int pCount;
        final int inputs = this.blockSize;
        pCount = this.polygonCount * 3 * inputs;
        this.polygonData = new int[pCount];
        this.pIndex = 0;
        this.chunkIntReader = new ChunkIntReader()
        {
            @Override
            protected void valueFound(final int value)
            {
                ColladaParser.this.polygonData[ColladaParser.this.pIndex++] = value;
            }
        };
        this.mode = ParserMode.TRIANGLES_DATA;
    }


    /**
     * Leaves P element.
     */

    private void leaveTrianglesData()
    {
        this.chunkIntReader.finish();
        this.chunkIntReader = null;
        this.mode = ParserMode.TRIANGLES;
    }


    /**
     * Leaves a triangles element.
     */

    private void leaveTriangles()
    {
        // Setup some helper variables
        final int vertexOffset = this.vertexOffset;
        final int texCoordOffset = this.texCoordOffset;
        final int normalOffset = this.normalOffset;
        final boolean hasTexCoord = texCoordOffset >= 0;
        final boolean hasNormal = normalOffset >= 0;
        final MeshBuilder builder = this.meshBuilder;
        final int[] polygonData = this.polygonData;
        final float[] vertexData = this.vertexData;
        final float[] texCoordData = this.texCoordData;
        final float[] normalData = this.normalData;

        // Iterate over all polygons
        int i = 0;
        for (int p = 0; p < this.polygonCount; p++)
        {
            final int vcount = 3;

            // Iterate over all vertices of one polygon
            final int[] realVertexIndices = new int[vcount];
            final int[] realTexCoordIndices = hasTexCoord ? new int[vcount]
                    : null;
            final int[] realNormalIndices = hasNormal ? new int[vcount] : null;
            for (int v = 0; v < vcount; v++)
            {
                final int vertexIndex = polygonData[i + vertexOffset];
                final int texCoordIndex = hasTexCoord ? polygonData[i
                        + texCoordOffset] : -1;
                final int normalIndex = hasNormal ? polygonData[i
                        + normalOffset] : -1;

                if (hasTexCoord)
                {
                    final float x = texCoordData[texCoordIndex * 2];
                    final float y = texCoordData[texCoordIndex * 2 + 1];
                    realTexCoordIndices[v] = builder.addTexCoord(x, y);
                }

                if (hasNormal)
                {
                    final float x = normalData[normalIndex * 3];
                    final float y = normalData[normalIndex * 3 + 1];
                    final float z = normalData[normalIndex * 3 + 2];
                    realNormalIndices[v] = builder.addNormal(x, y, z);
                }

                final float x = vertexData[vertexIndex * 3];
                final float y = vertexData[vertexIndex * 3 + 1];
                final float z = vertexData[vertexIndex * 3 + 2];
                realVertexIndices[v] = builder.addVertex(x, y, z);

                // Go to next polygon data block
                i += this.blockSize;
            }

            builder.useTexCoords(realTexCoordIndices);
            builder.useNormals(realNormalIndices);
            builder.addElement(vcount, realVertexIndices);
        }

        this.polygonData = null;
        this.mode = ParserMode.MESH;
    }


    /**
     * Enters a polylist element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterPolyList(final Attributes attributes)
    {
        this.polygonCount = Integer.parseInt(attributes.getValue("count"));
        this.meshBuilder.useMaterial(attributes.getValue("material"));
        this.vertexOffset = -1;
        this.texCoordOffset = -1;
        this.normalOffset = -1;
        this.blockSize = 0;
        this.mode = ParserMode.POLYLIST;
    }


    /**
     * Processes polylist/input element.
     *
     * @param attributes
     *            The element attributes
     */

    private void processPolygonInput(final Attributes attributes)
    {
        final int offset = Integer.parseInt(attributes.getValue("offset"));
        final String semantic = attributes.getValue("semantic");
        final String sourceId = attributes.getValue("source").substring(1);
        if (semantic.equals("VERTEX"))
        {
            this.vertexData = this.vertices.get(sourceId);
            this.vertexOffset = offset;
            if (!this.normals.isEmpty())
            {
                this.normalData = this.normals.get(sourceId);
                this.normalOffset = offset;
            }
            this.blockSize++;
        }
        else if (semantic.equals("TEXCOORD"))
        {
            this.texCoordData = (float[]) this.sources.get(sourceId);
            this.texCoordOffset = offset;
            this.blockSize++;
        }
        else if (semantic.equals("NORMAL"))
        {
            this.normalData = (float[]) this.sources.get(sourceId);
            this.normalOffset = offset;
            this.blockSize++;
        }
    }


    /**
     * Enters the vcount element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterVCount(final Attributes attributes)
    {
        this.vcount = new int[this.polygonCount];
        this.vcountIndex = 0;
        this.chunkIntReader = new ChunkIntReader()
        {
            @Override
            protected void valueFound(final int value)
            {
                ColladaParser.this.vcount[ColladaParser.this.vcountIndex++] = value;
            }
        };
        this.mode = ParserMode.VCOUNT;
    }


    /**
     * Leaves the vcount element
     */

    private void leaveVCount()
    {
        this.chunkIntReader.finish();
        this.chunkIntReader = null;
        this.mode = ParserMode.POLYLIST;
    }


    /**
     * Enters p element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterPolyListData(final Attributes attributes)
    {
        int pCount = 0, inputs = 0;
        for (int i = this.polygonCount - 1; i >= 0; --i)
            pCount += this.vcount[i];
        if (this.vertexOffset >= 0) inputs++;
        if (this.texCoordOffset >= 0) inputs++;
        if (this.normalOffset >= 0) inputs++;
        pCount *= inputs;
        this.polygonData = new int[pCount];
        this.pIndex = 0;
        this.chunkIntReader = new ChunkIntReader()
        {
            @Override
            protected void valueFound(final int value)
            {
                ColladaParser.this.polygonData[ColladaParser.this.pIndex++] = value;
            }
        };
        this.mode = ParserMode.POLYLIST_DATA;
    }


    /**
     * Leaves P element.
     */

    private void leavePolyListData()
    {
        this.chunkIntReader.finish();
        this.chunkIntReader = null;
        this.mode = ParserMode.POLYLIST;
    }


    /**
     * Leaves a polylist element.
     */

    private void leavePolyList()
    {
        // Setup some helper variables
        final int vertexOffset = this.vertexOffset;
        final int texCoordOffset = this.texCoordOffset;
        final int normalOffset = this.normalOffset;
        final boolean hasTexCoord = texCoordOffset >= 0;
        final boolean hasNormal = normalOffset >= 0;
        final MeshBuilder builder = this.meshBuilder;
        final int[] polygonData = this.polygonData;
        final float[] vertexData = this.vertexData;
        final float[] texCoordData = this.texCoordData;
        final float[] normalData = this.normalData;

        // Iterate over all polygons
        int i = 0;
        for (int p = 0; p < this.polygonCount; p++)
        {
            final int vcount = this.vcount[p];

            // Iterate over all vertices of one polygon
            final int[] realVertexIndices = new int[vcount];
            final int[] realTexCoordIndices = hasTexCoord ? new int[vcount]
                    : null;
            final int[] realNormalIndices = hasNormal ? new int[vcount] : null;
            for (int v = 0; v < vcount; v++)
            {
                final int vertexIndex = polygonData[i + vertexOffset];
                final int texCoordIndex = hasTexCoord ? polygonData[i
                        + texCoordOffset] : -1;
                final int normalIndex = hasNormal ? polygonData[i
                        + normalOffset] : -1;

                if (hasTexCoord)
                {
                    final float x = texCoordData[texCoordIndex * 2];
                    final float y = texCoordData[texCoordIndex * 2 + 1];
                    realTexCoordIndices[v] = builder.addTexCoord(x, y);
                }

                if (hasNormal)
                {
                    final float x = normalData[normalIndex * 3];
                    final float y = normalData[normalIndex * 3 + 1];
                    final float z = normalData[normalIndex * 3 + 2];
                    realNormalIndices[v] = builder.addNormal(x, y, z);
                }

                final float x = vertexData[vertexIndex * 3];
                final float y = vertexData[vertexIndex * 3 + 1];
                final float z = vertexData[vertexIndex * 3 + 2];
                realVertexIndices[v] = builder.addVertex(x, y, z);

                // Go to next polygon data block
                i += this.blockSize;
            }

            if (vcount < 4)
            {
                builder.useTexCoords(realTexCoordIndices);
                builder.useNormals(realNormalIndices);
                builder.addElement(vcount, realVertexIndices);
            }
            else
            {
                final int triangles = vcount - 2;
                for (int t = 0; t < triangles; t++)
                {
                    if (hasTexCoord)
                        builder.useTexCoords(realTexCoordIndices[0],
                                realTexCoordIndices[1 + t],
                                realTexCoordIndices[2 + t]);
                    if (hasNormal)
                        builder.useNormals(realNormalIndices[0],
                                realNormalIndices[1 + t],
                                realNormalIndices[2 + t]);
                    builder.addElement(3, realVertexIndices[0],
                            realVertexIndices[1 + t], realVertexIndices[2 + t]);
                }
            }
        }

        this.vcount = null;
        this.polygonData = null;
        this.mode = ParserMode.MESH;
    }


    /**
     * Leaves a mesh element.
     */

    private void leaveMesh()
    {
        this.mesh = this.meshBuilder.build(this.meshId);
        this.meshBuilder = null;
        this.sources = null;
        this.mode = ParserMode.GEOMETRY;
    }


    /**
     * Leaves a geometry element.
     */

    private void leaveGeometry()
    {
        this.meshes.add(this.mesh);
        this.mesh = null;
        this.mode = ParserMode.LIBRARY_GEOMETRIES;
    }


    /**
     * Leaves a library_geometries element.
     */

    private void leaveLibraryGeometries()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Enters a library_visual_scene element.
     */

    private void enterLibraryVisualScenes()
    {
        this.mode = ParserMode.LIBRARY_VISUAL_SCENES;
    }


    /**
     * Enters a visual_scene element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterVisualScene(final Attributes attributes)
    {
        final String id = attributes.getValue("id");
        this.scene = new Scene(id);
        this.mode = ParserMode.VISUAL_SCENE;
    }


    /**
     * Enters a visual_scene element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterNode(final Attributes attributes)
    {
        final SceneNode parentNode = this.sceneNode;
        final String id = attributes.getValue("id");
        final SceneNode sceneNode = new SceneNode(id);
        if (parentNode == null)
            this.scene.getRootNode().appendChild(sceneNode);
        else
            parentNode.appendChild(sceneNode);
        this.sceneNode = sceneNode;
        this.mode = ParserMode.NODE;
    }


    /**
     * Enters a instance_geometry element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceGeometry(final Attributes attributes)
    {
        final String id = attributes.getValue("url").substring(1);
        final MeshInstance mesh = new MeshInstance(this.meshes.get(id));
        this.sceneNode.addMesh(mesh);
        this.meshInstance = mesh;
        this.mode = ParserMode.INSTANCE_GEOMETRY;
    }


    /**
     * Leaves a instance_geometry element.
     */

    private void leaveInstanceGeometry()
    {
        this.meshInstance = null;
        this.mode = ParserMode.NODE;
    }


    /**
     * Leaves a node element.
     */

    private void leaveNode()
    {
        final SceneNode parentNode = this.sceneNode.getParentNode();
        if (parentNode == this.scene.getRootNode())
            this.mode = ParserMode.VISUAL_SCENE;
        else
            this.mode = ParserMode.NODE;
        this.sceneNode = parentNode;
    }


    /**
     * Leaves a visual_scene element.
     */

    private void leaveVisualScene()
    {
        // When there are no lights in the scene then add a default one
        if (this.lights.isEmpty())
        {
            final Light light = new DirectionalLight(Color.WHITE);
            this.lights.add(light);
            final LightInstance lightInstance = new LightInstance(light);
            final SceneNode rootNode = this.scene.getRootNode();
            rootNode.addLight(lightInstance);
            rootNode.enableLight(lightInstance);
        }

        // When there are no cameras in the scene then add a default one
        if (this.cameras.isEmpty())
        {
            final Camera camera = new Camera();
            this.cameras.add(camera);
            final CameraNode cameraNode = new CameraNode(camera);
            cameraNode.translateZ(10);
            final SceneNode rootNode = this.scene.getRootNode();
            rootNode.appendChild(cameraNode);
            this.scene.setCameraNode(cameraNode);
        }

        this.scenes.add(this.scene);
        this.scene = null;
        this.mode = ParserMode.LIBRARY_VISUAL_SCENES;
    }


    /**
     * Leaves a library_visual_scenes element.
     */

    private void leaveLibraryVisualScenes()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Enters a scene element.
     */

    private void enterScene()
    {
        this.mode = ParserMode.SCENE;
    }


    /**
     * Enters a instance_visual_scene element.
     *
     * @param attributes
     *            The element attributes
     */

    private void enterInstanceVisualScene(final Attributes attributes)
    {
        final String id = attributes.getValue("url").substring(1);
        this.asset.setActiveScene(this.scenes.get(id));
        this.mode = ParserMode.INSTANCE_VISUAL_SCENE;
    }


    /**
     * Leaves a library_visual_scenes element.
     */

    private void leaveInstanceVisualScene()
    {
        this.mode = ParserMode.SCENE;
    }


    /**
     * Leaves a scene element.
     */

    private void leaveScene()
    {
        this.mode = ParserMode.COLLADA;
    }


    /**
     * Leaves the asset element.
     */

    private void leaveAsset()
    {
        this.mode = ParserMode.ROOT;
    }

    /**
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */

    @Override
    public void characters(final char[] ch, final int start, final int length)
            throws SAXException
    {
        switch (this.mode)
        {
            case FLOAT:
            case COLOR:
            case INIT_FROM:
            case TRANSLATE:
            case ROTATE:
            case SCALE:
            case MATRIX:
                this.stringBuilder.append(ch, start, length);
                break;

            case VCOUNT:
            case POLYLIST_DATA:
            case TRIANGLES_DATA:
            case POLYGONS_DATA:
                this.chunkIntReader.addChunk(ch, start, length);
                break;

            case FLOAT_ARRAY:
                this.chunkFloatReader.addChunk(ch, start, length);
                break;

            default:
                // Ignored
        }
    }


    /**
     * Returns the parsed asset
     *
     * @return The parsed asset
     */

    public Asset getAsset()
    {
        return this.asset;
    }
}
