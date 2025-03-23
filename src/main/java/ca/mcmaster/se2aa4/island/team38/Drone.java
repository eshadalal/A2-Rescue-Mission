package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private BatteryManager batteryManager;
    private int y; 
    private int x; 

    public Drone(Direction direction, int battery, int x, int y) {
        this.direction = direction;
        this.position = new Position(x, y);
        this.batteryManager = new BatteryManager(battery);
    }

    public void startCoordinates() { 
        y = 0;
        x = 0;
    }

    private boolean batteryCheck() {
        return batteryManager.getBatteryLevel() > 10;
    }

    @Override
    public JSONObject fly() {
        if (!batteryCheck()) {
            return stop();
        }
        updatePositionAfterFly();
        JSONObject request = new JSONObject();
        request.put("action", "fly");
        updateBattery("fly");
        return request;
    }

    private void updatePositionAfterFly() {
        switch (direction.toString()) {
            case "N": position.updateY(1); break;
            case "S": position.updateY(-1); break;
            case "E": position.updateX(1); break;
            case "W": position.updateX(-1); break;
        }
    }

    @Override
    public JSONObject turnRight() {
        if (!batteryCheck()) {
            return stop();
        }
        this.direction = this.direction.turnRight();
        JSONObject request = new JSONObject();
        request.put("action", "heading");
        request.put("parameters", new JSONObject().put("direction", this.direction.toString()));
        updateBattery("heading");
        return request;
    }

    
    @Override
    public JSONObject turnLeft() {
        if (!batteryCheck()) {
            return stop();
        }
        this.direction = this.direction.turnLeft();
        JSONObject request = new JSONObject();
        request.put("action", "heading");
        request.put("parameters", new JSONObject().put("direction", this.direction.toString()));
        updateBattery("heading");
        return request;
    }

    @Override
    public JSONObject scan() {
        if (!batteryCheck()) {
            return stop();
        }
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
        if (!batteryCheck()) {
            return stop();
        }
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", this.direction.toString()));
        updateBattery("echo");
        return request;
    }

    @Override
    public JSONObject echoRight() {
        if (!batteryCheck()) {
            return stop();
        }
        JSONObject request = new JSONObject();
        request.put("action", "echo");
        request.put("parameters", new JSONObject().put("direction", this.direction.turnRight().toString()));
        updateBattery("echo");
        return request;
    }

    @Override
    public JSONObject echoLeft() {
        if (!batteryCheck()) {
            return stop();
        }
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

    @Override
    public int getBatteryLevel() {
        return batteryManager.getBatteryLevel();
    }

}
