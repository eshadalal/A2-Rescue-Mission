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
            // If no ground is detected in the current direction, decide the next direction to turn
            decideTurnDirection();
        }
    }

    public void decideTurnDirection() {
        JSONObject leftScan = drone.echo("WEST"); 
        JSONObject rightScan = drone.echo("EAST"); 

        int leftRange = leftScan.getJSONObject("extras").getInt("range");
        int rightRange = rightScan.getJSONObject("extras").getInt("range");

        if (leftRange > rightRange) {
            drone.turnLeft(); // Turn left
        } else {
            drone.turnRight(); // Turn right
        }
    }
}
