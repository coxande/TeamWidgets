package possebom.com.teamswidgets;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.text.format.DateUtils;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import possebom.com.teamswidgets.service.UpdateJobService;
import timber.log.Timber;

/**
 * Created by alexandre on 02/11/14.
 */
public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        context = getApplicationContext();

        final JobInfo job = new JobInfo.Builder(0, new ComponentName(context, UpdateJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(DateUtils.HOUR_IN_MILLIS/2)
                .setOverrideDeadline(DateUtils.HOUR_IN_MILLIS*2)
                .setPersisted(true)
                .build();

        JobScheduler.getInstance(this).schedule(job);
    }
}
