package com.example.accesa_application;

import com.example.accesa_application.controllers.LoginController;
import com.example.accesa_application.repository.*;
import com.example.accesa_application.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        IUserRepository<Integer> userRepository = new UserRepository(props);
        IQuestRepository<Integer> questRepository = new QuestRepository(props);
        IResponseRepository<Integer> responseRepository = new ResponseRepository(props);
        IUserService<Integer> userService = new UserService(props, userRepository);
        IQuestService<Integer> questService = new QuestService(props, questRepository);
        IResponseService<Integer> responseService = new ResponseService(props, responseRepository);
        Service service = new Service(props, userService, questService, responseService);
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 718);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        loginController.initialize();
        //Image icon = new Image("https://blog.hootsuite.com/wp-content/uploads/2021/07/free-stock-photos-03-scaled.png");
        //stage.getIcons().add(icon);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
