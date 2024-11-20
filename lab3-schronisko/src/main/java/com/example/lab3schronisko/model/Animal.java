package com.example.lab3schronisko.model;

import java.util.Objects;

// 2. Klasa Animal
public class Animal implements Comparable<Animal> {
    private String name;
    private String species;
    private AnimalCondition condition;
    private int age;
    private double price;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public AnimalCondition getCondition() {
        return condition;
    }

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be a positive integer");
        }
        this.price = price;
    }

    public void print() {
        System.out.printf("Name: %s, Species: %s, Condition: %s, Age: %d, Price: %.2f\n",
                name, species, condition, age, price);
    }

    @Override
    public int compareTo(Animal other) {
        return this.name.compareTo(other.name); // Porównanie po nazwie
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Animal)) return false;
        Animal animal = (Animal) obj;
        return age == animal.age &&
                Double.compare(animal.price, price) == 0 &&
                Objects.equals(name, animal.name) &&
                Objects.equals(species, animal.species) &&
                condition == animal.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, species, condition, age, price);
    }
}
