package ca.mcmaster.se2aa4.island.team38;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private String direction;
    private int batteryLevel;
    private String creekId = null;
    private boolean foundCreek = false;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        direction = info.getString("heading");
        batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    public String getDirection() {
        return direction;
    }


    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        if (!foundCreek) {
            decision.put("action", "scan");  // look for a creek
        } else {
            decision.put("action", "stop");  // stop if a creek has been found 
        }

        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        if (status.equals("success")) {
            JSONObject extraInfo = response.getJSONObject("extras");
            logger.info("Additional information received: {}", extraInfo);
                if (extraInfo.has("creek")) {
                    this.creekId = response.getString("creek");
                    this.foundCreek = true;
                    logger.info("Creek found: {}", creekId);
            }
        }
    }

    @Override
    public String deliverFinalReport() {
        if (foundCreek) {
            return this.creekId;
        } else {
            return "no creek found";
    }
    }

}
