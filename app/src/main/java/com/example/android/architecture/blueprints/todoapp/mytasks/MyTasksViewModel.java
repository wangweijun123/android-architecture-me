package com.example.android.architecture.blueprints.todoapp.mytasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import java.util.List;

public class MyTasksViewModel extends AndroidViewModel {
    public MutableLiveData<Void> pageState = new MutableLiveData<>();
//    public List<Task> mTasks;

    // 这样的obseravle list 需要自定义BindAdapter自定义属性，在布局文件使用
    // 如果单纯的使用list那就需要livedata 监听，然后自己刷新
    public ObservableList<Task> observableList = new ObservableArrayList<>();

    private final TasksRepository mTasksRepository;

    public MyTasksViewModel(@NonNull Application application,
                            TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }


    public void loadTasks() {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                Log.i("wangweijun", tasks.size()+"");
                observableList.clear();
                observableList.addAll(tasks);
//                items.clear();
//                items.addAll(tasks);
//                mTasks = tasks;
//                pageState.setValue(null);
            }

            @Override
            public void onDataNotAvailable() {
                Log.i("wangweijun", "onDataNotAvailable");
            }
        });
    }
}
