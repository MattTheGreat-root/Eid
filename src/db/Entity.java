package db;

public abstract class Entity {
    static int count = 1;
    public int id;

    public Entity() {
        this.id = count++;
    }
}
