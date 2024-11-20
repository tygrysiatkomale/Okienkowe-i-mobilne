package com.example.lab3schronisko.model;

import com.example.lab3schronisko.exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 4. Klasa ShelterManager
public class ShelterManager {
    private Map<String, AnimalShelter> shelters;

    public ShelterManager() {
        this.shelters = new HashMap<>();
    }

    public void addShelter(String name, int capacity) throws InvalidOperationException {
        if (shelters.containsKey(name)) {
            throw new InvalidOperationException("A shelter named " + name + " already exists!");
        }
        shelters.put(name, new AnimalShelter(name, capacity));
    }

    public void removeShelter(String name) throws InvalidOperationException {
        if (!shelters.containsKey(name)) {
            throw new InvalidOperationException("A shelter named \" + name + \" does not exists!");
        }
        shelters.remove(name);
    }

    public List<String> findEmpty() {
        return shelters.values().stream()
                .filter(shelter -> shelter.getAnimalList().isEmpty())
                .map(AnimalShelter::getShelterName)
                .toList();
    }

    public void summary() {
        shelters.forEach((name, shelter) -> {
            double occupancy = (double) shelter.getAnimalList().size() / shelter.getMaxCapacity() * 100;
            System.out.printf("Shelter %s - Occupancy: %.2f%%\n", name, occupancy);
            shelter.summary();
        });
    }

    public Map<String, AnimalShelter> getShelters() {
        return shelters;
    }

    public List<String> getShelterNames() {
        return new ArrayList<>(shelters.keySet());
    }

    public AnimalShelter getShelter(String name) {
        return shelters.get(name);
    }
}