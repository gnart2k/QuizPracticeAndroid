package com.example.quizpractice.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.example.quizpractice.repository.HistoryRepository;
import com.example.quizpractice.repository.QuizListRepository;

import java.util.List;

public class HistoryViewModel extends ViewModel implements HistoryRepository.onFirestoneTaskComplete {
    private MutableLiveData<List<ResultModel>> resultLiveData = new MutableLiveData<>();
    private HistoryRepository historyRepository = new HistoryRepository(this);

    private List<ResultModel> resultBackup;
    public HistoryViewModel(){
//
//        resultLiveData = new MutableLiveData<>();
//        historyRepository = new HistoryRepository(this);
//
        historyRepository.getResults();
    }

    @Override
    public void resultLoad(List<ResultModel> resultModels) {
        resultBackup = resultModels;
        resultModels.forEach(e -> Log.d("each result", e.getQuizTitle()));
        resultLiveData.setValue(resultModels);
        Log.d("data at getResultLiveData", resultLiveData.getValue().get(0).getQuizTitle());
    }

    public void reloadData(){
        this.resultLiveData.setValue(resultBackup);
    }

    public MutableLiveData<List<ResultModel>> getResultLiveData() {
        Log.d("getResultLiveData", this.resultLiveData.getClass().getName());
        return this.resultLiveData;
    }

    public void setResultLiveData(MutableLiveData<List<ResultModel>> resultLiveData) {
        this.resultLiveData = resultLiveData;
    }

    @Override
    public void onError(Exception e) {
        Log.d("Quiz Error", "onError: " + e.getMessage());
    }
}
