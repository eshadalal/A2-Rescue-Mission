// package ca.mcmaster.se2aa4.island.team38;

// import org.json.JSONObject;

// public class DecideAction {

//     private JSONObject nextDecision;
//     private String prevScan; 
//     private Boolean lastDesicionWasScan; 
//     private Boolean checkBiome; 
//     private JSONObject action;
//     private Stage stage;
//     private Drone drone;
//     private int moveCount; 
//     private Boolean rightScan; 
    
//     public DecideAction(Stage stage) {
//         checkBiome = false;
//         lastDesicionWasScan = false;
//         rightScan = false;
//         this.drone = drone;
//     }

//     public void chooseAction(Drone drone, DroneResponse response, PointsOfInterest map) {
//         if (prevScan != null) {
//             if (response.getRange(prevScan) != null) {
//                 if (stage == Stage.START) {
//                     if (response.getRange(prevScan).equals("GROUND")) {
//                         stage = Stage.TURN;
//                     }
//                 }

//                 if (stage == Stage.OUT_OF_RANGE) {
//                     if (lastDesicionWasScan) {
//                         if (response.getRange(prevScan).equals("OUT_OF_RANGE")) {
//                             if (!rightScan) {
//                                 leftTurn();
//                                 flyForward();
//                                 leftTurn();
//                                 rightScan = true;
//                             } else {
//                                 rightTurn();
//                                 flyForward();
//                                 rightTurn();
//                                 rightScan = false;
//                             }
//                             forwardEcho();
//                             stage = Stage.LOCATE_2;
//                         }
//                     }
//                 }
//             }
//         }

//         if (checkBiome) {
//             if (stage == Stage.SCAN) {
//                 if (map.getBiomes().contains("OCEAN")) {
//                     if (map.getBiomes().size() == 1) {
//                         if (response.getRange(prevScan).equals("OUT_OF_RANGE")) {
//                                 stage = Stage.SCAN_AND_TURN;
//                         }
//                 }
//             }
//         }

//         switch (stage) {
//             case START:
//                 forwardEcho();
//                 rightEcho();
//                 leftEcho();
//                 flyForward();
//                 break;

//             case TURN:
//                 decideTurn(response);
//                 break;

//             case ECHO:
//                 forwardEcho();
//                 stage = Stage.FIND_ISLAND;
//                 break;

//             case FIND_ISLAND:
//                 moveForwardInRange(response);
//                 break;

//             case SCAN:
//                 forwardEcho();
//                 scanArea();
//                 flyForward();
//                 break;

//             case SCAN_AND_TURN:
//                 ScanAndTurn();
//                 break;

//             case OUT_OF_RANGE:
//                 getBackInRange();
//                 break;

//             default:
//                 stopDrone();
//                 break;
//         }
    
//         }

//         lastDesicionWasScan = action.getString("action").equals("echo");
//         checkBiome = action.getString("action").equals("scan");
//         nextDecision = action;


//     }

//     public JSONObject getDecision() {
//         return nextDecision;
//     }

//     private void decideTurn(DroneResponse response) {
//         String leftEchoResult = response.leftEcho();
//         String rightEchoResult = response.rightEcho();

//         if (prevScan.equals(leftEchoResult)) {
//             leftTurn();
//         } else if (prevScan.equals(rightEchoResult)) {
//             rightTurn();
//         } else {
//             forwardEcho();
//             flyForward();
//         }
//         stage = Stage.ECHO;
//     }

//     private void moveForwardInRange(DroneResponse response) {
//         if (moveCount < response.getRange()) {
//             flyForward();
//             moveCount++;
//         } else {
//             scanArea();
//             stage = Stage.SCAN;
//         }
//     }

//     private void ScanAndTurn() {
//         if (!rightScan) {
//             leftTurn();
//             leftTurn();
//             rightScan = true;
//         } else {
//             rightTurn();
//             rightTurn();
//             rightScan = false;
//         }
//         stage = Stage.SCAN;
//     }

//     private void getBackInRange() {
//         if (!rightScan) {
//             leftEcho();
//         } else {
//             rightEcho();
//         }
//         flyForward();
//     }

//     private void flyForward() {
//         setAction(Action.FLY);
//     }

//     private void stopDrone() {
//         setAction(Action.STOP);
//     }

//     private void forwardEcho() {
//         setAction(Action.ECHOFORWARD);
//     }

//     private void rightEcho() {
//         setAction(Action.ECHORIGHT);
//     }

//     private void leftEcho() {
//         setAction(Action.ECHOLEFT);
//     }

//     private void rightTurn() {
//         setAction(Action.TURNRIGHT);
//     }

//     private void leftTurn() {
//         setAction(Action.TURNLEFT);
//     }

//     private void scanArea() {
//         setAction(Action.SCAN);
//     }

//     public JSONObject setAction(Action action) {
//         switch (action) {
//             case FLY:
//                 return drone.fly();
//             case TURNRIGHT:
//                 return drone.turnRight();
//             case TURNLEFT:
//                 return drone.turnLeft();
//             case SCAN:
//                 return drone.scan();
//             case STOP:
//                 return drone.stop();
//             case ECHOFORWARD:
//                 return drone.echoForward();
//             case ECHORIGHT:
//                 return drone.echoRight();
//             case ECHOLEFT:
//                 return drone.echoLeft();
//             default:
//                 throw new IllegalArgumentException("Cannot perform action: " + action);
//         }
//     }
// }
