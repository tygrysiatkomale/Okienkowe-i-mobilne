package com.example.lab3schronisko.model;

import com.example.lab3schronisko.exceptions.CapacityExceededException;
import com.example.lab3schronisko.exceptions.InvalidOperationException;

import java.util.*;

// 3. Klasa AnimalShelter
public class AnimalShelter {
    private String shelterName;
    private List<Animal> animalList;
    private int maxCapacity;

    public AnimalShelter(String shelterName, int maxCapacity) {
        this.shelterName = shelterName;
        this.animalList = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Max capacity must be a positive integer");
        }
        this.maxCapacity = maxCapacity;
    }

    public void addAnimal(Animal animal) throws CapacityExceededException, InvalidOperationException {
        if (animalList.size() >= maxCapacity) {
            throw new CapacityExceededException("Cannot add more animals, maximum capacity has been reached");
        }
        if (animalList.contains(animal)) {
            throw new InvalidOperationException("The animal already exists in the shelter");
        }
        animalList.add(animal);
    }

    public void removeAnimal(Animal animal) throws InvalidOperationException {
        if (!animalList.contains(animal)) {
            throw new InvalidOperationException("The animal is not in a shelter");
        }
        animalList.remove(animal);
    }

    public void getAnimal(Animal animal) throws InvalidOperationException {
        if (!animalList.contains(animal)) {
            throw new InvalidOperationException("The animal is not in a shelter");
        }
        animal.setCondition(AnimalCondition.IN_PROGRESS_OF_ADOPTION);
        animalList.remove(animal);
    }

    public void changeCondition(Animal animal, AnimalCondition condition) throws InvalidOperationException {
        int index = animalList.indexOf(animal);
        if (index == -1) {
            throw new InvalidOperationException("The animal is not in a shelter");
        }
        animalList.get(index).setCondition(condition);
    }

    public void changeAge(Animal animal, int newAge) throws InvalidOperationException {
        int index = animalList.indexOf(animal);
        if (index == -1) {
            throw new InvalidOperationException("The animal is not in a shelter");
        }
        animalList.get(index).setAge(newAge);
    }

    public long countByCondition(AnimalCondition condition) {
        return animalList.stream()
                .filter(a -> a.getCondition() == condition)
                .count();
    }

    public List<Animal> sortByName() {
        return animalList.stream()
                .sorted(Comparator.comparing(Animal::getName))
                .toList();
    }

    public List<Animal> sortByPrice() {
        return animalList.stream()
                .sorted(Comparator.comparingDouble(Animal::getPrice))
                .toList();
    }

    public Optional<Animal> search(String name) {
        return animalList.stream()
                .filter(animal -> animal.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Animal> searchPartial(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return animalList.stream()
                .filter(animal -> animal.getName().toLowerCase().contains(lowerKeyword) ||
                        animal.getSpecies().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public void summary() {
        System.out.printf("Shelter: %s, Max capacity: %d, Current number of animals: %d\n",
                shelterName, maxCapacity, animalList.size());
        animalList.forEach(Animal::print);
    }

    public Animal max() {
        return Collections.max(animalList, Comparator.comparingDouble(Animal::getPrice));
    }

}