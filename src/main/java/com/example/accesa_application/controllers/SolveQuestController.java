package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import com.example.accesa_application.domain.Quest;
import com.example.accesa_application.domain.Response;
import com.example.accesa_application.domain.User;
import com.example.accesa_application.service.ResponseService;
import com.example.accesa_application.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SolveQuestController {

    @FXML
    public Label errorLabel;
    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public TextArea answerTextArea;
    @FXML
    public Label pointsLabel;

    @FXML
    public Label titleLabel;

    @FXML
    public Label backLabel;
    private Service service;
    private User<Integer> user;
    private Quest<Integer> selectedQuest;


    public void initialize(Service service, User<Integer> user, Quest<Integer> selectedQuest) {
        this.service = service;
        this.user = user;
        this.selectedQuest = selectedQuest;
        titleLabel.setText(selectedQuest.getName());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setPrefWidth(500); // Set the preferred width to 100px
        descriptionTextArea.setMaxWidth(Double.MAX_VALUE);
        descriptionTextArea.setText(selectedQuest.getDescription());
        pointsLabel.setText(String.valueOf(selectedQuest.getPoints()));
    }
    @FXML
    void onAddAnswerButtonAction(ActionEvent event) {
        String answer = answerTextArea.getText();

        if (answer.equals("")) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Answer cannot be empty!");
            }
        else{
        ResponseService responseService = (ResponseService) service.getResponseService();
        Response<Integer> response = new Response<>(user.getId(), selectedQuest.getId(), answer, "Not Evaluated");
        responseService.add(response);
        errorLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        errorLabel.setText("Answer added successfully!");
        answerTextArea.setText("");
    }}
    @FXML
    public void onBackLabelClickAction(){
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
