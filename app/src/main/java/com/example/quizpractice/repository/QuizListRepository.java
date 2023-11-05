package com.example.quizpractice.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class QuizListRepository {
    private onFirestoneTaskComplete onFirestoneTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Quiz");

    public QuizListRepository(onFirestoneTaskComplete onFirestoneTaskComplete){
        this.onFirestoneTaskComplete = onFirestoneTaskComplete;
    }
    public void getQuizFromFirebase(){
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFirestoneTaskComplete.quizLoad(task.getResult().toObjects(QuizListModel.class));
                } else{
                    onFirestoneTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public void addQuiz(QuizListModel data){
        DocumentReference quizDocumentRef = firebaseFirestore.collection("Quiz").document();
        quizDocumentRef.set(data).addOnSuccessListener(event -> {
            CollectionReference questionsCollectionRef = quizDocumentRef.collection("questions");
            data.getQuestionModels().forEach(questionsCollectionRef::add);
        }).addOnFailureListener(event -> {
            Log.d("error", event.getMessage());
        });
    }
    public interface onFirestoneTaskComplete{
        void quizLoad (List<QuizListModel> quizListModels);

        void onError(Exception e );
    }

}
