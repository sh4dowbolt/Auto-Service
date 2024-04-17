package com.agropro.testTask.service;


import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.Entity.Dealer;
import com.agropro.testTask.Entity.Owner;

import java.util.List;

public interface AutoService {

    <T> void create(T object);

    List<Owner> showAllOwner();
    List<Dealer> showAllDealers();
    List<Car> showAllCar();


    void updateToAddCarToOwner(Long OwnerId, Long CarId);
    void updateToRemoveCarFromOwner(Long OwnerId, Long CarId);

    List<Car> showCarByNotOwnerId(Long id);
    List<Car> showCarByOwnerId(Long id);


    void updateToAddOwnerToDealer(Long DealerId, Long OwnerId);
    void updateToRemoveOwnerFromDealer(Long OwnerId, Long CarId);

    List<Owner> showOwnerByNotDealerId(Long id);
    List<Owner> showOwnerByDealerId(Long id);
    List<Car> showCarsFromDealer(Long id);



}
