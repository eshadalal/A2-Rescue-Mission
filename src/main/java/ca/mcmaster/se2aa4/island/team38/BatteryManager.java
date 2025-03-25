package ca.mcmaster.se2aa4.island.team38;
//This class is used to keep track of the batterylevel of the drone
public class BatteryManager {

    private int batteryLevel;

    public BatteryManager(int initialBatteryLevel) {
        this.batteryLevel = initialBatteryLevel;
    }

    public void decreaseBattery(int cost) {
        batteryLevel -= cost;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

}
