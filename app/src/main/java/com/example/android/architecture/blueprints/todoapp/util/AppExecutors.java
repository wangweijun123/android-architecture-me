package com.example.android.architecture.blueprints.todoapp.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private final Executor diskIO;
    private final Executor mainThread;

    public AppExecutors() {
        diskIO = Executors.newSingleThreadExecutor();
        mainThread = new MainThreadExecutor();
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    static class MainThreadExecutor implements Executor {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    }
}
