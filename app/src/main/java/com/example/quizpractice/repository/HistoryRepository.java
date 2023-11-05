package com.example.quizpractice.repository;

import androidx.annotation.NonNull;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HistoryRepository {
    private onFirestoneTaskComplete onFirestoneTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public HistoryRepository(onFirestoneTaskComplete onFirestoneTaskComplete){
        this.onFirestoneTaskComplete = onFirestoneTaskComplete;
    }

    public void getResults(){
        firebaseFirestore.collection("results").document(currentUserId).collection("history")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            onFirestoneTaskComplete.resultLoad(task.getResult().toObjects(ResultModel.class));
                        }else{
                            onFirestoneTaskComplete.onError(task.getException());
                        }
                    }
                });
    }

    public interface onFirestoneTaskComplete{
        void resultLoad (List<ResultModel> resultModels);

        void onError(Exception e );
    }

}
