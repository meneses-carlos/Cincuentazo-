module com.projects.poe.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projects.poe.cincuentazo to javafx.fxml;
    exports com.projects.poe.cincuentazo;
}