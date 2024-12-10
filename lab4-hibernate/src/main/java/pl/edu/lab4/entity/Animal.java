package pl.edu.lab4.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "animals")
public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String species;

    @Enumerated(EnumType.STRING)
    private AnimalCondition condition;

    private int age;
    private double price;

    //  Many-to-one relation (n:1) with shelter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private AnimalShelter shelter;

    public Animal() {
    }

    public Animal(String name, String species, AnimalCondition condition, int age, double price) {
    this.name = name;
    this.species = species;
    this.condition = condition;
    this.age = age;
    this.price = price;
    }

    //  get/set
    // equals/hashCode na podstawie id lub name/species/age
    public void setShelter(AnimalShelter shelter) {
        this.shelter = shelter;
    }
}