package ca.mcmaster.se2aa4.island.team38;

public class PointOfInterestFactory {
    public static PointOfInterest createPointOfInterest(Position position, String id, PointOfInterestType type) {
        switch (type) {
            case CREEK:
                return new PointOfInterest(position, id, PointOfInterestType.CREEK);
            case SITE:
                return new PointOfInterest(position, id, PointOfInterestType.SITE);
            case BIOME:
                return new PointOfInterest(position, id, PointOfInterestType.BIOME);
            default:
                throw new IllegalArgumentException("Unknown Point of Interest");
        }
    }
}
