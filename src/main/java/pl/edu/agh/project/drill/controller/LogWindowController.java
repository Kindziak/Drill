package pl.edu.agh.project.drill.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pl.edu.agh.project.drill.App;
import pl.edu.agh.project.drill.logger.StatisticReader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LogWindowController implements Initializable{

    @FXML
    private Button okButton;

    @FXML
    private ListView<String> logList = new ListView<>();

    public void handleOkButtonAction(ActionEvent actionEvent) {
       Stage stage = (Stage) okButton.getScene().getWindow();
       stage.close();
    }

    @Override
    public void initialize(URL l, ResourceBundle s) {
        try {
            StatisticReader reader = new StatisticReader(App.fileName);
            logList.setItems(reader.getList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
