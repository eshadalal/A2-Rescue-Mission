package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class DroneResponse {

    private Integer cost; // The cost of the operation/action
    private JSONObject extras; // Additional extras included in the response
    private String status; // The status of the drone given in the response

    DroneResponse(JSONObject response) {
        this.cost = response.getInt("cost");
        this.status = response.getString("status");
        this.extras = response.getJSONObject("extras");
    }

    public int getCost() {
        return this.cost;
    }

    public JSONObject getExtras() {
        return this.extras;
    }

    public String getStatus() {
        return this.status;
    }
}

