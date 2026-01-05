package com.reelcounter.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.reelcounter.data.ReelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker that runs daily to reset the reel count.
 * Scheduled to run at midnight each day.
 */
class DailyResetWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val repository = ReelRepository(applicationContext)
            // Note: We don't actually reset here because we're using date-based keys
            // The old counts remain for historical tracking
            // If you want to clear old data, you can add logic here
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
