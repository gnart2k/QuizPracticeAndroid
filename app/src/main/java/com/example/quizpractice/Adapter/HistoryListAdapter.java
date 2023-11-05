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
import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.example.quizpractice.R;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder> {
    public List<ResultModel> resultModels;
    private OnItemCLickedListener onItemCLickedListener;

    public List<ResultModel> getResultModels() {
        return resultModels;
    }

    public void setResultModels(List<ResultModel> resultModels) {
        this.resultModels= resultModels;
    }
    public HistoryListAdapter(OnItemCLickedListener onItemCLickedListener){
        this.onItemCLickedListener  = onItemCLickedListener;
    }
    @NonNull
    @Override
    public HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_result, parent, false);
        return new HistoryListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListViewHolder holder, int position) {
        ResultModel model = resultModels.get(position);
        holder.title.setText("Quiz Tittle: " + model.getQuizTitle());
        Log.d("check", model.getCorrect() + "");
        holder.correctAnswer.setText("Correct Answer: " + model.getCorrect());
        holder.wrongAnswer.setText("Wrong Answer: " + model.getWrong());
        holder.noAnswer.setText("No Answer: " + model.getNotAnswer());
//        holder.timestamp.setText(model.getDate()));
        long score = (model.getCorrect() / (model.getWrong() + model.getNotAnswer() + model.getCorrect())) * 100;
        holder.score.setText("Score: " + score + "/100");

    }

    @Override
    public int getItemCount() {
        if (resultModels == null){
            return 0;
        } else {
            return resultModels.size();
        }
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, correctAnswer, wrongAnswer, noAnswer, score, timestamp;


        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quizTitleEachResult);
            correctAnswer = itemView.findViewById(R.id.correctEachResult);
            wrongAnswer = itemView.findViewById(R.id.wrongEachResult);
            noAnswer = itemView.findViewById(R.id.noAnswerEachResult);
            score = itemView.findViewById(R.id.scoreEachResult);
        }

        @Override
        public void onClick(View v) {
            onItemCLickedListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemCLickedListener{
        void onItemClick(int positon);
    }
}
