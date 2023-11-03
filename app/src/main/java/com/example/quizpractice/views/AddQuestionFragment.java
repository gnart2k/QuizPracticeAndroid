package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizpractice.Adapter.QuizListAdapter;
import com.example.quizpractice.R;

public class AddQuestionFragment extends Fragment implements QuizListAdapter.OnItemCLickedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false);
    }

    @Override
    public void onItemClick(int positon) {

    }
}