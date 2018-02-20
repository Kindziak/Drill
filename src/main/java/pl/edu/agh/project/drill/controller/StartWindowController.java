package pl.edu.agh.project.drill.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.edu.agh.project.drill.App;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends Application implements Initializable {
    private OptionWindowController optionController;
    private Parent optionParent;

    @FXML
    Button startButton, readyButton, optionsButton, filechooserButton;

    @FXML
    Label welcomeLabel, enterPathLabel;

    @FXML
    TextField pathField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/optionWindow.fxml"));
            optionParent = loader.load();
            optionController = loader.getController();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStartAction(ActionEvent e) {
        startButton.setVisible(false);
        welcomeLabel.setVisible(false);
        pathField.setVisible(true);
        enterPathLabel.setVisible(true);
        readyButton.setVisible(true);
        optionsButton.setVisible(true);
        filechooserButton.setVisible(true);
    }

    @FXML
    private void handleReadyAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/mainWindow.fxml"));
            Parent p = loader.load();
            MainWindowController controller = loader.getController();
            controller.setOptions(optionController.isShuffleA(), optionController.isShuffleQ(), optionController.isReplayQ(), optionController.getCounterOption());
            controller.setPath(pathField.getText());

            Scene scene = new Scene(p);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(scene);
            mainStage.setTitle("Drill");
            mainStage.show();
        } catch (IOException e1) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/WrongPathErr.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            Stage s = new Stage();
            s.setScene(scene);
            s.setTitle("Błąd");
            s.show();
        } catch (IllegalStateException e2) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/WrongFileErr.fxml"));
            Parent p = loader.load();
            ErrorWindowController controller = loader.getController();
            controller.setText(e2.getMessage());
            Scene scene = new Scene(p);
            Stage errorStage = new Stage();
            errorStage.setScene(scene);
            errorStage.setTitle("Błąd");
            errorStage.show();
        }
    }

    @FXML
    public void handleOptionsButtonAction(ActionEvent e) {
        Scene scene = new Scene(optionParent);
        Stage stage = new Stage();
        stage.setTitle("Opcje");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage arg0) {}

    public void handleFileChooserButton(ActionEvent e) {
        File file;
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            String path = file.getAbsolutePath();
            pathField.setText(path);
        }
    }
}
