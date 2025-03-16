package ca.mcmaster.se2aa4.island.team38;

public class PointOfInterest {

    private Position position;
    private String id;
    private PointsOfInterest.PointOfInterestType type;

    public PointOfInterest(Position position, String id, PointsOfInterest.PointOfInterestType type) {
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
        return this.position;
    }

    public String getID() {
        return id;
    }

    public PointsOfInterest.PointOfInterestType getPointOfInterestType() {
        return this.type;
    }
}
