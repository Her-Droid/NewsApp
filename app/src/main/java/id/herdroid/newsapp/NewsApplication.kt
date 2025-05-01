package id.herdroid.newsapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import id.herdroid.newsapp.utils.SyncWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class NewsApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(3, TimeUnit.HOURS).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "sync_articles",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .build()
}
