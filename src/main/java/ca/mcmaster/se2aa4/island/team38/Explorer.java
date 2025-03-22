package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Explorer {
    private Drone drone;
    private BatteryManager batteryManager;
    private MissionControl missionControl;

    public Explorer() {
        this.drone = new Drone(Direction.EAST, 10000, 0, 0);
        this.batteryManager = new BatteryManager(10000);
        this.missionControl = new MissionControl(drone, batteryManager);
    }

    public String takeDecision() {
        JSONObject decision = missionControl.determineMove();
        return decision.toString();
    }

    public void acknowledgeResults(String response) {
        JSONObject result = new JSONObject(response);
        missionControl.acknowledgeResults(result);
    }

    public String deliverFinalReport() {
        return missionControl.deliverFinalReport();
    }
}