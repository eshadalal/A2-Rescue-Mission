package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class MissionControl {
    private Drone drone;
    private BatteryManager batteryManager;
    private PointsOfInterest pointsOfInterest;
    // private DecideAction action;

    public MissionControl(Drone drone, BatteryManager batteryManager) {
        this.drone = drone;
        this.batteryManager = batteryManager;
        this.pointsOfInterest = new PointsOfInterest();
    }

    public JSONObject determineMove() {
        if (batteryManager.getBatteryLevel() < 10) {
            return drone.stop();
        }
        
        return drone.echoForward(); // placeholder for now 
        // return action.chooseAction(Drone drone, DroneResponse response, PointsOfInterest map);
    }

    public void acknowledgeResults(JSONObject result) {
        if (result.getString("status").equals("OK")) {
            JSONObject extras = result.optJSONObject("extras");
            if (extras != null) {
                PointsOfInterest.processResponse(extras, pointsOfInterest, drone);
            }
        }
    }

    public String deliverFinalReport() {
        return pointsOfInterest.generateFinalReport(drone.getPosition());
    }
}
