package db;
import java.util.ArrayList;

public class Database {
    //better way to use id
    static int count = 1;
    private Database() {
        count++;
    }
    private static ArrayList<Entity> entities = new ArrayList<Entity>();

    public static void add(Entity e){
        entities.add(e);
        e.id = count++;
    }

    public static Entity get(int id){
        for(Entity e : entities){
            if(e.id == id){
                return e;
            }
        }
        throw EntityNotFoundException;
    }

    public static void delete(int id){
        for(Entity e : entities){
            if(e.id == id){
                entities.remove(e);
            }
        }
        throw EntityNotFoundException;
    }

    public static void update(Entity e){
        for (Entity temp : entities) {
            if(temp.id == e.id){
                temp = e;
            }
        }
        throw EntityNotFoundException;
    }
}
