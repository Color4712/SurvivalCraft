package com.survivalcraft;

import java.util.Locale;
import java.util.Random;

public class SurvivalEngine {
    private final GameConfig config;
    private final Island island;
    private final Player player;
    private final Random random;

    public SurvivalEngine(GameConfig config, Island island, Player player) {
        this(config, island, player, new Random(42));
    }

    public SurvivalEngine(GameConfig config, Island island, Player player, Random random) {
        this.config = config;
        this.island = island;
        this.player = player;
        this.random = random;
    }

    public void startSession() {
        printHeader();
        runDayCycle(1, "Arrive on the shore", ResourceType.WOOD, 4);
        runDayCycle(2, "Stabilize camp and gather food", ResourceType.FOOD, 3);
        summarize();
    }

    private void printHeader() {
        System.out.printf(Locale.ROOT, "==== Survival Craft v%s ====%n", config.version());
        System.out.printf(Locale.ROOT, "Landing on %s (%s)%n", island.name(), island.weather());
        System.out.printf(Locale.ROOT, "Player: %s | Hunger %d | Energy %d%n%n",
                player.name(), player.hunger(), player.energy());
    }

    private void runDayCycle(int day, String goal, ResourceType focus, int request) {
        System.out.printf(Locale.ROOT, "Day %d — %s%n", day, goal);
        adjustWeather();
        gatherResources(focus, request);
        craftIfPossible();
        rest();
        System.out.println();
    }

    private void adjustWeather() {
        String[] patterns = {"Mild breeze", "Cloudy", "Humid", "Light rain"};
        String next = patterns[random.nextInt(patterns.length)];
        island.updateWeather(next);
        System.out.printf(Locale.ROOT, "Weather shifts to: %s%n", island.weather());
    }

    private void gatherResources(ResourceType focus, int requested) {
        if (!player.canCarryMore(requested)) {
            System.out.println("Backpack is full; skipping gathering.");
            return;
        }
        int collected = island.gather(focus, requested);
        player.addResource(focus, collected);
        player.adjustEnergy(-5);
        player.adjustHunger(-3);
        System.out.printf(Locale.ROOT, "Collected %d %s. Hunger %d, Energy %d%n",
                collected, focus.displayName(), player.hunger(), player.energy());
    }

    private void craftIfPossible() {
        int wood = player.inventory().getOrDefault(ResourceType.WOOD, 0);
        int fiber = player.inventory().getOrDefault(ResourceType.FIBER, 0);
        if (wood >= 3 && fiber >= 1) {
            player.inventory().put(ResourceType.WOOD, wood - 3);
            player.inventory().put(ResourceType.FIBER, fiber - 1);
            System.out.println("Crafted a makeshift shelter frame (+comfort).");
        } else if (wood >= 2) {
            player.inventory().put(ResourceType.WOOD, wood - 2);
            System.out.println("Built a campfire for the night (+warmth).");
        } else {
            System.out.println("Not enough resources to craft yet.");
        }
    }

    private void rest() {
        player.adjustEnergy(+10);
        player.adjustHunger(-5);
        System.out.printf(Locale.ROOT, "Resting by the shore. Hunger %d, Energy %d%n",
                player.hunger(), player.energy());
    }

    private void summarize() {
        System.out.println("=== Alpha 0.0.1 summary ===");
        System.out.printf(Locale.ROOT, "Inventory: %s%n", player.inventory());
        System.out.printf(Locale.ROOT, "Status: Hunger %d | Energy %d%n", player.hunger(), player.energy());
        System.out.println("Roadmap: expand crafting, add wildlife AI, and day-night cycles.");
    }
}
