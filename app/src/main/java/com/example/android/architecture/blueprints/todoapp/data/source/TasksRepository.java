package com.example.android.architecture.blueprints.todoapp.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource;
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors;

import java.util.List;

public class TasksRepository implements TasksDataSource {
    private static  TasksRepository INSTANCE = null;

    private TasksRemoteDataSource tasksRemoteDataSource;

    private TasksLocalDataSource tasksLocalDataSource;

    private TasksRepository(Context context){
        this.tasksRemoteDataSource = new TasksRemoteDataSource();

        ToDoDatabase database = ToDoDatabase.getInstance(context);
        tasksLocalDataSource  = new TasksLocalDataSource(
                database.taskDao(), new AppExecutors()
        );
    }

    public static TasksRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TasksRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TasksRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(final LoadTasksCallBack callBack) {
        // 1, 内存， 2 db ， 3 网络
        tasksLocalDataSource.getTasks(new LoadTasksCallBack() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                Log.i("wangweijun", "TasksRepository onTasksLoaded tid:"+Thread.currentThread().getId());
                // 可以用
                callBack.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                getTasksFromRemoteDataSource(callBack);
            }
        });

    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadTasksCallBack callback) {
        tasksRemoteDataSource.getTasks(new LoadTasksCallBack() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
