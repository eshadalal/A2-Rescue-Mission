package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class Radar {
    private Drone drone;
    private Set<Position> obstacles = new HashSet<>();

    public Radar(Drone drone) {
        this.drone = drone;
    }

    public boolean processRadarData(JSONObject response) {
        if (response.has("extras")) {
            JSONObject extras = response.getJSONObject("extras");
            String found = extras.getString("found");
            
            if ("OUT_OF_RANGE".equals(found)) {
                markCurrentPositionAsObstacle();
                return false;
            }
            return "GROUND".equals(found);
        }
        return false;
    }

    private void markCurrentPositionAsObstacle() {
        obstacles.add(new Position(
            drone.getPosition().getX(),
            drone.getPosition().getY()
        ));
    }

    public Set<Position> getObstacles() { return obstacles; }
}