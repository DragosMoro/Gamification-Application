package com.example.accesa_application.controllers;

import com.example.accesa_application.MainGUI;
import com.example.accesa_application.domain.User;
import com.example.accesa_application.service.Service;
import com.example.accesa_application.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    @FXML
    public AnchorPane logInAnchorPane;
    @FXML
    public Button logInButton;
    @FXML
    public TextField emailLoginTextField;
    @FXML
    public TextField passwordLoginTextField;
    @FXML
    public Label errorRegisterLabel;
    @FXML
    public Label registerHereLabel;
    @FXML
    public AnchorPane registerAnchorPane;
    @FXML
    public Button registerButton;
    @FXML
    public TextField emailRegisterTextField;
    @FXML
    public TextField passwordRegisterTextField;
    @FXML
    public TextField repeatPasswordRegisterTextField;

    @FXML
    public TextField usernameRegisterTextField;
    @FXML
    public AnchorPane registerInfoAnchorPane;
    @FXML
    public AnchorPane loginInfoAnchorPane;
    @FXML
    public Label errorLabel;
    private Service service;
    private UserService userService;

    public void setService(Service service) {
        this.userService = (UserService) service.getUserService();
        this.service = service;
    }

    private void changeSceneLoginToRegister() {
        logInAnchorPane.setVisible(false);
        loginInfoAnchorPane.setVisible(false);
        registerAnchorPane.setVisible(true);
        registerInfoAnchorPane.setVisible(true);
    }
    @FXML
    public void onRegisterLabelClick() {
        changeSceneLoginToRegister();
    }
    @FXML
    public void onLoginLabelClick() {
        initialize();
    }
    @FXML
    public void onRegisterButtonClick() {
        errorRegisterLabel.setTextFill(javafx.scene.paint.Color.RED);
        String username = usernameRegisterTextField.getText();
        String email = emailRegisterTextField.getText();
        String password = passwordRegisterTextField.getText();
        String repeatPassword = repeatPasswordRegisterTextField.getText();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}$";


        if (email.equals("") || password.equals("") || repeatPassword.equals("") || username.equals("")) {
            errorRegisterLabel.setText("Please complete all fields");
            return;
        }
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        boolean match = matcher.matches();
        if(!match){
            errorRegisterLabel.setText("Email is invalid");
            return;
        }
        if (!password.equals(repeatPassword)) {
            errorRegisterLabel.setText("Passwords do not match");
            return;
        }

        if (userService.getUserByEmail(email) != null) {
            errorRegisterLabel.setText("Email already exists");
            return;
        }
        if (userService.getUserByUsername(username) != null) {
            errorRegisterLabel.setText("Username already exists");
            return;
        }
        User<Integer> user = new User<>(username,email, password, 0,100);
        userService.add(user);
        errorRegisterLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        errorRegisterLabel.setText("User registered successfully");

    }
    @FXML
    public void onLoginButtonClick()
    {
        errorRegisterLabel.setTextFill(javafx.scene.paint.Color.RED);
        String email = emailLoginTextField.getText();
        String password = passwordLoginTextField.getText();
        if (email.equals("") || password.equals("")) {
            errorLabel.setText("Please complete all fields");
            return;
        }
        User<Integer> user = userService.getUserByEmail(email);
        if (user == null) {
            errorLabel.setText("Email does not exist");
            return;
        }
        if (!password.equals(user.getPassword())) {
            errorLabel.setText("Password is invalid");
            return;
        }
        changeScene(user);
    }

    private void changeScene(User<Integer> user) {
        Scene scene ;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("main_menu.fxml"));
        try {
            scene = new Scene(loader.load(), 1200, 718);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.initialize(service, user);
        Stage currentStage = (Stage) logInButton.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Accesa Application");
        currentStage.close();
        newStage.show();
    }

    public void initialize() {
        registerAnchorPane.setVisible(false);
        registerInfoAnchorPane.setVisible(false);
        logInAnchorPane.setVisible(true);
        loginInfoAnchorPane.setVisible(true);
    }
}
