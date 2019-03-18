package com.example.android.architecture.blueprints.todoapp.tasks;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.architecture.blueprints.todoapp.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.databinding.TasksFragBinding;

import java.util.ArrayList;

public class TasksFragment extends Fragment {
    private TasksFragBinding tasksFragBinding;
    private TasksViewModel tasksViewModel;

    public TasksFragment() {

    }

    public static TasksFragment newInstance() {
        Bundle args = new Bundle();
        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tasksFragBinding = TasksFragBinding.inflate(inflater, container, false);
        // Use a Factory to inject dependencies into the ViewModel
        tasksViewModel = TasksActivity.obtainViewModel(getActivity(), TasksViewModel.class);
        tasksFragBinding.setViewmodel(tasksViewModel);

        return tasksFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListAdapter();
    }

    private void setupListAdapter() {
        TasksAdapter mListAdapter = new TasksAdapter(
                new ArrayList<Task>(0), tasksViewModel);
        tasksFragBinding.tasksList.setAdapter(mListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        tasksViewModel.loadTasks();
    }
}
