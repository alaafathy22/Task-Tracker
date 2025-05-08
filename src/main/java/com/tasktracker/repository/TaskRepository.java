package com.tasktracker.repository;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final String FILE_PATH = "tasks.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<Task> loadTasks() throws IOException {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        String content = Files.readString(path);
        JSONArray jsonArray = new JSONArray(content);
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonTask = jsonArray.getJSONObject(i);
            Task task = new Task(
                jsonTask.getInt("id"),
                jsonTask.getString("description")
            );
            task.setStatus(TaskStatus.valueOf(jsonTask.getString("status")));
            task.setCreatedAt(LocalDateTime.parse(jsonTask.getString("createdAt"), DATE_FORMATTER));
            task.setUpdatedAt(LocalDateTime.parse(jsonTask.getString("updatedAt"), DATE_FORMATTER));
            tasks.add(task);
        }

        return tasks;
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("id", task.getId());
            jsonTask.put("description", task.getDescription());
            jsonTask.put("status", task.getStatus().toString());
            jsonTask.put("createdAt", task.getCreatedAt().format(DATE_FORMATTER));
            jsonTask.put("updatedAt", task.getUpdatedAt().format(DATE_FORMATTER));
            jsonArray.put(jsonTask);
        }

        Files.writeString(Paths.get(FILE_PATH), jsonArray.toString(2));
    }
}
