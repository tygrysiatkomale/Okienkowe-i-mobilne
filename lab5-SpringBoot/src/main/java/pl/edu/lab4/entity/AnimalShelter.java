package pl.edu.lab4.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelters")
public class AnimalShelter implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shelterName;
    private int maxCapacity;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Animal> animalList = new ArrayList<>();

    // New relation. One shelter can have many ratings
    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Rating> ratings = new ArrayList<>();

    public AnimalShelter() {
    }

    public AnimalShelter(String shelterName, int maxCapacity) {
        this.shelterName = shelterName;
        this.maxCapacity = maxCapacity;
    }

    // get/set
    // Helper methods to add and remove animals

    public void addAnimal(Animal animal) {
        if (animalList.size() >= maxCapacity) {
            throw new RuntimeException("Max capacity exceeded");
        }
        animalList.add(animal);
        animal.setShelter(this);
    }

    public void removeAnimal(Animal animal) {
        animalList.remove(animal);
        animal.setShelter(null);
    }

    public void addRating(Rating rating){
        ratings.add(rating);
        rating.setShelter(this);
    }

    public String getShelterName() {
        return shelterName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public List<Rating> getRatings() {
        return ratings;
    }



}