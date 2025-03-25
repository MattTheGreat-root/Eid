package db;

import java.util.ArrayList;
import java.util.HashMap;
import db.exception.*;

public class Database {
    private Database() {}
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    public static void add(Entity e) throws InvalidEntityException {

        Validator v = validators.get(e.getEntityCode());
        //double check
        if (v == null) {
            throw new IllegalArgumentException("No validator found for entity code: " + e.getEntityCode());
        }
        else {
             v.validate(e);
             entities.add(e.clone());
        }
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

    public static void update(Entity e) throws InvalidEntityException {
        Validator v = validators.get(e.getEntityCode());
        if (v == null) {
            throw new IllegalArgumentException("No validator found for entity code: " + e.getEntityCode());
        }
        else {
            v.validate(e);
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).id == e.id) {
                      entities.set(i, e.clone());
                      return;
                }
            }
        }
        throw new EntityNotFoundException("Entity with ID " + e.id + " not found");
    }

    public static void registerValidator(int entityCode, Validator validator) {
        //throws exception if the validator with the same entity code already exist or has another entity code
        if (validators.containsKey(entityCode)) {
            if (validators.get(entityCode).equals(validator)) {
                throw new IllegalArgumentException("Entity with ID " + entityCode + " already exists");
            }
            else {
                throw new IllegalArgumentException("The given entity code has already been registered with a validator");
            }
        }
        else validators.put(entityCode, validator);
    }
}
