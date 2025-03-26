import db.Database;
import db.exception.*;
import example.*;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Human ali = new Human("ali", 18);
        Document doc = new Document("Eid Eid Eid");
        Database.registerValidator(Human.HUMAN_ENTITY_CODE, new HumanValidator());

        Database.add(ali);
        Database.add(doc);

        System.out.println("Document added");

        System.out.println("id: " + doc.id);
        System.out.println("content: " + doc.content);
        System.out.println("creation date: " + doc.getCreationDate());
        System.out.println("last modification date: " + doc.getLastModificationDate());
        System.out.println();

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted!");
        }

        doc.content = "This is the new content";

        Database.update(doc);

        System.out.println("Document updated");
        System.out.println("id: " + doc.id);
        System.out.println("content: " + doc.content);
        System.out.println("creation date: " + doc.getCreationDate());
        System.out.println("last modification date: " + doc.getLastModificationDate());
    }
}