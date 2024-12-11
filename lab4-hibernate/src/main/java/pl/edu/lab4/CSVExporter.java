package pl.edu.lab4;

import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalShelter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVExporter {

    public void exportShelterToCSV(AnimalShelter shelter, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {

            pw.println("name,species,condition,age,price");

            for (Animal a : shelter.getAnimalList()) {
                pw.printf("%s,%s,%s,%d,%.2f%n",
                        a.getName(),
                        a.getSpecies(),
                        a.getCondition(),
                        a.getAge(),
                        a.getPrice());
            }
            System.out.println("The data has been exported to a file: " + filename);
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }
}
