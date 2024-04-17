package com.agropro.testTask.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}$", message = "Неверный гос. знак. Пример: М976ММ163, прописные буквы")
    private String regNumber;

    @Pattern(regexp = "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$", message = "Неверный формат даты. Пример: 15.04.2024")
    private String releaseDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


    public Car() {
    }

    public Car(String regNumber, String releaseDate, Owner owner) {
        this.regNumber = regNumber;
        this.releaseDate = releaseDate;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}

