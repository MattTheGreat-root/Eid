package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import static todo.service.TaskService.scanner;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException {
        //creates and saves a new step
        Step step = new Step(title, Step.Status.NOT_STARTED, taskRef);
        Database.add(step);
        System.out.println("Step added successfully. ID: " + step.id);
        System.out.println("Creation Date: " + step.getCreationDate());
    }

    public static void updateTitle(int id) throws InvalidEntityException {
        Step step = (Step) Database.get(id);
        System.out.println("Enter the new value:");
        String newTitle = scanner.nextLine();
        String oldTitle = step.title;
        step.title = newTitle;
        Database.update(step);
        System.out.println("Successfully updated step.");
        System.out.println("Field: title");
        System.out.println("old value: " + oldTitle);
        System.out.println("new value: " + newTitle);
        System.out.println("Modification Date: " + step.getLastModificationDate());
    }

    public static void updateStatus(int id) throws InvalidEntityException {
        System.out.println("Enter the task status:");
        String newStatus = scanner.nextLine();
        switch (newStatus) {
            case "completed": {
                setAsCompleted(id);
                check((Step) Database.get(id));
                break;
            }
            case "not started": {
                setAsNotStarted(id);
                break;
            }
            default: {
                System.out.println("Invalid step status");
                break;
            }
        }
    }

    public static void deleteTaskSteps(int taskRef) throws InvalidEntityException {
        if (Database.get(taskRef) instanceof Task) {
            for (Entity e : Database.getAll(Step.STEP_ENTITY_CODE)) {
                if (((Step) e).taskRef == taskRef) {
                    Database.delete(e.id);
                }
            }

        }
    }

    public static void setAsCompleted(int id) throws InvalidEntityException {
        Step step = (Step) Database.get(id);
        Step.Status oldStatus = step.status;
        step.status = Step.Status.COMPLETED;
        Database.update(step);
        System.out.println("Successfully updated the step.");
        System.out.println("Field: status");
        System.out.println("old value: " + oldStatus);
        System.out.println("new value: " + step.status);
        System.out.println("Modification Date: " + step.getLastModificationDate());
    }

    public static void setAsNotStarted(int id) throws InvalidEntityException {
        Step step = (Step) Database.get(id);
        Step.Status oldStatus = step.status;
        step.status = Step.Status.NOT_STARTED;
        Database.update(step);
        System.out.println("Successfully updated the step.");
        System.out.println("Field: status");
        System.out.println("old value: " + oldStatus);
        System.out.println("new value: " + step.status);
        System.out.println("Modification Date: " + step.getLastModificationDate());
    }

    //checks if all the steps are completed and make the task completed.also checks if at least one step is completed
    //the task should not be NOT_STARTED
    public static void check(Step step) throws InvalidEntityException {
        boolean allStepsCompleted = true;

        for (Entity e : Database.getAll(Step.STEP_ENTITY_CODE)) {
            Step s = (Step) e;
            if (s.taskRef == step.taskRef) {
                if (s.status != Step.Status.COMPLETED) {
                    allStepsCompleted = false;
                }
            }
        }

        Task task = (Task) Database.get(step.taskRef);

        if (allStepsCompleted) {
            task.status = Task.Status.COMPLETED;
        }
        if (task.status == Task.Status.NOT_STARTED) {
            task.status = Task.Status.IN_PROGRESS;
        }

        Database.update(task);
    }
}