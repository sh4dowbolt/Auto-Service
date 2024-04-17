package com.agropro.testTask.Entity.GPSparser;

import com.agropro.testTask.Entity.GPSPosition.GPSPosition;

public interface SentenceParser {
    boolean parse(String[] tokens, GPSPosition position);
}
