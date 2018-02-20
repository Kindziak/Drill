package pl.edu.agh.project.drill.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pl.edu.agh.project.drill.model.Question;

public class FileParser implements IParser {
    private BufferedReader bufferedReader;
    private List<Question> questions = new ArrayList<>();

    public FileParser(String filePath) throws IOException {
        this.bufferedReader = new BufferedReader(new FileReader(filePath));
    }

    public List<Question> readFile() {
        try {
            String textLine = bufferedReader.readLine();
            do {
                if (!textLine.trim().isEmpty()) {
                    if (isInt(textLine)) textLine = addQuestionToTest(textLine);
                } else {
                    textLine = bufferedReader.readLine();
                }
            } while (textLine != null);
            bufferedReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return this.questions;
    }

    private String addQuestionToTest(String textLine) throws IOException {
        Question.QuestionBuilder builder = new Question.QuestionBuilder();
        parseQuestion(textLine, builder);
        textLine = bufferedReader.readLine();
        while (!isInt(textLine)) {
            parseAnswers(textLine, builder);
            textLine = bufferedReader.readLine();
            if (textLine == null || textLine.trim().isEmpty()) break;
        }
        this.questions.add(builder.build());
        return textLine;
    }


    private void parseQuestion(String questionText, Question.QuestionBuilder builder) {
        String questionBuffer1[] = questionText.split("\\.");
        String questionBuffer2[] = questionBuffer1[1].split(" ", 2);
        if (questionBuffer2[0].trim().equals("*")) builder.prepareIsOneAnswer(false);
        else builder.prepareIsOneAnswer(true);
        builder.prepareQuestionText(questionBuffer2[1]);
    }

    private void parseAnswers(String answer, Question.QuestionBuilder builder) {
        String answerBuffer[] = answer.split("\\.");
        if (answer.substring(0, 1).equals(">")) builder.prepareAnswer(answerBuffer[1], true);
        else builder.prepareAnswer(answerBuffer[1], false);
    }

    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str.substring(0, 1));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
