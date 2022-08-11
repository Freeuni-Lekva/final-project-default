package Quizs;
import java.util.ArrayList;

public abstract class Question {
    private int id;
    private int quizId;
    private String description;

    public Question(int id, int quizId, String description){
        this.id = id;
        this.quizId = quizId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.getId();
    }

    @Override
    public String toString(){
        return "ID: " + getId() + " quizId: " + getQuizId() + " description: " + getDescription();
    }

    abstract String getTag();
    abstract String getType();
}
