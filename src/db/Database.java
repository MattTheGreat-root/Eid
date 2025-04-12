package db;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import db.exception.*;


public class Database {
    private Database() {}
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();

    public static void save() throws IOException {
        FileWriter writer = new FileWriter("db.txt");
            for (Entity e : entities) {
                Serializer serializer = serializers.get(e.getEntityCode());
                if (serializer != null) {
                    writer.write(serializer.serialize(e) + "\n");
                }
            }
            System.out.println("Database saved successfully.");
            writer.close();
    }

    public static void load() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("db.txt"));

        String line;
        while ((line = reader.readLine()) != null) {

            String[] parts = line.split("\\|");
            int entityCode = Integer.parseInt(parts[0]);
            Serializer serializer = serializers.get(entityCode);
            if (serializer != null) {
                Entity entity = serializer.deserialize(line);
                if (entity != null) {
                    entities.add(entity);
                }
            }
            else {
                System.out.println("Serializer not found. Make sure not to change db.txt");
            }
        }
        System.out.println("Database loaded successfully.");
        reader.close();

    }


    public static void add(Entity e) throws InvalidEntityException {

        Validator v = validators.get(e.getEntityCode());

        if (v != null) {
            v.validate(e);
        }
        if (e instanceof Trackable){
            ((Trackable) e).setCreationDate(new Date());
            ((Trackable) e).setLastModificationDate(new Date());
        }
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

    public static void update(Entity e) throws InvalidEntityException {
        Validator v = validators.get(e.getEntityCode());

        if (v != null) {
            v.validate(e);
        }
        if (e instanceof Trackable){
            ((Trackable) e).setLastModificationDate(new Date());
        }

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.clone());
                return;
            }
        }
        throw new EntityNotFoundException("Entity with ID " + e.id + " not found");
    }

    public static void registerValidator(int entityCode, Validator validator) {
        //throws exception if the validator with the same entity code already exists
        if (validators.containsKey(entityCode)) {
            if (validators.get(entityCode).equals(validator)) {
                throw new IllegalArgumentException("Validator for entity code " + entityCode + " already exists");
            }
            else {
                throw new IllegalArgumentException("The given entity code has already been registered with a validator");
            }
        }
        else validators.put(entityCode, validator);
    }

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if (serializers.containsKey(entityCode)) {
            if (serializers.get(entityCode).equals(serializer)) {
                throw new IllegalArgumentException("Serializer for entity code " + entityCode + " already exists");
            }
        }
        else serializers.put(entityCode, serializer);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        // return all the entities with specified entityCode
        ArrayList<Entity> ret = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getEntityCode() == entityCode) {
                ret.add(e.clone());
            }
        }
        return ret;
    }
}
