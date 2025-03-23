package ca.mcmaster.se2aa4.island.team38;

public class PointOfInterest {

    private Position position;
    private String id;
    private PointOfInterestType type;

    public PointOfInterest(Position position, String id, PointOfInterestType type) {
        this.position = new Position(position.getX(), position.getY());
        this.id = id;
        this.type = type;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Position getPosition() {
        return position;
    }

    public String getID() {
        return id;
    }

    public PointOfInterestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "position=" + position +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }

}