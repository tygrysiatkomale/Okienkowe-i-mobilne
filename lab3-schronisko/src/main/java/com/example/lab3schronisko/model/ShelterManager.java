package com.example.lab3schronisko.model;


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

    public void addShelter(String name, int capacity) {
        if (!shelters.containsKey(name)) {
            shelters.put(name, new AnimalShelter(name, capacity));
        }
    }

    public void removeShelter(String name) {
        shelters.remove(name);
    }

    public List<String> findEmpty() {
        return shelters.entrySet().stream()
                .filter(entry -> entry.getValue().getAnimalList().isEmpty())
                .map(Map.Entry::getKey)
                .toList();
    }

    public void summary() {
        for (var entry : shelters.entrySet()) {
            String shelterName = entry.getKey();
            AnimalShelter shelter = entry.getValue();
            double occupancy = (double) shelter.getAnimalList().size() / shelter.getMaxCapacity() * 100;
            System.out.printf("Schronisko %s - Zapełnienie: %.2f%%\n", shelterName, occupancy);
            shelter.summary();
        }
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