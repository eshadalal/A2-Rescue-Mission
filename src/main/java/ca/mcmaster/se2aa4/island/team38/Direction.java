package ca.mcmaster.se2aa4.island.team38;

public enum Direction {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private final String dir;

    Direction(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }

    public Direction turnRight() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction turnLeft() {
        switch (this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case EAST: return NORTH;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static Direction fromDir(String dir) {
        for (Direction direction : Direction.values()) {
            if (direction.getDir().equalsIgnoreCase(dir)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + dir);
    }
}
