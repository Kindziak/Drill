package pl.edu.agh.project.drill.logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StatisticReader {
    private BufferedReader buffer;
    private ObservableList<String> logs;

    public StatisticReader(String filePath) throws IOException {
        this.logs = FXCollections.observableArrayList();
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        this.buffer = new BufferedReader(new FileReader(filePath));
    }

    public ObservableList<String> getList() throws IOException {
        String textLine = buffer.readLine();
        do {
            logs.add(textLine);
            textLine = buffer.readLine();
        } while (textLine != null);
        buffer.close();
        return this.logs;
    }
}
