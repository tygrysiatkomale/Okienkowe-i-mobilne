package pl.edu.lab4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.edu.lab4.dto.AnimalDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.exception.EntityNotFoundException;
import pl.edu.lab4.service.AnimalService;

@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // POST /api/animal - Adds animal to the shelter
    @PostMapping("/animal")
    public ResponseEntity<Animal> addAnimal(@RequestBody AnimalDTO dto) {
        Animal created = animalService.addAnimal(dto);
        return ResponseEntity.ok(created);
    }

    // DELETE /api/animal/{id} - removes animal
    @DeleteMapping("/animal/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok("Animal deleted");
    }

    // GET /api/animal/{id} - returns info about animal
    @GetMapping("/animal/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animalService.getAnimalById(id);
        if (animal == null) {
            throw new EntityNotFoundException("Animal not found");
        }
        return ResponseEntity.ok(animal);
    }

}