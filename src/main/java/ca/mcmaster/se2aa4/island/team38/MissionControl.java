package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class MissionControl {
    private Drone drone;
    private BatteryManager batteryManager;
    private PointsOfInterest pointsOfInterest;

    public MissionControl(Drone drone, BatteryManager batteryManager) {
        this.drone = drone;
        this.batteryManager = batteryManager;
        this.pointsOfInterest = new PointsOfInterest();
    }

    public JSONObject determineMove() {
        if (batteryManager.getBatteryLevel() < 10) {
            return drone.stop();
        }

        JSONObject scan = drone.scan();
        if (scan.has("extras")) {
            JSONObject extras = scan.getJSONObject("extras");
            PointsOfInterest.processResponse(extras, pointsOfInterest, drone);
        }

        if (pointsOfInterest.hasEmergencySite()) {
            return planReturnPath();
        }
        return followCoastline();
    }

    private JSONObject followCoastline() {
        // Coastline following logic
        return drone.fly();
    }

    private JSONObject planReturnPath() {
        // Pathfinding logic
        return drone.fly();
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
