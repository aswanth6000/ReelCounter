package com.reelcounter

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.reelcounter.worker.DailyResetWorker
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupDailyResetWorker()
    }

    /**
     * Sets up a periodic work request to run daily at midnight.
     * Note: PeriodicWorkRequest minimum interval is 15 minutes,
     * so we schedule it to run every 24 hours with some flexibility.
     */
    private fun setupDailyResetWorker() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .build()

        val dailyResetRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reset_work",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyResetRequest
        )
    }
}
