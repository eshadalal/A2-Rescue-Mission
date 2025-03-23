package ca.mcmaster.se2aa4.island.team38;

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
