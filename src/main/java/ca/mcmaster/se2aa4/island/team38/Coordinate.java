// package ca.mcmaster.se2aa4.island.team38;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.json.JSONObject;

// public class Coordinate {
//     private final Logger logger = LogManager.getLogger();
//     private Integer y_coord; 
//     private Integer x_coord; 
//     private String closestCreek; 

//     public void droneCordsStart() { 
//         y_coord = 0;
//         x_coord = 0;
//     }
    
//     public void moveDrone(JSONObject move, String currentDirection) {
//         if (move.getString("action").equals("fly")) {
//             switch (currentDirection) {
//                 case "N": {
//                     y_coord += 1;
//                     break;
//                 }
//                 case "S": {
//                     y_coord -= 1;
//                     break;
//                 }
//                 case "E": {
//                     y_coord += 1;
//                     break;
//                 }
//                 case "W": {
//                     y_coord -= 1;
//                     break;
//                 }
//                 default: {
//                     logger.info("ERROR in drone cords move");
//                 }
//             }
//         }

//         else if (move.getString("action").equals("heading")) {
//             String new_heading = move.getJSONObject("parameters").getString("direction");
//             switch (currentDirection) {
//                 case "N": {
//                     if (new_heading.equals("W")) {
//                         x_coord -= 1;
//                     }
//                     else if (new_heading.equals("E")) {
//                         x_coord += 1;
//                     }
//                     y_coord += 1;
//                     break;
//                 }
//                 case "S": {
//                     if (new_heading.equals("W")) {
//                         x_coord -= 1;
//                     }
//                     else if (new_heading.equals("E")) {
//                         x_coord += 1;
//                     }
//                     y_coord -= 1;
//                     break;
//                 }
//                 case "E": {
//                     if (new_heading.equals("S")) {
//                         y_coord -= 1;
//                     }
//                     else if (new_heading.equals("N")) {
//                         y_coord += 1;
//                     }
//                     x_coord += 1;
//                     break;
//                 }
//                 case "W": {
//                     if (new_heading.equals("S")) {
//                         y_coord -= 1;
//                     }
//                     else if (new_heading.equals("N")) {
//                         y_coord += 1;
//                     }
//                     x_coord -= 1;
//                     break;
//                 }
//                 default: {
//                     logger.info("ERROR");
//                 }
//             }
//         }
//     }

//     public Integer getYCoordinate() {
//         int coord = y_coord;
//         return coord;
//     }

//     public Integer getXCoordinate() {
//         int coord = x_coord;
//         return coord;
//     }

//     public String findClosestCreek(ExploreMap map) {
//         double smalles = 999999999.0; 
//         try {
//             closestCreek = map.getFirstCreekID();
//         } catch (Exception e) {
//             closestCreek = null;
//         }
//         String currentcreek;
//         double result;
//         if (map.siteStatus()) {
//             String site = map.getSiteID();
//             int[] site_cords = map.getSiteCoordinate(site);
//             for (int i = 0; i <= map.getCreekCount(); i++) {
//                 currentcreek = map.getCreekID(i);
//                 if (map.inCreeks(currentcreek)) {
//                     int[] currentcords = map.getCreekCoordinate(currentcreek);
//                     result = distanceCalculation(currentcords, site_cords);
//                     if (result < closestResult) {
//                         closestResult = result;
//                         closestCreek = currentcreek;
//                     }
//                 }
//             }
//         }
//         return closestCreek;
//     }

//     private double distanceCalculation(int[] currentcords, int[] site_cord) {
//         double x1 = currentcords[0];
//         double y1 = currentcords[1];
//         double x2 = site_cord[0];
//         double y2 = site_cord[1];
//         return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
//     }
// }





