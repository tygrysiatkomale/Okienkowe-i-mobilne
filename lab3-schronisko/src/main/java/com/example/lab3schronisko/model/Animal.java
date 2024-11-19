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

    public void setCondition(AnimalCondition condition) {
        this.condition = condition;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void print() {
        System.out.printf("Name: %s, Species: %s, Condition: %s, Age: %d, Price: %.2f\n", name, species, condition, age, price);
    }

    @Override
    public int compareTo(Animal other) {
        return this.name.compareTo(other.name); // Porównanie po nazwie
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return age == animal.age && name.equals(animal.name) && species.equals(animal.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, species, age);
    }
}