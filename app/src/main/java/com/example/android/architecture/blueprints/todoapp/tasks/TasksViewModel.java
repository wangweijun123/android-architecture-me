package com.example.android.architecture.blueprints.todoapp.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    // These observable fields will update Views automatically
    public final ObservableList<Task> items = new ObservableArrayList<>();
    public final ObservableBoolean empty = new ObservableBoolean(true);

    //
    public final MutableLiveData<String> mOpenTaskEvent = new MutableLiveData();

    private final TasksRepository mTasksRepository;
    private final Context mContext; // To avoid leaks, this must be an Application Context.
    public TasksViewModel(
            Application application,
            TasksRepository repository) {
        super(application);
        mContext = application.getApplicationContext(); // Force use of Application Context.
        mTasksRepository = repository;
    }

    public void loadTasks() {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                Log.i("wangweijun", tasks.size()+"");

                items.clear();
                items.addAll(tasks);
                empty.set(items.isEmpty());
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("wangweijun", "onDataNotAvailable");
            }
        });
    }

}
