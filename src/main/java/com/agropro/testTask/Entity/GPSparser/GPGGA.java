package com.agropro.testTask.Entity.GPSparser;

public class GPGGA implements SentenceParser {
    public boolean parse(String[] tokens, com.agropro.testTask.Entity.GPSPosition.GPSPosition position) {

        position.lat = GPSParser.Latitude2Decimal(tokens[2], tokens[3]);
        position.lon = com.agropro.testTask.Entity.GPSparser.GPSParser.Longitude2Decimal(tokens[4], tokens[5]);
        position.isGGA = true;

        return true;
    }
}
