package pl.edu.agh.project.drill;

import org.junit.*;
import pl.edu.agh.project.drill.manager.CounterOption;
import pl.edu.agh.project.drill.manager.IQuizManager;
import pl.edu.agh.project.drill.manager.QuestionLoader;
import pl.edu.agh.project.drill.manager.QuizManager;
import pl.edu.agh.project.drill.model.Question;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    private Question q1;
    private Question q2;
    private List<Question> qs;

    @Before
    public void init() {
        Question.QuestionBuilder qb = new Question.QuestionBuilder();
        qb.prepareQuestionText("Question 1");
        qb.prepareAnswer("Answer 1", true);
        qb.prepareAnswer("Answer 2", false);
        qb.prepareAnswer("Answer 3", false);
        qb.prepareAnswer("Answer 4", true);
        qb.prepareIsOneAnswer(false);
        q1 = qb.build();

        qb = new Question.QuestionBuilder();
        qb.prepareQuestionText("Question 2");
        qb.prepareAnswer("Answer 1", false);
        qb.prepareAnswer("Answer 2", false);
        qb.prepareAnswer("Answer 3", true);
        qb.prepareAnswer("Answer 4", false);
        qb.prepareIsOneAnswer(true);
        q2 = qb.build();

        qs = new ArrayList<>();
        qs.add(q1);
        qs.add(q2);
    }

    @Test
    public void equalAnswersTest() {
        ArrayList<String> correctAnswer = new ArrayList<>();
        correctAnswer.add("Answer 1");
        correctAnswer.add("Answer 4");
        IQuizManager quizManager = new QuizManager(CounterOption.NORMAL);
        quizManager.checkAnswers(correctAnswer,q1);

        Assert.assertEquals(quizManager.getPoints(),1.0, 0.001);
    }

    @Test
    public void notEqualAnswersTest() {
        ArrayList<String> correctAnswer = new ArrayList<>();
        correctAnswer.add("Answer 2");
        correctAnswer.add("Answer 4");
        IQuizManager quizManager = new QuizManager(CounterOption.NORMAL);
        quizManager.checkAnswers(correctAnswer,q1);

        Assert.assertEquals(quizManager.getPoints(),0.0, 0.001);
    }

    @Test
    public void nextQuestionLoadingTest() {
        QuestionLoader loader = new QuestionLoader();
        loader.setQuestions(qs);
        loader.next();

        Assert.assertEquals(loader.getCurrentQuestion(),q1);
    }

    @Test
    public void hasNextQuestionLoadingTest() {
        QuestionLoader loader = new QuestionLoader();
        loader.setQuestions(qs);
        loader.next();

        Assert.assertTrue(loader.hasNext());
    }

    @Test
    public void hasNotNextQuestionLoadingTest() {
        QuestionLoader loader = new QuestionLoader();
        loader.setQuestions(qs);
        loader.next();
        loader.next();

        Assert.assertFalse(loader.hasNext());
    }

//    @Test
//    public void parsingTest() {
//        ClassLoader classLoader = getClass().getClassLoader();
//        String ok = classLoader.getResource("drillExample.txt").getPath();
//        List<Question> list = new ArrayList<>();
//        try {
//            list = new FileParser(ok).readFile();
//            String content = new String(Files.readAllBytes(Paths.get("drillExample.txt")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Assert.assertEquals(list, qs);
//    }

}
