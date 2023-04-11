package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import com.example.accesa_application.domain.Quest;
import com.example.accesa_application.domain.User;
import com.example.accesa_application.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateQuestController {
    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public TextField rewardInPointsTextField;

    @FXML
    public TextField titleTextField;

    @FXML
    public Label errorLabel;
    @FXML
    public Label backLabel;

   private Service service;
    private User<Integer> user;


    public void initialize(Service service, User<Integer> user) {
        this.service = service;
        this.user = user;
    }

    @FXML
    public void onAddQuestButtonAction() {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        String rewardInPoints = rewardInPointsTextField.getText();
        if (title.isEmpty() || description.isEmpty() || rewardInPoints.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }
        try {
            int reward = Integer.parseInt(rewardInPoints);
            if (reward < 0) {
                errorLabel.setTextFill(javafx.scene.paint.Color.RED);
                errorLabel.setText("Reward must be a positive number!");
                return;
            }
            if(reward > user.getPoints()){
                errorLabel.setTextFill(javafx.scene.paint.Color.RED);
                errorLabel.setText("You don't have enough points!");
                return;
            }
            user.setPoints(user.getPoints() - reward);
            service.getUserService().update(user.getId(), user);
            Quest<Integer> quest = new Quest<>(user.getId(), title, description, reward, false);
            service.getQuestService().add(quest);
            errorLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            errorLabel.setText("Quest added successfully!");
        } catch (NumberFormatException e) {
            errorLabel.setText("Reward must be a number!");
        }
    }

    @FXML
    public void onBackButtonClickAction() {
        Scene scene;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("main_menu.fxml"));
        try {
            scene = new Scene(loader.load(), 1200, 718);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.initialize(service, user);
        mainMenuController.questsPageView();
        Stage currentStage = (Stage) backLabel.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Accesa Application");
        currentStage.close();
        newStage.show();
    }
}
