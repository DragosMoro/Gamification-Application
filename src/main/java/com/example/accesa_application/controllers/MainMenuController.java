package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import com.example.accesa_application.domain.Quest;
import com.example.accesa_application.domain.Response;
import com.example.accesa_application.domain.User;
import com.example.accesa_application.service.QuestService;
import com.example.accesa_application.service.ResponseService;
import com.example.accesa_application.service.Service;
import com.example.accesa_application.service.UserService;
import com.example.accesa_application.utils.WrappingListCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainMenuController {

    @FXML
    public AnchorPane badgesAnchorPane;

    @FXML
    public AnchorPane leaderboardAnchorPane;

    @FXML
    public AnchorPane overviewAnchorPane;

    @FXML
    public Label pointsLabel;

    @FXML
    public AnchorPane questsAnchorPane;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label solvedQuestsLabel;
    @FXML
    public TableView<Quest<Integer>> userQuestsTableView;
    @FXML
    public TableColumn<Quest<Integer>, String> questTitleColumn;
    @FXML
    public TableColumn<Quest<Integer>, String> pointsColumn;
    @FXML
    public ListView<String> responsesListView;

    @FXML
    public Button markAsBestResponseButton;

    @FXML
    public TableView<Quest<Integer>> availableQuestsTableView;

    @FXML
    public TableColumn<Quest<Integer>, String> availableQuestsTitleColumn;
    @FXML
    public TableColumn<Quest<Integer>, String> availableQuestsPointsColumn;

    @FXML
    public Button createAQuestButton;

    ObservableList<Quest<Integer>> userQuestsModel = FXCollections.observableArrayList();


    private Service service;
    private User<Integer> user;

    public void initialize(Service service, User<Integer> user) {
        this.service = service;
        this.user = user;
        usernameLabel.setText(user.getUsername());
        pointsLabel.setText(String.valueOf(user.getPoints()));
        solvedQuestsLabel.setText(String.valueOf(user.getNumberOfQuestsCompleted()));
        overviewPageView();
        populateUserQuestsTableView();
        populateAvailableQuestsTableView();

    }
    private void refreshPointsAndSolvedQuests() {
        UserService userService = (UserService) service.getUserService();
        User<Integer> user = userService.findAfterId(this.user.getId());
        pointsLabel.setText(String.valueOf(user.getPoints()));
        solvedQuestsLabel.setText(String.valueOf(user.getNumberOfQuestsCompleted()));
    }
    private void overviewPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(true);
        questsAnchorPane.setVisible(false);
    }
    private void badgesPageView() {
        badgesAnchorPane.setVisible(true);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(false);
    }
    private void leaderboardPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(true);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(false);
    }
    private void questsPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(true);
    }

    private void populateUserQuestsTableView() {
        QuestService questService = (QuestService) service.getQuestService();
        ArrayList<Quest<Integer>> userQuests = questService.filterQuestsByUser(user.getId());
        userQuestsModel.setAll(userQuests);
        questTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        userQuestsTableView.setItems(userQuestsModel);
    }
    private Quest<Integer> populateResponsesListView() {
        Quest<Integer> selectedQuest = userQuestsTableView.getSelectionModel().getSelectedItem();
        responsesListView.setCellFactory(new WrappingListCellFactory());
        ObservableList<String> responsesModel = FXCollections.observableArrayList();
        ResponseService responseService = (ResponseService) service.getResponseService();
        UserService userService = (UserService) service.getUserService();
        ArrayList<Response<Integer>>responsesForSpecificQuest = responseService.filterResponsesByQuest(selectedQuest.getId());

        for(Response<Integer> response : responsesForSpecificQuest) {
           String responseInfo = String.format("%s response:\n %s\nStatus: %s",userService.findAfterId(response.getIdUser()).getUsername(),   response.getMessage(), response.getStatus());
              responsesModel.add(responseInfo);
        }
        responsesListView.setItems(responsesModel);
        if (selectedQuest.isCompleted())
        {
            markAsBestResponseButton.setDisable(true);
        }
        else {
            markAsBestResponseButton.setDisable(false);
        }
        return selectedQuest;
    }
    @FXML
    public void onBadgesButtonClick(ActionEvent event) {
        badgesPageView();
    }

    @FXML
    public void onLeaderboardButtonClick(ActionEvent event) {
        leaderboardPageView();
    }

    @FXML
    public void onOverviewButtonClick(ActionEvent event) {
        overviewPageView();
    }

    @FXML
    public void onQuestsButtonClick(ActionEvent event) {
        questsPageView();
    }
    @FXML
    public void onShowAnswersButtonAction(ActionEvent event) {
        populateResponsesListView() ;
    }
    @FXML
    public void onMarkAsBestResponseButtonAction(ActionEvent event) {
        Quest<Integer> selectedQuest = populateResponsesListView();
        ResponseService responseService = (ResponseService) service.getResponseService();
        QuestService questService = (QuestService) service.getQuestService();
        UserService userService = (UserService) service.getUserService();
        questService.markAsCompleted(selectedQuest);
        Response<Integer> selectedResponse = responseService.findAfterId(responsesListView.getSelectionModel().getSelectedIndex() + 1);
        User<Integer> userThatAnswered = userService.findAfterId(selectedResponse.getIdUser());
        userThatAnswered.setPoints(user.getPoints() + selectedQuest.getPoints());
        userService.update(userThatAnswered.getId(),userThatAnswered);
        responseService.markAsBestResponse(selectedResponse);
        user.setNumberOfQuestsCompleted(user.getNumberOfQuestsCompleted() + 1);
        userService.update(user.getId(),user);
        populateResponsesListView();
        refreshPointsAndSolvedQuests();

    }

    private void populateAvailableQuestsTableView() {
        QuestService questService = (QuestService) service.getQuestService();
        ArrayList<Quest<Integer>> availableQuests = questService.filterQuestsByAvailability(user.getId());
        userQuestsModel.setAll(availableQuests);
        availableQuestsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableQuestsPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        availableQuestsTableView.setItems(userQuestsModel);
    }
    @FXML
    public void onSolveTheSelectedQuestButtonAction(ActionEvent event) {

    }

    @FXML
    public void onCreateAQuestButtonAction(ActionEvent event) {

        Scene scene ;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("create_quest.fxml"));
        try {
            scene = new Scene(loader.load(), 1200, 718);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        CreateQuestController createQuestController = loader.getController();
        createQuestController.initialize(service, user);
        Stage currentStage = (Stage) createAQuestButton.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Add a quest");
        currentStage.close();
        newStage.show();
    }


}
