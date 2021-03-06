
package de.ailis.threedee.assets.reader.assets;

/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */




/**
 * This loader loads a model from a WaveFront OBJ file.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class WavefrontObjAssetReader
{
//    /** Internal vertex counter */
//    private int vertexCount;
//
//    /** Internal normal counter */
//    private int normalCount;
//
//    /** Internal texture coordinate counter */
//    private int texCoordCount;
//
//    /** The materials */
//    private Map<String, Material> materials;
//
//    /** The base directory for loading referenced files. */
//    private String baseDir;
//
//
//    /**
//     * Constructs a new OBJ file reader.
//     *
//     * @param resourceProvider
//     *            The resource provider to use for reading models, material
//     *            libraries and texture files
//     */
//
//    public WavefrontObjAssetReader(final ResourceProvider resourceProvider)
//    {
//        super(resourceProvider);
//    }
//
//
//    /**
//     * @see de.ailis.threedee.io.ModelReader#read(java.io.InputStream, String)
//     */
//
//    @Override
//    public Model read(final InputStream stream, final String baseDir) throws IOException
//    {
//        final MeshBuilder builder = new MeshBuilder();
//
//        this.baseDir = baseDir;
//
//        // Reset counters
//        this.vertexCount = 0;
//        this.normalCount = 0;
//        this.texCoordCount = 0;
//
//        this.materials = new HashMap<String, Material>();
//
//        // Open a buffered reader to read the file line by line
//        final BufferedReader reader = new BufferedReader(new InputStreamReader(
//                stream));
//        try
//        {
//            String line;
//            int lineNo = 0;
//            while ((line = reader.readLine()) != null)
//            {
//                // Ignore empty lines
//                if (line.trim().length() == 0) continue;
//
//                // Increase line number for usage in error messages
//                lineNo++;
//
//                // Split the line into tokens and parse the command from it
//                final StringTokenizer tokenizer = new StringTokenizer(line, " ");
//                final String command = tokenizer.nextToken();
//
//                // Process commands
//
//                // Process vertex
//                if ("v".equals(command))
//                {
//                    addVertex(tokenizer, builder);
//                    continue;
//                }
//
//                // Process vertex normal
//                if ("vn".equals(command))
//                {
//                    addNormal(tokenizer, builder);
//                    continue;
//                }
//
//                // Process vertex texture coordinate
//                if ("vt".equals(command))
//                {
//                    addTexCoord(tokenizer, builder);
//                    continue;
//                }
//
//                // Process a face
//                if ("f".equals(command))
//                {
//                    processFace(tokenizer, builder);
//                    continue;
//                }
//
//                if ("mtllib".equals(command))
//                {
//                    final String filename = parseString(tokenizer);
//                    loadMaterials(filename);
//                    continue;
//                }
//
//                // Process material usage
//                if ("usemtl".equals(command))
//                {
//                    final String name = parseString(tokenizer);
//                    builder.useMaterial(name);
//                    continue;
//                }
//            }
//        }
//        finally
//        {
//            reader.close();
//        }
//
//        final Mesh mesh = builder.build("dummy");
//        final Model model = new Model(mesh);
//        for (final Map.Entry<String, Material> entry : this.materials
//            .entrySet())
//        {
//            final String name = entry.getKey();
//            final Material material = entry.getValue();
//            model.bindMaterial(name, material);
//        }
//        return model;
//    }
//
//
//    /**
//     * Loads materials from the specified file.
//     *
//     * @param filename
//     *            The file to read
//     * @throws IOException
//     *             When read fails
//     */
//
//    private void loadMaterials(final String filename) throws IOException
//    {
//        String realFilename = filename;
//        if (this.baseDir != null && !new File(filename).isAbsolute())
//            realFilename = new File(this.baseDir, filename).getPath();
//        final InputStream stream = this.resourceProvider.openForRead(realFilename);
//        try
//        {
//            loadMaterials(stream);
//        }
//        finally
//        {
//            stream.close();
//        }
//    }
//
//
//    /**
//     * Load materials.
//     *
//     * @param stream
//     *            The input stream
//     * @throws IOException
//     *             When material library could not be loaded
//     */
//
//    private void loadMaterials(final InputStream stream) throws IOException
//    {
//        MaterialBuilder builder = null;
//
//        // Open a buffered reader to read the file line by line
//        final BufferedReader reader = new BufferedReader(new InputStreamReader(
//                stream));
//        try
//        {
//            String line;
//            int lineNo = 0;
//            while ((line = reader.readLine()) != null)
//            {
//                // Ignore empty lines
//                if (line.trim().length() == 0) continue;
//
//                // Increase line number for usage in error messages
//                lineNo++;
//
//                // Split the line into tokens and parse the command from it
//                final StringTokenizer tokenizer = new StringTokenizer(line, " ");
//                final String command = tokenizer.nextToken();
//
//                // Process commands
//
//                // Process comment
//                if ("#".equals(command)) continue;
//
//                // Process material name
//                if ("newmtl".equals(command))
//                {
//                    if (builder != null)
//                    {
//                        final Material material = builder.build();
//                        this.materials.put(material.getId(), material);
//                        builder.reset();
//                    }
//                    else
//                        builder = new MaterialBuilder();
//                    builder.setId(parseString(tokenizer));
//                    continue;
//                }
//
//                if (builder == null)
//                    throw new ReaderException("Unexpected command in line "
//                            + lineNo + ": " + command);
//
//                // Process the ambient color
//                if ("Ka".equals(command))
//                {
//                    builder.setAmbientColor(parseColor(tokenizer));
//                    continue;
//                }
//
//                // Process the diffuse color
//                if ("Kd".equals(command))
//                {
//                    builder.setDiffuseColor(parseColor(tokenizer));
//                    continue;
//                }
//
//                // Process the diffuse texture
//                if ("map_Kd".equals(command))
//                {
//                    final String filename = parseString(tokenizer);
//                    builder.setDiffuseTexture(filename);
//                    continue;
//                }
//
//                // Process the specular color
//                if ("Ks".equals(command))
//                {
//                    builder.setSpecularColor(parseColor(tokenizer));
//                    continue;
//                }
//
//                // Process shininess
//                if ("Ns".equals(command))
//                {
//                    builder.setShininess(parseFloat(tokenizer) * 128 / 1000);
//                    continue;
//                }
//
//                // Process transparency
//                /*
//                 * TODO Implement me if ("d".equals(command)) {
//                 * builder.setAlpha(parseFloat(tokenizer)); continue; }
//                 */
//            }
//        }
//        finally
//        {
//            reader.close();
//        }
//
//        if (builder != null)
//        {
//            final Material material = builder.build();
//            this.materials.put(material.getId(), material);
//            builder.reset();
//        }
//    }
//
//
//    /**
//     * Parses a float from the specified tokenizer and returns it.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @return The parsed float number
//     */
//
//    private float parseFloat(final StringTokenizer tokenizer)
//    {
//        if (tokenizer.countTokens() != 1)
//        {
//            throw new ReaderException("1 float parameters expected but "
//                    + tokenizer.countTokens() + " found");
//        }
//
//        try
//        {
//            return Float.parseFloat(tokenizer.nextToken());
//        }
//        catch (final NumberFormatException e)
//        {
//            throw new ReaderException("Conversion to float failed: "
//                    + e.toString(), e);
//        }
//    }
//
//
//    /**
//     * Parses a color from the specified tokenizer and returns it.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @return The parsed color
//     */
//
//    private Color4f parseColor(final StringTokenizer tokenizer)
//    {
//        if (tokenizer.countTokens() != 3)
//        {
//            throw new ReaderException("3 float parameters expected but only "
//                    + tokenizer.countTokens() + " found");
//        }
//
//        try
//        {
//            return new ImmutableColor4f(Float.parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()), 1f);
//        }
//        catch (final NumberFormatException e)
//        {
//            throw new ReaderException("Conversion to float failed: "
//                    + e.toString(), e);
//        }
//    }
//
//
//    /**
//     * Parses a 3D vector with float values from the specified tokenizer and
//     * adds the vertex to the model builder.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @param builder
//     *            The model builder
//     */
//
//    private void addVertex(final StringTokenizer tokenizer,
//            final MeshBuilder builder)
//    {
//        if (tokenizer.countTokens() != 3)
//        {
//            throw new ReaderException("3 float parameters expected but only "
//                    + tokenizer.countTokens() + " found");
//        }
//
//        try
//        {
//            builder.addVertex(Float.parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()));
//            this.vertexCount++;
//        }
//        catch (final NumberFormatException e)
//        {
//            throw new ReaderException("Conversion to float failed: "
//                    + e.toString(), e);
//        }
//    }
//
//
//    /**
//     * Parses a normal from the specified tokenizer and adds it to the model
//     * builder.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @param builder
//     *            The model builder
//     */
//
//    private void addNormal(final StringTokenizer tokenizer,
//            final MeshBuilder builder)
//    {
//        if (tokenizer.countTokens() != 3)
//        {
//            throw new ReaderException("3 float parameters expected but only "
//                    + tokenizer.countTokens() + " found");
//        }
//
//        try
//        {
//            builder.addNormal(Float.parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()));
//            this.normalCount++;
//        }
//        catch (final NumberFormatException e)
//        {
//            throw new ReaderException("Conversion to float failed: "
//                    + e.toString(), e);
//        }
//    }
//
//
//    /**
//     * Parses a texture coordinate from the specified tokenizer and adds it to
//     * the model builder.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @param builder
//     *            The model builder
//     */
//
//    private void addTexCoord(final StringTokenizer tokenizer,
//            final MeshBuilder builder)
//    {
//        if (tokenizer.countTokens() != 2)
//        {
//            throw new ReaderException("2 float parameters expected but "
//                    + tokenizer.countTokens() + " found");
//        }
//
//        try
//        {
//            builder.addTexCoord(Float.parseFloat(tokenizer.nextToken()), Float
//                    .parseFloat(tokenizer.nextToken()));
//            this.texCoordCount++;
//        }
//        catch (final NumberFormatException e)
//        {
//            throw new ReaderException("Conversion to float failed: "
//                    + e.toString(), e);
//        }
//    }
//
//
//    /**
//     * Processes a face.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the command parameters
//     * @param builder
//     *            The model builder
//     */
//
//    private void processFace(final StringTokenizer tokenizer,
//            final MeshBuilder builder)
//    {
//        final int count = tokenizer.countTokens();
//        final int[] vertices = new int[count];
//        final int[] normals = new int[count];
//        final int[] texCoords = new int[count];
//        boolean useTexCoords = true, useNormals = true;
//        int index = 0;
//
//        // Parse indices
//        while (tokenizer.hasMoreTokens())
//        {
//            String[] items;
//            String def;
//            int vertexId, texCoordsId, normalId;
//
//            def = tokenizer.nextToken();
//            items = def.split("/");
//
//            // Parse vertex index
//            vertexId = Integer.parseInt(items[0]);
//            if (vertexId < 0)
//                vertices[index] = this.vertexCount - vertexId;
//            else
//                vertices[index] = vertexId - 1;
//
//            // Parse texture coordinate index
//            if (items.length > 1 && items[1].length() > 0)
//            {
//                texCoordsId = Integer.parseInt(items[1]);
//                if (texCoordsId < 0)
//                    texCoords[index] = this.texCoordCount - texCoordsId;
//                else
//                    texCoords[index] = texCoordsId - 1;
//            }
//            else
//                useTexCoords = false;
//
//            // Parse normal index
//            if (items.length > 2 && items[2].length() > 0)
//            {
//                normalId = Integer.parseInt(items[2]);
//                if (normalId < 0)
//                    normals[index] = this.normalCount - normalId;
//                else
//                    normals[index] = normalId - 1;
//            }
//            else
//                useNormals = false;
//
//            index++;
//        }
//
//        if (vertices.length < 4)
//        {
//            if (useTexCoords) builder.useTexCoords(texCoords);
//            if (useNormals) builder.useNormals(normals);
//            builder.addElement(vertices.length, vertices);
//        }
//        else
//        {
//            /*
//             * final int triangles = vertices.length - 2; for (int t = 0; t <
//             * triangles; t++) { if (useTexCoords)
//             * builder.useTexCoords(texCoords[0], texCoords[1 + t], texCoords[2
//             * + t]); if (useNormals) builder.useNormals(normals[0], normals[1 +
//             * t], normals[2 + t]); builder.addElement(3, vertices[0],
//             * vertices[1 + t], vertices[2 + t]); }
//             */
//            if (useTexCoords) builder.useTexCoords(texCoords);
//            if (useNormals) builder.useNormals(normals);
//            builder.addElement(vertices.length, vertices);
//        }
//    }
//
//
//    /**
//     * Parses a string from the specified tokenizer and returns it.
//     *
//     * @param tokenizer
//     *            The tokenizer pointing to the vector values
//     * @return The string
//     */
//
//    private String parseString(final StringTokenizer tokenizer)
//    {
//        if (tokenizer.countTokens() != 1)
//        {
//            throw new ReaderException("1 string parameter expected but "
//                    + tokenizer.countTokens() + " found");
//        }
//        return tokenizer.nextToken();
//    }
}
