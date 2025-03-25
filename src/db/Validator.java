package db;
import db.exception.InvalidEntityException;

public interface Validator {
    void validate(Entity entity) throws InvalidEntityException;
}
