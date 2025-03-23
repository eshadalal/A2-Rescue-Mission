package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroneTest {

    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone(Direction.NORTH, 10, 0, 0);
    }

    @Test
    void testConstructor() {
        assertNotNull(drone);
        assertEquals(0, drone.getPosition().getX());
        assertEquals(0, drone.getPosition().getY());
        assertEquals(Direction.NORTH, drone.getDirection());
        assertEquals(10, drone.getBatteryLevel());
    }

    @Test
    void testFly() {
        JSONObject response = drone.fly();
        assertNotNull(response);
        assertEquals("fly", response.getString("action"));
        assertEquals(8, drone.getBatteryLevel());  // Battery should decrease by 2
        assertEquals(1, drone.getPosition().getY());  // Position should update according to direction
    }

    @Test
    void testTurnRight() {
        JSONObject response = drone.turnRight();
        assertNotNull(response);
        assertEquals("heading", response.getString("action"));
        assertEquals(Direction.EAST, drone.getDirection());  // Direction should change to East
        assertEquals(6, drone.getBatteryLevel());  // Battery should decrease by 4
    }

    @Test
    void testTurnLeft() {
        JSONObject response = drone.turnLeft();
        assertNotNull(response);
        assertEquals("heading", response.getString("action"));
        assertEquals(Direction.WEST, drone.getDirection());  // Direction should change to West
        assertEquals(6, drone.getBatteryLevel());  // Battery should decrease by 4
    }

    @Test
    void testScan() {
        JSONObject response = drone.scan();
        assertNotNull(response);
        assertEquals("scan", response.getString("action"));
        assertEquals(8, drone.getBatteryLevel());  // Battery should decrease by 2
    }

    @Test
    void testStop() {
        JSONObject response = drone.stop();
        assertNotNull(response);
        assertEquals("stop", response.getString("action"));
        assertEquals(7, drone.getBatteryLevel());  // Battery should decrease by 3
    }

    @Test
    void testEchoForward() {
        JSONObject response = drone.echoForward();
        assertNotNull(response);
        assertEquals("echo", response.getString("action"));
        assertEquals(9, drone.getBatteryLevel());  // Battery should decrease by 1
    }

    @Test
    void testEchoRight() {
        JSONObject response = drone.echoRight();
        assertNotNull(response);
        assertEquals("echo", response.getString("action"));
        assertEquals(9, drone.getBatteryLevel());  // Battery should decrease by 1
    }

    @Test
    void testEchoLeft() {
        JSONObject response = drone.echoLeft();
        assertNotNull(response);
        assertEquals("echo", response.getString("action"));
        assertEquals(9, drone.getBatteryLevel());  // Battery should decrease by 1
    }

    @Test
    void testBatteryLevel() {
        assertEquals(10, drone.getBatteryLevel());
        
        drone.fly();
        drone.turnLeft();
        drone.scan();
        
        assertEquals(2, drone.getBatteryLevel());  // Battery should be 2
    }

}
