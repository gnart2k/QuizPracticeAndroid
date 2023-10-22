package com.example.quizpractice.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizpractice.Model.QuestionModel;
import com.example.quizpractice.repository.QuestionRepository;

import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad {


    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository;

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel(){
        questionMutableLiveData = new MutableLiveData<>();
        repository = new QuestionRepository(this);
    }

    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    public  void setQuizId(String quizId){
        repository.setQuizID(quizId);
        repository.getQuestions();
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: " + e.getMessage());
    }
}
