package com.lahutina.courseinsuranse.controllers;

import com.lahutina.courseinsuranse.models.Document;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.lahutina.courseinsuranse.controllers.AddController.showAlert;

public class UpdateController {

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
    private Label startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField risk;
    @FXML
    private Label idLabel;
    @FXML
    private Button updateButton;

    @FXML
    public void toShowPage(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/show.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void initData(Document doc, int id){
        initLabels(doc);
        idLabel.setText(String.valueOf(id));
    }
    public void initLabels(Document doc){
        title.setText(doc.getTitle());
        buyerName.setText(doc.getBuyer().getFullName());
        buyerEmail.setText(doc.getBuyer().getEmail());
        sellerName.setText(doc.getSeller().getFullName());
        sellerEmail.setText(doc.getSeller().getEmail());
        startDate.setText(String.valueOf(doc.getStartDate()));
        endDate.setValue(doc.getEndDate().toLocalDate());
        risk.setText(String.valueOf(doc.getRisk()));
    }
    public void updateData()
    {
        Window owner = updateButton.getScene().getWindow();

        if(!emptyFieldsCheck(owner))
            return;

        JdbcDao jdbcDao = new JdbcDao();
        int id = Integer.parseInt(idLabel.getText());
        Document document = readInputData();

        jdbcDao.updateDocument(document, id);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Successfully updated!", "Document \"" + title.getText() + "\" was updated");
    }
    public Document readInputData()
    {
        Document document = new Document();
        document.setTitle(title.getText());

        Person buyer = new Person(buyerName.getText(), buyerEmail.getText());
        Person seller = new Person(sellerName.getText(), sellerEmail.getText());

        document.setBuyer(buyer);
        document.setSeller(seller);

        document.setRisk(Double.parseDouble(risk.getText()));
        document.setEndDate(Date.valueOf(endDate.getValue()));

        return document;
    }

    public boolean emptyFieldsCheck(Window owner) {
        List<TextField> textFields = Arrays.asList(buyerName, sellerName, buyerEmail, sellerEmail, risk);

        for (TextField field : textFields) {
            if (field.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR",
                        "Please make sure you entered all fields!");
                return false;
            }
        }
        return true;
    }
}
