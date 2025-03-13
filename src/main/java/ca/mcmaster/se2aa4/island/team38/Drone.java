package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {
    
    private String direction;
    private int batteryLevel;
    private Position position;

    public Drone(String direction, int batteryLevel, int x, int y) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
        this.position = new Position(x, y);
    }

    @Override
    public void initialize(String direction, int batteryLevel, int x, int y) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
        this.position = new Position(x, y);
    }

    @Override
    public void fly() {
        if (batteryLevel <= 0) {
            System.out.println("DRONE LOST: Battery is empty.");
            return;
        }

        switch (direction) {
            case "NORTH":
                position.updateY(1);
                break;
            case "SOUTH":
                position.updateY(-1);
                break;
            case "EAST":
                position.updateX(1);
                break;
            case "WEST":
                position.updateX(-1);
                break;
            default:
                System.out.println("Invalid direction");
                return;
        }

        batteryLevel--;
    }

    @Override
    public void turnRight() {
        switch (direction) {
            case "NORTH": 
                direction = "EAST"; 
                break;
            case "EAST": 
                direction = "SOUTH"; 
                break;
            case "SOUTH": 
                direction = "WEST"; 
                break;
            case "WEST": 
                direction = "NORTH"; 
                break;
        }
        batteryLevel--;
    }

    @Override
    public void turnLeft() {
        switch (direction) {
            case "NORTH": 
                direction = "WEST"; 
                break;
            case "WEST": 
                direction = "SOUTH"; 
                break;
            case "SOUTH": 
                direction = "EAST"; 
                break;
            case "EAST": 
                direction = "NORTH"; 
                break;
        }
        batteryLevel--;
    }

    @Override
    public void scan() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void getInfo(Integer cost, JSONObject extraInfo) {
        System.out.println("Cost of the operation: " + cost);
        System.out.println("Additional Information: " + extraInfo.toString(2));
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getDirection() {
        return this.direction;
    }

    @Override
    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    public JSONObject echo(String direction) {
        // return the scan result as JSON
        JSONObject response = new JSONObject();
        JSONObject extraInfo = new JSONObject();

        // extraInfo.put("range", range);
        // extraInfo.put("found", found);

        response.put("extras", extraInfo);

        return response;
    }

}

