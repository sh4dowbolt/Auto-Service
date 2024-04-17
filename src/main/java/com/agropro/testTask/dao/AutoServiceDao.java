package com.agropro.testTask.dao;

import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.Entity.Dealer;
import com.agropro.testTask.Entity.Owner;

import java.util.List;

public interface AutoServiceDao {

    <T> void save(T t);

    List<Dealer> showAllDealer();
    List<Owner> showAllOwner();
    List<Car> showAllCar();



    List<Car> showCarByNotOwnerId(Long id);
    List<Car> showCarByOwnerId(Long id);


    void updateToAddCarToOwner(Long OwnerId, Long CarId);
    void updateToRemoveCarFromOwner(Long OwnerId, Long CarId);


    List<Owner> showOwnerByNotDealerId(Long id);
    List<Owner> showOwnerByDealerId(Long id);



    void updateToAddOwnerToDealer(Long DealerId, Long OwnerId);
    void updateToRemoveOwnerFromDealer(Long DealerId, Long OwnerId);

    List <Car> showCarsFromDealer(Long DealerId);







}
