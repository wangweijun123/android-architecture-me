package com.example.android.architecture.blueprints.todoapp.addedittask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.SingleLiveEvent;
import com.example.android.architecture.blueprints.todoapp.SnackbarMessage;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class AddEditTaskViewModel  extends AndroidViewModel  implements TasksDataSource.GetTaskCallback {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private final SingleLiveEvent<Void> mTaskUpdated = new SingleLiveEvent<>();

    private final TasksRepository mTasksRepository;

    @Nullable
    private String mTaskId;

    private boolean mIsNewTask;

    private boolean mIsDataLoaded = false;

    private boolean mTaskCompleted = false;

    public AddEditTaskViewModel(@NonNull Application application,
                                TasksRepository tasksRepository) {
        super(application);
        mTasksRepository = tasksRepository;
    }

    public void start(String taskId) {
        if (dataLoading.get()) {
            // Already loading, ignore.
            return;
        }
        mTaskId = taskId;
        if (taskId == null) {
            // No need to populate, it's a new task
            mIsNewTask = true;
            return;
        }
        if (mIsDataLoaded) {
            // No need to populate, already have data.
            return;
        }
        mIsNewTask = false;
        dataLoading.set(true);

        mTasksRepository.getTask(taskId, this);
    }

    @Override
    public void onTaskLoaded(Task task) {
        title.set(task.getTitle());
        description.set(task.getDescription());
        mTaskCompleted = task.isCompleted();
        dataLoading.set(false);
        mIsDataLoaded = true;
        // Note that there's no need to notify that the values changed because we're using
        // ObservableFields.
    }

    @Override
    public void onDataNotAvailable() {
        dataLoading.set(false);
    }

    // Called when clicking on fab.
    void saveTask() {
        Task task = new Task(title.get(), description.get());
        if (task.isEmpty()) {
            mSnackbarText.setValue(R.string.empty_task_message);
            return;
        }
        if (isNewTask() || mTaskId == null) {
            createTask(task);
        } else {
            task = new Task(title.get(), description.get(), mTaskId, mTaskCompleted);
            updateTask(task);
        }
    }
    SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    SingleLiveEvent<Void> getTaskUpdatedEvent() {
        return mTaskUpdated;
    }
    private boolean isNewTask() {
        return mIsNewTask;
    }
    private void createTask(Task newTask) {
        mTasksRepository.saveTask(newTask);
        mTaskUpdated.call();
    }
    private void updateTask(Task task) {
        if (isNewTask()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mTasksRepository.saveTask(task);
        mTaskUpdated.call();
    }
}
