package com.bitbytelab.cse226_ca3;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class VideoJob extends JobService {
    public static final String TAG = VideoJob.class.getSimpleName();
    MediaPlayer mp = MainActivity.mp;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Video Job started.");
        Toast.makeText(getBaseContext(),"Video Job Started.",Toast.LENGTH_SHORT).show();

        mp.reset();
        mp = MediaPlayer.create(getBaseContext(),R.raw.mini_world);
        mp.start();

        //jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"Video Job stopped.");
        Toast.makeText(getBaseContext(),"Video Job Stopped.",Toast.LENGTH_SHORT).show();

        return false;
    }
}
