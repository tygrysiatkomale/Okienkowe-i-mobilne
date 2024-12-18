package pl.edu.lab4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.lab4.dao.AnimalShelterDAO;
import pl.edu.lab4.dto.AnimalShelterDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.exception.EntityNotFoundException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class AnimalShelterService {

    private final AnimalShelterDAO shelterDAO;

    public AnimalShelterService(AnimalShelterDAO shelterDAO) {
        this.shelterDAO = shelterDAO;
    }

    @Transactional
    public AnimalShelter addShelter(AnimalShelterDTO dto) {
        AnimalShelter shelter = new AnimalShelter(dto.getShelterName(), dto.getMaxCapacity());
        shelterDAO.save(shelter);
        return shelter;
    }

    public AnimalShelter getShelterById(Long id) {
        return shelterDAO.findById(id);
    }

    public List<AnimalShelter> getAllShelters() {
        return shelterDAO.findAll();
    }

    @Transactional
    public void deleteShelter(Long id) {
        AnimalShelter shelter = shelterDAO.findById(id);
        if (shelter == null) {
            throw new EntityNotFoundException("Shelter not found");
        }
        shelterDAO.delete(shelter);
    }

    public List<Animal> getAnimalsInShelter(Long id) {
        AnimalShelter shelter = shelterDAO.findById(id);
        if (shelter == null) {
            throw new EntityNotFoundException("Shelter not found");
        }
        return shelter.getAnimalList();
    }

    public Double getShelterFill(Long id) {
        AnimalShelter shelter = shelterDAO.findById(id);
        if (shelter == null) return null;
        int currentCount = shelter.getAnimalList().size();
        int maxCapacity = shelter.getMaxCapacity();
        if (maxCapacity == 0) return 0.0;
        return (double) currentCount / maxCapacity * 100;
    }

    public byte[] generateShelterCSV(Long shelterId) {
        AnimalShelter shelter = shelterDAO.findById(shelterId);
        if (shelter == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String header = "Name,Species,Condition,Age,Price\n";
        try {
            out.write(header.getBytes(StandardCharsets.UTF_8));
            for (Animal a : shelter.getAnimalList()) {
                String line = String.format("%s,%s,%s,%d,%.2f\n",
                        a.getName(), a.getSpecies(), a.getCondition(), a.getAge(), a.getPrice());
                out.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
        }
        return out.toByteArray();
    }
}
