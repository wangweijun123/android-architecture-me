package com.example.android.architecture.blueprints.todoapp.mytasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.databinding.MyTasksActBinding;

public class MyTasksAct extends AppCompatActivity {
    MyTasksViewModel viewModel;
    MyTasksActBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.my_tasks_act);

        viewModel = obtainViewModel(this, MyTasksViewModel.class);
        binding.setViewmodel(viewModel);
        setupAdapter();

        viewModel.loadTasks();

//        viewModel.pageState.observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(@Nullable Void aVoid) {
//                MyTasksAdapter myTasksAdapter = (MyTasksAdapter) binding.tasksList.getAdapter();
//                myTasksAdapter.replace(viewModel.mTasks);
//            }
//        });
    }

    private void setupAdapter() {
        binding.tasksList.setAdapter(new MyTasksAdapter());
    }


    public static <T extends ViewModel> T obtainViewModel(FragmentActivity activity, Class<T> clazz) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        T tasksViewModel = ViewModelProviders.of(activity,factory).get(clazz);
        return tasksViewModel;
    }
}
