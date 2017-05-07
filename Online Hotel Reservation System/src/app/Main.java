package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application {

      public static Stage windows;






    @Override
     public void start(Stage primaryStage) throws Exception{
        windows=primaryStage;
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/home.fxml"))));
        primaryStage.setTitle("Home");
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }
}
