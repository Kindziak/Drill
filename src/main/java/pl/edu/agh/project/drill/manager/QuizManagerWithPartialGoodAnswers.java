package pl.edu.agh.project.drill.manager;

import pl.edu.agh.project.drill.model.Question;

import java.util.ArrayList;

public class QuizManagerWithPartialGoodAnswers implements IQuizManager {
    private ICounter pointCounter;

    public QuizManagerWithPartialGoodAnswers() {
        this.pointCounter = new PointCounter();
    }

    public int checkAnswers(ArrayList<String> answers, Question currentQuestion) {
        int checkPoint = equalAnswers(answers, currentQuestion);
        if (checkPoint == 0) { // opcja gdy wszystko jest poprawnie
            pointCounter.addPoint(1);
            return 0;
        } else if (checkPoint == -1) { // podane nasze sa dobre
            pointCounter.addPoint(0.5);
            return -1;
        } else { // podane nasze sa dobre i zle
            return 1;
        }
    }

    public double getPoints() {
        return pointCounter.getScore();
    }

    private int equalAnswers(ArrayList<String> answers, Question currentQuestion) {
        if (answers == null && currentQuestion.getCorrectAnswers() == null) return 0;

        ArrayList<String> sortedUserAnswers = new ArrayList<>(answers);
        ArrayList<String> sortedAnswers = new ArrayList<>(currentQuestion.getCorrectAnswers());

        int correctAnswers = 0;
        for (int i = 0; i < sortedAnswers.size(); i++) {
            for (int j = 0; j < sortedUserAnswers.size(); j++) {
                if (sortedAnswers.get(i).equals(sortedUserAnswers.get(j))) correctAnswers++ ;
            }
        }

        if (correctAnswers == sortedAnswers.size()) return 0;
        else if (correctAnswers < sortedAnswers.size() & correctAnswers == sortedUserAnswers.size()) return -1;
        else return 1;
    }
}
