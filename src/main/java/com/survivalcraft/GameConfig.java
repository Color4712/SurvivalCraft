package com.survivalcraft;

/**
 * Immutable configuration for the alpha build.
 */
public record GameConfig(
        String version,
        int initialHunger,
        int initialEnergy,
        int maxInventoryCapacity) {

    public static GameConfig alphaDefaults() {
        return new GameConfig("0.0.1-alpha", 80, 80, 30);
    }
}
