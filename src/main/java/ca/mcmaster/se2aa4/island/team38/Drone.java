package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class Drone implements DroneController {

    private Direction direction;
    private Position position;
    private BatteryManager batteryManager;
    private DroneCommand currentCommand;

    public Drone(Direction direction, int battery) {
        this.direction = direction;
        this.batteryManager = new BatteryManager(battery);
        this.position = new Position(0, 0);
    }

    public boolean batteryCheck() {
        return batteryManager.getBatteryLevel() > 10;
    }

    @Override
    public JSONObject fly() {
        currentCommand = new FlyCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject turnRight() {
        currentCommand = new TurnRightCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject turnLeft() {
        currentCommand = new TurnLeftCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject scan() {
        currentCommand = new ScanCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject stop() {
        currentCommand = new StopCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoForward() {
        currentCommand = new EchoForwardCommand(); 
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoRight() {
        currentCommand = new EchoRightCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public JSONObject echoLeft() {
        currentCommand = new EchoLeftCommand();  
        return currentCommand.execute(this);
    }

    @Override
    public void updateBattery(String action) {
        int cost = getActionCost(action);
        batteryManager.decreaseBattery(cost);
    }

    @Override
    public int getActionCost(String action) {
        switch (action) {
            case "fly": return 2;
            case "heading": return 4;
            case "scan": return 2;
            case "stop": return 3;
            case "echo": return 1;
            default: return 0;
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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void updatePositionAfterFly(String direction, JSONObject move) {
        switch (direction) {
            case "N": position.updateY(1); break;
            case "S": position.updateY(-1); break;
            case "E": position.updateX(1); break;
            case "W": position.updateX(-1); break;
        }
    }


}
