package ca.mcmaster.se2aa4.island.team38;
import org.json.JSONObject;

public interface DroneController {

    JSONObject fly();
    JSONObject turnRight();
    JSONObject turnLeft();
    JSONObject scan();
    JSONObject stop();
    JSONObject echoForward(); 
    JSONObject echoRight();
    JSONObject echoLeft();
    void updateBattery(String action);
    int getActionCost(String action);  
    Position getPosition();
    Direction getDirection();
    int getBatteryLevel();
}
