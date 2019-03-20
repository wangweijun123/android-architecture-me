package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskFragment;
import com.example.android.architecture.blueprints.todoapp.data.Task;


public class TaskDetailActivity  extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "TASK_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.taskdetail_act);

        TaskDetailViewModel taskDetailViewModel = obtainViewModel(this, TaskDetailViewModel.class);
        taskDetailViewModel.mEditTaskCommand.observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                onStartEditTask(task);
            }
        });

        setupFragment();

    }

    private void setupFragment() {
        // Get the requested task id
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (taskDetailFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentFrame, TaskDetailFragment.newInstance(taskId));
            ft.commit();
        }
    }

    public static <T extends ViewModel> T obtainViewModel(FragmentActivity activity, Class<T> clazz) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        T tasksViewModel = ViewModelProviders.of(activity,factory).get(clazz);
        return tasksViewModel;
    }


    public void onStartEditTask(Task task) {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, task);
        startActivityForResult(intent, 100);
    }
}
