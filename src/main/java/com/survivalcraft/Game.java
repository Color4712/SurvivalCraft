package com.survivalcraft;

public class Game {
    public static void main(String[] args) {
        GameConfig config = GameConfig.alphaDefaults();
        Island island = Island.sampleIsland();
        Player player = new Player("Voyager", config.initialHunger(), config.initialEnergy(), config.maxInventoryCapacity());
        SurvivalEngine engine = new SurvivalEngine(config, island, player);
        engine.startSession();
    }
}
