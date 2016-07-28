package com.example.daniel.webplay;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPDService extends Service {

    public static final String ACTION_START = "com.example.daniel.webplay.action.START";
    public static final String ACTION_STOP = "com.example.daniel.webplay.action.STOP";
    //
    public static final String STATUS_ONLINE = "ONLINE";
    public static final String STATUS_OFFLINE = "OFFLINE";

    //
    private static HTTPDService smHTTPDService;
    private static WebPlayHTTPD smHTTPD;

    public HTTPDService() {

        smHTTPDService = this;
        //smHTTPD = null;
    }

    //
    public static HTTPDService sGetHTTPDService()
    {
        return smHTTPDService;
    }

    //
    public static WebPlayHTTPD sGetWebPlayHTTPD()
    {
        return smHTTPD;
    }

    //
    public void startService()
    {
        if ( null == smHTTPDService )
        {
            smHTTPDService = new HTTPDService();
        }

        if ( null != smHTTPDService )
        {
            smHTTPDService.startWebPlayHTTPD();
        }

    }

    //
    public void stopService()
    {
        if ( null != smHTTPD )
        {
            smHTTPD.closeAllConnections();
            smHTTPD.stop();
        }

        smHTTPD = null;

        stopForeground( true );

        smHTTPDService = null;

        stopSelf();
    }

    //
    private void startWebPlayHTTPD()
    {

        if ( null == smHTTPD )
        {
            try {
                smHTTPD = new WebPlayHTTPD();
            }
            catch ( IOException ioe ) {
                Log.d( "HTTPDService", "WebPlay: IOException when new WebPlayHTTPD." );
            }
        }

        if ( ( null != smHTTPD ) && !(smHTTPD.isAlive()) )
        {
            try {
                //Log.d("TestIntentService.class", "Go to start smHTTPD PlayServiceHTTPD");
                smHTTPD.start();
            } catch (IOException ioe) {
                //return newFixedLengthResponse( msg + "Error </body></html>\n" );
                Log.d( "HTTPDService", "WebPlay: IOException when smHTTPD.start()." );
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    //
    public void onCreate()
    {
        super.onCreate();

        //smHTTPDService = this;

        //startWebPlayHTTPD();
        startService();


        //set foreground service with notification

        Intent notificationIntent = new Intent( this, MainActivity.class );
        //Intent notificationIntent = new Intent( "com.example.daniel.webplay.action.PLAY" );
        //notificationIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        //Log.d( "WebPlay", "notificationIntent:" + notificationIntent );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Log.d( "WebPlay", "pendingIntent:" + pendingIntent );
        //new version of api
        /*
        Notification notification = ( ( new NotificationCompat.Builder( this ) ).setSmallIcon( R.drawable.webplay_small_icon )
                .setContentTitle( "WebPlay comes" )
                .setContentText( "WebPlay" )
                .setContentIntent( pendingIntent ) ).build();
        */

        Notification notification = new NotificationCompat.Builder( this )
                .setContentTitle( "WebPlay" )
                .setContentText( "WebPlay is running" )
                .setSmallIcon( R.drawable.webplay_small_icon )
                .setContentIntent( pendingIntent )
                .build();

        //Log.d( "WebPlay", "notification:" + notification );

        //NotificationManager notificationManager = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        //notificationManager.notify( 1, notification );
        startForeground( 1, notification);

    }


    class WebPlayHTTPD extends NanoHTTPD {

        private final static int PORT = 8080;

        public WebPlayHTTPD() throws IOException {
            super(PORT);
            start();
            //Log.d("TestIntentService.class", "\nRunning! Point your browers to http://localhost:8080/ \n");
        }

        @Override
        public Response serve(IHTTPSession session) {
            //Log.d("TestIntentService.class", "Go to process incoming request session");

            String msg = "<html><body><h1>WebPlayServer</h1>\n";

            Map<String, String> files = new HashMap<String, String>();
            Method method = session.getMethod();
            if ( Method.PUT.equals(method) || Method.POST.equals(method) ) {
                try {
                    session.parseBody(files);
                } catch (IOException ioe) {
                    return newFixedLengthResponse( msg + "Error </body></html>\n" );
                    //return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
                } catch (ResponseException re) {
                    return newFixedLengthResponse( msg + "Error </body></html>\n" );
                    //return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
                }
            }

            //trigger play action
            if ( Method.POST.equals(method) ) {
                //Log.d("TestIntentService.class", "A post request");
                String url_play = session.getParms().get("url_play");

                msg += "<p>Go to play url: </p>";
                msg += "<p> " + url_play + " !</p>";
                msg += "<p> </p>";

                //send an intent to start a sub intentservice
                Intent intent = new Intent( PlayService.ACTION_PLAY );
                intent.putExtra( PlayService.EXTRA_PARAM1, url_play );
                //Log.d("TestIntentService.class", "Go to send a intent to PlayService");
                startService(intent);
            }

            msg += "<p>Please input url to play: </p>";
            msg += "<form method=\"post\" >";
            msg += "<input type=\"text\" name=\"url_play\"  size=\"180\" /><br />";
            msg += "<p> </p>";
            msg += "<input type=\"submit\" value=\"submit\" /><br />";
            msg += "</form>";

            return newFixedLengthResponse( msg + "</body></html>\n" );
        }
    }

}


