package com.example.android.architecture.blueprints.todoapp.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.SingleLiveEvent;
import com.example.android.architecture.blueprints.todoapp.SnackbarMessage;
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    // These observable fields will update Views automatically
    // These observable fields will update Views automatically
    public final ObservableList<Task> items = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> currentFilteringLabel = new ObservableField<>();

    public final ObservableField<String> noTasksLabel = new ObservableField<>();

    public final ObservableField<Drawable> noTaskIconRes = new ObservableField<>();

    public final ObservableBoolean empty = new ObservableBoolean(false);

    public final ObservableBoolean tasksAddViewVisible = new ObservableBoolean();

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    private final TasksRepository mTasksRepository;

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);

    private final SingleLiveEvent<String> mOpenTaskEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<Void> mNewTaskEvent = new SingleLiveEvent<>();

    public TasksViewModel(@NonNull Application application,
                          TasksRepository repository) {
        super(application);
        mTasksRepository = repository;
    }

    public void start() {
        loadTasks(false);
    }

    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        if (forceUpdate) {

            mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<>();

                // We filter the tasks based on the requestType
                for (Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;
                        case ACTIVE_TASKS:
                            if (task.isActive()) {
                                tasksToShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }
                if (showLoadingUI) {
                    dataLoading.set(false);
                }
                mIsDataLoadingError.set(false);

                items.clear();
                items.addAll(tasksToShow);
                empty.set(items.isEmpty());
            }

            @Override
            public void onDataNotAvailable() {
                mIsDataLoadingError.set(true);
            }
        });
    }

    public void completeTask(Task task, boolean checked) {

    }

    SingleLiveEvent<String> getOpenTaskEvent() {
        return mOpenTaskEvent;
    }

    SingleLiveEvent<Void> getNewTaskEvent() {
        return mNewTaskEvent;
    }
    public void addNewTask() {
        mNewTaskEvent.call();
    }

    void handleActivityResult(int requestCode, int resultCode) {
        if (AddEditTaskActivity.REQUEST_CODE == requestCode) {
            switch (resultCode) {
                case TaskDetailActivity.EDIT_RESULT_OK:
                    mSnackbarText.setValue(R.string.successfully_saved_task_message);
                    break;
                case AddEditTaskActivity.ADD_EDIT_RESULT_OK:
                    mSnackbarText.setValue(R.string.successfully_added_task_message);
                    break;
                case TaskDetailActivity.DELETE_RESULT_OK:
                    mSnackbarText.setValue(R.string.successfully_deleted_task_message);
                    break;
            }
        }
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }
}
