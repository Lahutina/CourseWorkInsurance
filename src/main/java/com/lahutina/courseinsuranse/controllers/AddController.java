package com.lahutina.courseinsuranse.controllers;

import com.lahutina.courseinsuranse.models.Document;
import com.lahutina.courseinsuranse.models.Futures;
import com.lahutina.courseinsuranse.models.Option;
import com.lahutina.courseinsuranse.models.Person;
import com.lahutina.courseinsuranse.repository.JdbcDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddController {
    @FXML
    private TextField title;
    @FXML
    private TextField buyerName;
    @FXML
    private TextField buyerEmail;
    @FXML
    private TextField sellerName;
    @FXML
    private TextField sellerEmail;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField risk;
    @FXML
    private TextField buyerPay;
    @FXML
    private TextField sellerPay;
    @FXML
    private Button submitButton;
    @FXML
    private CheckBox futureCheckBox;
    @FXML
    private CheckBox optionCheckBox;

    @FXML
    public void toMainPage(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/main.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void submitGeneral(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        if(!emptyFieldsCheck(owner))
            return;

        JdbcDao jdbcDao = new JdbcDao();
        Person buyer = new Person(buyerName.getText(), buyerEmail.getText());
        Person seller = new Person(sellerName.getText(), sellerEmail.getText());

        Document document = new Document(
                title.getText(), buyer, seller,
                Date.valueOf(startDate.getValue()),
                Date.valueOf(endDate.getValue()),
                Double.parseDouble(risk.getText()));

        if(futureCheckBox.isSelected())
        {
            Futures futures = new Futures(document);
            futures.setBuyerPayment(Double.parseDouble(buyerPay.getText()));
            futures.setSellerPayment(Double.parseDouble(sellerPay.getText()));

            jdbcDao.insertFutures(futures);
        }

        if(optionCheckBox.isSelected())
        {
            Option option = new Option(document);
            option.setBuyerPayment(Double.parseDouble(buyerPay.getText()));

            jdbcDao.insertOption(option);
        }

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Successfuly saved!", "Document \"" + title.getText() + "\" was added");
    }

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public boolean emptyFieldsCheck(Window owner)
    {
        List<TextField> textFields = Arrays.asList(buyerName, sellerName, buyerEmail, sellerEmail, risk, buyerPay);

        for(TextField field:textFields){
            if (field.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR",
                        "Please make sure you entered all fields!");
                return false;
            }
        }

        if (!futureCheckBox.isSelected() && !optionCheckBox.isSelected()) {
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!",
                    "Please choose type of the document");
            return false;
        }

        if(optionCheckBox.isSelected() && !sellerPay.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!",
                    "In option only buyer pays");
            return false;
        }
        return true;
    }
}
