package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizpractice.Model.QuestionModel;
import com.example.quizpractice.R;
import com.example.quizpractice.viewmodel.QuestionViewModel;
import com.example.quizpractice.viewmodel.QuizListViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {
    private QuestionViewModel viewModel;
    private NavController navController;
    private ProgressBar progressBar;
    private Button option1Btn, option2Btn, option3Btn, nextQueBtn;
    private TextView questionTv, ansFeedbackTv, questionNumberTv, timerCountTv;
    private ImageView closeQuizBtn;
    private int notAnswerd = 0;
    private int correctAnswerd = 0;
    private int wrongAnswerd = 0;
    private String answer = "";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        closeQuizBtn = view.findViewById(R.id.close_quiz_btn);
        option1Btn = view.findViewById(R.id.option1Btn);
        option2Btn = view.findViewById(R.id.option2Btn);
        option3Btn = view.findViewById(R.id.option3Btn);
        nextQueBtn = view.findViewById(R.id.nextQueBtn);
        ansFeedbackTv = view.findViewById(R.id.ansFeedbackTv);
        questionTv = view.findViewById(R.id.quizQuestionTv);
        timerCountTv = view.findViewById(R.id.countTimeQuiz);
        questionNumberTv = view.findViewById(R.id.quizQuestionCount);
        progressBar = view.findViewById(R.id.quizCountProgressBar);


    }

    private void loadQuestion(int i){
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                questionTv.setText(questionModels.get(i - 1).getQuestionID());
                option1Btn.setText(questionModels.get(i - 1).getOption_a());
                option2Btn.setText(questionModels.get(i - 1).getOption_b());
                option3Btn.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getAnswer();

                //todo set curren ques no, to ques number tv
                questionNumberTv.setText((String.valueOf(currenQueNo)));
                canAnswer = true;
            }
        });
        startTimer();

    }

    public  void submitResults(){
        HashMap<String , Objects> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswerd);
        resultMap.put("wrong", wrongAnswerd);
        resultMap.put("notAnswered", notAnswerd);

        viewModel.addResuts(resultMap);
        navController.navigate(R.id.action_QuizFragment_to_resultFragment);
    }

    private void verifiAnswer(Button button){
        if(canAnswer){
            if(answer.equals(button.getText())){
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green));
                correctAnswerd++;
                ansFeedbackTv.setText("Correct Answer");
            }else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red));
                wrongAnswerd++;
                ansFeedbackTv.setText("Wrong Answer \nCorect Answer :"+ answer);
            }
        }
        canAnswer = false;
        couDownTimer.cancer();
        showNextBtn();
    }

}