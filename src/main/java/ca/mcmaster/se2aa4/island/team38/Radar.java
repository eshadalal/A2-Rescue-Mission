package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Radar {

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
                drone.turnLeft(); // Turn around if left range is minimal
            } else {
                drone.turnLeft(); // Turn left if left range is greater
            }
        } else if (rightRange >= 1 && leftRange == 0) {
            if (rightRange == 1) {
                drone.turnRight();
                drone.turnRight(); // Turn around if right range is minimal
            } else {
                drone.turnRight(); // Turn right if right range is greater
            }
        } else {
            if (leftRange > rightRange) {
                drone.turnLeft(); // Turn left if left range is greater
            } else {
                drone.turnRight(); // Turn right if right range is greater
            }
        }
    }
}
