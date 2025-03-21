package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private BatteryManager batteryManager;

    public Drone(Direction direction, int battery, int x, int y) {
        this.direction = direction;
        this.position = new Position(x, y);
        this.batteryManager = new BatteryManager(battery); 
    }

    @Override
    public JSONObject fly() {
        JSONObject request = new JSONObject();
        request.put("action", "fly");
        updateBattery("fly");
        return request;
    }

    @Override
    public JSONObject turnRight() {
        JSONObject request = new JSONObject();
        request.put("action", "heading");
        request.put("parameters", new JSONObject().put("direction", this.direction.turnRight().toString()));
        updateBattery("heading");
        return request;
    }

    @Override
    public JSONObject turnLeft() {
        JSONObject request = new JSONObject();
        request.put("action", "heading");
        request.put("parameters", new JSONObject().put("direction", this.direction.turnLeft().toString()));
        updateBattery("heading");
        return request;
    }

    @Override
    public JSONObject scan() {
        JSONObject request = new JSONObject();
        request.put("action", "scan");
        updateBattery("scan");
        return request;
    }

    @Override
    public JSONObject stop() {
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        updateBattery("stop");
        return request;
    }

    @Override
    public JSONObject echoForward() {
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", this.direction.toString()));
        updateBattery("echo");
        return request;
    }

    @Override
    public JSONObject echoRight() {
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", this.direction.turnRight().toString()));
        updateBattery("echo");
        return request;
    }

    @Override
    public JSONObject echoLeft() {
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", this.direction.turnLeft().toString()));
        updateBattery("echo");
        return request;
    }

    @Override
    public void updateBattery(String action) {
        int cost = getActionCost(action);  
        batteryManager.decreaseBattery(cost); 
    }

    @Override
    public int getActionCost(String action) {
        switch (action) {
            case "fly":
                return 2;
            case "heading":
                return 4;
            case "scan":
                return 2;
            case "stop":
                return 3;
            case "echo":
                return 1;
            default:
                return 0;
        }
    }
    
    public Position getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getBatteryLevel() {
        return this.batteryManager.getBatteryLevel(); 
    }
}
