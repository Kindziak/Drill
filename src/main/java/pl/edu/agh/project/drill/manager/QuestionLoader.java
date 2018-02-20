package pl.edu.agh.project.drill.manager;

import pl.edu.agh.project.drill.model.Answer;
import pl.edu.agh.project.drill.model.Question;

import java.util.Collections;
import java.util.List;

public class QuestionLoader implements Iterator {

    private Question currentQuestion;
    private int questionPointer = -1; // wskazanie na obecnie przetwarzany question
    private List<Question> questions;

    private boolean shufflingAnswers;
    private boolean shufflingQuestions;
    private boolean replayingQuestions;

    public void setOptions(boolean sa, boolean sq, boolean rq){
        this.shufflingAnswers = sa;
        this.shufflingQuestions = sq;
        this.replayingQuestions = rq;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void next() {
        currentQuestion = questions.get(++questionPointer);
    }

    public boolean hasNext() {
        return (questions.size() > this.questionPointer + 1);
    }

    public void setQuestions(List<Question> questions) {
        if(shufflingQuestions){
            Collections.shuffle(questions);
        }
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        if(shufflingAnswers){
            List<Answer> answers = currentQuestion.getAnswers();
            Collections.shuffle(answers);
            return answers;
        }
        else return currentQuestion.getAnswers();
    }

    public void wrongAnswer(){
        if(replayingQuestions){
            questions.add(currentQuestion);
        }
    }
}
