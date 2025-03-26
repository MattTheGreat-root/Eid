import db.Database;
import db.exception.*;
import example.*;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Document doc = new Document("Test");
        Database.add(doc);
        System.out.println(doc.getLastModificationDate());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted!");
        }
        doc.setLastModificationDate(new Date());
        Document docFromDocument = (Document) Database.get(doc.id);
        System.out.println(docFromDocument.getLastModificationDate());


    }
}