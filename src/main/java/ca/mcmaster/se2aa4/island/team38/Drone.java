package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private int batteryLevel;

    public Drone(String direction, int batteryLevel, int x, int y) {
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.batteryLevel = batteryLevel;
        this.position = new Position(x, y);
    }

    @Override
    public void initialize(String direction, int batteryLevel, int x, int y) {
        this.direction = Direction.valueOf(direction.toUpperCase());
        this.position = new Position(x, y);
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void fly() {
        
        if (getBatteryLevel() <= 0) 
            return;

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
    }

    @Override
    public void turnRight() {
        
        if (getBatteryLevel() <= 0) 
            return;

        direction = direction.turnRight();
    }

    @Override
    public void turnLeft() {
    
        if (getBatteryLevel() <= 0) 
            return;        

        direction = direction.turnLeft();
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
        return command;
    }

    @Override
    public JSONObject stop() {
        JSONObject command = new JSONObject();
        command.put("action", "stop");
        return command;
    }

    @Override
    public JSONObject echo(Direction direction) {
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", direction.toString()));

        JSONObject response = new JSONObject();
        response.put("action", "echo");
        response.put("extras", new JSONObject());

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

    public enum Direction {
        NORTH, 
        EAST, 
        SOUTH, 
        WEST;

    public Direction turnRight() {
        switch (this) {
            case NORTH: 
                return EAST;
            case EAST: 
                return SOUTH;
            case SOUTH: 
                return WEST;
            case WEST: 
                return NORTH;
            default: throw new IllegalStateException("Unexpected value");
        }
    }

    public Direction turnLeft() {
        switch (this) {
            case NORTH: 
                return WEST;
            case WEST: 
                return SOUTH;
            case SOUTH: 
                return EAST;
            case EAST: 
                return NORTH;
            default: throw new IllegalStateException("Unexpected value");

        }
    }

}
}
