# 🗂️ Task Tracker CLI

**Task Tracker CLI**

A simple command-line interface (CLI) application to track and manage your tasks. The application stores tasks in a JSON file and provides various commands to manage them.

Project URL: https://roadmap.sh/projects/task-tracker

This project is perfect for practicing core programming concepts such as:
- File system operations
- Command-line argument handling
- JSON data storage
- Error handling and clean CLI design

---

## 🚀 Features

- ✅ Add new tasks  
- ✏️ Update existing tasks  
- ❌ Delete tasks  
- 🔄 Mark tasks as `in-progress`  
- ✅ Mark tasks as `done`  
- 📋 List all tasks  
- ✅ List completed tasks  
- ⏳ List tasks in progress  
- 🕓 List tasks not yet done  

---

## 🛠️ How It Works

- The application runs via the command line using positional arguments.
- All tasks are stored in a local JSON file (`tasks.json`).
- The file is created automatically if it does not exist.
- No external libraries or frameworks are used – just native filesystem and argument parsing.

---

## 📄 Example Usage

```bash
# Add a new task
tasktracker add "Finish writing README"

# Update a task
tasktracker update 1 "Write unit tests"

# Delete a task
tasktracker delete 1

# Mark a task as in-progress
tasktracker progress 2

# Mark a task as done
tasktracker done 2

# List all tasks
tasktracker list

# List completed tasks
tasktracker list-done

# List in-progress tasks
tasktracker list-progress

# List not-done tasks
tasktracker list-pending
