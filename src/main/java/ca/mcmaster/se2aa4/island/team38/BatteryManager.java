package ca.mcmaster.se2aa4.island.team38;

public class BatteryManager {

    private int batteryLevel;

    public BatteryManager(int initialBatteryLevel) {
        this.batteryLevel = initialBatteryLevel;
    }

    public boolean decreaseBattery(int cost) {
        batteryLevel -= cost;
        return true;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isBatteryDepleted() {
        return batteryLevel <= 0;
    }
}
