package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {

        if (entity instanceof Step){
            if (((Step) entity).title == null || ((Step) entity).title.isEmpty()){
                throw new InvalidEntityException("Step title should not be empty");
            }
            if (entity.id != ((Step) entity).taskRef){
                throw new InvalidEntityException("no task available for the given taskRef");
            }
        }
        else throw new IllegalArgumentException("the entity is not a instance of Step");
    }
}
