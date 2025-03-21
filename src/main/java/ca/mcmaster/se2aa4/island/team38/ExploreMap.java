// package ca.mcmaster.se2aa4.island.team38;

// import org.json.JSONArray;
// import org.json.JSONObject;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// public class ExploreMap {
//     private List<String> biomes;
//     private List<String> creeks;
//     private List<String> sites;
//     private Integer creekCounter;
//     private Integer siteCounter;
//     private Map<String, int[]> creeksMap = new HashMap<>();
//     private Map<String, int[]> sitesMap = new HashMap<>();

//     public void processBiomes(JSONObject extras, Coordinate coords) {

//         try {
//             JSONArray biomesInfo = extras.getJSONArray("biomes");
//             biomes = JSONConvertArray(biomesInfo, 2);} catch (Exception e) {
//         }
//         JSONArray creeksInfo = extras.getJSONArray("creeks");
//         if (!creeksInfo.isEmpty()) {
//             List<String> temp = JSONConvertArray(creeksInfo, 0);
//             if (creeks == null) {
//                 creeks = temp;
//             }
//             else {
//                 creeks.addAll(temp);
//             }
//             creekCordStorage(creeks.get(creekCounter), coords);
//         }
//         JSONArray sitesInfo = extras.getJSONArray("sites");
//         if (!sitesInfo.isEmpty()) {
//             List<String> temp2 = JSONConvertArray(sitesInfo, 1);
//             if (sites == null) {
//                 sites = temp2;
//             }
//             else {
//                 sites.addAll(temp2);
//             }
//             siteCordStorage(sites.get(siteCounter), coords);
//         }
//     }
    
//     private List<String> JSONConvertArray(JSONArray JSONArray, Integer creek_status){
//         List<String> list = new ArrayList<>();
//         for (Object value : JSONArray){
//             list.add(String.valueOf(value));
//             if (creek_status == 0) {
//                 if (creeks != null) {
//                     creekCounter++;
//                 }
//             }
//             else if (creek_status == 1) {
//                 if (sites != null) {
//                     siteCounter++;
//                 }
//             }
//         }
//         return list;
//     }

//     private void creekCordStorage(String id, Coordinate coords) {
//         creeksMap.put(id, new int[]{coords.getXCoordinate(), coords.getYCoordinate()});
//     }
//     private void siteCordStorage(String id, Coordinate coords) {
//         sitesMap.put(id, new int[]{coords.getXCoordinate(), coords.getYCoordinate()});
//     }

//     public int[] getCreekCoordinate(String id){
//         int[] cords = creeksMap.get(id);
//         return cords;
//     }

//     public int[] getSiteCoordinate(String id){
//         int[] cords = sitesMap.get(id);
//         return cords;
//     }

//     public Integer getCreekCount() {
//         Integer copy = creekCounter;
//         return copy;
//     }

//     public String getFirstCreekID(){
//         return creeks.get(0);
//     }

//     public String getCreekID(int num){
//         return creeks.get(num);
//     }

//     public String getSiteID() {
//         return sites.get(0);
//     }

//     public List<String> getBiomes() {
//         List<String> copy = biomes;
//         return copy;
//     }

//     public List<String> getSites() {
//         List<String> copy = sites;
//         return copy;
//     }

//     public List<String> getCreeks() {
//         List<String> copy = creeks;
//         return copy;
//     }

//     public Boolean siteStatus() {
//         return sites != null;
//     }

//     public Boolean inCreeks(String id) {
//         return creeksMap.containsKey(id);
//     }

//     public void sitesCordsStart() {
//         creekCounter = 0;
//         siteCounter = 0;
//     }

// }
