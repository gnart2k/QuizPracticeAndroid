package com.example.quizpractice.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.repository.QuizListRepository;

import java.util.List;

public class QuizListViewModel extends ViewModel implements QuizListRepository.onFirestoneTaskComplete {
    private MutableLiveData<List<QuizListModel>> quizListLiveData = new MutableLiveData<>();
    private QuizListRepository database =new QuizListRepository(this);

    public MutableLiveData<List<QuizListModel>> getQuizListLiveData() {
        return quizListLiveData;
    }

    public void setQuizListLiveData(MutableLiveData<List<QuizListModel>> quizListMutableLiveData) {
        this.quizListLiveData = quizListMutableLiveData;
    }

    public QuizListRepository getDatabase() {
        return database;
    }

    public void setDatabase(QuizListRepository database) {
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


    public void addQuiz(QuizListModel quizListModel){
        database.addQuiz(quizListModel);
    }
}
