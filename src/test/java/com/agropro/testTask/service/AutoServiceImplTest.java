package com.agropro.testTask.service;

import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.dao.AutoServiceDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AutoServiceImplTest {

    @InjectMocks
    private AutoServiceImpl autoService;

    @Mock
    private AutoServiceDao autoServiceDao;


    private static Car getCar() {
        return new Car();

    }
    @Test
    void createSuccess() {
        Car car = getCar();
        car.setReleaseDate("21-10-1996");

        Mockito.doNothing().when(autoServiceDao).save(car);

        Mockito.verify(autoServiceDao).save(car);

    }

}