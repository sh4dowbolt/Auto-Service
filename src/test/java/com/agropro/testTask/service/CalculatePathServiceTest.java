package com.agropro.testTask.service;

import com.agropro.testTask.Entity.GPSPosition.GPSPosition;
import com.agropro.testTask.controller.FileController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatePathServiceTest {

    private CalculatePathService calculatePathService;
    private  File file;

    @BeforeAll
    void prepare() {
        String dummy= "hello world";
        try {
           File file =new File("src/main/resources/upload/dummy.txt");
            FileWriter fileWriter= new FileWriter(file);
            fileWriter.write(dummy);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterAll
    void cleanUp() {
        if(file == null) {
            return;
        }
        file.delete();

    }
    @BeforeEach
    void init() {
        calculatePathService = new CalculatePathService();
    }


    @Test()
    void doesNotContainsNeededVTGData() {
        List<GPSPosition> someData = new ArrayList<>();

        someData.add(getGGAGpsPosition());

        List<GPSPosition> actualResult = calculatePathService.prepareDataVTGType(someData);

        org.assertj.core.api.Assertions.assertThat(actualResult).doesNotContainAnyElementsOf(someData);

    }
    @Test
    void ContainsNeededVTGData() {
        List<GPSPosition> someData = new ArrayList<>();
        someData.add(getVTGGpsPosition());

        List<GPSPosition> actualResult = calculatePathService.prepareDataVTGType(someData);

        org.assertj.core.api.Assertions.assertThat(actualResult).containsAnyElementsOf(someData);
    }

    @Test()
    void doesNotContainsNeededGGAData() {
        List<GPSPosition> someData = new ArrayList<>();

        someData.add(getVTGGpsPosition());

        List<GPSPosition> actualResult = calculatePathService.prepareDataGGAType(someData);

        org.assertj.core.api.Assertions.assertThat(actualResult).doesNotContainAnyElementsOf(someData);

    }
    @Test
    void ContainsNeededGGAData() {
        List<GPSPosition> someData = new ArrayList<>();
        someData.add(getGGAGpsPosition());

        List<GPSPosition> actualResult = calculatePathService.prepareDataGGAType(someData);

        org.assertj.core.api.Assertions.assertThat(actualResult).containsAnyElementsOf(someData);
    }


    @Test
    void countPathThrowExceptionIfDataIsEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> calculatePathService.countPath(getListGPSpositionGGA(), getListGPSpositionGGA()));
    }
    static Stream<Arguments> getSomeData() {
        return Stream.of(
                Arguments.of(getListGPSpositionGGA(), getListGPSpositionVTG().add(getVTGGpsPosition())),
                Arguments.of(getListGPSpositionGGA().add(getGGAGpsPosition()), getListGPSpositionVTG()),
                Arguments.of(getListGPSpositionGGA().add(getGGAGpsPosition()), getListGPSpositionVTG().add(getVTGGpsPosition()))
        );
    }






    @Test()
    void fileIsExisted() {
        String parameter= "src/main/resources/upload/dummy.txt";
        List<String> actualResult = calculatePathService.readFromTheFile(parameter);
        assertThat(actualResult.get(0).equalsIgnoreCase("hello world"));
        assertThat(actualResult).hasSize(1);

    }
    @Test()
    void shouldThrowExceptionIfFileDoesNotExist()  {
        String dummy = "dummy";
        Assertions.assertThrows(RuntimeException.class,() -> calculatePathService.readFromTheFile(dummy));
    }


    private static GPSPosition getGGAGpsPosition() {
        GPSPosition GGAtype= new GPSPosition();
        GGAtype.isGGA=true;
        return GGAtype;
    }

    private static GPSPosition getVTGGpsPosition() {
        GPSPosition VTGType= new GPSPosition();
        VTGType.isVTG=true;
        return VTGType;
    }

    private static List<GPSPosition> getListGPSpositionGGA() {
        List<GPSPosition> gpsPositionList= new ArrayList<>();


        return gpsPositionList;
    }

    private static List<GPSPosition> getListGPSpositionVTG() {
        List<GPSPosition> gpsPositionList= new ArrayList<>();

        return gpsPositionList;
    }





}