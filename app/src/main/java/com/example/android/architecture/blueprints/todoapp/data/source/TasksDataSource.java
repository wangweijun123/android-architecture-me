package com.example.android.architecture.blueprints.todoapp.data.source;

import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.List;

public interface TasksDataSource {

    interface LoadTasksCallBack {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    void getTasks(LoadTasksCallBack callBack);
}
