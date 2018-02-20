package pl.edu.agh.project.drill.manager;

public class PointCounter implements ICounter {

    private double score = 0;

    public double getScore() {
        return score;
    }

    public void addPoint(double number) {
        score = score + number;
    }

    public void subtractPoint(double number) {score = score - number;}
}
