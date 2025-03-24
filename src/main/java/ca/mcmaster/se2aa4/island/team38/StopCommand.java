package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public class StopCommand implements DroneCommand {
    @Override
    public JSONObject execute(Drone drone) {
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        drone.updateBattery("stop");
        return request;
    }
}
