package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Radar {

    private Drone drone;

    public Radar(Drone drone) {
        this.drone = drone;
    }

    public void processRadarData(JSONObject response) {
        // Check if 'extras' field exists
        if (response.has("extras")) {
            JSONObject extraInfo = response.getJSONObject("extras");
            int range = extraInfo.getInt("range");
            String found = extraInfo.getString("found");

            if (found.equals("GROUND")) {
                drone.fly(); // Safe to explore area
            } else if (found.equals("OUT_OF_RANGE")) {
                decideTurnDirection();
            }
        } else {
            System.out.println("No 'extras' field found in the response.");
        }
    }

    public void decideTurnDirection() {
        // Get the current direction of the drone
        Direction currentDirection = drone.getDirection();

        // Scan the left and right directions
        JSONObject leftScan = drone.echo(currentDirection.turnLeft());
        JSONObject rightScan = drone.echo(currentDirection.turnRight());

        int leftRange = leftScan.getJSONObject("extras").getInt("range");
        int rightRange = rightScan.getJSONObject("extras").getInt("range");

        if (leftRange == 0 && rightRange == 0) {
            drone.turnLeft();
            drone.turnLeft(); // Turn around if both directions are blocked
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
                drone.turnLeft();
            } else {
                drone.turnRight();
            }
        }
    }
}
