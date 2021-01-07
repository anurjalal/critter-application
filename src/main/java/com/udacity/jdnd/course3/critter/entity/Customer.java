package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @NotNull
    private String name;

    @Nationalized
    @NotNull
    private String phoneNumber;

    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Pet> pet;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getNotes() {
        return Optional.ofNullable(notes);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Optional<List<Pet>> getPet() {
        return Optional.ofNullable(pet);
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    public void addPet(Pet pet) {
        this.pet.add(pet);
        pet.setCustomer(this);
    }

    public void removePet(Pet pet) {
        this.pet.remove(pet);
        pet.setCustomer(this);
    }
}
