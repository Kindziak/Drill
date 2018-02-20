package pl.edu.agh.project.drill.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private List<Answer> answers;
    private boolean isOneAnswer;

    private Question(QuestionBuilder builder) {
        this.questionText = builder.questionText;
        this.answers = builder.answers;
        this.isOneAnswer = builder.isOneAnswer;
    }

    public String getQuestionText() { return this.questionText; }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public boolean getIsOneAnswer() {
        return this.isOneAnswer;
    }

    public List<String> getCorrectAnswers() {
        List<String> correctAnswer = new ArrayList<>();
        for (Answer answer : answers) {
            if (answer.getIsCorrect()) correctAnswer.add(answer.getAnswer());
        }
        return correctAnswer;
    }

    public static class QuestionBuilder {
        private String questionText;
        private List<Answer> answers = new ArrayList<>();
        private boolean isOneAnswer;

        private int numberOfCorrectAnswers = 0;
        private boolean isCompliteQuestion = false;

        public QuestionBuilder prepareQuestionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public QuestionBuilder prepareAnswer(String answerText, boolean isCorrect) {
            if (this.isOneAnswer) {
                if((isCorrect & numberOfCorrectAnswers == 0) | !isCorrect) {
                    if(isCorrect) {
                        numberOfCorrectAnswers++;
                        isCompliteQuestion = true;
                    }
                    this.answers.add(new Answer(answerText, isCorrect));
                } else isCompliteQuestion = false;
            } else {
                this.answers.add(new Answer(answerText, isCorrect));
                isCompliteQuestion = true;
            }
            return this;
        }

        public QuestionBuilder prepareIsOneAnswer(boolean isOneAnswer) {
            this.isOneAnswer = isOneAnswer;
            return this;
        }

        public Question build() {
            if (this.isCompliteQuestion) return new Question(this);
            else {
                throw new IllegalStateException("Niepoprawna forma pytania: " + this.questionText);
            }
        }
    }
}

