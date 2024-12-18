package pl.edu.lab4.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.lab4.dto.AnimalShelterDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.exception.EntityNotFoundException;
import pl.edu.lab4.service.AnimalShelterService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnimalShelterController {

    private final AnimalShelterService shelterService;

    public AnimalShelterController(AnimalShelterService shelterService) {
        this.shelterService = shelterService;
    }

    // GET /api/animalshelter/csv - return csv shelter info
    @GetMapping("/animalshelter/csv")
    public ResponseEntity<byte[]> getShelterAsCSV(@RequestParam Long shelterId) {
        byte[] csvData = shelterService.generateShelterCSV(shelterId);
        if (csvData == null) {
            throw new EntityNotFoundException("Shelter not found");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=shelter.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvData);
    }

    // GET /api/sheltermanager - returns all shelters
    @GetMapping("/sheltermanager")
    public ResponseEntity<List<AnimalShelter>> getAllShelters() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }

    // POST /api/animalshelter - adds new shelter
    @PostMapping("/animalshelter")
    public ResponseEntity<AnimalShelter> addShelter(@RequestBody AnimalShelterDTO dto) {
        AnimalShelter shelter = shelterService.addShelter(dto);
        return ResponseEntity.ok(shelter);
    }

    // DELETE = removes a shelter
    @DeleteMapping("/animalshelter/{id}")
    public ResponseEntity<String> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok("Shelter deleted");
    }

    // GET - get all animals in shelter
    @GetMapping("/animalshelter/{id}/animal")
    public ResponseEntity<List<Animal>> getAnimalsInShelter(@PathVariable Long id) {
        List<Animal> animals = shelterService.getAnimalsInShelter(id);
        return ResponseEntity.ok(animals);
    }

    // GET - Returns shelter occupancy
    @GetMapping("/animalshelter/{id}/fill")
    public ResponseEntity<Double> getShelterFill(@PathVariable Long id) {
        Double fill = shelterService.getShelterFill(id);
        if (fill == null) {
            throw new EntityNotFoundException("Shelter not found");
        }
        return ResponseEntity.ok(fill);
    }
}
