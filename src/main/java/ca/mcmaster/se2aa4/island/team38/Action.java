package ca.mcmaster.se2aa4.island.team38;

public enum Action {
    FLY, TURN_RIGHT, TURN_LEFT, SCAN, STOP, ECHO;

    public static Action fromString(String action) {
        switch (action.toUpperCase()) {
            case "FLY":
                return FLY;
            case "TURN_RIGHT":
                return TURN_RIGHT;
            case "TURN_LEFT":
                return TURN_LEFT;
            case "SCAN":
                return SCAN;
            case "STOP":
                return STOP;
            case "ECHO":
                return ECHO;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
