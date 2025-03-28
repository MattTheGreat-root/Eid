package todo.entity;

import db.Entity;
import db.Trackable;
import java.util.Date;

public class Task extends Entity implements Trackable {

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED,
    }

    private Date creationDate;
    private Date lastModificationDate;
    public String title;
    public static final int TASK_ENTITY_CODE = 11;
    public String description;
    public Task.Status status;
    public Date dueDate;

    public Task(String title, String description, Status status, Date dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
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
    public String toString(){
        return "ID:" + id + "\nTitle:" + title + "Due date: " + dueDate + "\nStatus:" + status;
    }
}
