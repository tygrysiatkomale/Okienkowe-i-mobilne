package com.example.lab3schronisko.controller;

import com.example.lab3schronisko.exceptions.CapacityExceededException;
import com.example.lab3schronisko.exceptions.InvalidOperationException;
import com.example.lab3schronisko.model.Animal;
import com.example.lab3schronisko.model.AnimalCondition;
import com.example.lab3schronisko.model.ShelterManager;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.lab3schronisko.model.AnimalShelter;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.fxml.FXML;


import java.util.Comparator;
import java.util.Optional;


public class AdminPanelController {

    @FXML
    private TableView<AnimalShelter> shelterTable;

    @FXML
    private TableColumn<AnimalShelter, String> nameColumn;

    @FXML
    private TableColumn<AnimalShelter, Integer> capacityColumn;

    @FXML
    private TextField filterTextField;

    @FXML
    private Button filterButton;

    @FXML
    private Button addShelterButton;

    @FXML
    private Button removeShelterButton;

    @FXML
    private Button editShelterButton;

    @FXML
    private Button sortShelterButton;

    @FXML
    private Button addAnimalButton;

    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, String> animalNameColumn;

    @FXML
    private TableColumn<Animal, String> animalSpeciesColumn;

    @FXML
    private TableColumn<Animal, Integer> animalAgeColumn;

    @FXML
    private TableColumn<Animal, String> animalConditionColumn;

    @FXML
    private TableColumn<Animal, Double> animalPriceColumn;

    private ObservableList<AnimalShelter> shelterList;
    private ObservableList<Animal> animalList;

    private ShelterManager shelterManager;

    @FXML
    private void initialize() {
        shelterManager = new ShelterManager();

        // Add example shelters
        try {
            shelterManager.addShelter("Shelter A", 10);
            shelterManager.addShelter("Shelter B", 15);

            // Add example animals
            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Jack", "Dog", AnimalCondition.HEALTHY, 3, 200.0));
            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Mike", "Cat", AnimalCondition.SICK, 2, 100.0));
            shelterManager.getShelter("Shelter A").addAnimal(new Animal(
                    "Tony", "Parrot", AnimalCondition.QUARANTINE, 5, 500.0));
        } catch (InvalidOperationException | CapacityExceededException e) {
            e.printStackTrace();
        }

        // Initialize table
        shelterList = FXCollections.observableArrayList(shelterManager.getShelters().values());
        shelterTable.setItems(shelterList);

        // Initialize shelter columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shelterName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));

        // Initialize animal table columns
        animalNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        animalSpeciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        animalAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        animalConditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        animalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add listener to shelterTable selection
        shelterTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnimalShelter>() {
            @Override
            public void changed(ObservableValue<? extends AnimalShelter> observable, AnimalShelter oldValue, AnimalShelter newValue) {
                if (newValue != null) {
                    showAnimalsInShelter(newValue);
                } else {
                    animalTable.setItems(FXCollections.observableArrayList());
                }
            }
        });

        // Select first by default
        if (!shelterList.isEmpty()) {
            shelterTable.getSelectionModel().selectFirst();
            showAnimalsInShelter(shelterList.get(0));
        }
    }

    private void showAnimalsInShelter(AnimalShelter shelter) {
        animalList = FXCollections.observableArrayList(shelter.getAnimalList());
        animalTable.setItems(animalList);
    }

    @FXML
    private void handleAddShelter() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Shelter");
        nameDialog.setHeaderText(null);
        nameDialog.setContentText("Shelter Name");

        Optional<String> nameResult = nameDialog.showAndWait();
        if (nameResult.isPresent()) {
            String name = nameResult.get().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Shelter name cannot be empty");
                return;
            }

            TextInputDialog capacityDialog = new TextInputDialog();
            capacityDialog.setTitle("Add Shelter");
            capacityDialog.setHeaderText(null);
            capacityDialog.setContentText("Max Capacity");

            Optional<String> capacityResult = capacityDialog.showAndWait();
            if (capacityResult.isPresent()) {
                String capacityStr = capacityResult.get().trim();
                try {
                    int capacity = Integer.parseInt(capacityStr);
                    if (capacity <= 0) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Maximum capacity must be greater than 0");
                        return;
                    }
                    shelterManager.addShelter(name, capacity);
                    shelterList.add(shelterManager.getShelter(name));
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Maximum capacity must be an integer");
                } catch (InvalidOperationException e) {
                    showAlert(Alert.AlertType.ERROR, "Błąd", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleRemoveShelter() {
        AnimalShelter selectedShelter = shelterTable.getSelectionModel().getSelectedItem();
        if (selectedShelter != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Remove Shelter");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to remove shelter: " + selectedShelter.getShelterName() + "?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    shelterManager.removeShelter(selectedShelter.getShelterName());
                    shelterList.remove(selectedShelter);
                } catch (InvalidOperationException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Shelter Selected", "Please select a shelter to remove");
        }
    }

    @FXML
    private void handleEditShelter() {
        AnimalShelter selectedShelter = shelterTable.getSelectionModel().getSelectedItem();
        if (selectedShelter != null) {
            TextInputDialog nameDialog = new TextInputDialog(selectedShelter.getShelterName());
            nameDialog.setTitle("Edit Shelter");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Shelter Name");

            Optional<String> nameResult = nameDialog.showAndWait();
            if (nameResult.isPresent()) {
                String newName = nameResult.get().trim();
                if (newName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Shelter name cannot be empty");
                    return;
                }

                TextInputDialog capacityDialog = new TextInputDialog(String.valueOf(selectedShelter.getMaxCapacity()));
                capacityDialog.setTitle("Edit Shelter");
                capacityDialog.setHeaderText(null);
                capacityDialog.setContentText("Max Capacity");

                Optional<String> capacityResult = capacityDialog.showAndWait();
                if (capacityResult.isPresent()) {
                    String capacityStr = capacityResult.get().trim();
                    try {
                        int newCapacity = Integer.parseInt(capacityStr);
                        if (newCapacity <= 0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Maximum capacity must be greater than 0");
                            return;
                        }
                        selectedShelter.setShelterName(newName);
                        selectedShelter.setMaxCapacity(newCapacity);
                        shelterTable.refresh();
                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Maximum capacity must be an integer");
                    }
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Shelter Selected", "Please select a shelter to edit");
        }
    }

    @FXML
    private void handleSortShelter() {
        FXCollections.sort(shelterList, Comparator.comparingInt(AnimalShelter::getMaxCapacity));
        shelterTable.setItems(shelterList);
    }

    @FXML
    private void handleFilterShelters() {
        String filterText = filterTextField.getText().trim().toLowerCase();
        if (filterText.isEmpty()) {
            shelterTable.setItems(shelterList);
            return;
        }

        ObservableList<AnimalShelter> filteredList = FXCollections.observableArrayList(
                shelterList.stream()
                        .filter(shelter -> shelter.getShelterName().toLowerCase().contains(filterText))
                        .toList()
        );
        shelterTable.setItems(filteredList);
    }

    @FXML
    private void handleAddAnimal() {
        AnimalShelter selectedShelter = shelterTable.getSelectionModel().getSelectedItem();
        if (selectedShelter == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No Shelter Selected");
            return;
        }

        // Wprowadzanie nazwy
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Animal");
        nameDialog.setHeaderText(null);
        nameDialog.setContentText("Animal Name");

        Optional<String> nameResult = nameDialog.showAndWait();
        if (!nameResult.isPresent()) return;

        String name = nameResult.get().trim();
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Animal Name cannot be empty");
            return;
        }

        // Wprowadzanie gatunku
        TextInputDialog speciesDialog = new TextInputDialog();
        speciesDialog.setTitle("Add Animal");
        speciesDialog.setHeaderText(null);
        speciesDialog.setContentText("Species: ");

        Optional<String> speciesResult = speciesDialog.showAndWait();
        if (!speciesResult.isPresent()) return;

        String species = speciesResult.get().trim();
        if (species.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Species cannot be empty");
            return;
        }

        // Wprowadzanie zdrowia
        ChoiceDialog<AnimalCondition> conditionsDialog = new ChoiceDialog<>(AnimalCondition.HEALTHY, AnimalCondition.values());
        conditionsDialog.setTitle("Add Animal");
        conditionsDialog.setHeaderText(null);
        conditionsDialog.setContentText("Select Condition: ");

        Optional<AnimalCondition> conditionResult = conditionsDialog.showAndWait();
        if (!conditionResult.isPresent()) return;

        AnimalCondition condition = conditionResult.get();

        // Wprowadzanie wieku
        TextInputDialog ageDialog = new TextInputDialog();
        ageDialog.setTitle("Add Animal");
        ageDialog.setHeaderText(null);
        ageDialog.setContentText("Age: ");

        Optional<String> ageResult = ageDialog.showAndWait();
        if (!ageResult.isPresent()) return;
        String ageStr = ageResult.get().trim();
        int age;
        try{
            age = Integer.parseInt(ageStr);
            if (age < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Age must be a positive integer");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Age must be an integer");
            return;
        }

        // Wprowadzanie ceny
        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setTitle("Add Animal");
        priceDialog.setHeaderText(null);
        priceDialog.setContentText("Price: ");

        Optional<String> priceResult = priceDialog.showAndWait();
        if (!priceResult.isPresent()) return;

        String priceStr = priceResult.get().trim();
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Price must be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price must be a number");
            return;
        }

        // Tworzenie obiektu animal
        Animal newAnimal = new Animal(name, species, condition, age, price);

        // Dodawanie do schroniska
        try {
            selectedShelter.addAnimal(newAnimal);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Animal Added successfully");
        } catch (CapacityExceededException | InvalidOperationException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }

        showAnimalsInShelter(selectedShelter);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

