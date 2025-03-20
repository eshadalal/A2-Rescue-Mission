package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class MissionControl {

    private Drone drone;
    private BatteryManager batteryManager;
    private boolean hasFoundLand = false;
    private boolean foundCreek = false;
    private Radar radar;
    private Action action;

    public MissionControl(Drone drone, BatteryManager batteryManager) {
        this.drone = drone;
        this.batteryManager = batteryManager;
        this.radar = new Radar(drone);
    }

    public JSONObject determineMove() {
        JSONObject decision = new JSONObject();

        if (batteryManager.getBatteryLevel() < 5 || foundCreek) {
            decision.put("action", "stop");
            return decision;
        }

        if (!hasFoundLand) {
            JSONObject echoNose = drone.echoForward();
            JSONObject echoLeft = drone.echoLeft();
            JSONObject echoRight = drone.echoRight();

            if (echoNose != null && echoLeft != null && echoRight != null) {
                if (radar.processRadarData(echoNose) || radar.processRadarData(echoLeft) || radar.processRadarData(echoRight)) {
                    hasFoundLand = true;
                    decision.put("action", "scan");
                } else {
                    decision.put("action", "fly");
                }
            } else {
                decision.put("action", "turnLeft");  
            }
        } else {
            decision.put("action", "scan");
        }

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
                decision.put("action", "stop");
                System.out.println("Creek detected, stopping.");
            }
        }
    }

    public JSONObject takeAction() {
        switch (this.action) {
            case SCAN -> {
                return drone.scan();
            }
            case ECHOFORWARD -> {
                return drone.echoForward();
            }
            case ECHORIGHT -> {
                return drone.echoRight();
            }
            case ECHOLEFT -> {
                return drone.echoLeft();
            }
            case FLY -> {
                return drone.fly();
            }
            case TURNRIGHT -> {
                return drone.turnRight();
            }
            case TURNLEFT -> {
                return drone.turnLeft();
            }
            case STOP -> {
                return drone.stop();
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.action);
        }
    }
}
