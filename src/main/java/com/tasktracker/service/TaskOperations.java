package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import java.io.IOException;
import java.util.List;

public interface TaskOperations {
    Task addTask(String description) throws IOException;
    boolean updateTask(int id, String description) throws IOException;
    boolean deleteTask(int id) throws IOException;
    boolean updateTaskStatus(int id, TaskStatus status) throws IOException;
    boolean markTaskInProgress(int id) throws IOException;
    boolean markTaskDone(int id) throws IOException;
    List<Task> getAllTasks() throws IOException;
    List<Task> getTasksByStatus(TaskStatus status) throws IOException;
}
