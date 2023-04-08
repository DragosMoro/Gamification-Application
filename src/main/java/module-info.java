module com.example.accesa_application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.accesa_application to javafx.fxml;
    opens com.example.accesa_application.repository;
    opens com.example.accesa_application.domain;
    opens com.example.accesa_application.service;
    opens com.example.accesa_application.controllers;

    exports com.example.accesa_application;
    exports com.example.accesa_application.repository;
    exports com.example.accesa_application.domain;
    exports com.example.accesa_application.service;
    exports com.example.accesa_application.controllers;
}