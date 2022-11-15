module com.example.courseinsuranse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.lahutina.courseinsuranse to javafx.fxml;
    opens com.lahutina.courseinsuranse.models to javafx.fxml;


    exports com.lahutina.courseinsuranse.models;
    exports com.lahutina.courseinsuranse;
    exports com.lahutina.courseinsuranse.repository;
    opens com.lahutina.courseinsuranse.repository to javafx.fxml;
    exports com.lahutina.courseinsuranse.controllers;
    opens com.lahutina.courseinsuranse.controllers to javafx.fxml;
}