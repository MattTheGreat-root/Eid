package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (entity instanceof Task){
            if (((Task) entity).title == null || ((Task) entity).title.isEmpty()) {
                throw new InvalidEntityException("Task title should not be empty");
            }
        }
        else {
            throw new IllegalArgumentException("the entity is not an instance of Task");
        }
    }
}
