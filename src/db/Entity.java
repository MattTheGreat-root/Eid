package db;

import java.util.Date;

public abstract class Entity implements Cloneable {
    static int count = 1;
    public int id;

    public Entity() {
        this.id = count++;
    }

    @Override
    public Entity clone() {
        try {
            Entity clonedEntity = (Entity) super.clone();

            if (clonedEntity instanceof Trackable) {
                Trackable trackable = (Trackable) clonedEntity;
                if (trackable.getCreationDate() != null) {
                    trackable.setCreationDate((Date) trackable.getCreationDate().clone());
                }
                if (trackable.getLastModificationDate() != null) {
                    trackable.setLastModificationDate((Date) trackable.getLastModificationDate().clone());
                }
            }

            return clonedEntity;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported for entity", e);
        }
    }


    public abstract int getEntityCode();
}
