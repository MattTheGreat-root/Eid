package todo.serializers;

import db.Entity;
import db.Serializer;
import todo.entity.Task;
import java.util.Date;

public class TaskSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        if (e instanceof Task) {
            Task task = (Task) e;
            return Task.TASK_ENTITY_CODE + "|" + task.id + "|" + task.title + "|" + task.description + "|" + task.status + "|" + task.dueDate.getTime()
                    + "|" + task.getCreationDate().getTime() + "|" + task.getLastModificationDate().getTime();
        }
        throw new IllegalArgumentException("Cannot serialize non-Task entity.");
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split("\\|");
        Task task = new Task(parts[2], parts[3], Task.Status.valueOf(parts[4]), new Date(Long.parseLong(parts[5])));
        task.id = Integer.parseInt(parts[1]);
        task.setCreationDate(new Date(Long.parseLong(parts[6])));
        task.setLastModificationDate(new Date(Long.parseLong(parts[7])));
        return task;
    }

}
