package pl.edu.agh.project.drill.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.edu.agh.project.drill.logger.StatisticsWriter;
import pl.edu.agh.project.drill.manager.*;
import pl.edu.agh.project.drill.model.Answer;
import pl.edu.agh.project.drill.parser.FileParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private QuestionLoader questionLoader;
    private IQuizManager manager;
    private ToggleGroup group = new ToggleGroup();
    private int questionNumber = 1;
    private String fileName;

    @FXML
    Button nextButton, checkButton, yesButton, noButton, statsButton;

    @FXML
    Label notificationLabel, questionLabel, taskLabel, pointsLabel, endLabel1, endLabel2;

    @FXML
    VBox answerBox;

    public MainWindowController() {
        this.questionLoader = new QuestionLoader();
        this.manager = new QuizManager(CounterOption.NORMAL);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        questionLabel.setText("");
        pointsLabel.setText("Punkty: " + manager.getPoints());

    }

    @FXML
    private void handleCheckAction(ActionEvent e) {
        int isCorrect = manager.checkAnswers(getAnswer(), questionLoader.getCurrentQuestion());
        pointsLabel.setText("Punkty: " + manager.getPoints());
        checkButton.setVisible(false);
        nextButton.setVisible(true);
        if (isCorrect == 0) {
            notificationLabel.setText("Odpowiedź prawidłowa!");
            notificationLabel.setTextFill(Color.GREEN);
            notificationLabel.setVisible(true);
        } else if (isCorrect == 1) {
            questionLoader.wrongAnswer();
            notificationLabel.setText("Odpowiedź błędna.");
            notificationLabel.setTextFill(Color.RED);
            notificationLabel.setVisible(true);
        } else {
            questionLoader.wrongAnswer();
            notificationLabel.setText("Odpowiedź częściowo błędna.");
            notificationLabel.setTextFill(Color.ORANGE);
            notificationLabel.setVisible(true);
        }
    }

    @FXML
    private void handleNextAction(ActionEvent e) throws IOException {
        notificationLabel.setVisible(false);
        if (questionLoader.hasNext()) {
            questionLoader.next();
            nextButton.setText("Dalej");
            nextQuestion();
            nextButton.setVisible(false);
            checkButton.setVisible(true);
            taskLabel.setText("Pytanie " + this.questionNumber++);
        } else {
            taskLabel.setText("Koniec");
            answerBox.setVisible(false);
            questionLabel.setVisible(false);
            nextButton.setVisible(false);
            pointsLabel.setVisible(false);
            endLabel1.setText("Gratulacje! Zdobyłeś/łaś " + manager.getPoints() + " punktów.");
            endLabel1.setVisible(true);
            endLabel2.setVisible(true);
            noButton.setVisible(true);
            yesButton.setVisible(true);
            statsButton.setVisible(true);

            new StatisticsWriter().writeToFile(manager.getPoints(),this.fileName);
        }
    }

    public void setPath(String fileName) throws IOException, IllegalStateException {
        this.fileName = fileName;
        this.questionLoader.setQuestions(new FileParser(fileName).readFile());
    }

    public void setOptions(boolean sAnswers, boolean sQuestions, boolean replayQuestions, CounterOption counterOption){
        this.questionLoader.setOptions(sAnswers, sQuestions, replayQuestions);
        if(counterOption == CounterOption.PARTIAL) this.manager = new QuizManagerWithPartialGoodAnswers();
        else this.manager = new QuizManager(counterOption);
    }

    @FXML
    private void handleYesAction(Event event) throws IOException {
        FXMLLoader floader = new FXMLLoader();
        floader.setLocation(getClass().getResource("/StartWindow.fxml"));
        Parent p = floader.load();
        Scene scene = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(scene);
        mainStage.setTitle("Drill");
        mainStage.show();
    }

    @FXML
    private void handleStatsAction(ActionEvent event) throws IOException {
        FXMLLoader floader = new FXMLLoader();
        floader.setLocation(getClass().getResource("/logsWindow.fxml"));
        Parent p = floader.load();
        Scene scene = new Scene(p);
        Stage mainStage = new Stage();
        mainStage.setScene(scene);
        mainStage.setTitle("Statystyki");
        mainStage.show();
    }

    @FXML
    private void handleNoAction() {
        System.exit(0);
    }

    private void nextQuestion() {
        answerBox.getChildren().clear();
        if (questionLoader.getCurrentQuestion().getIsOneAnswer()) {
            questionLabel.setText(questionLoader.getCurrentQuestion().getQuestionText() + " \n(Jedna odpowiedź prawidłowa)");
            for (Answer ans : questionLoader.getAnswers()) {
                RadioButton r = new RadioButton(ans.getAnswer());
                r.setToggleGroup(group);
                answerBox.getChildren().addAll(r);
            }
        } else {
            questionLabel.setText(questionLoader.getCurrentQuestion().getQuestionText() + " \n(Wiele odpowiedzi prawidłowych)");
            for (Answer ans : questionLoader.getAnswers()) {
                RadioButton r = new RadioButton(ans.getAnswer());
                answerBox.getChildren().addAll(r);
            }
        }
    }

    private ArrayList<String> getAnswer() {
        ArrayList<String> answers = new ArrayList<String>();
        for (Node node : answerBox.getChildren()) {
            if (((RadioButton) node).isSelected())
                answers.add(((RadioButton) node).getText());
        }
        return answers;
    }
}
