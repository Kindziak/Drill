package pl.edu.agh.project.drill.manager;

import pl.edu.agh.project.drill.model.Question;

import java.util.ArrayList;
import java.util.Collections;

public class QuizManager implements IQuizManager {
    private ICounter pointCounter;
    private CounterOption counterOption;

    public QuizManager(CounterOption counterOption) {
        this.counterOption = counterOption;
        this.pointCounter = new PointCounter();
    }

    public int checkAnswers(ArrayList<String> answers, Question currentQuestion) {
        if (equalAnswers(answers, currentQuestion)) {
            pointCounter.addPoint(1);
            return 0;
        }
        else {
            if (counterOption == CounterOption.SUBTRACTING) {
                pointCounter.subtractPoint(1);
            }
            return 1;
        }
    }

    public double getPoints() {
        return pointCounter.getScore();
    }

    private boolean equalAnswers(ArrayList<String> answers, Question currentQuestion) {
        if (answers == null && currentQuestion.getCorrectAnswers() == null) return true;
        if ((answers == null && currentQuestion.getCorrectAnswers() != null) || (answers != null && currentQuestion.getCorrectAnswers() == null) || answers.size() != currentQuestion.getCorrectAnswers().size())
            return false;

        ArrayList<String> sortedUserAnswers = new ArrayList<>(answers);
        ArrayList<String> sortedAnswers = new ArrayList<>(currentQuestion.getCorrectAnswers());

        Collections.sort(sortedUserAnswers);
        Collections.sort(sortedAnswers);

        return sortedUserAnswers.equals(sortedAnswers);
    }
}
