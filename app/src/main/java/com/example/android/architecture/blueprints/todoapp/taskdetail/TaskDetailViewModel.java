package com.example.android.architecture.blueprints.todoapp.taskdetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class TaskDetailViewModel extends AndroidViewModel {

    public final ObservableField<Task> task = new ObservableField<>();

    public final ObservableBoolean completed = new ObservableBoolean();

    public final MutableLiveData<Task> mEditTaskCommand = new MutableLiveData<>();

    private TasksRepository mTasksRepository;

    private boolean mIsDataLoading;

    public TaskDetailViewModel(@NonNull Application application, TasksRepository tasksRepository) {
        super(application);

        mTasksRepository = tasksRepository;
    }

    public boolean isDataLoading() {
        return mIsDataLoading;
    }

    public boolean isDataAvailable() {
        return task.get() != null;
    }

    public void onRefresh() {
        if (task.get() != null) {
            start(task.get().getId());
        }
    }


    public void start(String taskId) {
        if (taskId != null) {
            mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {
                @Override
                public void onTaskLoaded(Task task) {
                    setTask(task);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    private void setTask(Task task) {
        this.task.set(task);
    }

    public void editTask() {
        mEditTaskCommand.setValue(task.get());
    }
}
