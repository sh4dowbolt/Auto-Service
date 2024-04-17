package com.agropro.testTask.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "dealers")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Название организации не может быть пустым")
    @Size(min = 5, max = 250)
    private String name;
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Пример почты: suraevvvitaly@gmail.com")
    private String email;

    @NotEmpty(message = "ФИО представителя не может быть пустым")
    @Size(min = 5, max = 250)
    private String initialsOfDealer;

    @OneToMany(mappedBy = "dealer")
    private List<Owner> ownerList;

    public Dealer(String name, String email, String initialsOfDealer, List<Owner> ownerList) {
        this.name = name;
        this.email = email;
        this.initialsOfDealer = initialsOfDealer;
        this.ownerList = ownerList;
    }

    public Dealer() {
    }

    public void addOwnerToDealer(Owner owner) {
        if(ownerList==null) {
            ownerList = new ArrayList<>();
        }
        ownerList.add(owner);
    }
    public void removeOwnerFromDealer(Owner owner) {
        ownerList.remove(owner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitialsOfDealer() {
        return initialsOfDealer;
    }

    public void setInitialsOfDealer(String initialsOfDealer) {
        this.initialsOfDealer = initialsOfDealer;
    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }
}
