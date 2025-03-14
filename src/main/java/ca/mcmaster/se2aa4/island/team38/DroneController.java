package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team38.Drone.Direction;


public interface DroneController {

    void initialize(String direction, int batteryLevel, int x, int y);
    void fly();
    void turnRight();
    void turnLeft();
    JSONObject scan();
    JSONObject stop();
    JSONObject echo(Direction direction);
    JSONObject echoForward(); 
    JSONObject echoRight();
    JSONObject echoLeft();
    void getInfo(int cost, JSONObject extraInfo);
    Position getPosition();
    String getDirection();
    int getBatteryLevel();
}
