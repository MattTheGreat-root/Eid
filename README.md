A console-based To-Do List app written in Java that allows users to manage **tasks** and **steps**, with support for saving and loading data to/from a text file. 
Designed with modularity in mind, it separates logic into entities, services, and a simple file-based database.

features:
-  Add, update, and delete tasks and steps
-  Steps are linked to tasks via ID reference
-  If all steps are completed, the task becomes completed
-  If a step is completed and the task is not started, the task becomes in progress
-  Save and load database to/from a plain `.txt` file
-  Tracks creation and modification timestamps
-  Sort tasks by due date
-  Supports deep cloning of entities
-  Easily extendable for GUI or other interfaces
