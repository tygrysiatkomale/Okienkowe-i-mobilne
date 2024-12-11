package pl.edu.lab4;

import pl.edu.lab4.dao.AnimalDAO;
import pl.edu.lab4.dao.AnimalShelterDAO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;
import pl.edu.lab4.entity.AnimalShelter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVImporter {
    public void importShelterFromCSV(String filename, AnimalShelterDAO shelterDAO, AnimalDAO animalDAO, String shelterName, int maxCapacity) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine();

            AnimalShelter shelter = new AnimalShelter(shelterName, maxCapacity);
            shelterDAO.save(shelter);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 5) {
                    System.err.println("Invalid CSV line: " + line);
                    continue;
                }

                String name = fields[0].trim();
                String species = fields[1].trim();
                AnimalCondition condition = AnimalCondition.valueOf(fields[2].trim());
                int age = Integer.parseInt(fields[3].trim());
                double price = Double.parseDouble(fields[4].trim());

                Animal animal = new Animal(name, species, condition, age, price);
                shelter.addAnimal(animal);
            }

            shelterDAO.update(shelter);
            System.out.println("Data was imported from CSV and saved to the database");
        } catch (IOException e) {
            System.err.println("Error importing from CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing data from CSV: " + e.getMessage());
        }
    }
}
