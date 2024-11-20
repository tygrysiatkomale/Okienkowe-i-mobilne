package com.example.lab3schronisko.tests;

import com.example.lab3schronisko.exceptions.CapacityExceededException;
import com.example.lab3schronisko.exceptions.InvalidOperationException;
import com.example.lab3schronisko.model.Animal;
import com.example.lab3schronisko.model.AnimalCondition;
import com.example.lab3schronisko.model.AnimalShelter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;


public class AnimalShelterTest {

    private AnimalShelter shelter;

    @BeforeEach
    public void setUp() {
        shelter = new AnimalShelter("Schronisko A", 2);
    }

    @Test
    public void testAddAnimal() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        assertTrue(shelter.getAnimalList().contains(animal1));
    }

    @Test
    public void testAddAnimalExceedCapacity() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Murka", "Kot", AnimalCondition.SICK, 2, 150.0);
        Animal animal3 = new Animal("Burek", "Pies", AnimalCondition.QUARANTINE, 5, 300.0);

        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        assertThrows(CapacityExceededException.class, () -> shelter.addAnimal(animal3));
    }

    @Test
    public void testAddDuplicateAnimal() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        assertThrows(InvalidOperationException.class, () -> shelter.addAnimal(animal1));
    }

    @Test
    public void testRemoveAnimal() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        shelter.removeAnimal(animal1);
        assertFalse(shelter.getAnimalList().contains(animal1));
    }

    @Test
    public void testRemoveNonexistentAnimal() {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        assertThrows(InvalidOperationException.class, () -> shelter.removeAnimal(animal1));
    }

    @Test
    public void testGetAnimal() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        shelter.getAnimal(animal1);
        assertFalse(shelter.getAnimalList().contains(animal1));
        assertEquals(AnimalCondition.IN_PROGRESS_OF_ADOPTION, animal1.getCondition());
    }

    @Test
    public void testChangeCondition() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        shelter.changeCondition(animal1, AnimalCondition.SICK);
        assertEquals(AnimalCondition.SICK, animal1.getCondition());
    }

    @Test
    public void testChangeAge() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        shelter.changeAge(animal1, 4);
        assertEquals(4, animal1.getAge());
    }

    @Test
    public void testCountByCondition() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Murka", "Kot", AnimalCondition.SICK, 2, 150.0);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        assertEquals(1, shelter.countByCondition(AnimalCondition.HEALTHY));
        assertEquals(1, shelter.countByCondition(AnimalCondition.SICK));
    }

    @Test
    public void testSortByName() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Burek", "Pies", AnimalCondition.SICK, 5, 250.0);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        List<Animal> sorted = shelter.sortByName();
        assertEquals("Burek", sorted.get(0).getName());
        assertEquals("Reksio", sorted.get(1).getName());
    }

    @Test
    public void testSortByPrice() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Burek", "Pies", AnimalCondition.SICK, 5, 150.0);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        List<Animal> sorted = shelter.sortByPrice();
        assertEquals("Burek", sorted.get(0).getName());
        assertEquals("Reksio", sorted.get(1).getName());
    }

    @Test
    public void testSearch() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        Optional<Animal> result = shelter.search("Reksio");
        assertTrue(result.isPresent());
        assertEquals(animal1, result.get());
    }

    @Test
    public void testSearchPartial() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Murka", "Kot", AnimalCondition.SICK, 2, 150.0);
        Animal animal3 = new Animal("Burek", "Pies", AnimalCondition.QUARANTINE, 5, 300.0);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        shelter.addAnimal(animal3);
        List<Animal> result = shelter.searchPartial("pe");
        assertEquals(2, result.size());
        assertTrue(result.contains(animal1));
        assertTrue(result.contains(animal3));
    }

    @Test
    public void testSummary() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        shelter.addAnimal(animal1);
        shelter.summary();
        // Sprawdzenie, czy nie wystąpiły wyjątki podczas wypisywania podsumowania
    }

    @Test
    public void testMax() throws CapacityExceededException, InvalidOperationException {
        Animal animal1 = new Animal("Reksio", "Pies", AnimalCondition.HEALTHY, 3, 200.0);
        Animal animal2 = new Animal("Murka", "Kot", AnimalCondition.SICK, 2, 250.0);
        shelter.addAnimal(animal1);
        shelter.addAnimal(animal2);
        Animal maxAnimal = shelter.max();
        assertEquals(animal2, maxAnimal);
    }
}
