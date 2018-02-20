package pl.edu.agh.project.drill.parser;

import pl.edu.agh.project.drill.model.Question;
import java.util.List;

public interface IParser {
    List<Question> readFile();
}
