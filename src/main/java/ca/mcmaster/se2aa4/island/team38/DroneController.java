package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public interface DroneController {

    void initialize(String direction, int batteryLevel, int x, int y);
    void fly();
    void turnRight();
    void turnLeft();
    void scan();
    void stop();
    void getInfo(Integer cost, JSONObject extraInfo);
    Position getPosition();
    String getDirection();
    int getBatteryLevel();
}
