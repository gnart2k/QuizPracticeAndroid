package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.R;
import com.example.quizpractice.viewmodel.QuizListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private TextView title, difficulty, totalQuestion;
    private Button startQuizBtn;
    private NavController navController;
    private int position;
    private ProgressBar progressBar;

    private QuizListViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.detailFragmentDifficulty);
        difficulty = view.findViewById(R.id.detailFragmentDifficulty);
        totalQuestion = view.findViewById(R.id.detailFragmentQuestions);
        startQuizBtn = view.findViewById(R.id.startQuizBtn);

        navController = Navigation.findNavController(view);

        position = DetailFragmentArgs.fromBundle(getArguments()).getPosition();

        viewModel.getQuizListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                QuizListModel quiz = QuizListModel.get(position);
                difficulty.setText(quiz.getDifficulty());
                title.setText(quiz.getTitle());
                totalQuestion.setText(String.valueOf(quiz.getQuestions()));
            }
        });
    }
}