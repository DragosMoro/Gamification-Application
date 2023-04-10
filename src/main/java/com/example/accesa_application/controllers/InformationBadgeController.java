package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InformationBadgeController {
    @FXML
    public Button closeButton;
    @FXML
    public void onCloseButtonAction()
    {

        Stage currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();

    }
}
