package pl.edu.agh.project.drill.manager;

import pl.edu.agh.project.drill.model.Question;

import java.util.ArrayList;

public interface IQuizManager {
    int checkAnswers(ArrayList<String> answers, Question currentQuestion);
    double getPoints();
}
