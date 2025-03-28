import db.*;
import db.exception.*;
import todo.service.StepService;
import todo.service.TaskService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
//we can make choices multiple choice so the user only have to enter a number.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\nEnter a command");
            choice = scanner.nextLine().trim().toLowerCase(); // trim?

            switch (choice) {
                case "add task": {
                    //what if we move all these to service too?
                    System.out.println("Enter task title:");
                    String title = scanner.nextLine();

                    System.out.println("Enter task description:");
                    String description = scanner.nextLine();

                    System.out.println("Enter due date (yyyy-MM-dd):");
                    String dueDateInput = scanner.nextLine();

                    try {
                        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateInput);
                        TaskService.saveTask(title, description, dueDate);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    } catch (InvalidEntityException | IllegalArgumentException e) {
                        System.out.println("Cannot save task. Error: " + e.getMessage());
                    }
                    break;
                }

                case "add step": {
                    //maybe add these too
                    System.out.println("Enter the ID of the task to add a step:");
                    int taskId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter step title:");
                    String stepTitle = scanner.nextLine();

                    try {
                        StepService.saveStep(taskId, stepTitle);
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
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid command.");
                    break;
            }

        } while (!Objects.equals(choice, "exit"));

        scanner.close();
    }
}
