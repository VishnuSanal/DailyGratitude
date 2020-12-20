package phone.vishnu.dailygratitude.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import phone.vishnu.dailygratitude.model.Gratitude;

@Dao
public interface GratitudeDao {

    @Insert
    void insert(Gratitude gratitude);

    @Update
    void update(Gratitude gratitude);

    @Delete
    void delete(Gratitude gratitude);

    @Query("SELECT * FROM Gratitude order by _id desc")
    LiveData<List<Gratitude>> getAllGratitude();
}
