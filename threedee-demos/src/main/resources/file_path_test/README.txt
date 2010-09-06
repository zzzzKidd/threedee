This tests for correct interpretation of percent coding and absolute/relative paths in URIs.  There are tests for percent encoding of spaces and percent encoding of arbitrary text in both relative and absolute paths.  This test is geared primarily for windows computers because it contains windows absolute paths.  It could be easily modified to work for other operating systems.

FOR THIS TEST TO WORK CORRECTLY the enclosed FilePathTest MUST be at the root of your C: drive (ie: C:\FilePathTest)

To run the test, just load the FilePathTest.dae file and display it with textures.  If everything worked right you should see two rows of 3 polygons with text on them.  Here are the messages and their descriptions:

"relative no spaces passed" the file name is relative to the COLLADA document and contains no spaces.
"relative percent encoded spaces passed" the file name is relative to the COLLADA document and contains spaces encoded as %20
"relative percent encoded text passed" the file name is relative to the COLLADA document a portion of the file path "With Spaces" has been encoded as "With%20%53%70aces" which is equivalent.

"absolute no spaces passed" the file name is an absolute path and contains no spaces.
"relative percent encoded spaces passed" the file name is an absolute path and contains spaces encoded as %20
"relative percent encoded text passed" the file name is an absolute path, a portion of the file path "With Spaces" has been encoded as "With%20%53%70aces" which is equivalent.

Any URI may contain percent encoded characters, while this is usually limited to reserved characters it is possible to percent encode any character.  Implementations should process all types of percent encoded characters correctly.  This only tests URIs appearing in the <image> <init_from> tags.  If you use different code to handle other URIs in documents you need to confirm it works correctly too.

For additional information post messages on www.collada.org or mail collada@collada.org

These models are Copyright 2006 Sony Computer Entertainment Inc. and are distributed under the terms of the SCEA Shared Source License, available at http://research.scea.com/scea_shared_source_license.html