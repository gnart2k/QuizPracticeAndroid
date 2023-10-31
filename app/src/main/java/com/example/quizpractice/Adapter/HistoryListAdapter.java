package com.example.quizpractice.Adapter;

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
    public List<ResultModel> resultListModel;
    private OnItemCLickedListener onItemCLickedListener;

    public List<ResultModel> getHistoryListModels() {
        return resultListModel;
    }

    public void setResultListModels(List<ResultModel> resultListModel) {
        this.resultListModel = resultListModel;
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
        ResultModel model = resultListModel.get(position);
        String result = "";
        if(model != null){
            Long score = model.getCorrect()/(model.getWrong() + model.getNotAnswer() + model.getCorrect());
            result = String.valueOf(score);
        }
        holder.quizTitle.setText(model.getQuizTitle());
        holder.correctAnswer.setText(String.valueOf(model.getCorrect()));
        holder.wrongAnswer.setText(String.valueOf(model.getWrong()));
        holder.notAnswer.setText(String.valueOf(model.getNotAnswer()));
        holder.score.setText(result);
        holder.timestamp.setText(String.valueOf(model.getDate()));
    }

    @Override
    public int getItemCount() {
        if (resultListModel == null){
            return 0;
        } else {
            return resultListModel.size();
        }
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView correctAnswer, wrongAnswer, notAnswer, quizTitle, timestamp, score;
        ConstraintLayout constraintLayout;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.historyLayout);
            constraintLayout.setOnClickListener(this);
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
