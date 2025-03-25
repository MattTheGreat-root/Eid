import db.Database;
import db.exception.InvalidEntityException;
import example.Human;
import example.HumanValidator;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Human.HUMAN_ENTITY_CODE, new HumanValidator());

        Human ali = new Human("Ali", 10);
        ali.age = -10;
        Database.update(ali);
        Database.add(ali);
    }
}