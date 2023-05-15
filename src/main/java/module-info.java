module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.login to javafx.fxml;
    exports com.example.login;
}