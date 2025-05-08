package com.tasktracker;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.service.TaskService;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final TaskService taskService = new TaskService(new TaskRepository());

    public static void main(String[] args) {
        
        //args = new String[] { "add", "test6" }; => add new task
        //args = new String[] { "update", "1", "test6" }; => update task
        //args = new String[] { "delete", "1" }; => delete task
        //args = new String[] { "mark-in-progress", "1" }; => mark task as in progress
        //args = new String[] { "mark-done", "1" }; => mark task as done
        //args = new String[] { "list" }; => list all tasks
        //args = new String[] { "list", "todo" }; => list todo tasks
        //args = new String[] { "list", "in-progress" }; => list in progress tasks
        //args = new String[] { "list", "done" }; => list done tasks

        if (args.length < 1) {
            printUsage();
            return;
        }
        try {
            String command = args[0].toLowerCase();
            switch (command) {
                case "add":
                    handleAdd(args);
                    break;
                case "update":
                    handleUpdate(args);
                    break;
                case "delete":
                    handleDelete(args);
                    break;
                case "mark-in-progress":
                    handleMarkInProgress(args);
                    break;
                case "mark-done":
                    handleMarkDone(args);
                    break;
                case "list":
                    handleList(args);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: Invalid arguments");
            printUsage();
        }
    }

    private static void handleAdd(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Error: Description required");
            return;
        }
        String description = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        Task task = taskService.addTask(description);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    private static void handleUpdate(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Error: Task ID and description required");
            return;
        }
        int id = Integer.parseInt(args[1]);
        String description = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        if (taskService.updateTask(id, description)) {
            System.out.println("Task updated successfully");
        } else {
            System.out.println("Task not found");
        }
    }

    private static void handleDelete(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Error: Task ID required");
            return;
        }
        int id = Integer.parseInt(args[1]);
        if (taskService.deleteTask(id)) {
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task not found");
        }
    }

    private static void handleMarkInProgress(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Error: Task ID required");
            return;
        }
        int id = Integer.parseInt(args[1]);
        if (taskService.markTaskInProgress(id)) {
            System.out.println("Task marked as in progress");
        } else {
            System.out.println("Task not found");
        }
    }

    private static void handleMarkDone(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Error: Task ID required");
            return;
        }
        int id = Integer.parseInt(args[1]);
        if (taskService.markTaskDone(id)) {
            System.out.println("Task marked as done");
        } else {
            System.out.println("Task not found");
        }
    }

    private static void handleList(String[] args) throws IOException {
        List<Task> tasks;
        if (args.length > 1) {
            String status = args[1].toUpperCase();
            switch (status) {
                case "TODO":
                    tasks = taskService.getTasksByStatus(TaskStatus.TODO);
                    break;
                case "IN-PROGRESS":
                    tasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);
                    break;
                case "DONE":
                    tasks = taskService.getTasksByStatus(TaskStatus.DONE);
                    break;
                default:
                    System.out.println("Invalid status: " + status);
                    return;
            }
        } else {
            tasks = taskService.getAllTasks();
        }
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }

        System.out.println("ID | Status | Created At | Updated At | Description");
        System.out.println("-".repeat(80));
        for (Task task : tasks) {
            System.out.printf("%d | %s | %s | %s | %s%n",
                task.getId(),
                task.getStatus(),
                task.getCreatedAt().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE),
                task.getUpdatedAt().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE),
                task.getDescription());
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  task-cli add <description>");
        System.out.println("  task-cli update <id> <description>");
        System.out.println("  task-cli delete <id>");
        System.out.println("  task-cli mark-in-progress <id>");
        System.out.println("  task-cli mark-done <id>");
        System.out.println("  task-cli list [todo|in-progress|done]");
    }
}
