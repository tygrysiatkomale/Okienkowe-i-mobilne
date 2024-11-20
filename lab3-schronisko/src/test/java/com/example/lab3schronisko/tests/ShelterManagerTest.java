package com.example.lab3schronisko.tests;

import com.example.lab3schronisko.exceptions.InvalidOperationException;
import com.example.lab3schronisko.model.AnimalShelter;
import com.example.lab3schronisko.model.ShelterManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy ShelterManager.
 */
public class ShelterManagerTest {

    private ShelterManager shelterManager;

    @BeforeEach
    public void setUp() {
        shelterManager = new ShelterManager();
    }

    @Test
    public void testAddShelter() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        assertNotNull(shelterManager.getShelter("Schronisko A"));
    }

    @Test
    public void testAddDuplicateShelter() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        assertThrows(InvalidOperationException.class, () -> shelterManager.addShelter("Schronisko A", 15));
    }

    @Test
    public void testRemoveShelter() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        shelterManager.removeShelter("Schronisko A");
        assertNull(shelterManager.getShelter("Schronisko A"));
    }

    @Test
    public void testRemoveNonexistentShelter() {
        assertThrows(InvalidOperationException.class, () -> shelterManager.removeShelter("Schronisko B"));
    }

    @Test
    public void testFindEmpty() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        shelterManager.addShelter("Schronisko B", 15);
        assertEquals(2, shelterManager.findEmpty().size());

        // Dodaj zwierzę do Schroniska A
        AnimalShelter shelterA = shelterManager.getShelter("Schronisko A");
        shelterA.getAnimalList().add(new com.example.lab3schronisko.model.Animal("Reksio", "Pies", com.example.lab3schronisko.model.AnimalCondition.HEALTHY, 3, 200.0));
        assertEquals(1, shelterManager.findEmpty().size());
        assertTrue(shelterManager.findEmpty().contains("Schronisko B"));
    }

    @Test
    public void testGetShelterNames() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        shelterManager.addShelter("Schronisko B", 15);
        assertEquals(2, shelterManager.getShelterNames().size());
        assertTrue(shelterManager.getShelterNames().contains("Schronisko A"));
        assertTrue(shelterManager.getShelterNames().contains("Schronisko B"));
    }

    @Test
    public void testGetShelter() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        AnimalShelter shelter = shelterManager.getShelter("Schronisko A");
        assertNotNull(shelter);
        assertEquals("Schronisko A", shelter.getShelterName());
    }

    @Test
    public void testSummary() throws InvalidOperationException {
        shelterManager.addShelter("Schronisko A", 10);
        shelterManager.addShelter("Schronisko B", 15);
        shelterManager.summary();
        // Sprawdzenie, czy nie wystąpiły wyjątki podczas wypisywania podsumowania
    }
}
