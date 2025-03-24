package ca.mcmaster.se2aa4.island.team38;

import org.json.JSONObject;

public class DecideAction {

    private JSONObject nextDecision;
    private Boolean lastDecisionWasScan;
    private Boolean checkBiome;
    private JSONObject prevScan;
    private JSONObject action;
    private Stage stage;
    private Drone drone;
    private int moveCount;
    private Boolean rightScan;

    public DecideAction(Stage stage) {
        this.stage = stage;
        this.checkBiome = false;
        this.lastDecisionWasScan = false;
        this.rightScan = false;
        this.moveCount = 0;
    }

    public void chooseAction(Drone drone, DroneResponse response, PointsOfInterest map) {
        this.drone = drone; 
        action = null;

        if (stage == Stage.OUT_OF_RANGE) {
            handleOutOfRange(response);
        }

        if (checkBiome) {
            handleBiomeScan(response, map);
        }

        switch (stage) {
            case START:
                performEchoActions();
                action = flyForward();

            case TURN:
                action = decideTurn(response);
                break;

            case ECHO:
                performEchoActions();
                stage = Stage.FIND_ISLAND;
                break;

            case FIND_ISLAND:
                action = moveForwardInRange(response);
                break;

            case SCAN:
                performEchoActions();
                action = scanArea();
                flyForward();
                break;

            case SCAN_AND_TURN:
                action = scanAndTurn();
                break;

            case OUT_OF_RANGE:
                action = getBackInRange();
                break;

            default:
                action = stopDrone();
                break;
        }

        lastDecisionWasScan = action.getString("action").equals("echo");
        checkBiome = action.getString("action").equals("scan");
        nextDecision = action;
    }

    public JSONObject getDecision() {
        return nextDecision;
    }

    private void handleOutOfRange(DroneResponse response) {
        if (lastDecisionWasScan && response.getRange().equals("OUT_OF_RANGE")) {
            if (!rightScan) {
                leftTurn();
                flyForward();
                leftTurn();
                rightScan = true;
            } else {
                rightTurn();
                flyForward();
                rightTurn();
                rightScan = false;
            }
            performEchoActions();
            stage = Stage.FIND_ISLAND;
        }
    }

    private void handleBiomeScan(DroneResponse response, PointsOfInterest map) {
        if (stage == Stage.SCAN) {
            if (map.getBiomes().contains("OCEAN") && map.getBiomes().size() == 1) {
                if (response.getRange().equals("OUT_OF_RANGE")) {
                    stage = Stage.SCAN_AND_TURN;
                }
            }
        }
    }

    private void performEchoActions() {
        forwardEcho();
        rightEcho();
        leftEcho();
    }

    private JSONObject decideTurn(DroneResponse response) {
        if (response.getIntRange() <= 0) {
            leftTurn();
            return flyForward();
        } else {
            forwardEcho();
            return flyForward();
        }
    }

    private JSONObject moveForwardInRange(DroneResponse response) {
        if (moveCount < response.getIntRange()) {
            moveCount++;
            return flyForward();
        } else {
            stage = Stage.SCAN;
            return scanArea();
        }
    }

    private JSONObject scanAndTurn() {
        if (!rightScan) {
            leftTurn();
            leftTurn();
            rightScan = true;
        } else {
            rightTurn();
            rightTurn();
            rightScan = false;
        }
        stage = Stage.SCAN;
        return scanArea();
    }

    public Boolean lastActionScan() {
        return lastDecisionWasScan;
    }

    public String getLastScan() {
        return prevScan != null ? prevScan.toString() : "{}";
    }

    private JSONObject getBackInRange() {
        if (!rightScan) {
            leftEcho();
        } else {
            rightEcho();
        }
        return flyForward();
    }

    private JSONObject flyForward() {
        return setAction(Action.FLY);
    }

    private JSONObject stopDrone() {
        return setAction(Action.STOP);
    }

    private JSONObject forwardEcho() {
        return setAction(Action.ECHOFORWARD);
    }

    private JSONObject rightEcho() {
        return setAction(Action.ECHORIGHT);
    }

    private JSONObject leftEcho() {
        return setAction(Action.ECHOLEFT);
    }

    private JSONObject rightTurn() {
        return setAction(Action.TURNRIGHT);
    }

    private JSONObject leftTurn() {
        return setAction(Action.TURNLEFT);
    }

    private JSONObject scanArea() {
        return setAction(Action.SCAN);
    }

    private JSONObject setAction(Action actionType) {
        JSONObject result;
        switch (actionType) {
            case FLY:
                result = drone.fly();
                break;
            case TURNRIGHT:
                result = drone.turnRight();
                break;
            case TURNLEFT:
                result = drone.turnLeft();
                break;
            case SCAN:
                prevScan = drone.scan();
                result = prevScan;
                break;
            case STOP:
                result = drone.stop();
                break;
            case ECHOFORWARD:
                result = drone.echoForward();
                break;
            case ECHORIGHT:
                result = drone.echoRight();
                break;
            case ECHOLEFT:
                result = drone.echoLeft();
                break;
            default:
                throw new IllegalArgumentException("Cannot perform action: " + actionType);
        }
        action = result; // Store the action result
        return result;
    }
}
