import db.*;
import db.exception.*;
import todo.entity.*;
import todo.serializers.*;
import todo.service.*;
import todo.validator.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());
        Database.registerSerializer(Task.TASK_ENTITY_CODE, new TaskSerializer());
        Database.registerSerializer(Step.STEP_ENTITY_CODE, new StepSerializer());
        try {
            Database.load();
        } catch (IOException e) {
            System.err.println("Could not load database.");
        }
        do {
                System.out.println("Enter a command:\nadd task - add step - delete - update task - update step - get task -" +
                        " get all tasks - get incomplete tasks - exit");
                choice = scanner.nextLine().trim().toLowerCase();
            try {
                switch (choice) {
                    case "add task": {
                        try {
                            TaskService.saveTask();
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        } catch (InvalidEntityException | IllegalArgumentException e) {
                            System.out.println("Cannot save task. Error: " + e.getMessage());
                        }
                        break;
                    }

                    case "add step": {
                        try {
                            StepService.saveStep();
                        } catch (InvalidEntityException | IllegalArgumentException e) {
                            System.out.println("Cannot save step. Error: " + e.getMessage());
                        }
                        break;
                    }
                    case "delete": {
                        System.out.println("Enter the ID of the entity to delete:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            StepService.deleteTaskSteps(id);
                            Database.delete(id);
                            System.out.println("Entity deleted successfully.");
                        } catch (EntityNotFoundException | InvalidEntityException e) {
                            System.out.println("Cannot delete entity with ID=" + id);
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }
                    case "update task": {
                        System.out.println("Enter task ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Field: ");
                        String editChoice = scanner.nextLine();
                        switch (editChoice) {
                            case "title": {
                                try {
                                    TaskService.updateTitle(id);
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update task with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            }
                            case "description": {
                                try {
                                    TaskService.updateDescription(id);
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update task with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            }
                            case "due date": {
                                try {
                                    TaskService.updateDueDate(id);
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update task with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                } catch (ParseException e) {
                                    System.out.println("No valid date entered.");
                                }
                                break;
                            }
                            case "status": {
                                try {
                                    TaskService.updateStatus(id);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Invalid status. Please enter a valid status: not started, in progress, or completed.");
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update task with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            }
                            default:
                                System.out.println("Invalid field.");
                                break;
                        }

                        break;
                    }
                    case "update step": {
                        System.out.println("Enter Step ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Field: ");
                        String editChoice = scanner.nextLine();
                        System.out.println("new value: ");

                        switch (editChoice) {
                            case "title": {
                                try {
                                    StepService.updateTitle(id);
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update step with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            }
                            case "status": {
                                try {
                                    StepService.updateStatus(id);
                                } catch (InvalidEntityException e) {
                                    System.out.println("Cannot update step with ID=" + id);
                                    System.out.println("Error: " + e.getMessage());
                                }
                                break;
                            }

                        }
                        break;
                    }
                    case "get task": {
                        try {
                            System.out.println("Enter the ID:");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            TaskService.getTask(id);
                        } catch (InvalidEntityException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }
                    case "get all tasks": {
                        try {
                            TaskService.printSortedTasks();
                        } catch (InvalidEntityException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case "get incomplete tasks": {
                        try {
                            TaskService.getUndoneTasks();
                        } catch (InvalidEntityException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case "exit":
                        try {
                            Database.save();
                        } catch (IOException e){
                            System.out.println("Could not save database.");
                        }
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.Please try again.");
            }
        } while (!Objects.equals(choice, "exit"));

        scanner.close();
    }
}
