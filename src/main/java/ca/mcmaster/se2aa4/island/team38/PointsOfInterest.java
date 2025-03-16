package ca.mcmaster.se2aa4.island.team38;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PointsOfInterest {

    private List<PointOfInterest> creeks = new ArrayList<>();
    private PointOfInterest emergencySite;

    public enum PointOfInterestType {
        CREEKS, 
        SITES, 
        BIOMES;
    }

    public PointsOfInterest() {
        this.creeks = new ArrayList<>();
        this.emergencySite = null;
    }

    public void addPointOfInterest(Position position, String id, PointOfInterestType type) {
        PointOfInterest pointOfInterest = new PointOfInterest(position, id, type);
        
        if (type == (PointOfInterestType.CREEKS)) {
            this.creeks.add(pointOfInterest);
        } else if (type == (PointOfInterestType.SITES)) {
            this.emergencySite = pointOfInterest;
        }
    }

    public PointOfInterest getEmergencySite() {
        return this.emergencySite;
    }

    public List<PointOfInterest> getCreeks() {
        return this.creeks;
    }

    public PointOfInterest findClosestCreek() {
        if (emergencySite == null || creeks.isEmpty()) {
            return null; 
        }

        double site_x = emergencySite.getX();
        double site_y = emergencySite.getY();
        double smallestDistance = Double.MAX_VALUE;
        PointOfInterest closestCreek = null;

        for (PointOfInterest creek : creeks) { // go through list of creeks found
            double creek_x = creek.getX();
            double creek_y = creek.getY();
            double distance = Math.sqrt(Math.pow(site_x - creek_x, 2) + Math.pow(site_y - creek_y, 2));
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestCreek = creek;
            }
        }
        return closestCreek;
    }

    public static void processResponse(JSONObject extras, PointsOfInterest pointsOfInterest, Drone drone) {
        if (extras.has("creeks")) {
            JSONArray creeksArray = extras.getJSONArray("creeks");
            for (int i = 0; i < creeksArray.length(); i++) {
                String creekID = creeksArray.getString(i);
                Position position = drone.getPosition();  
                pointsOfInterest.addPointOfInterest(position, creekID, PointOfInterestType.CREEKS);
            }
        }

        if (extras.has("site")) {
            String siteID = extras.getString("site");
            Position position = drone.getPosition();  
            pointsOfInterest.addPointOfInterest(position, siteID, PointOfInterestType.SITES);
        }
    }

    public String generateFinalReport(Drone drone) {    
        if (this.getCreeks().isEmpty() || this.getEmergencySite() == null) {
            return "No creek found or emergency site missing.";
        }
        return "Emergency site located at " + this.getEmergencySite().getPosition() + 
            ", closest creek at " + this.findClosestCreek().getPosition();
    }


    public class PointOfInterest {
    
        private Position position;
        private String id;
        private PointOfInterestType type;
    
        public PointOfInterest(Position position, String id, PointOfInterestType type) {
            this.position = new Position(position.getX(), position.getY());  
            this.id = id;
            this.type = type;
        }
    
        public int getX() {
            return position.getX();
        }
    
        public int getY() {
            return position.getY();
        }
    
        public Position getPosition() {
            return this.position;
        }
    
        public String getID() {
            return id;
        }
    
        public PointOfInterestType getPointOfInterestType() {
            return this.type;
        }
    }
}
