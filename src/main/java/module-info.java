/**
 * Info regarding modules used within the project
 */
module final_project.csci2020u_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;

    opens final_project.csci2020u_final_project to javafx.fxml;
    exports final_project.csci2020u_final_project;
}