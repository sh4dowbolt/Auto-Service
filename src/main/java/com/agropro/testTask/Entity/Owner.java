package com.agropro.testTask.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "ФИО владельца не может быть пустым")
    @Size(min = 5, max = 250)
    private String initials;
    @NotEmpty(message = "Столбец с номером телефона не может быть пустым")
    @Size(min = 5, max = 250)
    private String number;
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Пример почты: suraevvvitaly@gmail.com")
    private String email;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Car> carList;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    public Owner() {
    }

    public Owner(String initials, String number, String email, List<Car> carList, Dealer dealer) {
        this.initials = initials;
        this.number = number;
        this.email = email;
        this.carList = carList;
        this.dealer = dealer;
    }

    public void addCarToOwner(Car car) {
        if(carList==null) {
            carList= new ArrayList<>();
        }
        carList.add(car);
    }
    public void removeCarFromOwner(Car car) {
       carList.remove(car);
    }
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
