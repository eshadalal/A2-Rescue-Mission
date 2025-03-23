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
        // Process creeks
        if (extras.has("creeks")) {
            JSONArray creeksArray = extras.getJSONArray("creeks");
            for (int i = 0; i < creeksArray.length(); i++) {
                String id = creeksArray.getString(i);
                Position dronePosition = drone.getPosition();
                poi.addPointOfInterest(dronePosition, id, PointOfInterestType.CREEK);
            }
        }

        // Process sites
        if (extras.has("sites")) {
            JSONArray sitesArray = extras.getJSONArray("sites");
            for (int i = 0; i < sitesArray.length(); i++) {
                String id = sitesArray.getString(i);
                Position dronePosition = drone.getPosition();
                poi.addPointOfInterest(dronePosition, id, PointOfInterestType.SITE);
            }
        }
    }

    public String generateFinalReport() {
        if (emergencySite == null || creeks.isEmpty()) {
            return "No emergency site or creeks found.";
        }
        return "Emergency site found. Closest creek: " + creeks.get(0).getID();
    }

    public List<PointOfInterest> getCreeks() {
        return new ArrayList<>(creeks);
    }

    public PointOfInterest getEmergencySite() {
        return emergencySite;
    }

    public String getSiteID() {
        if (emergencySite != null) {
            return emergencySite.getID();
        }
        return "No emergency site found.";
    }
}
