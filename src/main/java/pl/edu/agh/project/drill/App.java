package pl.edu.agh.project.drill;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static String fileName = "statistics.txt";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.<Parent>load(App.class.getResource("/startWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Drill");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String args[]){
        launch(args);
    }
}
