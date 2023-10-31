package com.example.quizpractice.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizpractice.Adapter.HistoryListAdapter;
import com.example.quizpractice.Adapter.QuizListAdapter;
import com.example.quizpractice.Model.QuizListModel;
import com.example.quizpractice.Model.ResultModel;
import com.example.quizpractice.R;
import com.example.quizpractice.viewmodel.HistoryViewModel;
import com.example.quizpractice.viewmodel.QuestionViewModel;
import com.example.quizpractice.viewmodel.QuizListViewModel;

import java.util.HashMap;
import java.util.List;


public class QuizHistoryFragment extends Fragment implements HistoryListAdapter.OnItemCLickedListener {
    private RecyclerView recyclerView;
    private NavController navController;
    private HistoryViewModel viewModel;
    private HistoryListAdapter adapter;
    private ProgressBar progressBar;
    final Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_history, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(HistoryViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.quizHistoryProgressBar);

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.historyRecyclerView);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HistoryListAdapter(this);

        recyclerView.setAdapter(adapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<List<ResultModel>>() {
                    @Override
                    public void onChanged(List<ResultModel> resultModels) {
                        Log.d("check", String.valueOf(resultModels.size() == 2));
                        if (resultModels.isEmpty()) {
                            viewModel.reloadData();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            adapter.setResultModels(resultModels);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, 3000);


    }

    @Override
    public void onItemClick(int position) {
//        ListFragmentDirections.ActionListFragmentToDetailFragment action =
//                ListFragmentDirections.actionListFragmentToDetailFragment();
//        action.setPosition(position);
//        navController.navigate(action);
    }
}