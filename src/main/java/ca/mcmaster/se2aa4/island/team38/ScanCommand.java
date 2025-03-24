package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public class ScanCommand implements DroneCommand {
    @Override
    public JSONObject execute(Drone drone) {
        if (!drone.batteryCheck()) {
            return drone.stop();
        }
        JSONObject request = new JSONObject();
        request.put("action", "scan");
        drone.updateBattery("scan");
        return request;
    }
}
