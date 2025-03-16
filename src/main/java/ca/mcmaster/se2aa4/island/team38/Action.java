package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Action {

    private Drone drone;
    private BatteryManager batteryManager;
    private PointsOfInterest pointsOfInterest;
    private boolean hasFoundLand = false;
    private boolean foundCreek = false;

    public Action(Drone drone, BatteryManager batteryManager, PointsOfInterest pointsOfInterest) {
        this.drone = drone;
        this.batteryManager = batteryManager;
        this.pointsOfInterest = pointsOfInterest;
    }

    public String determineAction() {
        JSONObject decision = new JSONObject();

        if (foundCreek) {
            decision.put("action", "stop");
        } else if (!hasFoundLand) {
            JSONObject echoResult = drone.echoForward();
            drone.logAction(echoResult.toString());
            JSONObject extras = echoResult.optJSONObject("extras");

            if (extras != null && extras.has("found")) {
                String found = extras.getString("found");
                if (found.equals("GROUND")) {
                    hasFoundLand = true;
                } else {
                    drone.turnLeft();
                }
            } else {
                System.out.println("Warning: Missing 'found' key in echo response.");
                decision.put("action", "scan");  // Default safe action
            }
        } else {
            decision.put("action", "scan");
        }

        System.out.println("Decision made: " + decision.toString());
        return decision.toString();
    }
}
