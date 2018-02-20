package pl.edu.agh.project.drill.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.edu.agh.project.drill.manager.CounterOption;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionWindowController implements Initializable {

    private boolean shuffleAnswer;
    private boolean shuffleQuestions;
    private boolean replayQuestions;
    private CounterOption counterOption;
    private ToggleGroup group = new ToggleGroup();

    @FXML
    public Button okButton, statButton, pointChooserButton;

    @FXML
    public CheckBox shuffleAnswersCheckbox, shuffleQuestionsCheckbox, replayQuestionsCheckbox;

    @FXML
    public HBox pointBox;

    @FXML
    public RadioButton subPoints,partPoints;

    public boolean isShuffleQ() {
        return shuffleQuestions;
    }

    public boolean isShuffleA() {
        return shuffleAnswer;
    }

    public boolean isReplayQ() {
        return replayQuestions;
    }

    public CounterOption getCounterOption() {
        return counterOption;
    }

    public OptionWindowController(){
        this.shuffleQuestions = false;
        this.shuffleAnswer = false;
        this.replayQuestions = false;
        counterOption = CounterOption.NORMAL;
    }

    @FXML
    public void handleOkButtonAction(ActionEvent actionEvent) {
        if(shuffleAnswersCheckbox.isSelected()) shuffleAnswer = true;
        if(shuffleQuestionsCheckbox.isSelected()) shuffleQuestions = true;
        if(replayQuestionsCheckbox.isSelected()) replayQuestions = true;
        if(subPoints.isSelected()) counterOption = CounterOption.SUBTRACTING;
        else if(partPoints.isSelected()) counterOption = CounterOption.PARTIAL;

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleStatAction(ActionEvent event) throws IOException {
        FXMLLoader floader = new FXMLLoader();
        floader.setLocation(getClass().getResource("/logsWindow.fxml"));
        Parent p = floader.load();
        LogWindowController controller = floader.getController();
        Scene scene = new Scene(p);
        Stage mainStage = new Stage();
        mainStage.setScene(scene);
        mainStage.setTitle("Statystyki");
        mainStage.show();
    }

    @FXML
    public void handlePointChooserAction(ActionEvent event){
        pointBox.getChildren().clear();
        pointChooserButton.setVisible(false);
        subPoints.setToggleGroup(group);
        partPoints.setToggleGroup(group);
        pointBox.getChildren().addAll(partPoints);
        pointBox.getChildren().addAll(subPoints);
        pointBox.setVisible(true);
        subPoints.setVisible(true);
        partPoints.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
