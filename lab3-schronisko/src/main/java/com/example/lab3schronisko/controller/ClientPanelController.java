package com.example.lab3schronisko.controller;

import com.example.lab3schronisko.exceptions.InvalidOperationException;
import com.example.lab3schronisko.model.AnimalCondition;
import com.example.lab3schronisko.model.ShelterManager;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.lab3schronisko.model.AnimalShelter;
import com.example.lab3schronisko.model.Animal;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.Optional;
import javafx.fxml.FXML;
import java.util.List;


public class ClientPanelController {

    @FXML
    private ComboBox<String> shelterComboBox;

    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, String> nameColumn;

    @FXML
    private TableColumn<Animal, String> speciesColumn;

    @FXML
    private TableColumn<Animal, Integer> ageColumn;

    @FXML
    private TableColumn<Animal, String> conditionColumn;

    @FXML
    private TextField filterTextField;

    @FXML
    private Button filterButton;

    @FXML
    private Button adoptButton;

    @FXML
    private Button contactButton;

    @FXML
    private Button sortByAgeButton;

    private ObservableList<Animal> animalList;

    private ShelterManager shelterManager;


    @FXML
    private void initialize() {
        shelterManager = new ShelterManager();

        try {
            shelterManager.addShelter("Shelter A", 10);
            shelterManager.addShelter("Shelter B", 15);

            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Jack", "Dog", AnimalCondition.HEALTHY, 3, 200.0));
            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Mike", "Cat", AnimalCondition.SICK, 2, 100.0));
            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Tony", "Parrot", AnimalCondition.QUARANTINE, 5, 500.0));
        } catch (InvalidOperationException | com.example.lab3schronisko.exceptions.CapacityExceededException e) {
            e.printStackTrace();
        }

        ObservableList<String> shelterNames = FXCollections.observableArrayList(shelterManager.getShelterNames());
        shelterComboBox.setItems(shelterNames);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));

        shelterComboBox.setOnAction(event -> handleShelterSelection());
    }


    @FXML
    private void handleShelterSelection() {
        String selectedShelterName = shelterComboBox.getValue();
        if (selectedShelterName != null) {
            AnimalShelter selectedShelter = shelterManager.getShelter(selectedShelterName);
            if (selectedShelter != null) {
                List<Animal> animals = selectedShelter.getAnimalList();
                animalList = FXCollections.observableArrayList(animals);
                animalTable.setItems(animalList);
            } else {
                animalTable.setItems(FXCollections.emptyObservableList());
            }
        }
    }


    @FXML
    private void handleFilter() {
        String filterText = filterTextField.getText().trim().toLowerCase();
        if (filterText.isEmpty()) {
            shelterComboBox.getSelectionModel().selectFirst();
            handleShelterSelection();
            return;
        }

        String selectedShelterName = shelterComboBox.getValue();
        if (selectedShelterName != null) {
            AnimalShelter selectedShelter = shelterManager.getShelter(selectedShelterName);
            if (selectedShelter != null) {
                List<Animal> filteredAnimals = selectedShelter.getAnimalList().stream()
                        .filter(animal -> animal.getName().toLowerCase().contains(filterText) ||
                                animal.getSpecies().toLowerCase().contains(filterText))
                        .toList();
                animalList = FXCollections.observableArrayList(filteredAnimals);
                animalTable.setItems(animalList);
            }
        }
    }

    @FXML
    private void handleAdoptAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Animal Adoption");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to adopt: " + selectedAnimal.getName() + "?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String shelterName = shelterComboBox.getValue();
                if (shelterName != null) {
                    AnimalShelter shelter = shelterManager.getShelter(shelterName);
                    if (shelter != null) {
                        try {
                            shelter.getAnimalList().remove(selectedAnimal);
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Adoption Completed");
                            info.setHeaderText(null);
                            info.setContentText("Animal" + selectedAnimal.getName() +"Adopted successfully!");
                            info.showAndWait();
                            handleShelterSelection();
                        } catch (Exception e) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Could not adopt an animal");
                        }
                    }
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selected Animal", "Please select an animal for adoption");
        }
    }

    @FXML
    private void handleContactShelter() {
        String selectedShelterName = shelterComboBox.getValue();
        if (selectedShelterName != null) {
            Alert contactAlert = new Alert(Alert.AlertType.INFORMATION);
            contactAlert.setTitle("Contact with the Shelter");
            contactAlert.setHeaderText(null);
            contactAlert.setContentText("Contact the shelter: " + selectedShelterName + " by phone number: 123-456-789");
            contactAlert.showAndWait();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Shelter Selected", "Please select a shelter");
        }
    }

    @FXML
    private void handleSortByAge() {
        if (animalList != null) {
            FXCollections.sort(animalList, Comparator.comparingInt(Animal::getAge));
            animalTable.setItems(animalList);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
