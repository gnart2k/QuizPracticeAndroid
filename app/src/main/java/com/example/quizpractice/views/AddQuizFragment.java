package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizpractice.R;


public class AddQuizFragment extends Fragment {
    private String title;
    private String difficulty;
    private int questionNumber;

    private Button nextBtn;
    private NavController navController;
    private EditText titleView;
    private EditText difficultyView;
    private EditText questionNumberView;


    public AddQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        titleView = view.findViewById(R.id.quizTitleAddQuiz);
        difficultyView = view.findViewById(R.id.quizDifficulityAddQuiz);
        questionNumberView = view.findViewById(R.id.numberOfQuestionsAddQuiz);
        nextBtn = view.findViewById(R.id.nextToCreateQuyestionAddQuiz);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NavDirections action = AddQuizFragmentDirections.addQuizFragmentToAddQuestionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", titleView.getText().toString());
                bundle.putString("difficulty", difficultyView.getText().toString());
                bundle.putInt("questionNumber", Integer.parseInt(questionNumberView.getText().toString()));
                navController.navigate(R.id.addQuestionFragment, bundle);
            }
        });

    }
}