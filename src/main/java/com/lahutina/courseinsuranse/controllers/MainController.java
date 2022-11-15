package com.lahutina.courseinsuranse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    private Stage stage;
    private Scene scene;

    public void switchToShow(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/show.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
    public void switchToAdd(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/add.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}