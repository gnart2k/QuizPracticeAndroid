package com.example.quizpractice.repository;

import androidx.annotation.NonNull;

import com.example.quizpractice.Model.QuizListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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

    public void addQuiz(Object data){
        firebaseFirestore.collection("Quiz").add(data);
    }
    public interface onFirestoneTaskComplete{
        void quizLoad (List<QuizListModel> quizListModels);
        void onError(Exception e );
    }

}
