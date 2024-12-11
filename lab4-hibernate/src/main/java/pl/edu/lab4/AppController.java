package pl.edu.lab4;

import pl.edu.lab4.dao.AnimalDAO;
import pl.edu.lab4.dao.AnimalShelterDAO;
import pl.edu.lab4.dao.RatingDAO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;
import pl.edu.lab4.entity.AnimalShelter;
import pl.edu.lab4.entity.Rating;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AppController {
    private final AnimalShelterDAO animalShelterDAO;
    private final AnimalDAO animalDAO;
    private final RatingDAO ratingDAO;

    public AppController() {
        this.animalShelterDAO = new AnimalShelterDAO();
        this.animalDAO = new AnimalDAO();
        this.ratingDAO = new RatingDAO();
    }

    public void createShelter(String name, int maxCapacity){
        try {
            AnimalShelter shelter = new AnimalShelter(name, maxCapacity);
            animalShelterDAO.save(shelter);
            System.out.println("The shelter has been created and saved to the database");
        } catch (Exception e) {
            System.err.println("Error creating shelter: " + e.getMessage());
        }
    }

    public void addAnimalToShelter(Long shelterId, String animalName, String species, AnimalCondition condition, int age, double price) {
        try {
            AnimalShelter shelter = animalShelterDAO.findById(shelterId);
            if (shelter == null) {
                System.err.println("No shelter with ID found: " + shelterId);
                return;
            }

            Animal animal = new Animal(animalName, species, condition, age, price);
            shelter.addAnimal(animal);
            animalShelterDAO.update(shelter);
            System.out.println("The animal has been added to the shelter and saved in the database");
        } catch (Exception e) {
            System.err.println("Error adding animal: " + e.getMessage());
        }
    }

    public void printAllShelters() {
        try {
            List<AnimalShelter> shelters = animalShelterDAO.findAll();
            if (shelters.isEmpty()) {
                System.out.println("There are no shelters in the database\n");
            } else {
                for (AnimalShelter s : shelters) {
                    System.out.printf("Shelter: %s, Animals: %d/%d\n", s.getShelterName(), s.getAnimalList().size(), s.getMaxCapacity());
                }
            }
        } catch (Exception e) {
            System.err.println("Error displaying shelters: " + e.getMessage());
        }
    }

    public void addRatingToShelter(Long shelterId, int value, String comment) {
        try {
            AnimalShelter shelter = animalShelterDAO.findById(shelterId);
            if (shelter == null) {
                System.err.println("No shelter with ID found: " + shelterId);
                return;
            }
            Rating rating = new Rating(value, comment, LocalDate.now());
            shelter.addRating(rating);
            animalShelterDAO.update(shelter);
            System.out.println("The rating has been added to the shelter");
        } catch (Exception e) {
            System.err.println("Error adding rating: " + e.getMessage());
        }
    }

    public void printAverageRating(Long shelterId) {
        try {
            Double avg = ratingDAO.getAverageRatingForShelter(shelterId);
            if (avg == null) {
                System.out.println("There are no reviews for the shelter with ID: " + shelterId);
            } else {
                System.out.printf("Average rating for the shelter %d is %.2f\n", shelterId, avg);
            }
        } catch (Exception e) {
            System.err.println("Error in calculating average grade: " + e.getMessage());
        }
    }

    public void printShelterRatingStats() {
        List<Object[]> stats = ratingDAO.getShelterRatingStats();
        for (Object[] row : stats) {
            AnimalShelter shelter = (AnimalShelter) row[0];
            Long count = (Long) row[1];
            Double avg = (Double) row[2];
            System.out.printf("Shelter: %s, Number of ratings: %d, Average: %.2f%n",
                    shelter.getShelterName(), count, avg);
        }
    }

}
