package ca.mcmaster.se2aa4.island.team38;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DroneTest {
    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone("NORTH", 100, 0, 0);  // Drone starts at (0,0) facing NORTH
    }

    @Test
    void testFlyNorth() {
        drone.fly();
        assertEquals(1, drone.getPosition().getY());  // Should move +1 in Y direction
    }

    @Test
    void testTurnRight() {
        drone.turnRight();
        assertEquals("EAST", drone.getDirection()); // Should be facing EAST
    }

    @Test
    void testTurnLeft() {
        drone.turnLeft();
        assertEquals("WEST", drone.getDirection()); // Should be facing WEST
    }
}
