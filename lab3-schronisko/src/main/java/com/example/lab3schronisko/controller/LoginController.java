package com.example.lab3schronisko.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButton(){
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if ("admin".equals(username) && "admin".equals(password)){
            openAdminPanel();
        } else if ("user".equals(username) && "user".equals(password)) {
            openClientPanel();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid login or password");
            alert.showAndWait();
        }
    }

    private void openAdminPanel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab3schronisko/admin_panel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
            stage.show();

            // close login window
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openClientPanel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab3schronisko/client_panel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Client Panel");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
