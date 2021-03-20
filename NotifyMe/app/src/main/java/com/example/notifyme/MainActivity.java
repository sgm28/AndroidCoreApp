package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Useful reading resource:
    //https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-3-working-in-the-background/lesson-8-alarms-and-schedulers/8-1-c-notifications/8-1-c-notifications.html

    //Task 3 implementing an action button
    //action button are the actions you can when responding to a notification
    //i.e, replying to a text message without opening the app
    //action button requires:
    /*
        An icon,
        A label string
        A pendingIntent
     */
    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {

        }

        @Override //Based on the pending a intent, a certain function will execute
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_NOTIFICATION)) {
                updateNotification();
            }
            else if (intent.getAction().equals(ACTION_CANCEL_NOTIFICATION))
            {
                cancelNotification(NOTIFICATION_ID);
            }
        }
    }

    //Task 3
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";

    private static final String ACTION_CANCEL_NOTIFICATION = "com.example.ACTION_CANCEL_NOTIFICATION";

    NotificationReceiver mReceiver = new NotificationReceiver();


    //Fields

    private Button button_notify;

    //Every notification channel must be associated with an ID that is unique within your package
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    //Notification ID
    private static final int NOTIFICATION_ID = 0;

    //The Android system uses the NotificationManager class to deliver notifications to the user.
    private NotificationManager mNotifyManager;

    //Task 2
    private Button button_cancel;
    private Button button_update;


    //Methods
    public void sendNotification() {

        //Task 3
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        //The type of pending intent is a broadcast intent. A Notification receiver is required to list to this intent
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        //Task 2
        NotificationCompat.Builder builder = getNotificationBuilder();

        //Task 3
        //Adding an action button is similar to setting up the notification's default tap action: pass a PendingIntent to the addAction() method in the NotificationCompat.Builder class
        builder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);


        setNotificationButtonState(false, true, true);


        //Challenge exercise:
        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);

       //The cancelPendingIntent will be broadcast to the Notification Receiver when the user dismisses  the notification.
        builder.setDeleteIntent(cancelPendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());


    }

    //Each notification channel represents a type of notification.
    //When your app targets Android 8.0 (API level 26), to display notifications to your users you must implement at least one notification channel.
    // To display notifications on lower-end devices, you're not required to implement notification channels. However, it's good practice
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

            //Settings for notification manager
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }


    }
    //This method creates the notification
    private NotificationCompat.Builder getNotificationBuilder() {

        //Notification Intent to open up this app if it is close.
        Intent notificationIntent = new Intent(this, MainActivity.class);


        // When your app uses a PendingIntent, the system can launch the Activity in your app on your behalf.
        //PendingIntent will execute the notificationIntent intent
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
        notifyBuilder.setContentTitle("You've been notified!");
        notifyBuilder.setContentText("This is your notification text.");
        notifyBuilder.setSmallIcon(R.drawable.ic_android);

        // Set the intent that will fire when the user taps the notification
        //Clears the notification from the status bar when the users clicks on it
        notifyBuilder.setContentIntent(notificationPendingIntent).setAutoCancel(true);


        //1.5 Add priority and defaults to your notification for backward compatibility
        // require fro Android 7.1 and lower
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notifyBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;
    }

    //Task 2 Update or cancel the notification
    //Update the notification with a picture
    public void updateNotification() {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        //Using expanded layout by using the setStyle method.
        //The other view is the normal view

        //Comment this line out because of the homework
        //notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage).setBigContentTitle("Notification Updated!"));


       //Homework set notification  to use the inbox style
        notifyBuilder.setStyle(new NotificationCompat.InboxStyle()
        .addLine("Here is the first one")
                .addLine("This is the second one")
                .addLine("Yay this is the last one"));







        //Delivers the notification
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    //Cancel the notification
    public void cancelNotification(int NotificationID) {

        mNotifyManager.cancel(NotificationID);
        setNotificationButtonState(true, false, false);
    }

    //Toggle the buttons: update, cancel, Notify
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        createNotificationChannel();

        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel notification
                cancelNotification(NOTIFICATION_ID);
            }
        });
        //Task 2
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update notification
                updateNotification();
            }
        });

        setNotificationButtonState(true, false, false);

        //Task 3
       // However, using a PendingIntent delegates the responsibility of delivering the notification to the Android framework.
                registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
                registerReceiver(mReceiver, new IntentFilter(ACTION_CANCEL_NOTIFICATION));

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
