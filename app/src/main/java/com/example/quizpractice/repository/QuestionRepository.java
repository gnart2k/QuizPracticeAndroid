package com.example.quizpractice.repository;

import androidx.annotation.NonNull;

import com.example.quizpractice.Model.QuestionModel;
import com.example.quizpractice.Model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class  QuestionRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizID;
    private HashMap<String , Long> resultMap = new HashMap<>();
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OnResultLoad onResultLoad;


    public void addResults(HashMap<String , Object> resultMap){
        firebaseFirestore.collection("results").document(currentUserId).collection("history")
                .add(resultMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
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

    public QuestionRepository(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
    }

    public void getQuestions(){
        firebaseFirestore.collection("Quiz").document(quizID)
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

    public interface OnResultLoad{
        void onLoad(List<ResultModel> resultModels);
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
