package com.bitbytelab.cse226_ca3;

import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity{

    public static final String TAG = MainActivity.class.getSimpleName();

    JobScheduler jobSchedulerNotification, jobSchedulerVideo;
    JobInfo jobInfoNotification, jobInfoVideo;

    public static MediaPlayer mp;
    public static NotificationManager notificationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mp = new MediaPlayer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "My Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btnNotificationCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotificationJob();
            }
        });
        findViewById(R.id.btnNotificationCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJobNotification();
            }
        });
        findViewById(R.id.btnPlayVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoJob();
            }
        });
        findViewById(R.id.btnStopVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJobVideo();
            }
        });

    }

    private void cancelJobNotification() {
        Log.d(TAG,"cancelJobNotification called.");
        if(jobSchedulerNotification != null){
            jobSchedulerNotification.cancelAll();
            jobSchedulerNotification = null;
            Toast.makeText(this,"Scheduled notification job cancelled.",Toast.LENGTH_SHORT).show();
        }
        notificationManager.cancelAll();
    }
    private void cancelJobVideo() {
        Log.d(TAG,"cancelJobVideo called.");
        if(jobSchedulerVideo != null){
            jobSchedulerVideo.cancelAll();
            jobSchedulerVideo = null;
            Toast.makeText(this,"Scheduled video job cancelled.",Toast.LENGTH_SHORT).show();
        }
        mp.stop();
        //Toast.makeText(this,"Scheduled video job not found.",Toast.LENGTH_SHORT).show();
    }

    public void startNotificationJob(){
        Log.d(TAG,"startNotificationJob called");
        jobSchedulerNotification = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this,NotificationJob.class);

        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(0,componentName);

        jobInfoBuilder.setRequiresCharging(true);
        jobInfoBuilder.setRequiresDeviceIdle(false);

        jobInfoNotification = jobInfoBuilder.build();
        jobSchedulerNotification.schedule(jobInfoNotification);
    }
    public void startVideoJob(){
        Log.d(TAG,"startVideoJob called");
        jobSchedulerVideo = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this,VideoJob.class);

        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(0,componentName);

        jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobInfoBuilder.setRequiresDeviceIdle(false);

        jobInfoVideo = jobInfoBuilder.build();
        jobSchedulerVideo.schedule(jobInfoVideo);
    }

}
