package com.tasktracker.repository;

import com.tasktracker.model.Task;
import java.io.IOException;
import java.util.List;

public interface TaskStorage {
    List<Task> loadTasks() throws IOException;
    void saveTasks(List<Task> tasks) throws IOException;
}
