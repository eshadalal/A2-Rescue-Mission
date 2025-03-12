package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;
import eu.ace_design.island.bot.IExplorerRaid;


public class Drone {
    
    private String direction;
    private int batteryLevel;
    private Position position;

    public Drone(String direction, int batteryLevel, int x, int y) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
        this.position = new Position(x, y);
    }

    public void fly() { // simply move forward in current direction
        if (batteryLevel <= 0) {
            System.out.println("DRONE LOST");
            return;
        }

        if (direction.equals("NORTH")) {
            position.updateY(1);
        } else if (direction.equals("SOUTH")) {
            position.updateY(-1);
        } else if (direction.equals("EAST")) {
            position.updateX(1);
        } else if (direction.equals("WEST")) {
            position.updateX(-1);

        }

        batteryLevel--;
    }

    public void turnRight() {

        if (direction.equals("NORTH")) {
            direction = "EAST";
        } else if (direction.equals("SOUTH")) {
            direction = "WEST";
        } else if (direction.equals("EAST")) {
            direction = "SOUTH";
        } else if (direction.equals("WEST")) {
            direction = "NORTH";
        }

    }

    public void turnLeft() {

        if (direction.equals("NORTH")) {
            direction = "WEST";
        } else if (direction.equals("SOUTH")) {
            direction = "EAST";
        } else if (direction.equals("EAST")) {
            direction = "NORTH";
        } else if (direction.equals("WEST")) {
            direction = "SOUTH";
        }
    }

    public void scan() {

    }

    public void stop() {

    }

    public void getInfo(Integer cost, JSONObject extraInfo) { 
        
    }

    public Position getPosition() {
        return this.position;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }

}
