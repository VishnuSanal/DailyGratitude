package phone.vishnu.dailygratitude.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import phone.vishnu.dailygratitude.R;
import phone.vishnu.dailygratitude.activity.MainActivity;

public class NotificationHelper {

    private static final String NOTIFICATION_CHANNEL_ID = "phone.vishnu.dailygratitude";
    private static final String NOTIFICATION_CHANNEL_NAME = "DailyGratitudeNotificationChannel";
    private static final int NOTIFICATION_REQUEST_CODE = 2222;
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotification() {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_daily_gratitude)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mBuilder.setContentTitle(context.getString(R.string.app_name));
        mBuilder.setContentText("Set Today's Gratitude");

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_REQUEST_CODE, mBuilder.build());

    }
}