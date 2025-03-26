package db;

import java.util.Date;

public interface Trackable {
    void setCreationDate(Date date);
    Date getCreationDate();

    void setLastModificationDate(Date date);
    Date getLastModificationDate();
}
