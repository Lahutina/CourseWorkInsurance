package com.lahutina.courseinsuranse.controllers;

import com.lahutina.courseinsuranse.models.Futures;
import com.lahutina.courseinsuranse.models.Option;
import com.lahutina.courseinsuranse.models.TableDto;
import com.lahutina.courseinsuranse.repository.JdbcDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowController implements Initializable {

    @FXML
    private ChoiceBox<Integer> choiceBox;
    @FXML
    private TableView<TableDto> tableView;
    @FXML
    public TableColumn<TableDto, Integer> tableId;
    @FXML
    public TableColumn<TableDto, String> tableTitle;
    @FXML
    public TableColumn<TableDto, String> tableBuyerName;
    @FXML
    public TableColumn<TableDto, String> tableSellerName;
    @FXML
    public TableColumn<TableDto, Date> tableEndDate;
    @FXML
    public TableColumn<TableDto, Integer> tableRisk;
    @FXML
    public Label calculateProfitLabel;
    @FXML
    public Button calculateProfit;


    @FXML
    public void toMainPage(ActionEvent event) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/pages/main.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JdbcDao jdbcDao = new JdbcDao();
        choiceBox.getItems().addAll(jdbcDao.getIDs());
        insertDataToTable();

        tableView.setItems(jdbcDao.getAllToTable());
    }

    public void read(ActionEvent event) throws IOException, SQLException {
        JdbcDao jdbcDao = new JdbcDao();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/lahutina/courseinsuranse/pages/read.fxml"));

        Parent viewParent = loader.load();

        Scene viewScene = new Scene(viewParent);
        viewScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());

        ReadDataController readDataController = loader.getController();
        Object selected = tableView.getSelectionModel().getSelectedItem();
        if(selected==null)
            return;

        int selectedId = tableView.getSelectionModel().getSelectedItem().getId();

        if(jdbcDao.getTypeById(selectedId).equals("F"))
        {
            Futures doc = jdbcDao.readFutures(selectedId);
            readDataController.showFutures(doc);
        } else  {
            Option doc = jdbcDao.readOption(selectedId);
            readDataController.showOption(doc);
        }

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void insertDataToTable()
    {
        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        tableBuyerName.setCellValueFactory(new PropertyValueFactory<>("Buyer"));
        tableSellerName.setCellValueFactory(new PropertyValueFactory<>("Seller"));
        tableEndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        tableRisk.setCellValueFactory(new PropertyValueFactory<>("Risk"));
    }

    public void showFutures()
    {
        JdbcDao jdbcDao = new JdbcDao();
        insertDataToTable();
        tableView.setItems(jdbcDao.getFuturesToTable());
    }
    public void showOptions()
    {
        JdbcDao jdbcDao = new JdbcDao();
        insertDataToTable();
        tableView.setItems(jdbcDao.getOptionsToTable());
    }

    public void showAll()
    {
        JdbcDao jdbcDao = new JdbcDao();
        insertDataToTable();
        tableView.setItems(jdbcDao.getAllToTable());
    }
    public void sortedByRisk()
    {
        JdbcDao jdbcDao = new JdbcDao();
        insertDataToTable();
        tableView.setItems(jdbcDao.sortedByRisk());
    }

    public void deleteById(){
        JdbcDao jdbcDao = new JdbcDao();
        insertDataToTable();
        if(choiceBox.getValue()!=null)
            tableView.setItems(jdbcDao.deleteById(choiceBox.getValue()));
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(jdbcDao.getIDs());
    }

    public void updateById(ActionEvent event) throws IOException {
        JdbcDao jdbcDao = new JdbcDao();
        if(choiceBox.getValue()!=null){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/lahutina/courseinsuranse/pages/update.fxml"));

            Parent viewParent = loader.load();

            Scene viewScene = new Scene(viewParent);
            viewScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/lahutina/courseinsuranse/styles/style.css")).toExternalForm());

            UpdateController updateController = loader.getController();
            updateController.initData(jdbcDao.readFutures(choiceBox.getValue()), choiceBox.getValue());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(viewScene);
            window.show();
        }
    }

    public void setCalculateProfitLabel(){
        JdbcDao jdbcDao = new JdbcDao();
        double sum = jdbcDao.calculateProfit();
        calculateProfitLabel.setText(String.valueOf(sum));
    }
}
