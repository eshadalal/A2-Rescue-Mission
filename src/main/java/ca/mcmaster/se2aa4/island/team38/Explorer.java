package ca.mcmaster.se2aa4.island.team38;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;


import eu.ace_design.island.bot.IExplorerRaid;



public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private int batteryLevel;
    private boolean hasFoundLand = false;
    private boolean foundCreek = false;
    private boolean lastActionScan = false;
    private PointsOfInterest pointsOfInterest = new PointsOfInterest(new ArrayList<>());

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));

        String direction = info.getString("heading");
        batteryLevel = info.getInt("budget");

        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        // Initialize drone at (0,0)
        drone = new Drone(direction, batteryLevel, 0, 0);
        hasFoundLand = false;
        foundCreek = false;
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        if (foundCreek) {
            decision.put("action", "stop");
        } else if (!hasFoundLand) {
            JSONObject echoResult = drone.echoForward();
            JSONObject extras = echoResult.optJSONObject("extras");
            
            if (extras != null && extras.has("found")) {
                String found = extras.getString("found");
                if (found.equals("GROUND")) {
                    hasFoundLand = true;
                } else {
                    drone.turnLeft();
                }
            } else {
                logger.warn("Warning: Missing 'found' key in echo response.");
                decision.put("action", "scan");  // Default safe action
            }
        } else {
            decision.put("action", "scan");
        }

        logger.info("Decision made: {}", decision.toString());
        return decision.toString();  // ✅ Ensure a JSON response is always returned
    }


    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n" + response.toString(2));

        int cost = response.getInt("cost");
        batteryLevel -= cost;
        logger.info("Remaining battery level: {}", batteryLevel);

        String status = response.getString("status");
        logger.info("Drone status: {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");

        if (extraInfo.has("creeks")) {
            JSONArray creeks = extraInfo.getJSONArray("creeks");
            for (int i = 0; i < creeks.length(); i++) {
                String creek = creeks.getString(i);
                logger.info("Creek found: {}", creek);
                pointsOfInterest.addPointOfInterest(drone.getPosition(), creek, PointsOfInterest.PointOfInterestType.CREEKS);
                foundCreek = true;
            }
        }

        if (extraInfo.has("site")) {
            String site = extraInfo.getString("site");
            logger.info("Emergency site found: {}", site);
            pointsOfInterest.addPointOfInterest(drone.getPosition(), site, PointsOfInterest.PointOfInterestType.SITES);
        }

        drone.getInfo(cost, extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        if (pointsOfInterest.getCreeks().isEmpty() || pointsOfInterest.getEmergencySite() == null) {
            return "No creek found or emergency site missing.";
        }

        Position emergencySite = pointsOfInterest.getEmergencySite().getPosition();
        List<Position> creeks = pointsOfInterest.getCreeks().stream()
                        .map(PointsOfInterest.PointOfInterest::getPosition)
                        .collect(Collectors.toList());
        Set<Position> obstacles = getObstaclesFromMap(); // You need to implement this

        List<Position> shortestPath = PathFinder.findShortestPath(emergencySite, creeks, obstacles);

        if (shortestPath == null || shortestPath.isEmpty()) {
            return "No valid path found to a creek.";
        }

        Position closestCreekPos = shortestPath.get(shortestPath.size() - 1);
        PointsOfInterest.PointOfInterest closestCreek = pointsOfInterest.getCreeks().stream()
                .filter(creek -> creek.getPosition().equals(closestCreekPos))
                .findFirst()
                .orElse(null);

        String finalReport;
        if (closestCreek != null) {
            finalReport = "Closest creek to the emergency site: " + closestCreek.getID();
        } else {
            finalReport = "Path found, but no valid creek ID.";
        }

        logger.info("Final Report: " + finalReport);  // ✅ Log the final result
        return finalReport;  
    }


    private Set<Position> getObstaclesFromMap() {
        Set<Position> obstacles = new HashSet<>();

        // Loop through all scanned positions
        for (PointsOfInterest.PointOfInterest poi : pointsOfInterest.getCreeks()) {
            Position pos = poi.getPosition();
            
            // Example: If the tile is "OCEAN", mark it as an obstacle
            if (poi.getPointOfInterestType() == PointsOfInterest.PointOfInterestType.BIOMES) {
                obstacles.add(pos);
            }
        }

        return obstacles;
    }

    public int getBatteryLevel() {  // ✅ Add this to `Explorer.java`
        return this.batteryLevel;
    }
    
    public String getDirection() {  // ✅ Add this to `Explorer.java`
        return this.drone.getDirection();  // Assuming `drone` is tracking direction
    }
    

}
