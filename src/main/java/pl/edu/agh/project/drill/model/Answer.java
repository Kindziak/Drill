package pl.edu.agh.project.drill.model;

public class Answer {
    private String answer;
    private boolean isCorrect;

    public Answer (String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }
}
