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
import com.example.quizpractice.R;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder> {
    public List<QuizListModel> quizListModels;
    private OnItemCLickedListener onItemCLickedListener;

    public List<QuizListModel> getQuizListModels() {
        return quizListModels;
    }

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        this.quizListModels = quizListModels;
    }
    public QuizListAdapter(OnItemCLickedListener onItemCLickedListener){
        this.onItemCLickedListener  = onItemCLickedListener;
    }
    @NonNull
    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_quiz, parent, false);
        return new QuizListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListViewHolder holder, int position) {
        QuizListModel model = quizListModels.get(position);
        holder.title.setText(model.getTitle());
        Glide.with(holder.itemView).load(model.getImage()).into(holder.quizImage);
    }

    @Override
    public int getItemCount() {
        if (quizListModels == null){
            return 0;
        } else {
            return quizListModels.size();
        }
    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView quizImage;
        ConstraintLayout constraintLayout;

        public QuizListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quizTitleList);
            quizImage = itemView.findViewById(R.id.quizImageList);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
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
