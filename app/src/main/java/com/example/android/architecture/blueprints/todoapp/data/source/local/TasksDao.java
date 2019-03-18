package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.List;

@Dao
public interface TasksDao {

    @Query("SELECT * FROM Tasks")
    List<Task> getTasks();
}
