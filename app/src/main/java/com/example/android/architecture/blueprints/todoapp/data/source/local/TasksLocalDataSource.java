package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors;

import java.util.List;

public class TasksLocalDataSource implements TasksDataSource {

    private TasksDao tasksDao;
    private AppExecutors appExecutors;

    public TasksLocalDataSource(TasksDao tasksDao, AppExecutors appExecutors) {
        this.tasksDao = tasksDao;
        this.appExecutors = appExecutors;
    }

    @Override
    public void getTasks(final LoadTasksCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {// 注意这里io线程
                Log.i("wangweijun", "TasksLocalDataSource getTasks tid:"+Thread.currentThread().getId());

                final List<Task> tasks = tasksDao.getTasks();

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {// 切换到ui线程
                        Log.i("wangweijun", "TasksLocalDataSource onTasksLoaded tid:"+Thread.currentThread().getId());
                        callBack.onTasksLoaded(tasks);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }
}
