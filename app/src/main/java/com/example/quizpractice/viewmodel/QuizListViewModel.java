package com.example.quizpractice.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.repository.QuizListDatabase;

import java.util.List;

public class QuizListViewModel extends ViewModel implements QuizListDatabase.onFirestoneTaskComplete {
    private MutableLiveData<List<QuizListModel>> quizListLiveData = new MutableLiveData<>();
    private QuizListDatabase database =new QuizListDatabase(this);

    public MutableLiveData<List<QuizListModel>> getQuizListLiveData() {
        return quizListLiveData;
    }

    public void setQuizListLiveData(MutableLiveData<List<QuizListModel>> quizListMutableLiveData) {
        this.quizListLiveData = quizListMutableLiveData;
    }

    public QuizListDatabase getDatabase() {
        return database;
    }

    public void setDatabase(QuizListDatabase database) {
        this.database = database;
    }

    public QuizListViewModel(){
        database.getQuizFromFirebase();
    }

    @Override
    public void quizLoad(List<QuizListModel> quizListModels) {
        quizListLiveData.setValue(quizListModels);
    }

    @Override
    public void onError(Exception e) {
        Log.d("Quiz Error", "onError: " + e.getMessage());
    }
}
