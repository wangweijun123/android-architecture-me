package com.example.android.architecture.blueprints.todoapp.tasks;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.databinding.TaskItemBinding;

import java.util.List;

public class TasksAdapter extends BaseAdapter {
    private List<Task> mTasks;
    private TasksViewModel mTasksViewModel;

    public TasksAdapter(List<Task> tasks, TasksViewModel tasksViewModel) {
        mTasks = tasks;
        mTasksViewModel = tasksViewModel;
    }
    @Override
    public int getCount() {
        return mTasks != null ? mTasks.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItemBinding taskItemBinding;
        if (convertView == null) {
            taskItemBinding = TaskItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
        } else {
            taskItemBinding = DataBindingUtil.getBinding(convertView);
        }
        taskItemBinding.setTask(mTasks.get(position));
        taskItemBinding.setListener(new TaskItemUserActionsListener() {
            @Override
            public void onCompleteChanged(Task task, View v) {

            }
            @Override
            public void onTaskClicked(Task task) {
                Log.i("wangweijun", " onTaskClicked  : "+task);
                mTasksViewModel.mOpenTaskEvent.setValue(task.getId());
            }
        });
        taskItemBinding.executePendingBindings();
        return taskItemBinding.getRoot();
    }


    public void replaceData(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }
}
