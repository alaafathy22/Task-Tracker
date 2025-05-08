package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.repository.TaskRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task addTask(String description) throws IOException {
        List<Task> tasks = repository.loadTasks();
        int newId = tasks.isEmpty() ? 1 : tasks.stream().mapToInt(Task::getId).max().getAsInt() + 1;
        Task newTask = new Task(newId, description);
        tasks.add(newTask);
        repository.saveTasks(tasks);
        return newTask;
    }

    public boolean updateTask(int id, String description) throws IOException {
        List<Task> tasks = repository.loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        
        if (taskOpt.isPresent()) {
            taskOpt.get().setDescription(description);
            repository.saveTasks(tasks);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) throws IOException {
        List<Task> tasks = repository.loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            repository.saveTasks(tasks);
        }
        return removed;
    }

    public boolean markTaskInProgress(int id) throws IOException {
        return updateTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    public boolean markTaskDone(int id) throws IOException {
        return updateTaskStatus(id, TaskStatus.DONE);
    }

    private boolean updateTaskStatus(int id, TaskStatus status) throws IOException {
        List<Task> tasks = repository.loadTasks();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        
        if (taskOpt.isPresent()) {
            taskOpt.get().setStatus(status);
            repository.saveTasks(tasks);
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() throws IOException {
        return repository.loadTasks();
    }

    public List<Task> getTasksByStatus(TaskStatus status) throws IOException {
        return repository.loadTasks().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }
}
