/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.tasks;

import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailActivity;
import com.example.android.architecture.blueprints.todoapp.util.ActivityUtils;
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource;

public class TasksActivity extends AppCompatActivity{

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        setupViewFragment();

        TasksViewModel tasksViewModel = obtainViewModel(this, TasksViewModel.class);

        tasksViewModel.mOpenTaskEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                openTaskDetails(s);
            }
        });
    }

    private void setupViewFragment() {
        TasksFragment tasksFragment =
                (TasksFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            tasksFragment = TasksFragment.newInstance();
            fragmentTransaction.replace(R.id.contentFrame, tasksFragment);
            fragmentTransaction.commit();
        }
    }


    public static <T extends ViewModel> T obtainViewModel(FragmentActivity activity, Class<T> clazz) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        T tasksViewModel = ViewModelProviders.of(activity,factory).get(clazz);
        return tasksViewModel;
    }

    public void openTaskDetails(String taskId) {
        Log.i("wangweijun", " openTaskDetails  : "+taskId);
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
        startActivityForResult(intent, 1000);

    }
}
