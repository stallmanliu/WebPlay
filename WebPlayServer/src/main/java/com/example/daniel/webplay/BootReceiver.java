package com.example.daniel.webplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if ( intent.getAction().equals(ACTION) )
        {
            //start PlayService service
            //Intent intent = new Intent( context, PlayService.class );
            //Log.d("BootReceiver", "WEBP: go to startservice(PlayService)" );
            context.startService(new Intent(context, HTTPDService.class));
        }

    }

}
