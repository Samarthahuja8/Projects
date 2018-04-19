package com.app.farmerswikki.service;

/**
 * Created by ORBITWS19 on 18-Jul-2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.cloud_messing.pojo.NotificationResponse;
import com.app.farmerswikki.model.cloud_messing.view.fragment.ViewPagerNotificationHandler;
import com.app.farmerswikki.model.dashboard.view.activity.DashBoardActivity;
import com.app.farmerswikki.util.custom.OnServiceCustomDialogOpen;
import com.app.farmerswikki.util.database_operations.DatabaseHandler;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.google.gson.GsonBuilder;


import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private boolean flag = false;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        try {
            if (remoteMessage.getData().size() > 0) {

                Map<String, String> map = remoteMessage.getData();
                JSONObject jsonObject = new JSONObject();
                if (map.get("imageNotificationUrl") != null) {
                    jsonObject.put("imageNotificationUrl", map.get("imageNotificationUrl"));
                }
                if (map.get("notificationHeading") != null) {
                    jsonObject.put("notificationHeading", map.get("notificationHeading"));
                }
                if (map.get("notificationBody") != null) {
                    jsonObject.put("notificationBody", map.get("notificationBody"));
                }
                if (map.get("notificationId") != null) {
                    jsonObject.put("notificationId", map.get("notificationId"));
                }
                NotificationResponse notificationResponse = new GsonBuilder().create().fromJson(jsonObject.toString(), NotificationResponse.class);
                sendNotification(notificationResponse.notificationHeading, notificationResponse);

            }




          /*  Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

*/


            else if (remoteMessage.getNotification() != null) {

                String notificationHeading = remoteMessage.getNotification().getTitle(); //get title
                String notificationBody = remoteMessage.getNotification().getBody(); //get message
                String click_action = remoteMessage.getNotification().getClickAction(); //get click_action
                JSONObject jsonObject = new JSONObject();
                Log.d(TAG, "Message Notification Title: " + notificationHeading);
                Log.d(TAG, "Message Notification Body: " + notificationBody);
                Log.d(TAG, "Message Notification click_action: " + click_action);

                jsonObject.put("imageNotificationUrl", click_action);
                jsonObject.put("notificationHeading", notificationHeading);
                jsonObject.put("notificationBody", notificationBody);

                NotificationResponse notificationResponse = new GsonBuilder().create().fromJson(jsonObject.toString(), NotificationResponse.class);
                sendNotification(remoteMessage.getNotification().getBody(), notificationResponse);
            }
        } catch (Exception e) {

            Log.d("Exception", "Exception in JsonObject creation.");
        }
    }


    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Farmers Wikki News Updates.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon1);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(String messageBody, NotificationResponse notificationResponse) {


        //Managing Database for Notification

        DatabaseHandler db = new DatabaseHandler(this);
        if (db.getSingleNotification(Integer.parseInt(notificationResponse.getNotificationId())) == null) {
            db.addNotification(notificationResponse);
        } else {
            db.updateContact(notificationResponse);
        }

        /*to find either user is already in notification tab or not*/





        /*End of to find either user is already in notification tab or not**/

            /**********************************************************************************************************************/


            Intent intent = new Intent(this, DashBoardActivity.class);
            intent.putExtra("isNotificationGenerated", "Y");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle("Farmers Wikki News Updates.")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.app_icon1);
            } else {
                notificationBuilder.setSmallIcon(R.drawable.app_icon);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


           if(ViewPagerNotificationHandler.isNotificationFragmentActive){
              Intent i=new Intent(this,OnServiceCustomDialogOpen.class);
               startActivity(i);

           }

        }



}
