package com.catchingnow.tinyclipboards;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by heruoxin on 15/1/19.
 */
public class SyncJobService extends JobService {
    private final static String PACKAGE_NAME = "com.catchingnow.tinyclipboards";
    private final static String STORAGE_DATE = "pref_save_dates";
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private Storage db;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        preference = PreferenceManager.getDefaultSharedPreferences(this);
        float days = (float) Integer.parseInt(preference.getString(STORAGE_DATE, "7"));
        db = new Storage(this.getBaseContext());
        db.deleteClipHistoryBefore(days);
        Log.v(PACKAGE_NAME,"Start JobScheduler, the days is"+days);
        Intent i = new Intent(this, CBWatcherService.class);
        this.startService(i);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
