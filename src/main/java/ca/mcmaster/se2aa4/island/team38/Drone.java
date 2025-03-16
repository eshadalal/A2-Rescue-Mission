package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private BatteryManager batteryManager;
    private JSONObject lastActionResponse;

    public Drone(Direction direction, int batteryLevel, int x, int y) {
        this.direction = direction;
        this.position = new Position(x, y);
        this.batteryManager = new BatteryManager(batteryLevel);
        this.lastActionResponse = new JSONObject(); 
    }

    @Override
    public void initialize(String direction, int batteryLevel, int x, int y) {
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.position = new Position(x, y);
        this.batteryManager = new BatteryManager(batteryLevel);
        this.lastActionResponse = new JSONObject(); 
    }

    public void performAction(Action action) {
        switch (action) {
            case FLY:
                fly();
                break;
            case TURN_RIGHT:
                turnRight();
                break;
            case TURN_LEFT:
                turnLeft();
                break;
            case SCAN:
                scan();
                break;
            case STOP:
                stop();
                break;
            case ECHO:
                echo(direction);
                break;
            default:
                System.out.println("Invalid action.");
        }
    }

    @Override
    public void fly() {
        if (isBatteryDepleted()) return;
        moveDrone(direction);
        getCost("fly");
    }

    @Override
    public void turnRight() {
        if (isBatteryDepleted()) return;
        direction = direction.turnRight();
        getCost("heading");
    }

    @Override
    public void turnLeft() {
        if (isBatteryDepleted()) return;
        direction = direction.turnLeft();
        getCost("heading");
    }

    private boolean isBatteryDepleted() {
        if (batteryManager.getBatteryLevel() <= 0) {
            System.out.println("Battery depleted. Cannot perform the action.");
            return true;
        }
        return false;
    }

    private void moveDrone(Direction direction) {
        switch (direction) {
            case NORTH: position.updateY(1); break;
            case SOUTH: position.updateY(-1); break;
            case EAST: position.updateX(1); break;
            case WEST: position.updateX(-1); break;
        }
    }

    public void getCost(String action) {
        if (lastActionResponse != null && lastActionResponse.has("cost")) {
            int cost = lastActionResponse.getInt("cost");
            batteryManager.updateBatteryLevel(cost); 
            System.out.println("Battery level after " + action + ": " + batteryManager.getBatteryLevel());
        } else {
            System.out.println("No cost information available for action: " + action);
        }
    }

    @Override
    public void getInfo(int cost, JSONObject extraInfo) {
        System.out.println("Cost of the operation: " + cost);
        System.out.println("Additional Information: " + extraInfo.toString(2));
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public int getBatteryLevel() {
        return this.batteryManager.getBatteryLevel();
    }

    @Override
    public JSONObject scan() {
        lastActionResponse.put("action", "scan");
        getCost("scan");
        return lastActionResponse;
    }

    @Override
    public JSONObject stop() {
        lastActionResponse.put("action", "stop");
        getCost("stop");
        return lastActionResponse;
    }

    @Override
    public JSONObject echo(Direction direction) {
        JSONObject response = new JSONObject();
        response.put("action", "echo");
        response.put("parameters", new JSONObject().put("direction", direction.toString()));
        getCost("echo");
        return response;
    }

    @Override
    public JSONObject echoForward() {
        return echo(direction);
    }

    @Override
    public JSONObject echoRight() {
        return echo(direction.turnRight());
    }

    @Override
    public JSONObject echoLeft() {
        return echo(direction.turnLeft());
    }

    public void logAction(String action) {
        lastActionResponse.put("action", action);
        System.out.println("Action performed: " + action);
    }

    public JSONObject getLastActionResponse() {
        return this.lastActionResponse;
    }
}
