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

    public JSONObject determineAction() {
        JSONObject decision = new JSONObject();

        if (foundCreek) {
            decision.put("action", "stop");
        } else if (!hasFoundLand) {
            JSONObject echoResult = drone.echoForward();
            drone.logAction(echoResult.toString(), echoResult);  

            if (echoResult != null) {
                JSONObject extras = echoResult.optJSONObject("extras");
                if (extras != null && extras.has("found")) {
                    String found = extras.getString("found");
                    if ("GROUND".equals(found)) {
                        hasFoundLand = true;  
                        decision.put("action", "land");
                    } else {
                        drone.turnLeft();  
                        decision.put("action", "turn_left");
                    }
                } else {
                    System.out.println("Warning: 'extras' or 'found' key missing in echo response.");
                    decision.put("action", "scan");  
                }
            } else {
                System.out.println("Warning: echoResult is null.");
                decision.put("action", "scan"); 
            }
        } else {
            decision.put("action", "scan");  
        }

        System.out.println("Decision made: " + decision.toString());
        return decision;
    }

}