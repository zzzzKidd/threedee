/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * Opaque information
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public enum Opaque
{
    /**
     * Takes the transparency information from the color’s
     * alpha channel, where the value 1.0 is opaque.
     */
    A_ONE,

    /**
     * Takes the transparency information from the color’s red, green,
     * and blue channels, where the value 0.0 is opaque, with each channel
     * modulated independently.
     */
    RGB_ZERO,

    /**
     * Takes the transparency information from the color’s
     * alpha channel, where the value 0.0 is opaque.
     */
    A_ZERO,

    /**
     * Takes the transparency information from the color’s red, green,
     * and blue channels, where the value 1.0 is opaque, with each channel
     * modulated independently.
     */
    RGB_ONE
}
