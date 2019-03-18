package com.example.android.architecture.blueprints.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import java.util.List;

public class MainAct extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.task_item);

        TasksRepository.getInstance(getApplicationContext()).getTasks(new TasksDataSource.LoadTasksCallBack() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                Log.i("wangweijun", "MainAct onTasksLoaded tid:"+Thread.currentThread().getId());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
