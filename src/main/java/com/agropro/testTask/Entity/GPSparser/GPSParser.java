package com.agropro.testTask.Entity.GPSparser;


import com.agropro.testTask.Entity.GPSPosition.GPSPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GPSParser {

    private GPSPosition position = new GPSPosition();
    private static final Map<String, SentenceParser> sentenceParsers = new HashMap<>();

    public GPSParser() {
        getSentenceParsers().put("GPGGA", new GPGGA());
        getSentenceParsers().put("GNVTG", new GNVTG());
    }

    public GPSPosition getPosition() {
        return position;
    }

    public void setPosition(GPSPosition position) {
        this.position = position;
    }


    static double Latitude2Decimal(String lat, String NS) {
        if (lat.isEmpty()) {
            return 0;
        }
        int minutesPosition = lat.indexOf('.') - 2;

        double minutes = Double.parseDouble(lat.substring(minutesPosition));
        double decimalDegrees = Double.parseDouble(lat.substring(minutesPosition)) / 60.0f;


        double degree = Double.parseDouble(lat) - minutes;
        double wholeDegrees = (int) degree / 100;

        double latitudeDegrees = wholeDegrees + decimalDegrees;
        if (NS.startsWith("S")) {
            latitudeDegrees = -latitudeDegrees;
        }
        return (latitudeDegrees * Math.PI) / 180;
    }

    static double Longitude2Decimal(String lon, String WE) {
        if (lon == "") {
            return 0;
        }
        int minutesPosition = lon.indexOf('.') - 2;

        double minutes = Double.parseDouble(lon.substring(minutesPosition));
        double decimalDegrees = Double.parseDouble(lon.substring(minutesPosition)) / 60.0f;

        double degree = Double.parseDouble(lon) - minutes;
        double wholeDegrees = (int) degree / 100;

        double longitudeDegrees = wholeDegrees + decimalDegrees;
        if (WE.startsWith("W")) {
            longitudeDegrees = -longitudeDegrees;
        }
        return (longitudeDegrees * Math.PI) / 180;
    }

    public static Map<String, SentenceParser> getSentenceParsers() {
        return sentenceParsers;
    }

    public GPSPosition parse(String line) {
        if (line == null) {
            return null;
        }

        final Pattern pGGA = Pattern.compile("^\\$GPGGA.*");

        final Pattern pVTG = Pattern.compile("^\\$GNVTG,([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?,[A-Za-z],([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?,[A-Za-z],([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?,N,([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[Ee]([+-]?\\d+))?,[A-Za-z],[A-Za-z]\\*[A-Za-z0-9]+$");

        if (pGGA.matcher(line).matches() || pVTG.matcher(line).matches()) {
            String nmea = line.substring(1);
            String[] tokens = nmea.split(",");
            String type = tokens[0];
            if (getSentenceParsers().containsKey(type)) {
                getSentenceParsers().get(type).parse(tokens, getPosition());
            }
            return getPosition();
        }
        return getPosition();
    }


}
