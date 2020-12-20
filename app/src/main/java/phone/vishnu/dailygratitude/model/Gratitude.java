package phone.vishnu.dailygratitude.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Gratitude {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String title;
    private String description;
    private String dateAdded;

    public Gratitude() {
    }

    public Gratitude(String title, String description, String dateAdded) {
        this.title = title;
        this.description = description;
        this.dateAdded = dateAdded;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}