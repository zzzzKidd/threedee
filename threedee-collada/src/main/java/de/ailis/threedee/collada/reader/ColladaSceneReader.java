/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.ailis.gramath.Color4f;
import de.ailis.gramath.ImmutableColor4f;
import de.ailis.gramath.ImmutableMatrix4f;
import de.ailis.gramath.Matrix4f;
import de.ailis.gramath.MutableMatrix4d;
import de.ailis.jollada.model.Ambient;
import de.ailis.jollada.model.AnimationChannel;
import de.ailis.jollada.model.AnimationChannels;
import de.ailis.jollada.model.AnimationLibrary;
import de.ailis.jollada.model.AnimationSampler;
import de.ailis.jollada.model.BRDFShader;
import de.ailis.jollada.model.CameraInstance;
import de.ailis.jollada.model.CommonEffectProfile;
import de.ailis.jollada.model.CommonEffectTechnique;
import de.ailis.jollada.model.CommonNewParam;
import de.ailis.jollada.model.DataFlowSource;
import de.ailis.jollada.model.DiffuseShader;
import de.ailis.jollada.model.Directional;
import de.ailis.jollada.model.Document;
import de.ailis.jollada.model.Effect;
import de.ailis.jollada.model.FloatArray;
import de.ailis.jollada.model.FloatAttribute;
import de.ailis.jollada.model.FloatValue;
import de.ailis.jollada.model.Geometric;
import de.ailis.jollada.model.Geometry;
import de.ailis.jollada.model.GeometryInstance;
import de.ailis.jollada.model.Image;
import de.ailis.jollada.model.ImageInstance;
import de.ailis.jollada.model.IntList;
import de.ailis.jollada.model.LightInstance;
import de.ailis.jollada.model.LightSource;
import de.ailis.jollada.model.MaterialInstance;
import de.ailis.jollada.model.NameArray;
import de.ailis.jollada.model.Node;
import de.ailis.jollada.model.Optics;
import de.ailis.jollada.model.Perspective;
import de.ailis.jollada.model.Point;
import de.ailis.jollada.model.PolyList;
import de.ailis.jollada.model.Polygons;
import de.ailis.jollada.model.Primitives;
import de.ailis.jollada.model.Projection;
import de.ailis.jollada.model.RGBAColor;
import de.ailis.jollada.model.RGBColor;
import de.ailis.jollada.model.Sampler2DParam;
import de.ailis.jollada.model.Shader;
import de.ailis.jollada.model.SharedInput;
import de.ailis.jollada.model.SharedInputs;
import de.ailis.jollada.model.Spot;
import de.ailis.jollada.model.Texture;
import de.ailis.jollada.model.Transform;
import de.ailis.jollada.model.Triangles;
import de.ailis.jollada.model.UnsharedInputs;
import de.ailis.jollada.model.Vertices;
import de.ailis.jollada.model.VisualScene;
import de.ailis.jollada.model.VisualSceneInstance;
import de.ailis.jollada.model.VisualSceneLibraries;
import de.ailis.jollada.model.VisualScenes;
import de.ailis.jollada.reader.ColladaReader;
import de.ailis.threedee.builder.MaterialBuilder;
import de.ailis.threedee.builder.MeshBuilder;
import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.SceneReader;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.sampling.Interpolation;
import de.ailis.threedee.sampling.Sampler;
import de.ailis.threedee.sampling.SamplerValue;
import de.ailis.threedee.scene.Camera;
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
 * Scene reader for COLLADA files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaSceneReader extends SceneReader
{
    /** The COLLADA document which is currently processed */
    private Document doc;

    /** The mesh cache */
    private Map<String, Mesh> meshes;

    /** Used textures */
    private Map<String, ImageTexture> textures;

    /** The scene. */
    private Scene scene;

    /** The base directory for loading referenced files. */
    private String baseDir;


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
     * @see de.ailis.threedee.io.SceneReader#read(java.io.InputStream, String)
     */

    @Override
    public Scene read(final InputStream stream, final String baseDir)
        throws IOException
    {
        this.doc = new ColladaReader().read(stream);
        this.baseDir = baseDir;
        this.meshes = new HashMap<String, Mesh>();
        try
        {
            return buildScene();
        }
        finally
        {
            this.doc = null;
            this.meshes = null;
        }
    }


    /**
     * Builds a scene from the specified COLLADA document.
     *
     * @return The scene
     */

    private Scene buildScene()
    {
        // If active scene is set then return this one
        final de.ailis.jollada.model.Scene colladaScene = this.doc
                .getScene();
        if (colladaScene != null)
        {
            final VisualSceneInstance sceneInstance = colladaScene
                    .getVisualSceneInstance();
            if (sceneInstance != null)
                return buildScene((VisualScene) this.doc.getById(
                        sceneInstance.getUrl().getFragment()));
        }

        // No active scene set, so return the first scene if there is one
        final VisualSceneLibraries sceneLibs = this.doc
                .getVisualSceneLibraries();
        if (sceneLibs.size() > 0)
        {
            final VisualScenes scenes = sceneLibs.get(0).getVisualScenes();
            if (scenes.size() > 0) return buildScene(scenes.get(0));
        }

        throw new ReaderException("No scene found in COLLADA document");
    }


    /**
     * Builds a ThreeDee scene from the specified COLLADA visual scene document.
     *
     * @param visualScene
     *            The COLLADA visual scene
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
     * Appends a COLLADA node to a ThreeDee scene node.
     *
     * @param parentNode
     *            The ThreeDee parent scene node
     * @param node
     *            The COLLADA node
     */

    private void appendNode(final SceneNode parentNode, final Node node)
    {
        // Create the ThreeDee scene node and append it to the parent node
        final SceneNode sceneNode = new Group();
        sceneNode.setId(node.getId());
        parentNode.appendChild(sceneNode);

        // Transform the scene node
        for (final Transform transform : node.getTransforms())
        {
            sceneNode.transform(new ImmutableMatrix4f(transform.asMatrix()));
        }

        // Process the meshes
        for (final GeometryInstance instanceGeometry : node
                .getGeometryInstances())
        {
            this.textures = new HashMap<String, ImageTexture>();
            final Geometry geometry = (Geometry) this.doc.getById(
                    instanceGeometry.getUrl().getFragment());
            final Mesh mesh = buildMesh(geometry);
            final Model model = new Model(mesh);
            for (final MaterialInstance instanceMaterial : instanceGeometry
                    .getMaterialBinding().getCommonTechnique()
                    .getMaterialInstances())
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
        for (final LightInstance instanceLight : node.getLightInstances())
        {
            final de.ailis.jollada.model.Light colladaLight = (de.ailis.jollada.model.Light) this.doc
                    .getById(
                    instanceLight.getUrl().getFragment());
            final Light light = buildLight(colladaLight);
            sceneNode.appendChild(light);
            this.scene.getRootNode().addLight(light);
        }

        // Process the cameras
        for (final CameraInstance instanceCamera : node.getCameraInstances())
        {
            final de.ailis.jollada.model.Camera colladaCamera = (de.ailis.jollada.model.Camera) this.doc
                    .getById(
                            instanceCamera.getUrl().getFragment());
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
        for (final AnimationLibrary animLib : this.doc.getAnimationLibraries())

            for (final de.ailis.jollada.model.Animation animation : animLib
                .getAnimations())
            {
                final Animation anim = processAnimation(animation);
                if (anim != null) scene.addAnimation(anim);
            }
    }


    /**
     * Converts a COLLADA animation into a ThreeDee animation.
     *
     * @param colladaAnimation
     *            The COLLADA animation
     * @return The ThreeDee animation
     */

    private Animation processAnimation(final de.ailis.jollada.model.Animation
        colladaAnimation)
    {
        final AnimationChannels channels = colladaAnimation.getChannels();
        final Animation group = new AnimationGroup(colladaAnimation.getId());
        for (final AnimationChannel channel : channels)
        {
            final URI uri = channel.getSource();
            final String target = channel.getTarget();
            final SceneNode node = getNodeByTarget(this.scene, target);
            final String animTarget = getAnimationTarget(target);
            final AnimationSampler colladaSampler = (AnimationSampler) this.doc
                .getById(uri.getFragment());
            final UnsharedInputs inputs = colladaSampler.getInputs();

            if (animTarget.equals("matrix"))
            {
                final String inputId = inputs.getBySemantic("INPUT")
                    .getSource()
                    .getFragment();
                final String outputId = inputs.getBySemantic("OUTPUT")
                    .getSource()
                    .getFragment();
                final String interpolationId = inputs
                    .getBySemantic("INTERPOLATION").getSource().getFragment();
                final DataFlowSource input = (DataFlowSource) this.doc
                    .getById(inputId);
                final DataFlowSource output = (DataFlowSource) this.doc
                    .getById(outputId);
                final DataFlowSource interpolation = (DataFlowSource) this.doc
                    .getById(interpolationId);
                final double[] inputData = ((FloatArray) input.getArray())
                    .getValues();
                if (inputData.length > 0)
                {
                    final Matrix4f[] outputData = getMatrixData(((FloatArray) output
                        .getArray()).getValues());
                    final String[] interpolationData = ((NameArray) interpolation
                        .getArray()).getValues();

                    final Sampler<Matrix4f> sampler = new Sampler<Matrix4f>();
                    for (int i = 0, max = inputData.length; i < max; i++)
                    {
                        sampler
                            .addSample((float) inputData[i],
                                new SamplerValue<Matrix4f>(
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
                throw new ReaderException("Unknown animation target: "
                    + animTarget);
            }
        }

        // Process sub animations
        for (final de.ailis.jollada.model.Animation subAnim : colladaAnimation
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

    private Matrix4f[] getMatrixData(final double[] values)
    {
        final int max = values.length / 16;
        final Matrix4f[] matrixData = new Matrix4f[max];
        for (int i = 0; i < max; i++)
        {
            matrixData[i] = new ImmutableMatrix4f(new MutableMatrix4d(values,
                i * 16).transpose());
        }

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
            throw new ReaderException("Can't find node for target " + target);
        return node;
    }


    /**
     * Builds a ThreeDee material from a Collada material.
     *
     * @param instanceMaterial
     *            The Collada instance material
     * @return The ThreeDee material
     */

    private Material buildMaterial(final MaterialInstance instanceMaterial)
    {
        // if (1==1) return Material.DEFAULT;
        final de.ailis.jollada.model.Material colladaMaterial = (de.ailis.jollada.model.Material) this.doc
                .getById(
                        instanceMaterial.getTarget().getFragment());
        final Effect effect = (Effect) this.doc.getById(
                colladaMaterial.getEffectInstance().getUrl().getFragment());
        final CommonEffectProfile profile = effect.getProfiles()
                .getCommonProfile();
        final CommonEffectTechnique technique = profile.getTechnique();
        final Shader shader = technique.getShader();
        return buildMaterial(profile, shader);
    }


    /**
     * Builds a ThreeDee material from COLLADA shading information.
     *
     * @param profile
     *            The effect profile
     * @param shading
     *            The COLLADA shading information
     * @return The ThreeDee material
     */

    private Material buildMaterial(final CommonEffectProfile profile,
        final Shader shading)
    {
        final MaterialBuilder builder = new MaterialBuilder();
        builder.setLighting(false);
        if (shading.getEmission().isColor())
            builder.setEmissionColor(buildColor(shading.getEmission()
                    .getColor()));
        final FloatAttribute transparency = shading.getTransparency();
        if (shading instanceof DiffuseShader)
        {
            final DiffuseShader diffuseShader = (DiffuseShader) shading;
            if (diffuseShader.getAmbient().isColor())
                builder
                        .setAmbientColor(buildColor(diffuseShader.getAmbient()
                                .getColor()));
            if (diffuseShader.getDiffuse().isColor())
                builder
                        .setDiffuseColor(buildColor(diffuseShader.getDiffuse()
                                .getColor()));
            if (diffuseShader.getDiffuse().isTexture())
            {
                final Texture colladaTexture = diffuseShader.getDiffuse()
                        .getTexture();
                final ImageTexture texture = buildTexture(profile,
                    colladaTexture);
                final String textureName = colladaTexture.getTexture();
                this.textures.put(textureName, texture);
                builder.setDiffuseTexture(textureName);
            }
        }
        if (shading instanceof BRDFShader)
        {
            builder.setLighting(true);
            final BRDFShader brdf = (BRDFShader) shading;
            if (brdf.getSpecular().isColor())
                builder.setSpecularColor(buildColor(brdf.getSpecular()
                        .getColor()));
            final FloatAttribute shininess = brdf.getShininess();
            if (shininess != null)
                builder.setShininess((float) shininess.getFloat().getValue());
        }
        return builder.build();
    }


    /**
     * Builds a ThreeDee texture from a COLLADA texture.
     *
     * @param profile
     *            The effect profile
     * @param texture
     *            The COLLADA Texture
     * @return The ThreeDee texture
     */

    private ImageTexture buildTexture(final CommonEffectProfile profile,
        final Texture texture)
    {
        String id = texture.getTexture();
        final CommonNewParam param = (CommonNewParam) profile.getBySid(id);

        // When no param was found then maybe we have a direct reference to
        // an image
        Image image;
        if (param == null)
        {
            image = (Image) this.doc.getById(id);
        }
        else
        {
            final Sampler2DParam sampler = (Sampler2DParam) (param
                .getParameter());
            final ImageInstance imageInstance = sampler.getImageInstance();
            if (imageInstance == null)
                throw new ReaderException("Texture " + id
                    + " references a sampler which is not referencing an image");
            id = imageInstance.getUrl().getFragment();
            image = (Image) this.doc.getById(id);
        }
        if (image == null)
            throw new ReaderException("Image " + id + " not found");
        String filename = image.getSource().getRef().getPath();
        if (this.baseDir != null && !new File(filename).isAbsolute())
            filename = new File(this.baseDir, filename).getPath();
        return new ImageTexture(filename);
    }


    /**
     * Builds a ThreeDee color from a Collada color.
     *
     * @param colladaColor
     *            The Collada color
     * @return The ThreeDee color
     */

    private Color4f buildColor(final RGBAColor colladaColor)
    {
        return new ImmutableColor4f((float) colladaColor.getRed(),
            (float) colladaColor.getGreen(),
            (float) colladaColor.getBlue(), (float) colladaColor.getAlpha());
    }


    /**
     * Builds a ThreeDee camera from a Collada camera.
     *
     * @param colladaCamera
     *            The Collada camera
     * @return The ThreeDee camera
     */

    private Camera buildCamera(final de.ailis.jollada.model.Camera colladaCamera)
    {
        final Optics optic = colladaCamera.getOptics();
        final Projection projection = optic.getCommonTechnique()
                .getProjection();
        if (projection instanceof Perspective)
        {
            final Perspective pOptic = (Perspective) projection;
            final FloatValue fovX = pOptic.getXFov();
            final FloatValue fovY = pOptic.getYFov();
            final FloatValue ar = pOptic.getAspectRatio();
            float fov;
            if (fovY == null)
            {
                if (fovX == null || ar == null)
                    throw new ReaderException(
                            "Unable to calculate fovY because fovX or aspect ratio is missing");
                fov = (float) (fovX.getValue() / ar.getValue());
            }
            else
                fov = (float) fovY.getValue();
            return new Camera(fov, (float) pOptic.getZNear().getValue(),
                (float) pOptic.getZFar().getValue());
        }
        throw new ReaderException("Unsupported camera projection: "
                + projection.getClass());
    }


    /**
     * Builds a ThreeDee light from a Colladalight.
     *
     * @param colladaLight
     *            The Collada light
     * @return The ThreeDee light
     */

    private Light buildLight(final de.ailis.jollada.model.Light colladaLight)
    {
        final LightSource source = colladaLight.getCommonTechnique()
                .getLightSource();
        if (source instanceof Point)
            return buildPointLight((Point) source);
        if (source instanceof Directional)
            return buildDirectionalLight((Directional) source);
        if (source instanceof Spot)
            return buildSpotLight((Spot) source);
        if (source instanceof Ambient)
            return buildAmbientLight((Ambient) source);
        throw new ReaderException("Unknown Collada light type "
                + source.getClass());
    }


    /**
     * Builds a ThreeDee point light from a COLLADA point light.
     *
     * @param colladaLight
     *            The COLLADA point light
     * @return The ThreeDee point light
     */

    private PointLight buildPointLight(final Point colladaLight)
    {
        final RGBColor color = colladaLight.getColor();
        return new PointLight(new ImmutableColor4f((float) color.getRed(),
            (float) color.getGreen(), (float) color
                    .getBlue(), 1f));
    }


    /**
     * Builds a ThreeDee ambient light from a COLLADA ambient light.
     *
     * @param colladaLight
     *            The COLLADA ambient light
     * @return The ThreeDee ambient light
     */

    private AmbientLight buildAmbientLight(
        final Ambient colladaLight)
    {
        final RGBColor color = colladaLight.getColor();
        return new AmbientLight(new ImmutableColor4f((float) color.getRed(),
            (float) color.getGreen(),
            (float) color
                    .getBlue(), 1f));
    }


    /**
     * Builds a ThreeDee directional light from a Collada directional light.
     *
     * @param colladaLight
     *            The Collada directional light
     * @return The ThreeDee directional light
     */

    private DirectionalLight buildDirectionalLight(
            final Directional colladaLight)
    {
        final RGBColor color = colladaLight.getColor();
        return new DirectionalLight(new ImmutableColor4f(
            (float) color.getRed(), (float) color.getGreen(),
            (float) color.getBlue(), 1));
    }


    /**
     * Builds a ThreeDee spot light from a Collada spot light.
     *
     * @param colladaLight
     *            The Collada point light
     * @return The ThreeDee point light
     */

    private SpotLight buildSpotLight(final Spot colladaLight)
    {
        final RGBColor color = colladaLight.getColor();
        final SpotLight spotLight = new SpotLight(new ImmutableColor4f(
            (float) color.getRed(),
            (float) color.getGreen(), (float) color
                    .getBlue(), 1f));
        final FloatValue falloffAngle = colladaLight.getFalloffAngle();
        if (falloffAngle != null)
            spotLight.setCutOff((float) falloffAngle.getValue() / 2);
        return spotLight;
    }


    /**
     * Builds a ThreeDee mesh from the specified COLLADA geometry and returns
     * it.
     *
     * @param geometry
     *            The COLLADA geometry
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final Geometry geometry)
    {
        // Try to read mesh from cache and use this one if found
        final String id = geometry.getId();
        Mesh mesh = this.meshes.get(id);
        if (mesh != null) return mesh;

        // Build the mesh
        mesh = buildMesh(geometry.getGeometric());

        // Put mesh into cache and return it
        this.meshes.put(id, mesh);
        return mesh;
    }


    /**
     * Builds a ThreeDee mesh from the specified COLLADA geometric element.
     *
     * @param element
     *            The COLLADA geometric element
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final Geometric element)
    {
        if (element instanceof de.ailis.jollada.model.Mesh)
            return buildMesh((de.ailis.jollada.model.Mesh) element);

        throw new ReaderException("Unknown geometric element type: "
                + element.getClass());
    }


    /**
     * Builds a ThreeDee mesh from the specified COLLADA mesh.
     *
     * @param mesh
     *            The COLLADA mesh
     * @return The ThreeDee mesh
     */

    private Mesh buildMesh(final de.ailis.jollada.model.Mesh mesh)
    {
        final MeshBuilder builder = new MeshBuilder();
        for (final Primitives primitives : mesh.getPrimitives())
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
     *            The COLLADA mesh
     * @param primitives
     *            The primitives to process
     */

    private void processPrimitives(final MeshBuilder builder,
            final de.ailis.jollada.model.Mesh mesh, final Primitives primitives)
    {
        builder.useMaterial(primitives.getMaterial());
        if (primitives instanceof Polygons)
            processPolygons(builder, mesh, (Polygons) primitives);
        else if (primitives instanceof Triangles)
            processTriangles(builder, mesh, (Triangles) primitives);
        else if (primitives instanceof PolyList)
            processPolyList(builder, mesh, (PolyList) primitives);
        else
            throw new ReaderException("Unknown primitives type: "
                    + primitives.getClass());
    }


    /**
     * Processes polygons.
     *
     * @param builder
     *            The mesh builder.
     * @param mesh
     *            The COLLADA mesh.
     * @param polygons
     *            The polygons to process.
     */

    private void processPolygons(final MeshBuilder builder,
        final de.ailis.jollada.model.Mesh mesh, final Polygons polygons)
    {
        // Process all the data inputs
        processSources(builder, mesh, polygons.getInputs());

        // Get block size of the indices array
        final int blockSize = getBlockSize(mesh, polygons);

        // Get the offsets into the indices array
        final int vertexOffset = getOffset(polygons, "VERTEX");
        final int normalOffset = getOffset(polygons, "NORMAL");
        final int texCoordOffset = getOffset(polygons, "TEXCOORD");

        // Check what we have
        final boolean hasNormals = normalOffset != -1;
        final boolean hasTexCoords = texCoordOffset != -1;
        // Process indices
        for (final IntList polygon : polygons.getData())
        {
            final int[] data = polygon.getValues();
            final int size = data.length / blockSize;


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
                vertices[i] = data[i * blockSize + vertexOffset];

                if (hasNormals)
                {
                    normals[i] = data[i * blockSize + normalOffset];
                    useNormals &= normals[i] >= 0;
                }

                if (hasTexCoords)
                {
                    texCoords[i] = data[i * blockSize + texCoordOffset];
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
     *            The COLLADA mesh
     * @param triangles
     *            The triangles to process
     */

    private void processTriangles(final MeshBuilder builder,
            final de.ailis.jollada.model.Mesh mesh, final Triangles triangles)
    {
        // Process all the data inputs
        processSources(builder, mesh, triangles.getInputs());

        // Get block size of the indices array
        final int blockSize = getBlockSize(mesh, triangles);

        // Get the offsets into the indices array
        final int vertexOffset = getOffset(triangles, "VERTEX");
        final int normalOffset = getOffset(triangles, "NORMAL");
        final int texCoordOffset = getOffset(triangles, "TEXCOORD");

        // Check what we have
        final boolean hasNormals = normalOffset != -1;
        final boolean hasTexCoords = texCoordOffset != -1;

        // Process indices
        final int[] indices = triangles.getData().getValues();
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
     * Processes triangles.
     *
     * @param builder
     *            The mesh builder
     * @param mesh
     *            The COLLADA mesh
     * @param polyList
     *            The polyList to process
     */

    private void processPolyList(final MeshBuilder builder,
            final de.ailis.jollada.model.Mesh mesh, final PolyList polyList)
    {
        // Process all the data inputs
        processSources(builder, mesh, polyList.getInputs());

        // Get block size of the indices array
        final int blockSize = getBlockSize(mesh, polyList);

        // Get the offsets into the indices array
        final int vertexOffset = getOffset(polyList, "VERTEX");
        final int normalOffset = getOffset(polyList, "NORMAL");
        final int texCoordOffset = getOffset(polyList, "TEXCOORD");

        // Check what we have
        final boolean hasNormals = normalOffset != -1;
        final boolean hasTexCoords = texCoordOffset != -1;

        // Process polygons
        final int[] vcounts = polyList.getVcount().getValues();
        final int[] indices = polyList.getData().getValues();
        final int count = polyList.getCount();
        int p = 0;
        for (int i = 0; i < count; i++)
        {
            // Check if normals/texCoords are used (this state can be
            // disabled during polygon processing when an illegal
            // index was found)
            boolean useNormals = hasNormals;
            boolean useTexCoords = hasTexCoords;

            final int size = vcounts[i];
            final int[] vertices = new int[size];
            final int[] normals = hasNormals ? new int[size] : null;
            final int[] texCoords = hasTexCoords ? new int[size] : null;
            for (int v = 0; v < size; v++)
            {
                vertices[v] = indices[(p + v) * blockSize + vertexOffset];
                if (hasNormals)
                {
                    normals[v] = indices[(p + v) * blockSize + normalOffset];
                    useNormals &= normals[v] >= 0;
                }

                if (hasTexCoords)
                {
                    texCoords[v] = indices[(p + v) * blockSize + texCoordOffset];
                    useTexCoords &= texCoords[v] >= 0;
                }
            }
            p += size;

            // Add polygon to the mesh builder
            if (useNormals) builder.useNormals(normals);
            if (useTexCoords) builder.useTexCoords(texCoords);
            builder.addElement(size, vertices);
        }
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

    private int getOffset(final Primitives primitives, final String semantic)
    {
        final SharedInput input = primitives.getInputs()
                .getBySemantic(semantic);
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

    private int getBlockSize(final de.ailis.jollada.model.Mesh mesh,
        final Primitives polygons)
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
            final de.ailis.jollada.model.Mesh mesh, final SharedInputs inputs)
    {
        final Vertices vertices = mesh.getVertices();
        for (final SharedInput input : inputs)
        {
            final String semantic = input.getSemantic();
            String id = input.getSource().getFragment();
            if (semantic.equals("VERTEX"))
            {
                // If id references the vertices then read sources from
                // there
                if (id.equals(vertices.getId()))
                    id = vertices.getInputs().getBySemantic("POSITION")
                            .getSource().getFragment();

                processVertexSource(builder,
                    (DataFlowSource) this.doc.getById(id));
            }
            else if (semantic.equals("NORMAL"))
            {
                processNormalSource(builder,
                    (DataFlowSource) this.doc.getById(id));
            }
            else if (semantic.equals("TEXCOORD"))
            {
                processTexCoordSource(builder,
                    (DataFlowSource) this.doc.getById(id));
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
            final DataFlowSource dataSource)
    {
        final double[] data = ((FloatArray) dataSource.getArray()).getValues();
        for (int i = 0, max = data.length; i < max; i += 3)
        {
            builder.addVertex((float) data[i], (float) data[i + 1],
                (float) data[i + 2]);
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
            final DataFlowSource dataSource)
    {
        final double[] data = ((FloatArray) dataSource.getArray()).getValues();
        for (int i = 0, max = data.length; i < max; i += 3)
        {
            builder.addNormal((float) data[i], (float) data[i + 1],
                (float) data[i + 2]);
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
            final DataFlowSource dataSource)
    {
        final double[] data = ((FloatArray) dataSource.getArray()).getValues();
        for (int i = 0, max = data.length; i < max; i += 2)
        {
            builder.addTexCoord((float) data[i], (float) data[i + 1]);
        }
    }
}
