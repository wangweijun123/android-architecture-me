package com.example.android.architecture.blueprints.todoapp.data.source.remote;

import android.os.Handler;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 远程数据
 */
public class TasksRemoteDataSource implements TasksDataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;

    private final static Map<String, Task> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.", "0");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "1");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "2");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "3");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "4");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "5");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "6");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "7");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "8");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "12");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "13");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "14");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "15");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "16");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "17");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!", "18");
    }
    @Override
    public void getTasks(final LoadTasksCallBack callBack) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    private static void addTask(String title, String description, String id) {
        Task newTask = new Task(title, description, id);
        TASKS_SERVICE_DATA.put(newTask.getId(), newTask);
    }

}
