package pl.edu.agh.project.drill.logger;

import pl.edu.agh.project.drill.App;

import java.io.*;
import java.time.LocalDateTime;

public class StatisticsWriter {
    public void writeToFile(double points, String fileName) throws IOException {
        FileWriter writer = new FileWriter(App.fileName, true);
        BufferedWriter out = new BufferedWriter(writer);
        out.write("Points: "+points+ " - " + fileName + " - " + LocalDateTime.now().toString() + "\n");
        out.close();
    }
}
