package com.example.android.architecture.blueprints.todoapp.mytasks;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.databinding.MyTasksItemBinding;

import java.util.List;

public class MyTasksAdapter extends BaseAdapter {
    private List<Task> mTasks;

    public MyTasksAdapter() {

    }

    @Override
    public int getCount() {
        return mTasks != null ? mTasks.size() : 0;
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
        MyTasksItemBinding bind;
        if (convertView == null) {
            bind = MyTasksItemBinding.bind(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.my_tasks_item, parent, false));
        } else {
            bind = DataBindingUtil.getBinding(convertView);
        }
        bind.setTask(mTasks.get(position));
        bind.executePendingBindings();
        return bind.getRoot();
    }

    public void replace(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

}
