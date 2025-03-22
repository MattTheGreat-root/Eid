package db.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }
  public EntityNotFoundException() {
    super("Cannot find entity");
  }
  public EntityNotFoundException(int id) {
    super("Cannot find entity with id: " + id);
  }
}
