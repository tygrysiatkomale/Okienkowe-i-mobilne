package com.example.lab3schronisko.controller;

import com.example.lab3schronisko.model.AnimalShelter;
import com.example.lab3schronisko.model.ShelterManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class AdminPanelController {

    @FXML
    private TableView<AnimalShelter> shelterTable;

    @FXML
    private TableColumn<AnimalShelter, String> nameColumn;

    @FXML
    private TableColumn<AnimalShelter, Integer> capacityColumn;

    private ObservableList<AnimalShelter> shelterList;

    private ShelterManager shelterManager;

    @FXML
    private void initialize() {
        shelterManager = new ShelterManager();
        shelterList = FXCollections.observableArrayList(shelterManager.getShelters().values());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shelterName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));

        shelterTable.setItems(shelterList);
    }
}
