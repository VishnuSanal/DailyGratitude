package phone.vishnu.dailygratitude;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Gratitude.class}, version = 1)
public abstract class GratitudeDatabase extends RoomDatabase {

    private static GratitudeDatabase instance;

    private static RoomDatabase.Callback callback = new Callback() {
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
        private GratitudeDao shelveDao;

        public PopulateDBAsyncTask(GratitudeDatabase database) {
            this.shelveDao = database.gratitudeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
