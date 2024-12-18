package pl.edu.lab4.entity;

import javax.persistence.*;

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

    @Column(name = "animal_condition")
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

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public AnimalCondition getCondition() {
        return condition;
    }

    public int getAge() {
        return age;
    }

    public double getPrice() {
        return price;
    }

    public AnimalShelter getShelter() {
        return shelter;
    }

    public long getId() {
        return id;
    }


    public void setShelter(AnimalShelter shelter) {
        this.shelter = shelter;
    }

    // Opcjonalnie settery, jeśli potrzebujesz modyfikować dane:
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", condition=" + condition +
                ", age=" + age +
                ", price=" + price +
                '}';
    }
}