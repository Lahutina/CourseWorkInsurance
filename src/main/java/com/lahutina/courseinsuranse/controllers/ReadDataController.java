package com.lahutina.courseinsuranse.controllers;

import com.lahutina.courseinsuranse.models.Document;
import com.lahutina.courseinsuranse.models.Futures;
import com.lahutina.courseinsuranse.models.Option;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ReadDataController {
    @FXML
    private Label title;
    @FXML
    private Label buyerName;
    @FXML
    private Label buyerEmail;
    @FXML
    private Label sellerName;
    @FXML
    private Label sellerEmail;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label risk;
    @FXML
    private Label buyerPay;
    @FXML
    private Label sellerPay;
    @FXML
    private Label profit;
    @FXML
    private Label type;

    @FXML
    public void toShowPage(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/show.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void showFutures(Futures doc){
        initLabels(doc);
        sellerPay.setText(String.valueOf(doc.getSellerPayment()));
        buyerPay.setText(String.valueOf(doc.getBuyerPayment()));
        profit.setText(String.valueOf(doc.getBuyerPayment() + doc.getSellerPayment()));
        type.setText("Futures");
    }
    public void showOption(Option doc){
        initLabels(doc);
        sellerPay.setText("");
        buyerPay.setText(String.valueOf(doc.getBuyerPayment()));
        profit.setText(String.valueOf(doc.getBuyerPayment()));
        type.setText("Option");
    }
    public void initLabels(Document doc){
        title.setText(doc.getTitle());
        buyerName.setText(doc.getBuyer().getFullName());
        buyerEmail.setText(doc.getBuyer().getEmail());
        sellerName.setText(doc.getSeller().getFullName());
        sellerEmail.setText(doc.getSeller().getEmail());
        startDate.setText(String.valueOf(doc.getStartDate()));
        endDate.setText(String.valueOf(doc.getEndDate()));
        risk.setText(String.valueOf(doc.getRisk()));
    }
}
