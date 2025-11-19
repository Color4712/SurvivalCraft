package com.survivalcraft;

import java.util.EnumMap;
import java.util.Map;

public class Island {
    private final String name;
    private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
    private String weather;

    public Island(String name, String initialWeather) {
        this.name = name;
        this.weather = initialWeather;
    }

    public String name() {
        return name;
    }

    public String weather() {
        return weather;
    }

    public void updateWeather(String newWeather) {
        this.weather = newWeather;
    }

    public void setResource(ResourceType type, int amount) {
        resources.put(type, amount);
    }

    public int getResource(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }

    public int gather(ResourceType type, int requested) {
        int available = resources.getOrDefault(type, 0);
        int collected = Math.min(available, requested);
        resources.put(type, available - collected);
        return collected;
    }

    public static Island sampleIsland() {
        Island island = new Island("Azure Atoll", "Mild breeze");
        island.setResource(ResourceType.WOOD, 18);
        island.setResource(ResourceType.STONE, 12);
        island.setResource(ResourceType.FIBER, 10);
        island.setResource(ResourceType.FOOD, 6);
        return island;
    }
}
