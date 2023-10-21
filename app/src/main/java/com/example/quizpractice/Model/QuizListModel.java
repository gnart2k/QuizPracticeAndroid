package com.example.quizpractice.Model;

import com.google.firebase.firestore.DocumentId;

import java.lang.annotation.Documented;


public class QuizListModel {
    @DocumentId
    private String quizId, title, image, difficulty;
    ;
    private int questions;
    //tao contructor va getter setter cho attribute

    public QuizListModel(String quizId, String title, String image, String difficulty, int questions) {
        this.quizId = quizId;
        this.title = title;
        this.image = image;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
