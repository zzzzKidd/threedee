/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.parser;


/**
 * The parser mode.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

enum ParserMode {
    /** Parser is at root level */
    ROOT(""),

    /** Parser is in COLLADA element */
    COLLADA("COLLADA"),

    /** Parser is in library_images element */
    LIBRARY_IMAGES("library_images"),

    /** Parser is in image element */
    IMAGE("image"),

    /** Parser is in image init_from element */
    IMAGE_INIT_FROM("init_from"),

    /** Parser is in library_materials element */
    LIBRARY_MATERIALS("library_materials"),

    /** Parser is in material element */
    MATERIAL("material"),

    /** Parser is in instance_effect element */
    INSTANCE_EFFECT("instance_effect"),

    /** Parser is in library_effects element */
    LIBRARY_EFFECTS("library_effects"),

    /** Parser is in effect element */
    EFFECT("effect"),

    /** Parser is in profile_COMMON element */
    PROFILE_COMMON("profile_COMMON"),

    /** Parser is in technique element */
    TECHNIQUE_COMMON("technique"),

    /** Parser is in phong element */
    PHONG("phong"),

    /** Parser is in emission element */
    EMISSION("emission"),

    /** Parser is in ambient element */
    AMBIENT("ambient"),

    /** Parser is in diffuse element */
    DIFFUSE("diffuse"),

    /** Parser is in specular element */
    SPECULAR("specular"),

    /** Parser is in reflective element */
    REFLECTIVE("reflective"),

    /** Parser is in transparent element */
    TRANSPARENT("transparent"),

    /** Parser is in shininess element */
    SHININESS("shininess"),

    /** Parser is in reflectivity element */
    REFLECTIVITY("reflectivity"),

    /** Parser is in transparency element */
    TRANSPARENCY("transparency"),

    /** Parser is in index_of_refraction element */
    INDEX_OF_REFRACTION("index_of_refraction"),

    /** Parser is in color element */
    SHADING_COLOR("color"),

    /** Parser is in texture element */
    TEXTURE("texture"),

    /** Parser is in float element */
    FLOAT("float"),

    /** Parser is in library_geometries element */
    LIBRARY_GEOMETRIES("library_geometries"),

    /** Parser is in geometry element */
    GEOMETRY("geometry"),

    /** Parser is in mesh element */
    MESH("mesh"),

    /** Parser is in data source element */
    DATA_SOURCE("source"),

    /** Parser is in float_array element */
    FLOAT_ARRAY("float_array"),

    /** Parser is in vertices element */
    VERTICES("vertices"),

    /** Parser is in vertices input element */
    VERTICES_INPUT("input"),

    /** Parser is in polygons element */
    POLYGONS("polygons"),

    /** Parser is in primitives input element */
    PRIMITIVES_INPUT("input"),

    /** Parser is in polygons p element */
    POLYGONS_P("p"),

    /** Parser is in library_lights element */
    LIBRARY_LIGHTS("library_lights"),

    /** Parser is in light element */
    LIGHT("light"),

    /** Parser is in light technique_common element */
    LIGHT_TECHNIQUE_COMMON("technique_common"),

    /** Parser is in directional light element */
    LIGHT_DIRECTIONAL("directional"),

    /** Parser is in ambient light element */
    LIGHT_AMBIENT("ambient"),

    /** Parser is in point light element */
    LIGHT_POINT("point"),

    /** Parser is in spot light element */
    LIGHT_SPOT("spot"),

    /** Parser is in light color element */
    LIGHT_COLOR("color"),

    /** Parser is in falloff angle element */
    FALLOFF_ANGLE("falloff_angle"),

    /** Parser is in library cameras element */
    LIBRARY_CAMERAS("library_cameras"),

    /** Parser is in camera element */
    CAMERA("camera"),

    /** Parser is in optics element */
    OPTICS("optics"),

    /** Parser is in optics techniq_common element */
    OPTICS_TECHNIQUE_COMMON("technique_common"),

    /** Parser is in perspective element */
    PERSPECTIVE("perspective"),

    /** Parser is in xfov element */
    XFOV("xfov"),

    /** Parser is in yfov element */
    YFOV("yfov"),

    /** Parser is in aspect_Ratio element */
    ASPECT_RATIO("aspect_ratio"),

    /** Parser is in znear element */
    ZNEAR("znear"),

    /** Parser is in zfar element */
    ZFAR("zfar"),

    /** Parser is in library_visual_scenes element */
    LIBRARY_VISUAL_SCENES("library_visual_scenes"),

    /** Parser is in visual_scene element */
    VISUAL_SCENE("visual_scene"),

    /** Parser is in visual_scene node element */
    VISUAL_SCENE_NODE("node"),

    /** Parser is in node element */
    NODE("node"),

    /** Parser is in matrix element */
    MATRIX("matrix"),

    /** Parser is in instance_geometry element */
    INSTANCE_GEOMETRY("instance_geometry"),

    /** Parser is in bind_material element */
    BIND_MATERIAL("bind_material"),

    /** Parser is in bind_material technique_common element */
    BIND_MATERIAL_TECHNIQUE_COMMON("technique_common"),

    /** Parser is in instance_material element */
    INSTANCE_MATERIAL("instance_material"),

    /** Parser is in instance_light element */
    INSTANCE_LIGHT("instance_light"),

    /** Parser is in instance camera element */
    INSTANCE_CAMERA("instance_camera"),

    /** Parser is in scene element */
    SCENE("scene"),

    /** Parser is in instance_visual_scene element */
    INSTANCE_VISUAL_SCENE("instance_visual_scene");


    /** The tag name */
    private String tagName;

    /**
     * Constructs a new parser mode.
     *
     * @param tagName
     *            The tag name
     */

    private ParserMode(final String tagName)
    {
        this.tagName = tagName;
    }


    /**
     * Returns the tag name.
     *
     * @return The tag name
     */

    public String getTagName()
    {
        return this.tagName;
    }
}
