package pl.edu.lab4;

import pl.edu.lab4.entity.AnimalShelter;

import java.io.*;
import java.util.List;

public class DataSerializer {
    public void saveSheltersToFile(List<AnimalShelter> shelters, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(shelters);
            System.out.println("The list of shelters has been saved to a file: " + filename);
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<AnimalShelter> loadSheltersFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> list = (List<?>) obj;
                if (!list.isEmpty() && list.get(0) instanceof AnimalShelter) {
                    return (List<AnimalShelter>) list;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }
}
