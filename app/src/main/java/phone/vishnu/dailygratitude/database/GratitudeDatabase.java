package phone.vishnu.dailygratitude.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import phone.vishnu.dailygratitude.dao.GratitudeDao;
import phone.vishnu.dailygratitude.model.Gratitude;

@Database(entities = {Gratitude.class}, version = 1)
public abstract class GratitudeDatabase extends RoomDatabase {

    private static GratitudeDatabase instance;

    private static final RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    public static synchronized GratitudeDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), GratitudeDatabase.class, "gratitude_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }

        return instance;
    }

    public abstract GratitudeDao gratitudeDao();

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private final GratitudeDao shelveDao;

        public PopulateDBAsyncTask(GratitudeDatabase database) {
            this.shelveDao = database.gratitudeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
