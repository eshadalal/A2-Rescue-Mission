package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class MissionControl {
    private Drone drone;
    private BatteryManager batteryManager;
    private DecideAction action;
    private DroneResponse response;
    private String prevScan;
    private Position dronePos;
    private PointsOfInterest poi;
    private JSONObject move;

    public void initialize(Direction direction, Integer batteryLevel) {
        action = new DecideAction(Stage.START);
        response = new DroneResponse(new JSONObject());
        dronePos = new Position(0, 0);
        poi = new PointsOfInterest();
        batteryManager = new BatteryManager(batteryLevel);
        drone = new Drone(direction, batteryLevel);
    }

    public void determineMove() {
        action.chooseAction(drone, response, poi);
        move = action.getDecision();
        response = new DroneResponse(move);
        if (action.lastActionScan()) {
            prevScan = action.getLastScan();
        }
        drone.updatePositionAfterFly(response.getHeading(), move);
    }

    public JSONObject getMove() {
        this.determineMove();
        JSONObject actionCopy = move;
        return actionCopy;
    }

    public void acknowledgeResults(JSONObject result) {
        if (result.getString("status").equals("OK")) {
            JSONObject extras = result.optJSONObject("extras");
            if (extras != null) {
                PointsOfInterest.processResponse(extras, poi, drone);
            }
        }
    }

    public String deliverFinalReport() {
        return poi.generateFinalReport(drone.getPosition());
    }
}
