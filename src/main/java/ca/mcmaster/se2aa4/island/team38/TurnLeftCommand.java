package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public class TurnLeftCommand implements DroneCommand {
    @Override
    public JSONObject execute(Drone drone) {
        if (!drone.batteryCheck()) {
            return drone.stop();
        }
        drone.setDirection(drone.getDirection().turnLeft());
        JSONObject request = new JSONObject();
        request.put("action", "heading");
        request.put("parameters", new JSONObject().put("direction", drone.getDirection().toString()));
        drone.updateBattery("heading");
        return request;
    }
}
