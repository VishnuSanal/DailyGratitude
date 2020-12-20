package phone.vishnu.dailygratitude.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import phone.vishnu.dailygratitude.helper.NotificationHelper;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.createNotification();
    }
}
