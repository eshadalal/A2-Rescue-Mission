package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public class EchoRightCommand implements DroneCommand {
    @Override
    public JSONObject execute(Drone drone) {
        if (!drone.batteryCheck()) {
            return drone.stop();
        }
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", drone.getDirection().turnRight().toString()));
        drone.updateBattery("echo");
        return request;
    }
}
