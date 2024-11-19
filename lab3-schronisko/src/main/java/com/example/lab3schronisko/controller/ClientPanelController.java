package com.example.lab3schronisko.controller;

import com.example.lab3schronisko.model.Animal;
import com.example.lab3schronisko.model.AnimalShelter;
import com.example.lab3schronisko.model.ShelterManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private ObservableList<Animal> animalList;

    private ShelterManager shelterManager;

    @FXML
    private void initialize() {
        shelterManager = new ShelterManager();

        // Initialize combobox with shelter names
        ObservableList<String> shelterNames = FXCollections.observableArrayList(shelterManager.getShelterNames());
        shelterComboBox.setItems(shelterNames);

        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    @FXML
    private void handleShelterSelection() {
        String selectedShelter = shelterComboBox.getValue();
        AnimalShelter selectedShelter = shelterManager.getShelter(selectedShelter);
        animalList = FXCollections.observableArrayList(selectedShelter.getAnimalList());
        animalTable.setItems(animalList);
    }

}
