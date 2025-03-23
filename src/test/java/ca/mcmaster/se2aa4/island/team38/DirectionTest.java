package ca.mcmaster.se2aa4.island.team38;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    public void testTurnRight() {
        assertEquals(Direction.EAST, Direction.NORTH.turnRight());
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight());
        assertEquals(Direction.NORTH, Direction.WEST.turnRight());
    }

    @Test
    public void testTurnLeft() {
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft());
    }

} 