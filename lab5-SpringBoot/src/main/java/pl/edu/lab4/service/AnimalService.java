package pl.edu.lab4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.dao.AnimalDAO;
import pl.edu.lab4.dto.AnimalDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.exception.EntityNotFoundException;

@Service
public class AnimalService {

    private final AnimalDAO animalDAO;
    private final AnimalShelterService shelterService;

    public AnimalService(AnimalDAO animalDAO, AnimalShelterService shelterService) {
        this.animalDAO = animalDAO;
        this.shelterService = shelterService;
    }

    @Transactional
    public Animal addAnimal(AnimalDTO dto) {
        AnimalShelter shelter = shelterService.getShelterById(dto.getShelterId());
        if (shelter == null) {
            throw new EntityNotFoundException("Shelter not found");
        }
        Animal animal = new Animal(dto.getName(), dto.getSpecies(), AnimalCondition.valueOf(dto.getCondition()), dto.getAge(), dto.getPrice());
        shelter.addAnimal(animal);
        animalDAO.save(animal);
        return animal;
    }

    public Animal getAnimalById(Long id) {
        return animalDAO.findById(id);
    }

    @Transactional
    public void deleteAnimal(Long id) {
        Animal animal = animalDAO.findById(id);
        if (animal == null) {
            throw new EntityNotFoundException("Animal not found");
        }
        animalDAO.delete(animal);
    }
}
