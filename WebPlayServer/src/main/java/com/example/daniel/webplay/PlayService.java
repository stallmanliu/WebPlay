package com.example.daniel.webplay;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class PlayService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_PLAY = "com.example.daniel.webplay.action.PLAY";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.daniel.webplay.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.daniel.webplay.extra.PARAM2";



    public PlayService() {

        super("PlayService");

    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*
    public static void startActionPlay(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    */

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*
    public static void startActionStart(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(ACTION_START);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    */


    /*
    public static void startActionStop(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(ACTION_STOP);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    */

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.d("TestIntentService.class", "Incoming a new intent in onHandleIntent()");
        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                //Log.d("TestIntentService.class", "Go to hendle a new ACTION_PLAY intent in onHandleIntent()");
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionPlay(param1, param2);
            }

            /*
            else if (ACTION_START.equals(action)) {
                //Log.d("TestIntentService.class", "Go to hendle a new ACTION_START intent in onHandleIntent()");
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                final String param1 = "";
                final String param2 = "";
                handleActionStart(param1, param2);
            } else if (ACTION_STOP.equals(action))
            {
                //Log.d("TestIntentService.class", "Go to hendle a new ACTION_STOP intent in onHandleIntent()");
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                final String param1 = "";
                final String param2 = "";
                handleActionStop(param1, param2);
            }
            */
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPlay(String param1, String param2) {
        // TODO: Handle action Foo
        //throw new UnsupportedOperationException("Not yet implemented");

        String url_play = param1;
        Intent intent = new Intent( "com.ucweb.intent.action.OPEN_URL", Uri.parse( url_play ) );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStart(String param1, String param2) {
        // TODO: Handle action Baz
        //throw new UnsupportedOperationException("Not yet implemented");

        //start smHTTPD
        //startPlayServiceHTTPD();
    }

    private void handleActionStop(String param1, String param2) {
        // TODO: Handle action Baz
        //throw new UnsupportedOperationException("Not yet implemented");

        /*
        if ( null != smHTTPD )
        {
            smHTTPD.closeAllConnections();
            smHTTPD.stop();
        }
        */
    }

    public void onCreate()
    {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
        //Log.d("TestIntentService.class", "PlayService.onStart()");

        //start smHTTPD
        //startPlayServiceHTTPD();

        //start notification and set this service to foreground service
        //Notification notification = new Notification.Builder( this )
                //.setSmallIcon( R.drawable.ic_notification )
          //      .setContentTitle( getString(R.string.app_name) )
                //.setContentText( contentText )
                //.setContentIntent( pendingIntent ).build();
        /* deprecated due to old version of api
        Notification notification = new Notification( R.drawable.icon, "WebPlay comes", System.currentTimeMillis() );
        notification.setLatestEventInfo( this, "WebPlay", "WebPlay is running", pendingIntent );
        */

        /*
        Intent notificationIntent = new Intent( this, MainActivity.class );
        notificationIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //new version of api
        Notification notification = ( ( new NotificationCompat.Builder( this ) ).setSmallIcon( R.drawable.webplay_small_icon )
                                    .setContentTitle( "WebPlay comes" )
                                    .setContentText( "WebPlay" )
                                    .setContentIntent( pendingIntent ) ).build();
        Log.d( "Notice", "notification:" + notification );
        startForeground( 1, notification);
        */


    }


}

