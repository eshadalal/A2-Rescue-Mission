package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class MissionControl {

    private Drone drone;
    private BatteryManager batteryManager;
    private boolean hasFoundLand = false;
    private boolean foundCreek = false;
    private Radar radar;

    public MissionControl(Drone drone, BatteryManager batteryManager) {
        this.drone = drone;
        this.batteryManager = batteryManager;
        this.radar = new Radar(drone);  
    }

    public JSONObject determineMove() {
        JSONObject decision = new JSONObject();

        // Check battery level or if creek has been found
        if (batteryManager.getBatteryLevel() <= 5 || foundCreek) {
            drone.performAction(Action.STOP);
            decision.put("action", "stop");
        }
        else if (!hasFoundLand) {
            // Get echo data for the different directions
            JSONObject echoNose = drone.echoForward();
            JSONObject echoLeft = drone.echoLeft();
            JSONObject echoRight = drone.echoRight();

            if (echoNose != null && echoLeft != null && echoRight != null) {
                radar.processRadarData(echoNose);
                radar.processRadarData(echoLeft);
                radar.processRadarData(echoRight);

                hasFoundLand = true;
                drone.performAction(Action.SCAN);
                decision.put("action", "scan");
                System.out.println("Ground found, scanning.");
            } else {
                radar.decideTurnDirection();  // Turn if no ground found
            }
        } else {
            // Continue scanning if land has already been found
            drone.performAction(Action.SCAN);
            decision.put("action", "scan");
            System.out.println("Scanning for new points of interest.");
        }

        // Detect creek
        detectCreek(decision);

        System.out.println("Decision made: " + decision.toString());
        return decision;
    }

    public void detectCreek(JSONObject decision) {
        JSONObject scanResult = drone.scan();  
        if (scanResult != null && scanResult.has("extras")) {
            JSONObject extras = scanResult.getJSONObject("extras");

            if (extras.has("creeks") && extras.getJSONArray("creeks").length() > 0) {
                foundCreek = true;
                drone.performAction(Action.STOP);
                decision.put("action", "stop");
                System.out.println("Creek detected, stopping.");
            }
        }
    }
}
