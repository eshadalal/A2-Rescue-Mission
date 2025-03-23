package ca.mcmaster.se2aa4.island.team38;

import java.util.List;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void updateX(int x) {
        this.x += x;
    }

    public void updateY(int y) {
        this.y += y;
    }

    public PointOfInterest findClosestCreek(List<PointOfInterest> creeks, PointOfInterest emergencySite) {
        if (emergencySite == null || creeks.isEmpty()) {
            return null; 
        }

        double site_x = emergencySite.getX();
        double site_y = emergencySite.getY();
        double smallestDistance = Double.MAX_VALUE;
        PointOfInterest closestCreek = null;

        for (PointOfInterest creek : creeks) {
            double creek_x = creek.getX();
            double creek_y = creek.getY();
            double distance = Math.sqrt(Math.pow(site_x - creek_x, 2) + Math.pow(site_y - creek_y, 2));
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestCreek = creek;
            }
        }
        return closestCreek;
    }
}
