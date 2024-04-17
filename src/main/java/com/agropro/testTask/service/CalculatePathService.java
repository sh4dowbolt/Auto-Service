package com.agropro.testTask.service;

import com.agropro.testTask.Entity.GPSPosition.GPSPosition;
import com.agropro.testTask.Entity.GPSparser.GPSParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;

@Service
public class CalculatePathService {


    public List<String> readFromTheFile(String name) {
        ArrayList<String> readFromFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/upload/"+name))) {
            String line;
            line = reader.readLine();
            while (line != null) {
                readFromFile.add(line);
                line = reader.readLine();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return readFromFile;
    }

    public List<GPSPosition> parseDataFromFileToGPSPosition(List<String> readFromFile) {
        return readFromFile.stream().map(x -> new GPSParser().parse(x)).collect(Collectors.toList());
    }

    public List<GPSPosition> prepareDataGGAType(List<GPSPosition> gpsPositionList) {
        return gpsPositionList.stream().filter(GPSPosition::isGGA).collect(Collectors.toList());
    }

    public List<GPSPosition> prepareDataVTGType(List<GPSPosition> gpsPositionList) {
        return gpsPositionList.stream().filter(GPSPosition::isVTG).collect(Collectors.toList());
    }

    public double countPath(List<GPSPosition> preparedDataGGAType, List<GPSPosition> preparedDataVTGType) {

        int correctionIndex= preparedDataGGAType.size() > preparedDataVTGType.size()
                ? preparedDataGGAType.size()-preparedDataVTGType.size(): -(preparedDataVTGType.size() - preparedDataGGAType.size());


        final int R = 6_371;
        double actualResult = 0;
        for (int i = 0; i < preparedDataGGAType.size() - correctionIndex; i++) {
            for (int j = i; j < i + 1; j++) {
                if (preparedDataVTGType.get(j).velocity == 0) {
                    continue;
                }
                double sin1 = sin((preparedDataGGAType.get(i).lat - preparedDataGGAType.get(i + 1).lat) / 2);
                double sin2 = sin((preparedDataGGAType.get(i).lon - preparedDataGGAType.get(i + 1).lon) / 2);
                double pathBetweenTwoCords = 2 * R * asin(sqrt(sin1 * sin1 +
                        sin2 * sin2 * cos(preparedDataGGAType.get(i).lat) * cos(preparedDataGGAType.get(i + 1).lat)));
                actualResult += pathBetweenTwoCords;
            }
        }
        return actualResult;
    }
}
