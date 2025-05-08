package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.repository.TaskStorage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService implements TaskOperations {
    private final TaskStorage storage;

    public TaskService(TaskStorage storage) {
        this.storage = storage;
    }

    public Task addTask(String description) throws IOException {
        List<Task> tasks = storage.loadTasks();
        int newId = tasks.isEmpty() ? 1 : tasks.stream().mapToInt(Task::getId).max().getAsInt() + 1;
        Task newTask = new Task(newId, description);
        tasks.add(newTask);
        storage.saveTasks(tasks);
        return newTask;
    }

    public boolean updateTask(int id, String description) throws IOException {
        List<Task> tasks = storage.loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        
        if (taskOpt.isPresent()) {
            taskOpt.get().setDescription(description);
            storage.saveTasks(tasks);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) throws IOException {
        List<Task> tasks = storage.loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            storage.saveTasks(tasks);
        }
        return removed;
    }

    public boolean markTaskInProgress(int id) throws IOException {
        return updateTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    public boolean markTaskDone(int id) throws IOException {
        return updateTaskStatus(id, TaskStatus.DONE);
    }

    public boolean updateTaskStatus(int id, TaskStatus status) throws IOException {
        List<Task> tasks = storage.loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        
        if (taskOpt.isPresent()) {
            taskOpt.get().setStatus(status);
            storage.saveTasks(tasks);
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() throws IOException {
        return storage.loadTasks();
    }

    public List<Task> getTasksByStatus(TaskStatus status) throws IOException {
        return storage.loadTasks().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }
}
