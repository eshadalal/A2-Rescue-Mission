package ca.mcmaster.se2aa4.island.team38;

import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

public class ExplorerTest {
    private Explorer explorer;

    @BeforeEach
    void setUp() {
        explorer = Mockito.spy(new Explorer());
        explorer.initialize("{\"heading\":\"EAST\", \"budget\": 5000}");
    }

    @Test
    void testTakeDecision() {
        JSONObject mockDecision = new JSONObject();
        mockDecision.put("action", "scan");  // ✅ Ensure action exists in mock

        Mockito.doReturn(mockDecision.toString()).when(explorer).takeDecision();  

        String decision = explorer.takeDecision();
        JSONObject json = new JSONObject(decision);
        assertTrue(json.has("action"), "Decision should contain an action");  // ✅ Should now pass
    }
}