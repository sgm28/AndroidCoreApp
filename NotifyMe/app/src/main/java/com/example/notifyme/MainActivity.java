package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Fields

    private Button button_notify;
    //Every notification channel must be associated with an ID that is unique within your package
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private static final int NOTIFICATION_ID = 0;

    //The Android system uses the NotificationManager class to deliver notifications to the user.
    private NotificationManager mNotifyManager;


    //Methods
    public void sendNotification() {

        NotificationCompat.Builder builder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, builder.build()); //STOP HERE

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
            notificationChannel.setDescription("Notificaiton from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }


    }

    private NotificationCompat.Builder getNotificationBuilder(){

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
        notifyBuilder.setContentTitle("You've been notified!");
        notifyBuilder.setContentText("This is your notification text.");
        notifyBuilder.setSmallIcon(R.drawable.ic_android);

        return notifyBuilder;
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
    }

}
