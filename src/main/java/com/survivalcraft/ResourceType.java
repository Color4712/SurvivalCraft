package com.survivalcraft;

public enum ResourceType {
    WOOD("Wood"),
    STONE("Stone"),
    FIBER("Fiber"),
    FOOD("Food");

    private final String displayName;

    ResourceType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
