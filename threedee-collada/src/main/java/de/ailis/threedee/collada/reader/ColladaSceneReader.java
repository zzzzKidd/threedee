/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.builder.MaterialBuilder;
import de.ailis.threedee.builder.MeshBuilder;
import de.ailis.threedee.collada.entities.Blinn;
import de.ailis.threedee.collada.entities.COLLADA;
import de.ailis.threedee.collada.entities.Channel;
import de.ailis.threedee.collada.entities.Channels;
import de.ailis.threedee.collada.entities.ColladaAmbientLight;
import de.ailis.threedee.collada.entities.ColladaAnimation;
import de.ailis.threedee.collada.entities.ColladaCamera;
import de.ailis.threedee.collada.entities.ColladaColor;
import de.ailis.threedee.collada.entities.ColladaDirectionalLight;
import de.ailis.threedee.collada.entities.ColladaLight;
import de.ailis.threedee.collada.entities.ColladaMaterial;
import de.ailis.threedee.collada.entities.ColladaMesh;
import de.ailis.threedee.collada.entities.ColladaPointLight;
import de.ailis.threedee.collada.entities.ColladaSampler;
import de.ailis.threedee.collada.entities.ColladaScene;
import de.ailis.threedee.collada.entities.ColladaSpotLight;
import de.ailis.threedee.collada.entities.ColladaTexture;
import de.ailis.threedee.collada.entities.CommonProfile;
import de.ailis.threedee.collada.entities.CommonTechnique;
import de.ailis.threedee.collada.entities.DataSource;
import de.ailis.threedee.collada.entities.DataSources;
import de.ailis.threedee.collada.entities.Effect;
import de.ailis.threedee.collada.entities.GeometricElement;
import de.ailis.threedee.collada.entities.Geometry;
import de.ailis.threedee.collada.entities.InstanceCamera;
import de.ailis.threedee.collada.entities.InstanceGeometry;
import de.ailis.threedee.collada.entities.InstanceLight;
import de.ailis.threedee.collada.entities.InstanceMaterial;
import de.ailis.threedee.collada.entities.InstanceVisualScene;
import de.ailis.threedee.collada.entities.LibraryAnimations;
import de.ailis.threedee.collada.entities.LibraryVisualScenes;
import de.ailis.threedee.collada.entities.MatrixTransformation;
import de.ailis.threedee.collada.entities.Node;
import de.ailis.threedee.collada.entities.Optic;
import de.ailis.threedee.collada.entities.PerspectiveOptic;
import de.ailis.threedee.collada.entities.Phong;
import de.ailis.threedee.collada.entities.Polygons;
import de.ailis.threedee.collada.entities.Primitives;
import de.ailis.threedee.collada.entities.Profile;
import de.ailis.threedee.collada.entities.Semantic;
import de.ailis.threedee.collada.entities.Shading;
import de.ailis.threedee.collada.entities.SharedInput;
import de.ailis.threedee.collada.entities.SharedInputs;
import de.ailis.threedee.collada.entities.Transformation;
import de.ailis.threedee.collada.entities.TranslateTransformation;
import de.ailis.threedee.collada.entities.Triangles;
import de.ailis.threedee.collada.entities.UnsharedInputs;
import de.ailis.threedee.collada.entities.Vertices;
import de.ailis.threedee.collada.entities.VisualScene;
import de.ailis.threedee.collada.entities.profileparams.Sampler2DParam;
import de.ailis.threedee.collada.entities.profileparams.SurfaceParam;
import de.ailis.threedee.collada.parser.ColladaParser;
import de.ailis.threedee.exceptions.ParserException;
import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.SceneReader;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.sampling.Interpolation;
import de.ailis.threedee.sampling.Sampler;
import de.ailis.threedee.sampling.SamplerValue;
import de.ailis.threedee.scene.Camera;
import de.ailis.threedee.scene.Color;
import de.ailis.threedee.scene.Group;
import de.ailis.threedee.scene.Light;
import de.ailis.threedee.scene.Model;
import de.ailis.threedee.scene.Scene;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.scene.animation.Animation;
import de.ailis.threedee.scene.animation.AnimationGroup;
import de.ailis.threedee.scene.animation.TransformAnimation;
import de.ailis.threedee.scene.lights.AmbientLight;
import de.ailis.threedee.scene.lights.DirectionalLight;
import de.ailis.threedee.scene.lights.PointLight;
import de.ailis.threedee.scene.lights.SpotLight;
import de.ailis.threedee.scene.model.Material;
import de.ailis.threedee.scene.model.Mesh;
import de.ailis.threedee.scene.textures.ImageTexture;


/**
 * Scene reader for collada files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaSceneReader extends SceneReader
{
    /** The collada document which is currently processed */
    private COLLADA collada;

    /** The mesh cache */
    private Map<String, Mesh> meshes;

    /** Used textures */
    private Map<String, ImageTexture> textures;

    /** The scene */
    private Scene scene;


    /**
     * Constructs a new Collada scene reader.
     *
     * @param resourceProvider
     *            The resource provider
     */

    public ColladaSceneReader(final ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }


    /**
     * @see de.ailis.threedee.io.SceneReader#read(java.io.InputStream)
     */

    @Override
    public Scene read(final InputStream stream) throws IOException
    {
        this.collada = new ColladaParser().parse(stream);
        this.meshes = new HashMap<String, Mesh>();
        try
        {
            return buildScene();
        }
        finally
        {
            this.collada = null;
            this.meshes = null;
        }
    }


    /**
     * Builds a scene from the specified collada document.
     *
     * @return The scene
     */

    private Scene buildScene()
    {
        // If active scene is set then return this one
        final ColladaScene colladaScene = this.collada.getScene();
        if (colladaScene != null)
        {
            final InstanceVisualScene sceneInstance = colladaScene
                    .getInstanceVisualScene();
            if (sceneInstance != null)
                return buildScene(this.collada.getLibraryVisualScenes().get(
                        sceneInstance.getURL().getFragment()));
        }

        // No active scene set, so return the first scene if there is one
        final LibraryVisualScenes scenes = this.collada
                .getLibraryVisualScenes();
        if (scenes.size() > 0) return buildScene(scenes.get(0));

        throw new ReaderException("No scene found in collada document");
    }


    /**
     * Builds a ThreeDee scene from the specified Collada visual scene document.
     *
     * @param visualScene
     *            The Collada visual scene
     * @return The ThreeDee scene
     */

    private Scene buildScene(final VisualScene visualScene)
    {
        final Scene scene = new Scene();
        this.scene = scene;
        final SceneNode rootNode = scene.getRootNode();
        for (final Node node : visualScene.getNodes())
        {
            appendNode(rootNode, node);
        }

        // Process animations
        processAnimations(scene);

        return scene;
    }


    /**
     * Appends a Collada node to a ThreeDee scene node.
     *
     * @param parentNode
     *            The ThreeDee parent scene node
     * @param node
     *            The Collada node
     */

    private void appendNode(final SceneNode parentNode, final Node node)
    {
        // Create the ThreeDee scene node and append it to the parent node
        final SceneNode sceneNode = new Group();
        sceneNode.setId(node.getId());
        parentNode.appendChild(sceneNode);

        // Transform the scene node
        for (final Transformation transformation : node.getTransformations())
        {
            if (transformation instanceof MatrixTransformation)
            {
                final float[] m = ((MatrixTransformation) transformation)
                        .getValues();
                sceneNode.transform(m[0], m[4], m[8], m[12], m[1], m[5], m[9],
                        m[13], m[2], m[6], m[10], m[14], m[3], m[7], m[11],
                        m[15]);
            }
            else if (transformation instanceof TranslateTransformation)
            {
                final float[] m = ((TranslateTransformation) transformation)
                        .getValues();
                sceneNode.translate(m[0], m[1], m[2]);
            }
        }

        // Process the meshes
        for (final InstanceGeometry instanceGeometry : node
                .getInstanceGeometries())
        {
            this.textures = new HashMap<String, ImageTexture>();
            final Geometry geometry = this.collada.getLibraryGeometries().get(
                    instanceGeometry.getURL().getFragment());
            final Mesh mesh = buildMesh(geometry);
            final Model model = new Model(mesh);
            for (final InstanceMaterial instanceMaterial : instanceGeometry
                    .getInstanceMaterials())
            {
                model.bindMaterial(instanceMaterial.getSymbol(),
                        buildMaterial(instanceMaterial));
            }
            for (final Map.Entry<String, ImageTexture> entry : this.textures
                .entrySet())
            {
                model.bindTexture(entry.getKey(), entry.getValue());
            }
            sceneNode.appendChild(model);
        }

        // Process the lights
        for (final InstanceLight instanceLight : node.getInstanceLights())
        {
            final ColladaLight colladaLight = this.collada.getLibraryLights()
                    .get(instanceLight.getURL().getFragment());
            final Light light = buildLight(colladaLight);
            sceneNode.appendChild(light);
            this.scene.getRootNode().addLight(light);
        }

        // Process the cameras
        for (final InstanceCamera instanceCamera : node.getInstanceCameras())
        {
            final ColladaCamera colladaCamera = this.collada
                    .getLibraryCameras().get(
                            instanceCamera.getURL().getFragment());
            final Camera camera = buildCamera(colladaCamera);
            sceneNode.appendChild(camera);
            this.scene.setCameraNode(camera);
        }

        // Process child nodes
        for (final Node childNode : node.getNodes())
            appendNode(sceneNode, childNode);
    }


    /**
     * Processes all animations and adds them to the specified scene.
     *
     * @param scene
     *            The scene to add the animations to
     */

    private void processAnimations(final Scene scene)
    {
        final LibraryAnimations animations = this.collada
            .getLibraryAnimations();
        for (final ColladaAnimation animation : animations)
        {
            final Animation anim = processAnimation(animation);
            if (anim != null) scene.addAnimation(anim);
        }
    }


    /**
     * Converts a Collada animation into a ThreeDee animation.
     *
     * @param colladaAnimation
     *            The Collada animation
     * @return The ThreeDee animation
     */

    private Animation processAnimation(final ColladaAnimation colladaAnimation)
    {
        final Channels channels = colladaAnimation.getChannels();
        final Animation group = new AnimationGroup(colladaAnimation.getId());
        for (final Channel channel : channels)
        {
            final URI uri = channel.getSource();
            final String target = channel.getTarget();
            final SceneNode node = getNodeByTarget(this.scene, target);
            final String animTarget = getAnimationTarget(target);
            final ColladaSampler colladaSampler = colladaAnimation
                .getSamplers().get(uri.getFragment());
            final UnsharedInputs inputs = colladaSampler.getInputs();
            final DataSources sources = colladaAnimation.getSources();

            if (animTarget.equals("matrix"))
            {
                final String inputId = inputs.get(Semantic.INPUT).getSource()
                    .getFragment();
                final String outputId = inputs.get(Semantic.OUTPUT).getSource()
                    .getFragment();
                final String interpolationId = inputs
                    .get(Semantic.INTERPOLATION).getSource().getFragment();
                final float[] inputData = sources.get(inputId).getData()
                    .getFloatData();
                if (inputData.length > 0)
                {
                    final Matrix4f[] outputData = getMatrixData(sources
                        .get(outputId).getData().getFloatData());
                    final String[] interpolationData = sources
                        .get(interpolationId)
                        .getData().getStringData();

                    final Sampler<Matrix4f> sampler = new Sampler<Matrix4f>();
                    for (int i = 0, max = inputData.length; i < max; i++)
                    {
                        sampler
                            .addSample(inputData[i], new SamplerValue<Matrix4f>(
                                outputData[i], Interpolation
                                    .valueOf(interpolationData[i])));
                    }

                    final Animation animation = new TransformAnimation(
                        colladaAnimation.getId(), sampler);
                    animation.addNode(node);

                    group.addAnimation(animation);
                }
            }
            else
            {
                throw new ParserException("Unknown animation target: "
                    + animTarget);
            }
        }

        // Process sub animations
        for (final ColladaAnimation subAnim : colladaAnimation
            .getAnimations())
            group.addAnimation(processAnimation(subAnim));

        return group;
    }

    /**
     * Creates a matrix array from the specified float array.
     *
     * @param values
     *            The matrix values (16 per matrix)
     * @return The matrix array
     */

    private Matrix4f[] getMatrixData(final float[] values)
    {
        final int max = values.length / 16;
        final Matrix4f[] matrixData = new Matrix4f[max];
        for (int i = 0; i < max; i++)
            matrixData[i] = new Matrix4f(values, i * 16).transpose();
        return matrixData;
    }


    /**
     * Extracts the animation target from the target string.
     *
     * @param target
     *            The target string
     * @return The animation target
     */

    private String getAnimationTarget(final String target)
    {
        final String[] parts = target.split("/", 2);
        return parts[1];
    }


    /**
     * Returns the targeted node.
     *
     * @param scene
     *            The scene
     * @param target
     *            The target
     * @return The node. Never null
     */

    private SceneNode getNodeByTarget(final Scene scene, final String target)
    {
        final String[] parts = target.split("/", 2);
        final SceneNode node = scene.getNodeById(parts[0]);
        if (node == null)
            throw new ParserException("Can't find node for target " + target);
        return node;
    }


    /**
     * Builds a ThreeDee material from a Collada material.
     *
     * @param instanceMaterial
     *            The Collada instance material
     * @return The ThreeDee material
     */

    private Material buildMaterial(final InstanceMaterial instanceMaterial)
    {
        // if (1==1) return Material.DEFAULT;
        final ColladaMaterial colladaMaterial = this.collada
                .getLibraryMaterials().get(
                        instanceMaterial.getTarget().getFragment());
        final Effect effect = this.collada.getLibraryEffects().get(
                colladaMaterial.getEffectURI().getFragment());
        final CommonProfile profile = effect.getProfiles().getCommonProfile();
        final CommonTechnique technique = profile.getTechniques().get(0);
        final Phong phong = technique.getPhong();
        final Blinn blinn = technique.getBlinn();
        return buildMaterial(profile, phong == null ? blinn : phong);
    }


    /**
     * Builds a ThreeDee material from Collada shading information.
     *
     * @param profile
     *            The effect profile
     * @param shading
     *            The Collada shading information
     * @return The ThreeDee material
     */

    private Material buildMaterial(final Profile profile, final Shading shading)
    {
        final MaterialBuilder builder = new MaterialBuilder();
        if (shading.getAmbient().isColor())
            builder
                    .setAmbientColor(buildColor(shading.getAmbient().getColor()));
        if (shading.getEmission().isColor())
            builder.setEmissionColor(buildColor(shading.getEmission()
                    .getColor()));
        if (shading.getSpecular().isColor())
            builder.setSpecularColor(buildColor(shading.getSpecular()
                    .getColor()));
        if (shading.getDiffuse().isColor())
            builder
                    .setDiffuseColor(buildColor(shading.getDiffuse().getColor()));
        if (shading.getDiffuse().isTexture())
        {
            final ColladaTexture colladaTexture = shading.getDiffuse()
                .getTexture();
            final ImageTexture texture = buildTexture(profile, colladaTexture);
            final String textureName = colladaTexture.getTexture();
            this.textures.put(textureName, texture);
            builder.setDiffuseTexture(textureName);
        }
        final Float shininess = shading.getShininess();
        if (shininess != null)
            builder.setShininess(shininess);
        return builder.build();
    }


    /**
     * Bulds a ThreeDee texture from a Collada texture.
     *
     * @param profile
     *            The effect profile
     * @param texture
     *            The collada Texture
     * @return The ThreeDee texture
     */

    private ImageTexture buildTexture(final Profile profile,
        final ColladaTexture texture)
    {
        String id = texture.getTexture();
        final Sampler2DParam sampler = (Sampler2DParam) profile.getParams()
            .get(id);
        if (sampler != null)
        {
            final SurfaceParam surface = (SurfaceParam) profile.getParams()
                .get(sampler.getSource());
            id = surface.getImageId();
        }
        return new ImageTexture(this.collada.getLibraryImages()
            .get(id).getURI().getPath());
    }


    /**
     * Builds a ThreeDee color from a Collada color.
     *
     * @param colladaColor
     *            The Collada color
     * @return The ThreeDee color
     */

    private Color buildColor(final ColladaColor colladaColor)
    {
        return new Color(colladaColor.getRed(), colladaColor.getGreen(),
                colladaColor.getBlue(), colladaColor.getAlpha());
    }


    /**
     * Builds a ThreeDee camera from a Collada camera.
     *
     * @param colladaCamera
     *            The Collada camera
     * @return The ThreeDee camera
     */

    private Camera buildCamera(final ColladaCamera colladaCamera)
    {
        final Optic optic = colladaCamera.getOptic();
        if (optic instanceof PerspectiveOptic)
        {
            final PerspectiveOptic pOptic = (PerspectiveOptic) optic;
            final Float fovX = pOptic.getXfov();
            Float fovY = pOptic.getYfov();
            final Float ar = pOptic.getAspectRatio();
            if (fovY == null)
            {
                if (fovX == null || ar == null)
                    throw new ReaderException(
                            "Unable to calculate fovY because fovX or aspect ratio is missing");
                fovY = fovX / ar;
            }
            return new Camera(fovY, pOptic.getZnear(), pOptic.getZfar());
        }
        throw new ReaderException("Unsupported camera optic: "
                + optic.getClass());
    }


    /**
     * Builds a ThreeDee light from a Colladalight.
     *
     * @param colladaLight
     *            The Collada light
     * @return The ThreeDee light
     */

    private Light buildLight(final ColladaLight colladaLight)
    {
        if (colladaLight instanceof ColladaPointLight)
            return buildPointLight((ColladaPointLight) colladaLight);
        if (colladaLight instanceof ColladaDirectionalLight)
            return buildDirectionalLight((ColladaDirectionalLight) colladaLight);
        if (colladaLight instanceof ColladaSpotLight)
            return buildSpotLight((ColladaSpotLight) colladaLight);
        if (colladaLight instanceof ColladaAmbientLight)
            return buildAmbientLight((ColladaAmbientLight) colladaLight);
        throw new ReaderException("Unknown Collada light type "
                + colladaLight.getClass());
    }


    /**
     * Builds a ThreeDee point light from a Collada point light.
     *
     * @param colladaLight
     *            The Collada point light
     * @return The ThreeDee point light
     */

    private PointLight buildPointLight(final ColladaPointLight colladaLight)
    {
        final ColladaColor color = colladaLight.getColor();
        return new PointLight(new Color(color.getRed(), color.getGreen(), color
                .getBlue()));
    }


    /**
     * Builds a ThreeDee ambient light from a Collada ambient light.
     *
     * @param colladaLight
     *            The Collada ambient light
     * @return The ThreeDee ambient light
     */

    private AmbientLight buildAmbientLight(
        final ColladaAmbientLight colladaLight)
    {
        final ColladaColor color = colladaLight.getColor();
        return new AmbientLight(new Color(color.getRed(), color.getGreen(),
            color
                .getBlue()));
    }


    /**
     * Builds a ThreeDee directional light from a Collada directional light.
     *
     * @param colladaLight
     *            The Collada directional light
     * @return The ThreeDee directional light
     */

    private DirectionalLight buildDirectionalLight(
            final ColladaDirectionalLight colladaLight)
    {
        final ColladaColor color = colladaLight.getColor();
        return new DirectionalLight(new Color(color.getRed(), color.getGreen(),
                color.getBlue()));
    }


    /**
     * Builds a ThreeDee spot light from a Collada spot light.
     *
     * @param colladaLight
     *            The Collada point light
     * @return The ThreeDee point light
     */

    private SpotLight buildSpotLight(final ColladaSpotLight colladaLight)
    {
        final ColladaColor color = colladaLight.getColor();
        final SpotLight spotLight = new SpotLight(new Color(color.getRed(),
            color.getGreen(), color
                .getBlue()));
        final Float falloffAngle = colladaLight.getFalloffAngle();
        if (falloffAngle != null) spotLight.setCutOff(falloffAngle / 2);
        return spotLight;
    }


    /**
     * Builds a ThreeDee mesh from the specified Collada geometry and returns
     * it.
     *
     * @param geometry
     *            The Collada geometry
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final Geometry geometry)
    {
        // Try to read mesh from cache and use this one if found
        final String id = geometry.getId();
        Mesh mesh = this.meshes.get(id);
        if (mesh != null) return mesh;

        // Build the mesh
        mesh = buildMesh(geometry.getGeometricElement());

        // Put mesh into cache and return it
        this.meshes.put(id, mesh);
        return mesh;
    }


    /**
     * Builds a ThreeDee mesh from the specified Collada geometric element.
     *
     * @param element
     *            The Collada geometric element
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final GeometricElement element)
    {
        if (element instanceof ColladaMesh)
            return buildMesh((ColladaMesh) element);

        throw new ReaderException("Unknown geometric element type: "
                + element.getClass());
    }


    /**
     * Builds a ThreeDee mesh from the specified Collada mesh.
     *
     * @param mesh
     *            The Collada mesh
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final ColladaMesh mesh)
    {
        final MeshBuilder builder = new MeshBuilder();
        for (final Primitives primitives : mesh.getPrimitiveGroups())
        {
            processPrimitives(builder, mesh, primitives);
        }

        return builder.build();
    }


    /**
     * Processes primitives.
     *
     * @param builder
     *            The mesh builder
     * @param mesh
     *            The collada mesh
     * @param primitives
     *            The primitives to process
     */

    private void processPrimitives(final MeshBuilder builder,
            final ColladaMesh mesh, final Primitives primitives)
    {
        builder.useMaterial(primitives.getMaterial());
        if (primitives instanceof Polygons)
        {
            processPolygons(builder, mesh, (Polygons) primitives);
        }
        else if (primitives instanceof Triangles)
        {
            processTriangles(builder, mesh, (Triangles) primitives);
        }
        else
            throw new ReaderException("Unknown primitives type: "
                    + primitives.getClass());
    }


    /**
     * Processes polygons.
     *
     * @param builder
     *            The mesh builder
     * @param mesh
     *            The collada mesh
     * @param polygons
     *            The polygons to process
     */

    private void processPolygons(final MeshBuilder builder,
            final ColladaMesh mesh, final Polygons polygons)
    {
        // Process all the data inputs
        processSources(builder, mesh, polygons.getInputs());

        // Get block size of the indices array
        final int blockSize = getBlockSize(mesh, polygons);

        // Get the offsets into the indices array
        final int vertexOffset = getOffset(polygons, Semantic.VERTEX);
        final int normalOffset = getOffset(polygons, Semantic.NORMAL);
        final int texCoordOffset = getOffset(polygons, Semantic.TEXCOORD);

        // Check what we have
        final boolean hasNormals = normalOffset != -1;
        final boolean hasTexCoords = texCoordOffset != -1;

        // Process indices
        final int[][] indices = polygons.getIndices();
        for (int p = 0, count = polygons.getCount(); p < count; p++)
        {
            final int[] pIndices = indices[p];
            final int size = pIndices.length / blockSize;

            final int[] vertices = new int[size];
            final int[] normals = hasNormals ? new int[size] : null;
            final int[] texCoords = hasTexCoords ? new int[size] : null;

            // Check if normals/texCoords are used (this state can be
            // disabled during polygon processing when an illegal
            // index was found)
            boolean useNormals = hasNormals;
            boolean useTexCoords = hasTexCoords;

            // Process the indices of a single polygon
            for (int i = 0; i < size; i++)
            {
                vertices[i] = pIndices[i * blockSize + vertexOffset];

                if (hasNormals)
                {
                    normals[i] = pIndices[i * blockSize + normalOffset];
                    useNormals &= normals[i] >= 0;
                }

                if (hasTexCoords)
                {
                    texCoords[i] = pIndices[i * blockSize + texCoordOffset];
                    useTexCoords &= texCoords[i] >= 0;
                }
            }

            // Add polygon to the mesh builder
            if (useNormals) builder.useNormals(normals);
            if (useTexCoords) builder.useTexCoords(texCoords);
            builder.addElement(size, vertices);
        }
    }


    /**
     * Processes triangles.
     *
     * @param builder
     *            The mesh builder
     * @param mesh
     *            The collada mesh
     * @param triangles
     *            The triangles to process
     */

    private void processTriangles(final MeshBuilder builder,
            final ColladaMesh mesh, final Triangles triangles)
    {
        // Process all the data inputs
        processSources(builder, mesh, triangles.getInputs());

        // Get block size of the indices array
        final int blockSize = getBlockSize(mesh, triangles);

        // Get the offsets into the indices array
        final int vertexOffset = getOffset(triangles, Semantic.VERTEX);
        final int normalOffset = getOffset(triangles, Semantic.NORMAL);
        final int texCoordOffset = getOffset(triangles, Semantic.TEXCOORD);

        // Check what we have
        final boolean hasNormals = normalOffset != -1;
        final boolean hasTexCoords = texCoordOffset != -1;

        // Process indices
        final int[] indices = triangles.getIndices();
        final int[] pIndices = indices;
        final int size = pIndices.length / blockSize;

        final int[] vertices = new int[size];
        final int[] normals = hasNormals ? new int[size] : null;
        final int[] texCoords = hasTexCoords ? new int[size] : null;

        // Check if normals/texCoords are used (this state can be
        // disabled during polygon processing when an illegal
        // index was found)
        boolean useNormals = hasNormals;
        boolean useTexCoords = hasTexCoords;

        // Process the indices of a single polygon
        for (int i = 0; i < size; i++)
        {
            vertices[i] = pIndices[i * blockSize + vertexOffset];

            if (hasNormals)
            {
                normals[i] = pIndices[i * blockSize + normalOffset];
                useNormals &= normals[i] >= 0;
            }

            if (hasTexCoords)
            {
                texCoords[i] = pIndices[i * blockSize + texCoordOffset];
                useTexCoords &= texCoords[i] >= 0;
            }
        }

        // Add polygon to the mesh builder
        if (useNormals) builder.useNormals(normals);
        if (useTexCoords) builder.useTexCoords(texCoords);
        builder.addElement(3, vertices);
    }


    /**
     * Returns the offset for the specified input semantic. Returns -1 if the
     * input was not found.
     *
     * @param primitives
     *            The primitives
     * @param semantic
     *            The input semantic
     * @return The offset or -1 if input is not present
     */

    private int getOffset(final Primitives primitives, final Semantic semantic)
    {
        final SharedInput input = primitives.getInputs().get(semantic);
        if (input == null) return -1;
        return input.getOffset();
    }


    /**
     * Calculates the size of a block in an index array.
     *
     * @param mesh
     *            The Collada mesh
     * @param polygons
     *            The polygons
     * @return The block size
     */

    private int getBlockSize(final ColladaMesh mesh, final Primitives polygons)
    {
        return polygons.getInputs().size();
    }


    /**
     * Processes Collada data sources.
     *
     * @param builder
     *            The mesh builder
     * @param mesh
     *            The Collada mesh
     * @param inputs
     *            The data sources to process
     */

    private void processSources(final MeshBuilder builder,
            final ColladaMesh mesh, final SharedInputs inputs)
    {
        final Vertices vertices = mesh.getVertices();
        for (final SharedInput input : inputs)
        {
            final Semantic semantic = input.getSemantic();
            String id = input.getSource().getFragment();
            switch (semantic)
            {
                case VERTEX:
                    // If id references the vertices then read sources from
                    // there
                    if (id.equals(vertices.getId()))
                        id = vertices.getInputs().get(Semantic.POSITION)
                                .getSource().getFragment();

                    processVertexSource(builder, mesh.getSources().get(id));
                    break;

                case NORMAL:
                    processNormalSource(builder, mesh.getSources().get(id));
                    break;

                case TEXCOORD:
                    processTexCoordSource(builder, mesh.getSources().get(id));
                    break;

                default:
                    // Ignored
            }
        }
    }


    /**
     * Processes a vertex source.
     *
     * @param builder
     *            The mesh builder
     * @param dataSource
     *            The vertex data source
     */

    private void processVertexSource(final MeshBuilder builder,
            final DataSource dataSource)
    {
        final float[] data = dataSource.getData().getFloatData();
        for (int i = 0, max = data.length; i < max; i += 3)
        {
            builder.addVertex(data[i], data[i + 1], data[i + 2]);
        }
    }


    /**
     * Processes a normal source.
     *
     * @param builder
     *            The mesh builder
     * @param dataSource
     *            The normal data source
     */

    private void processNormalSource(final MeshBuilder builder,
            final DataSource dataSource)
    {
        final float[] data = dataSource.getData().getFloatData();
        for (int i = 0, max = data.length; i < max; i += 3)
        {
            builder.addNormal(data[i], data[i + 1], data[i + 2]);
        }
    }


    /**
     * Processes a texture coordinate source.
     *
     * @param builder
     *            The mesh builder
     * @param dataSource
     *            The texture coordinate data source
     */

    private void processTexCoordSource(final MeshBuilder builder,
            final DataSource dataSource)
    {
        final float[] data = dataSource.getData().getFloatData();
        for (int i = 0, max = data.length; i < max; i += 2)
        {
            builder.addTexCoord(data[i], data[i + 1]);
        }
    }
}
