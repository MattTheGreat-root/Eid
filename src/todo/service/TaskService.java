package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskService {
    static Scanner scanner = new Scanner(System.in);
    public static void saveTask(String title, String description, Date dueDate) throws InvalidEntityException {
        //creates and saves a new task
        Task task = new Task(title, description, Task.Status.NOT_STARTED, dueDate);
        Database.add(task);
    }

    public static void updateTitle(int id) throws InvalidEntityException {
        System.out.println("Enter the new value:");
        String newTitle = scanner.nextLine();
        Task task = (Task) Database.get(id);
        String oldTitle = task.title;
        task.title = newTitle;
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: title");
        System.out.println("old value: " + oldTitle);
        System.out.println("new value: " + newTitle);
        System.out.println("Modification Date: " + task.getLastModificationDate());
    }

    public static void updateDescription(int id) throws InvalidEntityException {
        System.out.println("Enter the new value:");
        String newDescription = scanner.nextLine();
        Task task = (Task) Database.get(id);
        String oldDescription = task.description;
        task.description = newDescription;
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: description");
        System.out.println("old value: " + oldDescription);
        System.out.println("new value: " + newDescription);
        System.out.println("Modification Date: " + task.getLastModificationDate());
    }

    public static void updateDueDate(int id) throws InvalidEntityException, ParseException {
        System.out.println("Enter the new value:");
        String newDueDate = scanner.nextLine();
        Task task = (Task) Database.get(id);
        Date oldDueDate = task.dueDate;
        task.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDueDate);
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: dueDate");
        System.out.println("old value: " + oldDueDate);
        System.out.println("new value: " + newDueDate);
    }

    public static void updateStatus(int id) throws InvalidEntityException {
        System.out.println("Enter the task status:");
        String newStatus = scanner.nextLine();
        switch (newStatus){
            case "completed":{
                setAsCompleted(id);
                break;
            }
            case "in progress":{
                setAsInProgress(id);
                break;
            }
            case "not started":{
                setAsNotStarted(id);
                break;
            }
            default:{
                System.out.println("Invalid task status");
                break;
            }
        }
    }
    public static void setAsCompleted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        Task.Status oldStatus = task.status;
        task.status = Task.Status.COMPLETED;
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: status");
        System.out.println("old value: " + oldStatus);
        System.out.println("new value: " + task.status);
        System.out.println("Modification Date: " + task.getLastModificationDate());
        for (Entity e : Database.getAll(Step.STEP_ENTITY_CODE)) {
            if (((Step) e).taskRef == taskId) {
                ((Step) e).status = Step.Status.COMPLETED;
            }
        }
    }
    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        Task.Status oldStatus = task.status;
        task.status = Task.Status.NOT_STARTED;
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: status");
        System.out.println("old value: " + oldStatus);
        System.out.println("new value: " + task.status);
        System.out.println("Modification Date: " + task.getLastModificationDate());
    }
    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        Task.Status oldStatus = task.status;
        task.status = Task.Status.IN_PROGRESS;
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: status");
        System.out.println("old value: " + oldStatus);
        System.out.println("new value: " + task.status);
        System.out.println("Modification Date: " + task.getLastModificationDate());
    }

    public static void getTask(int id) throws InvalidEntityException {
        Task task = (Task) Database.get(id);
        System.out.println(task);
        System.out.println("Steps:");
        for (Entity e : Database.getAll(Step.STEP_ENTITY_CODE)) {
            Step step = (Step) e;
            if (step.taskRef == id) {
                System.out.println(step);
            }
        }
        System.out.println();
    }

    public static void printSortedTasks() throws InvalidEntityException {
        List<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);

        tasks.sort(Comparator.comparing(task -> ((Task) task).dueDate));

        for (Entity e : tasks) {
            Task task = (Task) e;
            getTask(task.id);
        }
    }

    public static void getUndoneTasks() throws InvalidEntityException {
        for (Entity e : Database.getAll(Task.TASK_ENTITY_CODE)) {
            Task task = (Task) e;
            if (task.status != Task.Status.COMPLETED) {
                System.out.println(task);
            }
        }
    }

}