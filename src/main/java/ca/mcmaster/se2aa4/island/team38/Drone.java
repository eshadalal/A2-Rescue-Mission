package ca.mcmaster.se2aa4.island.team38;

public class Drone {
    
    private String direction;
    private int batteryLevel;

    public Drone(String direction, int batteryLevel) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
    }

    public void fly() { // simply move forward in current direction

        if (direction.equals("NORTH")) {
          
        } else if (direction.equals("SOUTH")) {

        } else if (direction.equals("EAST")) {

        } else if (direction.equals("WEST")) {

        }

    }

    public void turnRight() {


    }

    public void turnLeft() {

    }

    public void scan() {

    }

    public void stop() {

    }

    public void report(String status) {
    }
}
