package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import com.example.accesa_application.domain.*;
import com.example.accesa_application.service.*;
import com.example.accesa_application.utils.WrappingListCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public AnchorPane shopAnchorPane;

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
    @FXML
    public Button solveTheSelectedQuestButton;

    @FXML
    public TableView<User<Integer>> leaderboardTableView;

    @FXML
    public TableColumn<User<Integer>, String> usernameColumn;
    @FXML
    public TableColumn<User<Integer>, Integer> questsCompletedColumn;

    @FXML
    public TableView<Item<Integer>> shopTableView;
    @FXML
    public TableColumn<Item<Integer>, String> itemNameTableColumn;
    @FXML
    public TableColumn<Item<Integer>, Integer> itemPriceTableColumn;
    @FXML
    public Button buyItemButton;
    @FXML
    public Label errorShopLabel;
    @FXML
    public Button logoutButton;

    @FXML
    public AnchorPane firstBadge;
    @FXML
    public AnchorPane secondBadge;
    @FXML
    public AnchorPane thirdBadge;
    @FXML
    public AnchorPane fourthBadge;
    @FXML
    public AnchorPane fifthBadge;
    @FXML
    public AnchorPane sixthBadge;
    @FXML
    public AnchorPane seventhBadge;
    @FXML
    public AnchorPane eighthBadge;

    @FXML
    public ListView<String> userItemsListView;

    ObservableList<Quest<Integer>> userQuestsModel = FXCollections.observableArrayList();
    ObservableList<Quest<Integer>> availableQuestsModel = FXCollections.observableArrayList();
    ObservableList<User<Integer>> leaderboardModel = FXCollections.observableArrayList();

    ObservableList<Item<Integer>> shopModel = FXCollections.observableArrayList();
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
        populateLeaderboard();
        activateBadges();
        populateUserItemsListView();
        populateShopTableView();

    }
    private void refreshPointsAndSolvedQuests() {
        UserService userService = (UserService) service.getUserService();
        User<Integer> userNew = userService.findAfterId(this.user.getId());
        pointsLabel.setText(String.valueOf(userNew.getPoints()));
        solvedQuestsLabel.setText(String.valueOf(userNew.getNumberOfQuestsCompleted()));
    }
    private void overviewPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(true);
        questsAnchorPane.setVisible(false);
        shopAnchorPane.setVisible(false);
    }
    private void badgesPageView() {
        badgesAnchorPane.setVisible(true);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(false);
        shopAnchorPane.setVisible(false);
    }
    private void leaderboardPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(true);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(false);
        shopAnchorPane.setVisible(false);
    }
    public void questsPageView() {
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(false);
        shopAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(true);
    }

    private void shopPageView(){
        badgesAnchorPane.setVisible(false);
        leaderboardAnchorPane.setVisible(false);
        overviewAnchorPane.setVisible(false);
        questsAnchorPane.setVisible(false);
        shopAnchorPane.setVisible(true);
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
        if (selectedQuest == null) {
            return null;
        }
        responsesListView.setCellFactory(new WrappingListCellFactory());
        ObservableList<String> responsesModel = FXCollections.observableArrayList();
        ResponseService responseService = (ResponseService) service.getResponseService();
        UserService userService = (UserService) service.getUserService();
        ArrayList<Response<Integer>>responsesForSpecificQuest = responseService.filterResponsesByQuest(selectedQuest.getId());

        for(Response<Integer> response : responsesForSpecificQuest) {
           String responseInfo = String.format("%s answered:\n %s",userService.findAfterId(response.getIdUser()).getUsername(),   response.getMessage());
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
    private void populateUserItemsListView(){
        RewardService rewardService = (RewardService) service.getRewardService();
        ItemService itemService = (ItemService) service.getItemService();
        ArrayList<Reward<Integer>> userRewards = rewardService.filterRewardsByUser(user.getId());
        ObservableList<String> userItemsModel = FXCollections.observableArrayList();
        for(Reward<Integer> reward : userRewards) {
            String rewardInfo = String.format("%s", itemService.findAfterId(reward.getIdItem()).getName());
            userItemsModel.add(rewardInfo);
        }
        userItemsListView.setItems(userItemsModel);
    }
    @FXML
    public void onBadgesButtonClick() {
        badgesPageView();
    }

    @FXML
    public void onLeaderboardButtonClick() {
        leaderboardPageView();
    }

    @FXML
    public void onOverviewButtonClick() {
        overviewPageView();
    }

    @FXML
    public void onQuestsButtonClick() {
        questsPageView();
    }

    @FXML
    public void onShopButtonClickAction() {
        shopPageView();
    }
    @FXML
    public void onShowAnswersButtonAction() {
        populateResponsesListView() ;
    }
    @FXML
    public void onMarkAsBestResponseButtonAction() {
        Quest<Integer> selectedQuest = populateResponsesListView();
        if (selectedQuest == null) {
            return;
        }
        QuestService questService = (QuestService) service.getQuestService();
        UserService userService = (UserService) service.getUserService();
        String selectedResponse = responsesListView.getSelectionModel().getSelectedItem();
        String username = selectedResponse.split(" ")[0];
        User<Integer> userThatAnswered = userService.getUserByUsername(username);
        userThatAnswered.setPoints(userThatAnswered.getPoints() + selectedQuest.getPoints());
        userThatAnswered.setNumberOfQuestsCompleted(userThatAnswered.getNumberOfQuestsCompleted() + 1);
        userService.update(userThatAnswered.getId(),userThatAnswered);
        questService.markAsCompleted(selectedQuest);
        populateResponsesListView();
        refreshPointsAndSolvedQuests();
        populateLeaderboard();

    }

    private void populateAvailableQuestsTableView() {
        QuestService questService = (QuestService) service.getQuestService();
        ArrayList<Quest<Integer>> availableQuests = questService.filterQuestsByAvailability(user.getId());
        availableQuestsModel.setAll(availableQuests);
        availableQuestsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableQuestsPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        availableQuestsTableView.setItems(availableQuestsModel);
    }
    @FXML
    public void onSolveTheSelectedQuestButtonAction() {
        Quest<Integer> selectedQuest = availableQuestsTableView.getSelectionModel().getSelectedItem();
        if (selectedQuest == null) {
            return;
        }
        Scene scene ;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("solve_quest.fxml"));
        try {
            scene = new Scene(loader.load(), 1200, 718);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        SolveQuestController solveQuestController = loader.getController();
        solveQuestController.initialize(service, user, selectedQuest);
        Stage currentStage = (Stage) solveTheSelectedQuestButton.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Solve the quest");
        currentStage.close();
        newStage.show();

    }

    @FXML
    public void onCreateAQuestButtonAction() {

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

    private void populateLeaderboard(){
        UserService userService = (UserService) service.getUserService();
        ArrayList<User<Integer>> users = (ArrayList<User<Integer>>) userService.getAll();
        users.sort((o1, o2) -> o2.getNumberOfQuestsCompleted() - o1.getNumberOfQuestsCompleted());
       leaderboardModel.setAll(users);
       usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
       questsCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfQuestsCompleted"));
       leaderboardTableView.setItems(leaderboardModel);
    }
    private void populateShopTableView(){
        ItemService itemService = (ItemService) service.getItemService();
        ArrayList<Item<Integer>> items = (ArrayList<Item<Integer>>) itemService.getAll();
        shopModel.setAll(items);
        itemNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        shopTableView.setItems(shopModel);
    }
    @FXML
    public void onLogoutButtonAction(){

        Scene scene ;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("login.fxml"));
        try {
            scene = new Scene(loader.load(), 1200, 718);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        LoginController loginController = loader.getController();
        loginController.initialize();
        loginController.setService(service);
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Login");
        currentStage.close();
        newStage.show();
    }



    private void activateBadges(){

        if (user.getNumberOfQuestsCompleted() >= 1) {
           firstBadge.setOpacity(1);
        }
        else{
            firstBadge.setOpacity(0.3);
        }
        if (user.getNumberOfQuestsCompleted() >= 10) {
            secondBadge.setOpacity(1);
        }
        else {
            secondBadge.setOpacity(0.3);
        }
        if (user.getNumberOfQuestsCompleted() >= 50) {
            thirdBadge.setOpacity(1);
        }
        else {
            thirdBadge.setOpacity(0.3);
        }
        if (user.getNumberOfQuestsCompleted() >= 100) {
            fourthBadge.setOpacity(1);
        }
        else {
            fourthBadge.setOpacity(0.3);
        }
        itemsBoughtBadges();
    }

    private void itemsBoughtBadges(){
        RewardService rewardService = (RewardService) service.getRewardService();
        if(rewardService.getNumberOfRewardsByUser(user.getId()) >= 1){
            fifthBadge.setOpacity(1);
        }
        else{
            fifthBadge.setOpacity(0.3);
        }
        if(rewardService.getNumberOfRewardsByUser(user.getId()) >= 10){
            sixthBadge.setOpacity(1);
        }
        else{
            sixthBadge.setOpacity(0.3);
        }
        if(rewardService.getNumberOfRewardsByUser(user.getId()) >= 50){
            seventhBadge.setOpacity(1);
        }
        else{
            seventhBadge.setOpacity(0.3);
        }
        if(rewardService.getNumberOfRewardsByUser(user.getId()) >= 100){
            eighthBadge.setOpacity(1);
        }
        else{
            eighthBadge.setOpacity(0.3);
        }
    }

    @FXML
    public void onBuyItemButtonClickAction(){
        Item<Integer> selectedItem = shopTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
            errorShopLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorShopLabel.setText("Please select an item");
        } else if (selectedItem.getPrice() > user.getPoints()){
        errorShopLabel.setTextFill(javafx.scene.paint.Color.RED);
        errorShopLabel.setText("You don't have enough points to buy this item");
        }
        else{
            errorShopLabel.setText("");
            UserService userService = (UserService) service.getUserService();
            RewardService rewardService = (RewardService) service.getRewardService();
            Reward<Integer> reward = new Reward<>(user.getId(), selectedItem.getId());
            rewardService.add(reward);
            user.setPoints(user.getPoints() - selectedItem.getPrice());
            userService.update(user.getId(),user);
            refreshPointsAndSolvedQuests();
            populateUserItemsListView();
            errorShopLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            errorShopLabel.setText("You have bought the item successfully");
            itemsBoughtBadges();
        }
    }
}
