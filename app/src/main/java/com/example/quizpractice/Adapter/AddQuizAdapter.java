package com.example.quizpractice.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizpractice.Model.QuestionModel;
import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.example.quizpractice.R;
import com.example.quizpractice.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddQuizAdapter extends RecyclerView.Adapter<AddQuizAdapter.AddQuizListViewHolder> {
    //public List<ResultModel> resultModels;
    private QuestionModel[] questionModels;
    private ArrayList<QuestionModel> questions;
    public AddQuizAdapter(QuestionModel[] questionModels) {
        this.questionModels = questionModels;
    }

    //    public List<ResultModel> getResultModels() {
//        return resultModels;
//    }

//    public void setResultModels(List<ResultModel> resultModels) {
//        this.resultModels= resultModels;
//    }
//    public AddQuizAdapter(OnItemCLickedListener onItemCLickedListener){
//        this.onItemCLickedListener  = onItemCLickedListener;
//    }
    @NonNull
    @Override
    public AddQuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_question, parent, false);
        return new AddQuizListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddQuizListViewHolder holder, int position) {
        String questionTitle = holder.questionTitle.getText().toString();
        String answer1_correct = holder.answer1_correct.getText().toString();
        String correctAnswer = answer1_correct;
        String answer2 = holder.answer2.getText().toString();
        String answer3 = holder.answer3.getText().toString();
        List<String> shuffledString = MyUtils.shuffleThreeStrings(answer1_correct, answer2, answer3);
        QuestionModel questionModel = new QuestionModel(questionTitle, correctAnswer, shuffledString.get(0) , shuffledString.get(1), shuffledString.get(2), 10);
        questionModels[position] = questionModel;
        Log.d("Question", questionModel.toString());
    }

    @Override
    public int getItemCount() {
        return questionModels.length;
    }

    public class AddQuizListViewHolder extends RecyclerView.ViewHolder{
        TextInputEditText questionTitle,answer1_correct,answer2,answer3;
        public AddQuizListViewHolder(View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            answer1_correct = itemView.findViewById(R.id.answer1_correct);
            answer2 = itemView.findViewById(R.id.answer2);
            answer3 = itemView.findViewById(R.id.answer3);
        }
    }
}
