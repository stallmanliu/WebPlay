package com.example.daniel.webplay;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
//import android.os.SystemClock;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.TextView;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static HTTPDService gHTTPDService;
    private static HTTPDService.WebPlayHTTPD gWebPlayHTTPD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gHTTPDService = HTTPDService.sGetHTTPDService();
        gWebPlayHTTPD = HTTPDService.sGetWebPlayHTTPD();

        startService();

        //servcie_display edittext
        final TextView service_display = (TextView) findViewById(R.id.service_display);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                service_display.setText("http service is " + checkServiceStatus());
            }
        }, 1000);

        Button start_service = (Button) findViewById(R.id.start_service);
        start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to start service
                startService();
                service_display.setText("go to start http service.");

                //check status
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        service_display.setText("http service is " + checkServiceStatus());
                    }
                }, 1000);
            }
        });

        Button stop_service = (Button) findViewById(R.id.stop_service);
        stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to stop service
                stopService();
                service_display.setText( "go to stop http service." );

                //check status
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        service_display.setText("http service is " + checkServiceStatus());
                    }
                }, 1000);

            }
        });

        Button service_status = (Button) findViewById(R.id.service_status);
        service_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service_display.setText("http service is " + checkServiceStatus());
            }
        });
    }

    //
    private void startService()
    {
        if ( null == gHTTPDService || null == gWebPlayHTTPD || !(gWebPlayHTTPD.isAlive())) {
            //startService( new Intent( "com.example.daniel.webplay.action.START"));
            //Log.d("TestIntentService.class", "MainActivity.onCreate() startService.START");
            //HTTPDService.startService();
            startService( new Intent( this, HTTPDService.class ) );
        }

    }

    //
    private void stopService()
    {
        //startService( new Intent( "com.example.daniel.webplay.action.STOP" ) );
        //Log.d("TestIntentService.class", "MainActivity.onCreate() startService.START");
        gHTTPDService.stopService();
    }

    //
    private String checkServiceStatus()
    {
        gHTTPDService = HTTPDService.sGetHTTPDService();
        gWebPlayHTTPD = HTTPDService.sGetWebPlayHTTPD();

        //Log.d("TestIntentService.class", "MainActivity.onResume(): status: gPlayService( " + gPlayService + " ), gPlayServiceHTTPD( " + gPlayServiceHTTPD + " ) ");
        if ( null == gHTTPDService || null == gWebPlayHTTPD || !(gWebPlayHTTPD.isAlive()) )
        {
            return HTTPDService.STATUS_OFFLINE;
            //PlayService is offline
        }
        else
        {
            return HTTPDService.STATUS_ONLINE;
            //PlayService is online
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("TestIntentService.class", "MainActivity.onResume()");

        TextView service_display = (TextView) findViewById(R.id.service_display);
        service_display.setText("http service is " + checkServiceStatus());
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}


