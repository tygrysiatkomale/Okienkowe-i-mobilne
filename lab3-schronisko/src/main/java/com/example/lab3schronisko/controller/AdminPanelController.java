package com.example.lab3schronisko.controller;

import com.example.lab3schronisko.exceptions.InvalidOperationException;
import com.example.lab3schronisko.model.ShelterManager;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.lab3schronisko.model.AnimalShelter;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.fxml.FXML;

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

    private ObservableList<AnimalShelter> shelterList;

    private ShelterManager shelterManager;

    @FXML
    private void initialize() {
        shelterManager = new ShelterManager();

        // Add example shelters
        try {
            shelterManager.addShelter("Shelter A", 10);
            shelterManager.addShelter("Shelter B", 15);
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }

        // Initialize table
        shelterList = FXCollections.observableArrayList(shelterManager.getShelters().values());
        shelterTable.setItems(shelterList);

        // Initialize columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shelterName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));
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

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

