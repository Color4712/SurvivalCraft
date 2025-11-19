package com.survivalcraft;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void addsResourcesAndTracksCapacity() {
        Player player = new Player("Tester", 80, 80, 5);
        assertTrue(player.canCarryMore(3));

        player.addResource(ResourceType.WOOD, 3);
        assertEquals(3, player.inventory().get(ResourceType.WOOD));
        assertEquals(3, player.totalInventory());
        assertFalse(player.canCarryMore(3)); // would exceed capacity
    }

    @Test
    void adjustsVitalsWithinBounds() {
        Player player = new Player("Tester", 50, 50, 5);
        player.adjustHunger(-200);
        player.adjustEnergy(200);
        assertEquals(0, player.hunger());
        assertEquals(100, player.energy());
    }
}
