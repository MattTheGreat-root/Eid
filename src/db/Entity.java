package db;

public abstract class Entity implements Cloneable {
    static int count = 1;
    public int id;

    public Entity() {
        this.id = count++;
    }

    public Entity clone(){
        try {
            return (Entity) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}
