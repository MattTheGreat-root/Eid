package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Step extends Entity implements Trackable {
    public static final int STEP_ENTITY_CODE = 20;
    private Date creationDate;
    private Date lastModificationDate;

    public enum Status {
        NOT_STARTED,
        COMPLETED,
    }

    public String title;
    public Status status;
    //taskRef equals to the corresponding taskID
    public int taskRef;

    public Step(String title, Status status, int taskRef) {
        this.title = title;
        this.status = status;
        this.taskRef = taskRef;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public String toString() {
        return "   + " + title + "\n        ID: " + id + "\n        Status: " + status;
    }
}
