package com.example.quizpractice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.quizpractice.Model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QuestionRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizID;
    private HashMap<String , Long> resultMap = new HashMap<>()
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;

    public String currenUserId  = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private onResultLoad onResultLoad;

    public void getResults(){
        firebaseFirestore.collection("Quiz").document(quizID)
                .collection("results").document(currenUserId).
                set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if( task.isSuccessful()){
                            resultMap.put("correct", task.getResult().getLong("correct"));
                            resultMap.put("wrong", task.getResult().getLong("wrong"));
                            resultMap.put("notAnswer", task.getResult().getLong("notAnswer"));
                            onResultLoad.OnResultLoad(resultMap);
                        }else{
                            onResultLoad.onError(task.getException());
                        }
                    }
                });
    }
    public void addResults(HashMap<String , Objects> resultMap){
        firebaseFirestore.collection("Quiz").document(quizID)
                .collection("results").document(currenUserId).
                set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            onResultAdded.onSubmit();
                        }else{
                            onResultAdded.onError(task.getException());
                        }
                    }
                });
    }
    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public QuestionRepository(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded,
                              onResultLoad onResultLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
        this.onResultLoad = onResultLoad;
    }

    public void getQuestions(){
        firebaseFirestore.collection("Quiz").document()
                .collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                        }else{
                            onQuestionLoad.onError(task.getException());
                        }
                    }
                });
    }

    public interface  onResultLoad{
        void OnResultLoad(HashMap<String , Long> resultMap);
        void onError(Exception e);
    }
    public interface OnQuestionLoad{
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }
    public interface OnResultAdded{
        boolean onSubmit();
        void onError(Exception e);
    }
}
