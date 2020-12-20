package phone.vishnu.dailygratitude.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import phone.vishnu.dailygratitude.dao.GratitudeDao;
import phone.vishnu.dailygratitude.database.GratitudeDatabase;
import phone.vishnu.dailygratitude.model.Gratitude;

public class GratitudeRepository {

    private final GratitudeDao gratitudeDao;
    private final LiveData<List<Gratitude>> gratitudeList;

    public GratitudeRepository(Application application) {
        GratitudeDatabase database = GratitudeDatabase.getInstance(application);
        gratitudeDao = database.gratitudeDao();
        gratitudeList = gratitudeDao.getAllGratitude();
    }

    public void insertGratitude(Gratitude gratitude) {
        new InsertGratitudeAsyncTask(gratitudeDao).execute(gratitude);
    }

    public void updateGratitude(Gratitude gratitude) {
        new UpdateGratitudeAsyncTask(gratitudeDao).execute(gratitude);
    }

    public void deleteGratitude(Gratitude gratitude) {
        new DeleteGratitudeAsyncTask(gratitudeDao).execute(gratitude);
    }

    public LiveData<List<Gratitude>> getAllGratitude() {
        return gratitudeList;
    }

    private static class InsertGratitudeAsyncTask extends AsyncTask<Gratitude, Void, Void> {
        private final GratitudeDao gratitudeDao;

        public InsertGratitudeAsyncTask(GratitudeDao gratitudeDao) {
            this.gratitudeDao = gratitudeDao;
        }

        @Override
        protected Void doInBackground(Gratitude... gratitudes) {
            gratitudeDao.insert(gratitudes[0]);
            return null;
        }
    }

    private static class UpdateGratitudeAsyncTask extends AsyncTask<Gratitude, Void, Void> {
        private final GratitudeDao gratitudeDao;

        public UpdateGratitudeAsyncTask(GratitudeDao gratitudeDao) {
            this.gratitudeDao = gratitudeDao;
        }

        @Override
        protected Void doInBackground(Gratitude... gratitudes) {
            gratitudeDao.update(gratitudes[0]);
            return null;
        }
    }

    private static class DeleteGratitudeAsyncTask extends AsyncTask<Gratitude, Void, Void> {
        private final GratitudeDao gratitudeDao;

        public DeleteGratitudeAsyncTask(GratitudeDao gratitudeDao) {
            this.gratitudeDao = gratitudeDao;
        }

        @Override
        protected Void doInBackground(Gratitude... gratitudes) {
            gratitudeDao.delete(gratitudes[0]);
            return null;
        }
    }
}
