package ca.mcmaster.se2aa4.island.team38;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PointsOfInterest {
    private List<PointOfInterest> creeks = new ArrayList<>();
    private PointOfInterest emergencySite;

    public void addPointOfInterest(Position position, String id, PointOfInterestType type) {
        PointOfInterest poi = new PointOfInterest(position, id, type);
        if (type == PointOfInterestType.CREEK) {
            creeks.add(poi);
        } else if (type == PointOfInterestType.SITE) {
            emergencySite = poi;
        }
    }

    public boolean hasEmergencySite() {
        return (emergencySite != null);
    }

    public static void processResponse(JSONObject extras, PointsOfInterest poi, Drone drone) {
        if (extras.has("creeks")) {
            JSONArray creeks = extras.getJSONArray("creeks");
            for (int i = 0; i < creeks.length(); i++) {
                String id = creeks.getString(i);
                poi.addPointOfInterest(drone.getPosition(), id, PointOfInterestType.CREEK);
            }
        }

        if (extras.has("sites")) {
            JSONArray sites = extras.getJSONArray("sites");
            for (int i = 0; i < sites.length(); i++) {
                String id = sites.getString(i);
                poi.addPointOfInterest(drone.getPosition(), id, PointOfInterestType.SITE);
            }
        }
    }

    public String generateFinalReport() {
        if (emergencySite == null || creeks.isEmpty()) {
            return "No emergency site or creeks found.";
        }
        return "Emergency site found. Closest creek: " + creeks.get(0).getID();
    }
}