package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public interface DroneCommand {
    JSONObject execute(Drone drone);
}
