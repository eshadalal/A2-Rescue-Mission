package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

class Radar { 

    private Drone drone;

    public Radar(Drone drone) { 
        this.drone = drone;
    }

    public void processRadarData(JSONObject response) {
        JSONObject extraInfo = response.getJSONObject("extras");
        int range = extraInfo.getInt("range"); 
        String found = extraInfo.getString("found"); 
        
        if (found.equals("GROUND")) {
            drone.fly(); // Safe to explore area
        } else if (found.equals("OUT_OF_RANGE")) {
            decideTurnDirection();
        }
    }

    public void decideTurnDirection() {
        JSONObject leftScan = drone.echo(Drone.Direction.WEST); 
        JSONObject rightScan = drone.echo(Drone.Direction.EAST); 

        int leftRange = leftScan.getJSONObject("extras").getInt("range");
        int rightRange = rightScan.getJSONObject("extras").getInt("range");

        if (leftRange == 0 && rightRange == 0) {
            drone.turnLeft(); 
            drone.turnLeft();
        } else if (leftRange >= 1 && rightRange == 0) {
            if (leftRange == 1) { 
                drone.turnLeft();
                drone.turnLeft(); 
            } else {
                drone.turnLeft(); 
            }
        } else if (rightRange >= 1 && leftRange == 0) { 
            if (rightRange == 1) { 
                drone.turnRight();
                drone.turnRight(); 
            } else {
                drone.turnRight(); 
            }
        } else {
            if (leftRange > rightRange) {
                drone.turnLeft();  // Turn left
            } else {
                drone.turnRight();  // Turn right
            }
        }
    }
}