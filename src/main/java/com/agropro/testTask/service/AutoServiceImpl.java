package com.agropro.testTask.service;

import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.Entity.Dealer;
import com.agropro.testTask.Entity.Owner;
import com.agropro.testTask.dao.AutoServiceDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutoServiceImpl implements  AutoService{
    private final AutoServiceDao autoServiceDao;

    public AutoServiceImpl(AutoServiceDao autoServiceDao) {
        this.autoServiceDao = autoServiceDao;
    }

    @Override
    @Transactional
    public <T> void create(T object) {
        autoServiceDao.save(object);
    }

    @Transactional
    @Override
    public void updateToAddCarToOwner(Long OwnerId, Long CarId) {
        autoServiceDao.updateToAddCarToOwner(OwnerId, CarId);
    }

    @Transactional
    @Override
    public void updateToRemoveCarFromOwner(Long OwnerId, Long CarId) {
        autoServiceDao.updateToRemoveCarFromOwner(OwnerId, CarId);
    }

    @Transactional
    @Override
    public List<Car> showAllCar() {
        return autoServiceDao.showAllCar();
    }

    @Transactional
    @Override
    public List<Owner> showAllOwner() {
        return autoServiceDao.showAllOwner();
    }

    @Transactional
    @Override
    public List<Dealer> showAllDealers() {
        return autoServiceDao.showAllDealer();
    }
    @Transactional
    @Override
    public List<Car> showCarByOwnerId(Long id) {

        return autoServiceDao.showCarByOwnerId(id);
    }
    @Transactional
    @Override
    public void updateToAddOwnerToDealer(Long DealerId, Long OwnerId) {
        autoServiceDao.updateToAddOwnerToDealer(DealerId, OwnerId);
    }
    @Transactional
    @Override
    public void updateToRemoveOwnerFromDealer(Long DealerId, Long OwnerId) {
        autoServiceDao.updateToRemoveOwnerFromDealer(DealerId, OwnerId);
    }
    @Transactional
    @Override
    public List<Owner> showOwnerByNotDealerId(Long id) {
        return autoServiceDao.showOwnerByNotDealerId(id);
    }
    @Transactional
    @Override
    public List<Owner> showOwnerByDealerId(Long id) {
        return autoServiceDao.showOwnerByDealerId(id);
    }

    @Transactional
    @Override
    public List<Car> showCarsFromDealer(Long id) {
        return autoServiceDao.showCarsFromDealer(id);
    }

    @Transactional
    @Override
    public List<Car> showCarByNotOwnerId(Long id) {
        return autoServiceDao.showCarByNotOwnerId(id);
    }

}
