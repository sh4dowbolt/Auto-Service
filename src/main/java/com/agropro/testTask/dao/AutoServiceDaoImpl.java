package com.agropro.testTask.dao;

import com.agropro.testTask.Entity.Car;
import com.agropro.testTask.Entity.Dealer;
import com.agropro.testTask.Entity.Owner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutoServiceDaoImpl implements AutoServiceDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public <T> void save(T object) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(object);
    }

    @Override
    public List<Dealer> showAllDealer() {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Dealer> dealers = currentSession.createQuery("from Dealer ", Dealer.class).getResultList();
        return dealers;
    }

    @Override
    public List<Owner> showAllOwner() {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Owner> owners = currentSession.createQuery("from Owner ", Owner.class).getResultList();
        return owners;
    }

    @Override
    public List<Car> showAllCar() {
        Session currentSession = entityManager.unwrap(Session.class);
        List<Car> cars = currentSession.createQuery("from Car ", Car.class).getResultList();
        return cars;
    }

    @Override
    public List<Car> showCarByNotOwnerId(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from Car c where fk(c.owner) is NULL or fk(c.owner) not in :id";
        Query query = currentSession.createQuery(hql);
        query.setParameter("id", id);
        List cars = query.getResultList();
        return cars;
    }

    @Override
    public List<Car> showCarByOwnerId(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from Car c where fk(c.owner) = :id";
        Query query = currentSession.createQuery(hql);
        query.setParameter("id", id);
        List cars = query.getResultList();
        return cars;
    }

    @Override
    public void updateToAddCarToOwner(Long OwnerId, Long CarId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Owner ownerFromDB = currentSession.get(Owner.class, OwnerId);
        Car carFromDB = currentSession.get(Car.class, CarId);

        ownerFromDB.addCarToOwner(carFromDB);
        carFromDB.setOwner(ownerFromDB);

        currentSession.merge(ownerFromDB);
        currentSession.merge(carFromDB);
    }
    @Override
    public void updateToRemoveCarFromOwner(Long OwnerId, Long CarId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Owner ownerFromDB = currentSession.get(Owner.class, OwnerId);
        Car carFromDB = currentSession.get(Car.class, CarId);

        ownerFromDB.removeCarFromOwner(carFromDB);
        carFromDB.setOwner(null);

        currentSession.merge(ownerFromDB);
        currentSession.merge(carFromDB);
    }

    @Override
    public List<Owner> showOwnerByNotDealerId(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from Owner o where fk(o.dealer) is NULL or fk(o.dealer) not in :id";
        Query query = currentSession.createQuery(hql);
        query.setParameter("id", id);
        List owners = query.getResultList();
        return owners;
    }

    @Override
    public List<Owner> showOwnerByDealerId(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from Owner o where fk(o.dealer) = :id";
        Query query = currentSession.createQuery(hql);
        query.setParameter("id", id);
        List owners = query.getResultList();
        return owners;
    }

    @Override
    public void updateToAddOwnerToDealer(Long DealerId, Long OwnerId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Dealer dealerFromDb = currentSession.get(Dealer.class, DealerId);
        Owner ownerFromDB = currentSession.get(Owner.class, OwnerId);

        dealerFromDb.addOwnerToDealer(ownerFromDB);
        ownerFromDB.setDealer(dealerFromDb);

        currentSession.merge(dealerFromDb);
        currentSession.merge(ownerFromDB);
    }

    @Override
    public void updateToRemoveOwnerFromDealer(Long DealerId, Long OwnerId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Dealer dealerFromDb = currentSession.get(Dealer.class, DealerId);
        Owner ownerFromDB = currentSession.get(Owner.class, OwnerId);

        dealerFromDb.removeOwnerFromDealer(ownerFromDB);
        ownerFromDB.setDealer(null);


        currentSession.merge(dealerFromDb);
        currentSession.merge(ownerFromDB);
    }

    @Override
    public List<Car> showCarsFromDealer(Long DealerId) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "from Car c JOIN Owner o ON fk(c.owner)=o.id" +
                " JOIN Dealer d ON fk(o.dealer)=d.id where fk(c.owner) = :id";
        Query query = currentSession.createQuery(hql);
        query.setParameter("id", DealerId);
        List cars = query.getResultList();
        return cars;
    }






}



