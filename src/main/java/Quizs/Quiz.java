package Quizs;

import java.util.ArrayList;

public class Quiz {
    private int id;
    private String title;
    private String description;
    private int creatorId;
    private int quizTimeSeconds;

    private boolean isRandom;

    private  boolean isOnePage;

    private boolean immediateCorrection;

    private boolean canBePracticed;



    public Quiz(int id, String title, String description, int creatorId, int quizTimeSeconds, boolean isRandom, boolean isOnePage, boolean immediateCorrection, boolean canBePracticed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.quizTimeSeconds = quizTimeSeconds;
        this.isRandom = isRandom;
        this.isOnePage = isOnePage;
        this.immediateCorrection = immediateCorrection;
        this.canBePracticed = canBePracticed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuizTime(){
        return quizTimeSeconds;
    }

    public void setQuizTime(int quizTimeSeconds){
        this.quizTimeSeconds = quizTimeSeconds;
    }

    public String toString(){
        return "ID: " + getId() + " TITLE: " + getTitle() + " DESCRIPTION: " + getDescription() + " CREATOR_ID: "
                + getCreatorId() + " QUIZTIME: " + getQuizTime();
    }

    public boolean isRandom() {
        return isRandom;
    }

    public void setRandom(boolean random) {
        isRandom = random;
    }

    public boolean isOnePage() {
        return isOnePage;
    }

    public void setOnePage(boolean onePage) {
        isOnePage = onePage;
    }

    public boolean isImmediateCorrection() {
        return immediateCorrection;
    }

    public void setImmediateCorrection(boolean immediateCorrection) {
        this.immediateCorrection = immediateCorrection;
    }

    public boolean isCanBePracticed() {
        return canBePracticed;
    }

    public void setCanBePracticed(boolean canBePracticed) {
        this.canBePracticed = canBePracticed;
    }
}
