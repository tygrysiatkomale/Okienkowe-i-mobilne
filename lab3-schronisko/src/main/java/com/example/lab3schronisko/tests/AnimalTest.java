package com.example.lab3schronisko.tests;

import com.example.lab3schronisko.model.Animal;
import com.example.lab3schronisko.model.AnimalCondition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Animal.
 */
public class AnimalTest {

    @Test
    public void testAnimalCreation() {
        Animal animal = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        assertEquals("Reksio", animal.getName());
        assertEquals("Pies", animal.getSpecies());
        assertEquals(AnimalCondition.HEALTHY, animal.getCondition());
        assertEquals(3, animal.getAge());
        assertEquals(200.0, animal.getPrice());
    }

    @Test
    public void testAnimalComparison() {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Burek", "Pies", AnimalCondition.SICK, 5, 250.0);
        assertTrue(animal1.compareTo(animal2) > 0); // "Reksio" > "Burek" w alfabetycznym porównaniu
    }

    @Test
    public void testAnimalEquality() {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Reksio", "Pies", AnimalCondition.SICK, 3, 200.0);
        assertEquals(animal1, animal2);
    }

    @Test
    public void testSetAgeNegative() {
        Animal animal = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            animal.setAge(-1);
        });
        assertEquals("Wiek nie może być ujemny", exception.getMessage());
    }

    @Test
    public void testSetPriceNegative() {
        Animal animal = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            animal.setPrice(-50.0);
        });
        assertEquals("Cena nie może być ujemna", exception.getMessage());
    }
}
