package com.example.quizpractice.Model;

import com.google.firebase.firestore.DocumentId;

import java.lang.annotation.Documented;
import java.util.Arrays;
import java.util.List;


public class QuizListModel {
    @DocumentId
    private String quizId;
    private String title, image, difficulty;
    private long questions;

    private List<QuestionModel> questionModels;
    private String creatorId;

    public String getCreatorId() {
        return creatorId;
    }

    public QuizListModel() {
    }



    //tao contructor va getter setter cho attribute

    public QuizListModel(String quizId, String title, String image, String difficulty, long questions) {
        this.quizId = quizId;
        this.title = title;
        this.image = image;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public QuizListModel(String title, String image, String difficulty, long questions, QuestionModel[] questionModels) {
        this.title = title;
        this.image = image;
        this.difficulty = difficulty;
        this.questions = questions;
        this.questionModels = Arrays.asList(questionModels);
    }


    public QuizListModel(String title, String image, String difficulty, long questions, QuestionModel[] questionModels, String creatorId) {
        this.title = title;
        this.image = image;
        this.difficulty = difficulty;
        this.questions = questions;
        this.questionModels = Arrays.asList(questionModels);
        this.creatorId = creatorId;
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

    public long getQuestions() {
        return questions;
    }

//    public void setQuestions(long questions) {
//        this.questions = questions;
//    }

    public List<QuestionModel> getQuestionModels() {
        return questionModels;
    }

}
