/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;


/**
 * AssetProvider
 *
 * @author k
 */

public interface AssetProvider
{
    /**
     * Checks if the specified asset exists.
     *
     * @param type
     *            The asset type.
     * @param id
     *            The asset ID.
     * @return True if asset exists, false if not.
     */

    boolean exists(AssetType type, String id);


    /**
     * Opens an asset for reading. Caller is responsible for closing the
     * stream when no longer needed.
     *
     * @param type
     *            The asset type.
     * @param id
     *            The asset ID.
     * @return The asset input stream.
     */

    AssetInputStream openInputStream(AssetType type, String id);
}
