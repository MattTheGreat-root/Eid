package db;
import java.util.ArrayList;
import db.exception.*;

public class Database {
    //better way to use id?
    private Database() {}
    private static ArrayList<Entity> entities = new ArrayList<>();

    public static void add(Entity e){
        entities.add(e.clone());
    }

    public static Entity get(int id){
        for(Entity e : entities){
            if(e.id == id){
                return e.clone();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id){
        for(Entity e : entities){
            if(e.id == id){
                entities.remove(e);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.clone());
                return;
            }
        }
        throw new EntityNotFoundException("Entity with ID " + e.id + " not found");
    }

}
