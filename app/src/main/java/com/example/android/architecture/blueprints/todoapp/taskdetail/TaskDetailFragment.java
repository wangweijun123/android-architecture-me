package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.databinding.TaskdetailFragBinding;

public class TaskDetailFragment extends Fragment {
    public static final String ARGUMENT_TASK_ID = "TASK_ID";

    private TaskDetailViewModel taskDetailViewModel;

    public static TaskDetailFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        taskDetailViewModel = TaskDetailActivity.obtainViewModel(
                getActivity(), TaskDetailViewModel.class);

        TaskdetailFragBinding binding = TaskdetailFragBinding.inflate(inflater, container, false);
        binding.setViewmodel(taskDetailViewModel);
        binding.setListener(new TaskDetailUserActionsListener() {
            @Override
            public void onCompleteChanged(View v) {
                Log.i("wangweijun", "onCompleteChanged v:"+v);
            }
        });
        setupFab();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        taskDetailViewModel.start(getArguments().getString(ARGUMENT_TASK_ID));
    }

    private void setupFab() {
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDetailViewModel.editTask();
            }
        });
    }
}
