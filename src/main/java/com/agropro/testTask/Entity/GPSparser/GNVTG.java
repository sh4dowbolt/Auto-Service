package com.agropro.testTask.Entity.GPSparser;

public class GNVTG implements SentenceParser {
    public boolean parse(String[] tokens, com.agropro.testTask.Entity.GPSPosition.GPSPosition position) {
        position.velocity = Double.parseDouble(tokens[7]);
        position.isVTG = true;
        return true;
    }
}
