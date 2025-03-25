package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

/**
 * The Drone class represents the physical drone agent that explores the island.
 * It implements the DroneController interface and handles all drone operations
 * including movement, scanning, and battery management.
 */
public class Drone implements DroneController {

    // Current facing direction of the drone (N, S, E, W)
    private Direction direction;
    
    // Current (x,y) position coordinates of the drone
    private Position position;
    
    // Manages the drone's battery consumption and level
    private BatteryManager batteryManager;
    
    // The most recent command executed by the drone
    private DroneCommand currentCommand;

    /**
     * Constructs a new Drone with specified initial direction and battery level.
     *
     * @param direction The starting direction (N, S, E, W)
     * @param battery The initial battery level
     */
    public Drone(Direction direction, int battery) {
        this.direction = direction;
        this.batteryManager = new BatteryManager(battery);
        this.position = new Position(0, 0); // Starts at origin (0,0)
    }

    /**
     * Checks if the drone has sufficient battery to continue operations.
     *
     * @return true if battery level is above safety threshold (10 units), false otherwise
     */
    public boolean batteryCheck() {
        return batteryManager.getBatteryLevel() > 10;
    }

    // Command execution methods (implementing DroneController interface)

    @Override
    public JSONObject fly() {
        currentCommand = new FlyCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject turnRight() {
        currentCommand = new TurnRightCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject turnLeft() {
        currentCommand = new TurnLeftCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject scan() {
        currentCommand = new ScanCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject stop() {
        currentCommand = new StopCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoForward() {
        currentCommand = new EchoForwardCommand(); 
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoRight() {
        currentCommand = new EchoRightCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoLeft() {
        currentCommand = new EchoLeftCommand();  
        return currentCommand.execute(this);
    }

    /**
     * Updates the drone's battery level after performing an action.
     *
     * @param action The action that was performed (fly, scan, etc.)
     */
    @Override
    public void updateBattery(String action) {
        int cost = getActionCost(action);
        batteryManager.decreaseBattery(cost);
    }

    /**
     * Gets the battery cost for a specific action.
     *
     * @param action The action to check cost for
     * @return The battery cost in units
     */
    @Override
    public int getActionCost(String action) {
        switch (action) {
            case "fly": return 2;     // Flying costs 2 units
            case "heading": return 4;  // Turning costs 4 units
            case "scan": return 2;     // Scanning costs 2 units
            case "stop": return 3;    // Stopping costs 3 units
            case "echo": return 1;    // Echo/radar costs 1 unit
            default: return 0;         // Unknown actions cost nothing
        }
    }

    // Accessor methods

    public Position getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public int getBatteryLevel() {
        return batteryManager.getBatteryLevel();
    }

    // Mutator methods

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Updates the drone's position after a fly movement.
     * Adjusts x or y coordinate based on current direction.
     *
     * @param direction The direction of movement (N, S, E, W)
     * @param move The JSON move command (unused in current implementation)
     */
    public void updatePositionAfterFly(String direction, JSONObject move) {
        switch (direction) {
            case "N": position.updateY(1); break;  // Move north (increase Y)
            case "S": position.updateY(-1); break; // Move south (decrease Y)
            case "E": position.updateX(1); break;  // Move east (increase X)
            case "W": position.updateX(-1); break; // Move west (decrease X)
        }
    }
}
