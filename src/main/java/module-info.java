module org.example.projeto_clinica_medica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.projeto_clinica_medica to javafx.fxml;

}