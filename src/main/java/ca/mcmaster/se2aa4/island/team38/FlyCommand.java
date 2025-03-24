package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public class FlyCommand implements DroneCommand {
    @Override
    public JSONObject execute(Drone drone) {
        if (!drone.batteryCheck()) {
            return drone.stop();
        }
        JSONObject request = new JSONObject();
        request.put("action", "fly");
        drone.updatePositionAfterFly(drone.getDirection().toString(), request);
        drone.updateBattery("fly");
        return request;
    }
}
