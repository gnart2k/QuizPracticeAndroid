package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

public class QuizFragment extends Fragment implements View.OnClickListener {
    private QuestionViewModel viewModel;
    private NavController navController;
    private ProgressBar progressBar;
    private Button option1Btn, option2Btn, option3Btn, nextQueBtn;
    private TextView questionTv, ansFeedbackTv, questionNumberTv, timerCountTv;
    private ImageView closeQuizBtn;
    private String quizId;

    private String quizTitle;

    private long totalQuestion;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private  int notAnswer = 0;
    private  int correctAnswer = 0;
    private  int wrongAnswer = 0;
    private String answer = "";


    private boolean isStopTimer = false;


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

        viewModel.setQuizId(quizId);

        quizId = QuizFragmentArgs.fromBundle(getArguments()).getQuizId();
        totalQuestion = QuizFragmentArgs.fromBundle(getArguments()).getTotalQueCount();
        quizTitle  =  QuizFragmentArgs.fromBundle(getArguments()).getQuizTitle();
        viewModel.setQuizId(quizId);
        viewModel.getQuestion();

        option1Btn.setOnClickListener(this);
        option2Btn.setOnClickListener(this);
        option3Btn.setOnClickListener(this);
        nextQueBtn.setOnClickListener(this);
        closeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_quizFragment_to_listFragment);
            }
        });

        loadData();
    }


    private void loadData(){
        enableOptions();
        loadQuestion(1);
    }

    private void enableOptions(){
        option1Btn.setVisibility(View.VISIBLE);
        option2Btn.setVisibility(View.VISIBLE);
        option3Btn.setVisibility(View.VISIBLE);

        //enable buttons, hide feedback tv, hide nextQuiz btn

        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);

        ansFeedbackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);
    }

    private void loadQuestion(int i){
        currentQueNo = i;
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                questionTv.setText(currentQueNo+ ") " +questionModels.get(i - 1).getQuestion());
                option1Btn.setText(questionModels.get(i - 1).getOption_a());
                option2Btn.setText(questionModels.get(i - 1).getOption_b());
                option3Btn.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getAnswer();

                //todo set current que no ,, to que number tv
                questionNumberTv.setText(String.valueOf(currentQueNo));
                startTimer();
            }
        });

        canAnswer = true;
    }


    private void startTimer(){
        timerCountTv.setText(String.valueOf(timer));
        progressBar.setVisibility(View.VISIBLE);
        isStopTimer = false;

        countDownTimer = new CountDownTimer(timer * 1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update time
                timerCountTv.setText(millisUntilFinished / 1000 + "");

                Long percent = millisUntilFinished/(timer*10);
                progressBar.setProgress(percent.intValue());
                if(isStopTimer) cancel();
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                ansFeedbackTv.setText("Times Up !! No answer selected");
                notAnswer ++;
                showNextBtn();
            }
        }.start();
    }

    private void showNextBtn() {
        ansFeedbackTv.setVisibility(View.VISIBLE);
        if (currentQueNo == totalQuestion){
            nextQueBtn.setText("Submit");
            nextQueBtn.setEnabled(true);
            nextQueBtn.setVisibility(View.VISIBLE);
        }else {
            nextQueBtn.setVisibility(View.VISIBLE);
            nextQueBtn.setEnabled(true);
        }
    }
    @Override
    public void onClick(View v) {
        int selectedId = v.getId();
        if(selectedId == R.id.option1Btn) {
            verifyAnswer(option1Btn);
        }else if(selectedId == R.id.option2Btn) {
            verifyAnswer(option2Btn);
        }else if(selectedId == R.id.option3Btn) {
            verifyAnswer(option3Btn);
        }else if(selectedId ==R.id.nextQueBtn){
            isStopTimer = true;
            if (currentQueNo == totalQuestion){
                submitResults();
            }else{
                currentQueNo ++;
                loadQuestion(currentQueNo);
                resetOption();
            }
        }
    }


    private void resetOption(){
        ansFeedbackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);
        nextQueBtn.setEnabled(false);
        option1Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.light_sky));
        option2Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.light_sky));
        option3Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.light_sky));
    }

    private void submitResults() {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswer);
        resultMap.put("wrong", wrongAnswer);
        resultMap.put("notAnswered", notAnswer);
        resultMap.put("quizTitle", quizTitle);
        resultMap.put("time", new Date());
        viewModel.addResults(resultMap);
        Log.d("result map", resultMap.toString());
        QuizFragmentDirections.ActionQuizFragmentToResultFragment action = QuizFragmentDirections.actionQuizFragmentToResultFragment();
        action.setQuizId(quizId);
        action.setCorrectAnswer(correctAnswer);
        action.setNotAnswer(notAnswer);
        action.setNotCorrectAnswer(wrongAnswer);
        navController.navigate(action);
    }


    private void verifyAnswer(Button button){
        if( canAnswer){
            if(answer.equals(button.getText())){
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green));
                correctAnswer++;
                ansFeedbackTv.setText("Correct Answer");
            }else{
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red));
                wrongAnswer++;
                Log.d("wrongAnswer", wrongAnswer + "");
                ansFeedbackTv.setText("Wrong answer \nCorrect Answer : " + answer);
            }
        }
//        countDownTimer.cancel();
        isStopTimer = true;
        canAnswer = false;
        showNextBtn();
    }
}