package db;

public interface Serializer {
    String serialize(Entity e);
    Entity deserialize(String s);
}
