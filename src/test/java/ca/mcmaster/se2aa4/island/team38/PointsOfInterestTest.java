package ca.mcmaster.se2aa4.island.team38;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointsOfInterestTest {

    private PointsOfInterest poi;
    private Position position;
    private Drone drone;

    @BeforeEach
    void setUp() {
        poi = new PointsOfInterest();
        position = new Position(5, 5);
        int batteryLevel = 100; 
        Direction direction = Direction.NORTH;
        drone = new Drone(direction, batteryLevel);
    }

    @Test
    void testAddCreek() {
        poi.addPointOfInterest(position, "creek1", PointOfInterestType.CREEK);
        poi.addPointOfInterest(position, "creek2", PointOfInterestType.CREEK);
        List<PointOfInterest> creeks = poi.getCreeks();
        assertEquals(2, creeks.size());
        assertEquals("creek1", creeks.get(0).getID());
    }

    @Test
    void testAddEmergencySite() {
        poi.addPointOfInterest(position, "site1", PointOfInterestType.SITE);
        assertNotNull(poi.getEmergencySite());
        assertEquals("site1", poi.getEmergencySite().getID());
    }

    @Test
    void testHasEmergencySite_False() {
        assertFalse(poi.hasEmergencySite());
    }

    @Test
    void testHasEmergencySite_True() {
        poi.addPointOfInterest(position, "site1", PointOfInterestType.SITE);
        assertTrue(poi.hasEmergencySite());
    }

    @Test
    void testProcessResponse() {
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray().put("creek1"));
        extras.put("sites", new JSONArray().put("site1"));

        PointsOfInterest.processResponse(extras, poi, drone);

        assertEquals(1, poi.getCreeks().size());
        assertEquals("creek1", poi.getCreeks().get(0).getID());

        assertNotNull(poi.getEmergencySite());
        assertEquals("site1", poi.getEmergencySite().getID());
    }

    @Test
    void testGenerateFinalReport() {
        poi.addPointOfInterest(position, "site1", PointOfInterestType.SITE);
        poi.addPointOfInterest(position, "creek1", PointOfInterestType.CREEK);

        String report = poi.generateFinalReport(position);
        assertTrue(report.contains("Emergency site found. Closest creek: creek1"));
    }

    @Test
    void testLoadClosestCreek() {
        Position sitePosition = new Position(6, 6);
        Position creekPosition = new Position(4, 4);

        poi.addPointOfInterest(sitePosition, "site1", PointOfInterestType.SITE);
        poi.addPointOfInterest(creekPosition, "creek1", PointOfInterestType.CREEK);

        PointOfInterest closestCreek = poi.loadClosestCreek(position);
        assertNotNull(closestCreek);
        assertEquals("creek1", closestCreek.getID());
    }
}
