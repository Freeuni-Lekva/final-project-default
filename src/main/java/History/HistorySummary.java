package History;

import java.util.Date;

public class HistorySummary {
    private double average_score;
    private double average_time;

    public HistorySummary(double average_score, double average_time){
        this.average_score = average_score;
        this.average_time = average_time;
    }

    public double getAverage_score() {
        return average_score;
    }

    public void setAverage_score(int average_score) {
        this.average_score = average_score;
    }

    public double getAverage_time() {
        return average_time;
    }

    public void setAverage_time(double average_time) {
        this.average_time = average_time;
    }
}
