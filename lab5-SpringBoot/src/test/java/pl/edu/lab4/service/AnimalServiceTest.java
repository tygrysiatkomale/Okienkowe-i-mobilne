package pl.edu.lab4.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.lab4.dao.AnimalDAO;
import pl.edu.lab4.dao.AnimalShelterDAO;
import pl.edu.lab4.dto.AnimalDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalDAO animalDAO;
    @Mock
    private AnimalShelterService shelterService;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void testAddAnimal() {
        AnimalDTO dto = new AnimalDTO();
        dto.setName("Burek");
        dto.setSpecies("Pies");
        dto.setCondition("ZDROWE");
        dto.setAge(3);
        dto.setPrice(200.0);
        dto.setShelterId(1L);

        AnimalShelter shelter = new AnimalShelter("Central Shelter", 10);
        Mockito.when(shelterService.getShelterById(1L)).thenReturn(shelter);

        animalService.addAnimal(dto);

        Mockito.verify(animalDAO, Mockito.times(1)).save(Mockito.any(Animal.class));
    }

    @Test
    void testAddAnimal_ShelterNotFound() {
        AnimalDTO dto = new AnimalDTO();
        dto.setShelterId(2L);

        Mockito.when(shelterService.getShelterById(2L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> animalService.addAnimal(dto));
    }
}
