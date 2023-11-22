module com.example.studentapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires mysql.connector.j;
    requires java.sql;

    opens com.student.studentapp to javafx.fxml;
    opens com.student.model to javafx.base;
    exports com.student.studentapp;
    exports com.student.controller;
    opens com.student.controller to javafx.fxml;
}