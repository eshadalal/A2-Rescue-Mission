package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private int batteryLevel;
    private JSONObject lastActionResponse;

    public Drone(String direction, int batteryLevel, int x, int y) {
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.batteryLevel = batteryLevel;
        this.position = new Position(x, y);
        this.lastActionResponse = new JSONObject();
    }

    @Override
    public void initialize(String direction, int batteryLevel, int x, int y) {
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.position = new Position(x, y);
        this.batteryLevel = batteryLevel;
        logAction("initialize");  
    }

    @Override
    public void fly() {
        if (batteryLevel <= 0) {
            System.out.println("Battery depleted. Cannot fly.");
            return;
        }
        switch (direction) {
            case NORTH: 
                position.updateY(1);
                break;
            case SOUTH: 
                position.updateY(-1);
                break;
            case EAST: 
                position.updateX(1);
                break;
            case WEST: 
                position.updateX(-1);
                break;
        }
        batteryLevel--; 
        logAction("fly");  
    }

    @Override
    public void turnRight() {
        if (batteryLevel <= 0) {
            System.out.println("Battery depleted. Cannot turn.");
            return;
        }
        direction = direction.turnRight();
        batteryLevel--; 
        logAction("turnRight");  
    }

    @Override
    public void turnLeft() {
        if (batteryLevel <= 0) {
            System.out.println("Battery depleted. Cannot turn.");
            return;
        }
        direction = direction.turnLeft();
        batteryLevel--; 
        logAction("turnLeft");  
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
    public String getDirection() {
        return this.direction.name();
    }

    @Override
    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    @Override
    public JSONObject scan() {
        JSONObject command = new JSONObject();
        command.put("action", "scan");
        logAction("scan", command);  
        return command;
    }

    @Override
    public JSONObject stop() {
        JSONObject command = new JSONObject();
        command.put("action", "stop");
        logAction("stop", command); 
        return command;
    }

    @Override
    public JSONObject echo(Direction direction) {
        JSONObject response = new JSONObject();
        response.put("action", "echo");
        response.put("parameters", new JSONObject().put("direction", direction.toString()));
        response.put("extras", new JSONObject());

        logAction("echo", response);  
        return response;
    }

    @Override
    public JSONObject echoForward() {
        JSONObject response = echo(direction);
        lastActionResponse = response;
        return response;
    }

    @Override
    public JSONObject echoRight() {
        JSONObject response = echo(direction.turnRight());
        lastActionResponse = response;
        return response;
    }

    @Override
    public JSONObject echoLeft() {
        JSONObject response = echo(direction.turnLeft());
        lastActionResponse = response;
        return response;
    }

    public void logAction(String action) {
        lastActionResponse = new JSONObject().put("action", action);
        System.out.println("Action performed: " + action);  
        System.out.println(lastActionResponse.toString(2));  
    }

    public void logAction(String action, JSONObject response) {
        lastActionResponse = response;
        System.out.println("Action performed: " + action);
        System.out.println(response.toString(2));  
    }

    public JSONObject getLastActionResponse() {
        return this.lastActionResponse;
    }

    public enum Direction {
        NORTH, EAST, SOUTH, WEST;

        public Direction turnRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST: return SOUTH;
                case SOUTH: return WEST;
                case WEST: return NORTH;
                default: throw new IllegalStateException("Unexpected value: " + this);
            }
        }

        public Direction turnLeft() {
            switch (this) {
                case NORTH: return WEST;
                case WEST: return SOUTH;
                case SOUTH: return EAST;
                case EAST: return NORTH;
                default: throw new IllegalStateException("Unexpected value: " + this);
            }
        }
    }
}
