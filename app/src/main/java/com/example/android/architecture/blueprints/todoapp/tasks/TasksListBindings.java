/*
 * Copyright 2016, The Android Open Source Project
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

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ListView;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.mytasks.MyTasksAdapter;

import java.util.List;

/**
 * Contains {@link BindingAdapter}s for the {@link Task} list.
 * 生成的binding类自动调用
 */
public class TasksListBindings {

    @SuppressWarnings("unchecked")
//    @BindingAdapter("app:items")
    @BindingAdapter(value = {"app:items", "app:divider"}, requireAll = false)
    public static void setItems(ListView listView, List<Task> items, Drawable divider) {
        TasksAdapter adapter = (TasksAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
        listView.setDivider(divider);
    }



    @SuppressWarnings("unchecked")
//    @BindingAdapter(value = {"xxxx:tasks"}, requireAll = false) //可以不用前缀,然后只有属性要么在xml，
    // 要么bindingAdapter
    @BindingAdapter(value = {"tasks"}, requireAll = false)
    public static void setTasks(ListView listView, List<Task> items) {
        MyTasksAdapter adapter = (MyTasksAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replace(items);
        }
    }
}
