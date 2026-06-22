module com.projects.poe.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.projects.poe.cincuentazo to javafx.fxml;
    exports com.projects.poe.cincuentazo;
    exports com.projects.poe.cincuentazo.controller;
    opens com.projects.poe.cincuentazo.view to javafx.graphics, javafx.fxml;
    opens com.projects.poe.cincuentazo.controller to javafx.fxml;


}