package ca.mcmaster.se2aa4.island.team38;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private String direction;
    private Drone drone; 
    private int batteryLevel;
    private String creekId = null;
    private boolean foundCreek = false;
    private boolean lastActionScan = false;
    private List<PointsOfInterest> pointsOfInterest = new ArrayList<>();
    
    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        
        direction = info.getString("heading");
        batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        drone = new Drone(direction, batteryLevel, 0, 0); // Assuming (0,0) is the starting position
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        
        if (foundCreek) {
            decision.put("action", "stop");  // Stop when a creek is found
        } else if (!lastActionScan) {
            decision.put("action", "scan"); // Switch to scanning after echoing
            lastActionScan = true;
        } else {
            decision.put("action", "echo"); // Echo to detect obstacles
            decision.put("parameters", new JSONObject().put("direction", direction));
            lastActionScan = false;
        }
        
        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n" + response.toString(2));

        int cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        batteryLevel -= cost;
        logger.info("The remaining battery level is {}", batteryLevel);

        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        drone.getInfo(cost, extraInfo); // Update drone info
        // Check if a creek is found
        if (extraInfo.has("creeks")) {
            JSONArray creeks = extraInfo.getJSONArray("creeks");
            for (int i = 0; i < creeks.length(); i++) {
                String creek = creeks.getString(i);
                logger.info("Creek found: {}", creek);
            }
            this.foundCreek = true;
}
    }

    @Override
    public String deliverFinalReport() {
        if (pointsOfInterest.isEmpty()) {
            return "No creek found";
        }
        StringBuilder report = new StringBuilder("Creeks found: ");
        for (PointsOfInterest point : pointsOfInterest) {
            report.append(point); // need to append the closest creek id
        }
        return report.toString();
    }
}
