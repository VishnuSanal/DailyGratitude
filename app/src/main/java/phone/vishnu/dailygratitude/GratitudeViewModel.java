package phone.vishnu.dailygratitude;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GratitudeViewModel extends AndroidViewModel {

    GratitudeRepository repository;
    LiveData<List<Gratitude>> allGratitude;

    public GratitudeViewModel(@NonNull Application application) {
        super(application);
        repository = new GratitudeRepository(application);
        allGratitude = repository.getAllGratitude();
    }

    public void insert(Gratitude gratitude) {
        repository.insertGratitude(gratitude);
    }

    public void delete(Gratitude gratitude) {
        repository.deleteGratitude(gratitude);
    }

    public void update(Gratitude gratitude) {
        repository.updateGratitude(gratitude);
    }

    public LiveData<List<Gratitude>> getAllGratitude() {
        return allGratitude;
    }
}
