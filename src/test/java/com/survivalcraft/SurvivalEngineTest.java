package com.survivalcraft;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SurvivalEngineTest {

    @Test
    void sessionProducesSummaryAndCollectsResources() {
        GameConfig config = GameConfig.alphaDefaults();
        Island island = Island.sampleIsland();
        Player player = new Player("Tester", config.initialHunger(), config.initialEnergy(), config.maxInventoryCapacity());
        SurvivalEngine engine = new SurvivalEngine(config, island, player);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(buffer));
        try {
            engine.startSession();
        } finally {
            System.setOut(originalOut);
        }

        String output = buffer.toString();
        assertTrue(output.contains("Alpha 0.0.1 summary"));
        assertFalse(player.inventory().isEmpty(), "Player should have collected something by day two");
    }
}
