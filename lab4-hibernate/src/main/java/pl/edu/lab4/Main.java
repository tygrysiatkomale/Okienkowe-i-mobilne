package pl.edu.lab4;

import pl.edu.lab4.CSVExporter;
import pl.edu.lab4.CSVImporter;
import pl.edu.lab4.dao.AnimalDAO;
import pl.edu.lab4.dao.AnimalShelterDAO;
import pl.edu.lab4.dao.RatingDAO;
import pl.edu.lab4.entity.AnimalCondition;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AppController controller = new AppController();
        AnimalShelterDAO shelterDAO = new AnimalShelterDAO();
        AnimalDAO animalDAO = new AnimalDAO();
        RatingDAO ratingDAO = new RatingDAO();
        DataSerializer dataSerializer = new DataSerializer();
        CSVExporter csvExporter = new CSVExporter();
        CSVImporter csvImporter = new CSVImporter();

//        Creating shelter
        controller.createShelter("Central Shelter", 5);

//        Let's assume that the shelter has ID 1 (the first record in the database should have id=1 thanks to IDENTITY)
        Long shelterId = 1L;

//        Adding animals
        controller.addAnimalToShelter(shelterId, "Burek", "Pies", AnimalCondition.ZDROWE, 3, 200.0);
        controller.addAnimalToShelter(shelterId, "Misia", "Kot", AnimalCondition.CHORE, 2, 150.0);
        controller.addAnimalToShelter(shelterId, "Rex", "Pies", AnimalCondition.W_TRAKCIE_ADOPCJI, 4, 300.0);


//        View all shelters and animals
        controller.printAllShelters();

//        Add ratings
        controller.addRatingToShelter(shelterId, 5, "Awesome Shelter");
        controller.addRatingToShelter(shelterId, 4, "almost good, could be cheaper");

//        View ratings
        controller.printAverageRating(shelterId);

//        Criteria
        controller.printShelterRatingStats();

//        Export shelter data to csv
        var shelter = shelterDAO.findById(shelterId);
        csvExporter.exportShelterToCSV(shelter, "shelter_export.csv");

//        Import shelter from csv to new shelter in db
        csvImporter.importShelterFromCSV("shelter_export.csv", shelterDAO, animalDAO, "Imported Shelter", 10);

//        View all shelters
        controller.printAllShelters();

//        Serialization to binary file
        List<pl.edu.lab4.entity.AnimalShelter> allShelters = shelterDAO.findAll();
        dataSerializer.saveSheltersToFile(allShelters, "shelters.dat");











    }
}
