package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizpractice.Adapter.AddQuizAdapter;
import com.example.quizpractice.Adapter.QuizListAdapter;
import com.example.quizpractice.Model.QuestionModel;
import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.R;
import com.example.quizpractice.viewmodel.HistoryViewModel;
import com.example.quizpractice.viewmodel.QuestionViewModel;
import com.example.quizpractice.viewmodel.QuizListViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AddQuestionFragment extends Fragment {
    private NavController navController;
    private QuizListViewModel viewModel;
    private RecyclerView recyclerView;
    private AddQuizAdapter adapter;
    private QuestionModel[] questions;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    final Handler handler = new Handler();
    private Button addQuizButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String quizTitle = getArguments().getString("title");
        String difficulty = getArguments().getString("difficulty");
        int questionNumber = getArguments().getInt("questionNumber");
        questions = new QuestionModel[questionNumber];
        navController = Navigation.findNavController(view);
        addQuizButton = view.findViewById(R.id.done_add_question);
        recyclerView = view.findViewById(R.id.addQuestionRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AddQuizAdapter(questions);
        recyclerView.setAdapter(adapter);
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.notifyDataSetChanged();
                QuizListModel quizListModel = new QuizListModel(quizTitle,"https://firebasestorage.googleapis.com/v0/b/quizzapp-29ac9.appspot.com/o/add_icon.png?alt=media&token=7e9c2b91-a020-470e-9f76-264661747239&_gl=1*1nqi8gt*_ga*MTE0MDAzNzY0Mi4xNjk3ODUyMTYy*_ga_CW55HF8NVT*MTY5OTI3Mzk5MC4xMi4xLjE2OTkyNzc0NTcuMTAuMC4w", difficulty, questionNumber, questions, currentUserId);
                viewModel.addQuiz(quizListModel);
                NavDirections action = AddQuestionFragmentDirections.addQuestionFragmentToListFragment();
                navController.navigate(action);
            }
        });
    }
}