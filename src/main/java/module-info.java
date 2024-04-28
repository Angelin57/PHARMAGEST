module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires org.apache.pdfbox;


    opens com.example.login to javafx.fxml;
    exports com.example.login;
    exports com.example.login.model;
    exports com.example.login.controller;
    opens com.example.login.controller to javafx.fxml;
}