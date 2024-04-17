package com.agropro.testTask.Entity.GPSPosition;

public class GPSPosition {
    public double lat = 0.0d;
    public double lon = 0.0d;
    public double velocity = 0.0d;
    public boolean isGGA;
    public boolean isVTG;

    public double getVelocity() {
        return velocity;
    }

    public boolean isGGA() {
        return isGGA;
    }

    public boolean isVTG() {
        return isVTG;
    }

    @Override
    public String toString() {
        return "GPSPosition{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", velocity=" + velocity +
                '}';
    }


}
