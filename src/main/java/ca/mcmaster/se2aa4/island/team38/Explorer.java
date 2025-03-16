package ca.mcmaster.se2aa4.island.team38;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private BatteryManager batteryManager;
    private Radar radar; 
    private boolean hasFoundLand = false;
    private PointsOfInterest pointsOfInterest = new PointsOfInterest();
    private MissionControl missionControl;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(s));

        String direction = info.getString("heading");
        int initialBatteryLevel = info.getInt("budget");

        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", initialBatteryLevel);

        Direction droneDirection = Direction.fromDir(direction.toUpperCase());
        drone = new Drone(droneDirection, initialBatteryLevel, 0, 0);

        batteryManager = new BatteryManager(initialBatteryLevel);
        missionControl = new MissionControl(drone, batteryManager);  
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        decision.put("action", missionControl.determineMove());  
        logger.info("** Decision: {}", decision.toString(2));
        return decision.toString();  
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(s));
        logger.info("** Response received:\n" + response.toString(2));

        batteryManager.updateBatteryLevel(response.getInt("cost"));

        if (response.has("extras")) {
            PointsOfInterest.processResponse(response.getJSONObject("extras"), pointsOfInterest, drone);
        }
    }

    @Override
    public String deliverFinalReport() {
        return pointsOfInterest.generateFinalReport(drone);
    }
}
