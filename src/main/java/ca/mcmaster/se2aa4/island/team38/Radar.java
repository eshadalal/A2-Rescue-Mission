package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Radar {

    private Drone drone;

    public Radar(Drone drone) {
        this.drone = drone;
    }

    public boolean processRadarData(JSONObject response) {
        if (response.has("extras")) {
            JSONObject extraInfo = response.getJSONObject("extras");
            int range = extraInfo.getInt("range");
            String found = extraInfo.getString("found");

            if (found.equals("GROUND")) {
                return true;
            } else if (found.equals("OUT_OF_RANGE")) {
                // If out of range, decide on a turn direction
                decideTurnDirection();
            }
        } else {
            System.out.println("No 'extras' field found in the response.");
        }
        return false;
    }

    public void decideTurnDirection() {
        JSONObject leftScan = drone.echoLeft();
        JSONObject rightScan = drone.echoRight();

        if (leftScan != null && rightScan != null) {
            int leftRange = leftScan.getJSONObject("extras").getInt("range");
            int rightRange = rightScan.getJSONObject("extras").getInt("range");

            if (leftRange == 0 && rightRange == 0) {
                // Turn around if both directions are blocked
                drone.turnLeft();
                drone.turnLeft();
            } else if (leftRange >= 1 && rightRange == 0) {
                // If only left direction has a valid range, turn left
                drone.turnLeft();
            } else if (rightRange >= 1 && leftRange == 0) {
                // If only right direction has a valid range, turn right
                drone.turnRight();
            } else {
                // If both directions are valid, choose the direction with the greater range
                if (leftRange > rightRange) {
                    drone.turnLeft();
                } else {
                    drone.turnRight();
                }
            }
        } else {
            System.out.println("Error: Invalid scan data, unable to determine direction.");
        }
    }
}
