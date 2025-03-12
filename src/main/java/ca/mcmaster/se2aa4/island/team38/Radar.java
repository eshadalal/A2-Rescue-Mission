package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;
import eu.ace_design.island.bot.IExplorerRaid;

class Radar { 

    private Drone drone;

    public void processRadarData(JSONObject response) {
        JSONObject extraInfo = response.getJSONObject("extras");
        int range = extraInfo.getInt("range");
        String found = extraInfo.getString("found");

        if (found.equals("GROUND")) {
            drone.fly(); // safe to explore area
        } else if (found.equals("OUT_0F_RANGE")) {
            turn(); // turn either left or right 
        }
    }

    private void decideTurnDirection() {

        JSONObject leftScan = drone.echo("W"); // Check left
        JSONObject rightScan = drone.echo("E"); // Check right

        int leftRange = leftScan.getJSONObject("extras").getInt("range");
        int rightRange = rightScan.getJSONObject("extras").getInt("range");

        if (leftRange > rightRange) {
            drone.turnLeft();
        } else {
            drone.turnRight();
        }
    }

}