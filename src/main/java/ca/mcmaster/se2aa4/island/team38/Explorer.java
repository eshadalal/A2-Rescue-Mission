package ca.mcmaster.se2aa4.island.team38;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import eu.ace_design.island.bot.IExplorerRaid;

/**
 * The Explorer class implements the main island exploration logic.
 * It serves as the interface between the game engine and our drone system,
 * handling initialization, decision making, and response processing.
 */
public class Explorer implements IExplorerRaid {

    // Logger for tracking exploration progress and debugging
    private final Logger logger = LogManager.getLogger();
    
    // The drone being controlled by this explorer
    private Drone drone;
    
    // Manages the drone's battery consumption
    private BatteryManager batteryManager;
    
    // Central controller for mission planning and execution
    private MissionControl missionControl;
    
    // Stores the last response from the drone
    private DroneResponse response;
    
    // Decision maker for determining next actions
    private DecideAction action;

    /**
     * Initializes the exploration with starting conditions.
     * Parses the initialization JSON and sets up mission components.
     *
     * @param s JSON string containing initial drone parameters:
     *          - heading: starting direction (N,S,E,W)
     *          - budget: starting battery level
     */
    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        
        // Parse the initialization JSON
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        
        // Extract starting parameters
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
        
        // Initialize mission components
        this.action = new DecideAction(Stage.START); // Start in initial stage
        this.missionControl = new MissionControl();   // Create mission controller
    }

    /**
     * Determines the next action for the drone to take.
     * Delegates decision making to MissionControl and logs the choice.
     *
     * @return JSON string representing the next action command
     */
    @Override
    public String takeDecision() {
        // Get next move from mission control
        JSONObject decision = missionControl.getMove();
        logger.info("** Decision: {}", decision.toString());
        
        // Format the response JSON
        JSONObject response = new JSONObject();
        response.put("action", decision.getString("action"));
        
        return response.toString();
    }

    /**
     * Processes the results of the last action taken.
     * Extracts cost, status, and additional information from the response.
     *
     * @param s JSON string containing the action results:
     *          - cost: battery consumed by last action
     *          - status: outcome of last action
     *          - extras: additional scan/echo data
     */
    @Override
    public void acknowledgeResults(String s) {
        // Parse the response JSON
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        
        // Forward to mission control for processing
        missionControl.acknowledgeResults(response);
        
        logger.info("** Response received:\n" + response.toString(2));
        
        // Extract and log response details
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    /**
     * Generates the final exploration report when mission is complete.
     * Delegates report generation to MissionControl.
     *
     * @return Final report string containing exploration results
     */
    @Override
    public String deliverFinalReport() {
        return missionControl.deliverFinalReport();
    }
}
