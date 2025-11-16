package com.survivalcraft;

import java.util.EnumMap;
import java.util.Map;

public class Player {
    private final String name;
    private int hunger;
    private int energy;
    private final Map<ResourceType, Integer> inventory = new EnumMap<>(ResourceType.class);
    private final int maxInventoryCapacity;

    public Player(String name, int initialHunger, int initialEnergy, int maxInventoryCapacity) {
        this.name = name;
        this.hunger = initialHunger;
        this.energy = initialEnergy;
        this.maxInventoryCapacity = maxInventoryCapacity;
    }

    public String name() {
        return name;
    }

    public int hunger() {
        return hunger;
    }

    public int energy() {
        return energy;
    }

    public Map<ResourceType, Integer> inventory() {
        return inventory;
    }

    public boolean canCarryMore(int amount) {
        return totalInventory() + amount <= maxInventoryCapacity;
    }

    public int totalInventory() {
        return inventory.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void adjustHunger(int delta) {
        hunger = Math.max(0, Math.min(100, hunger + delta));
    }

    public void adjustEnergy(int delta) {
        energy = Math.max(0, Math.min(100, energy + delta));
    }

    public void addResource(ResourceType type, int amount) {
        inventory.merge(type, amount, Integer::sum);
    }
}
