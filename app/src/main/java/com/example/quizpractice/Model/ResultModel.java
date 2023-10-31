package com.example.quizpractice.Model;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class ResultModel {
    @Override
    public String toString() {
        return "ResultModel{" +
                "resultId='" + resultId + '\'' +
                ", quizTitle='" + quizTitle + '\'' +
                ", correct=" + correct +
                ", notAnswer=" + notAnswer +
                ", wrong=" + wrong +
                ", date=" + date +
                '}';
    }

    @DocumentId
    private String resultId;
    private String quizTitle;
    private long correct;
    private long notAnswer;
    private long wrong;
    private Date date;


    public ResultModel(long correct, long notAnswer, long wrong, Date date) {
        this.correct = correct;
        this.notAnswer = notAnswer;
        this.wrong = wrong;
        this.date = date;
    }

    public ResultModel(String resultId, String quizTitle, long correct, long notAnswer, long wrong, Date date) {
        this.resultId = resultId;
        this.quizTitle = quizTitle;
        this.correct = correct;
        this.notAnswer = notAnswer;
        this.wrong = wrong;
        this.date = date;
    }

    public ResultModel() {
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public long getCorrect() {
        return correct;
    }

    public void setCorrect(long correct) {
        this.correct = correct;
    }

    public long getNotAnswer() {
        return notAnswer;
    }

    public void setNotAnswer(long notAnswer) {
        this.notAnswer = notAnswer;
    }

    public long getWrong() {
        return wrong;
    }

    public void setWrong(long wrong) {
        this.wrong = wrong;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
