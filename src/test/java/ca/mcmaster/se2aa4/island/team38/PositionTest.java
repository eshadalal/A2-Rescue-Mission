package ca.mcmaster.se2aa4.island.team38;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testFindClosestCreek() {
        Position emergencySitePosition = new Position(2, 2);
        Position creek1Position = new Position(1, 1);
        Position creek2Position = new Position(10, 10);

        PointOfInterest emergencySite = new PointOfInterest(emergencySitePosition, "site1", PointOfInterestType.SITE);
        PointOfInterest creek1 = new PointOfInterest(creek1Position, "creek1", PointOfInterestType.CREEK);
        PointOfInterest creek2 = new PointOfInterest(creek2Position, "creek2", PointOfInterestType.CREEK);

        List<PointOfInterest> creeks = Arrays.asList(creek1, creek2);

        Position position = new Position(0, 0);
        PointOfInterest closestCreek = position.findClosestCreek(creeks, emergencySite);
        assertEquals("creek1", closestCreek.getID());
    }

    @Test
    public void testNoCreeks() {
        Position emergencySitePosition = new Position(2, 2);
        Position position = new Position(0, 0);
        PointOfInterest emergencySiteNoCreek = new PointOfInterest(emergencySitePosition, "site1", PointOfInterestType.SITE);
        PointOfInterest closestCreek = position.findClosestCreek(Arrays.asList(), emergencySiteNoCreek);
        assertNull(closestCreek);
    }

    @Test
    public void testOneCreek() {
        Position position = new Position(0, 0);
        Position emergencySitePosition = new Position(2, 2);
        Position creekPosition = new Position(3, 4);

        PointOfInterest emergencySite = new PointOfInterest(emergencySitePosition, "site1", PointOfInterestType.SITE);
        PointOfInterest creek = new PointOfInterest(creekPosition, "creek1", PointOfInterestType.CREEK);

        PointOfInterest closestCreek = position.findClosestCreek(Arrays.asList(creek), emergencySite);
        assertEquals("creek1", closestCreek.getID());
    }

}
