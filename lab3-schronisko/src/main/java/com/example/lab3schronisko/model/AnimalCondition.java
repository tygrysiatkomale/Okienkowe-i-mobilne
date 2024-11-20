package com.example.lab3schronisko.model;


// 1. Enum: AnimalCondition
public enum AnimalCondition {
    HEALTHY("Healthy"),
    SICK("Sick"),
    IN_PROGRESS_OF_ADOPTION("In progress of adoption"),
    QUARANTINE("Quarantine");

    private final String displayName;

    AnimalCondition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String toString() {
        return displayName;
    }
}