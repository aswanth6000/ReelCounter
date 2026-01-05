package com.reelcounter.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocalStore(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "reel_store",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    /**
     * Gets today's reel count.
     */
    fun getTodayCount(): Int {
        val today = getTodayKey()
        return prefs.getInt(today, 0)
    }

    /**
     * Increments today's reel count by 1.
     */
    fun incrementToday() {
        val today = getTodayKey()
        val current = prefs.getInt(today, 0)
        prefs.edit().putInt(today, current + 1).apply()
    }

    /**
     * Sets today's count to a specific value.
     */
    fun setTodayCount(value: Int) {
        val today = getTodayKey()
        prefs.edit().putInt(today, value).apply()
    }

    /**
     * Resets today's count to 0.
     */
    fun resetToday() {
        setTodayCount(0)
    }

    /**
     * Gets the count for a specific date.
     */
    fun getCountForDate(date: Date): Int {
        val key = dateFormat.format(date)
        return prefs.getInt(key, 0)
    }

    /**
     * Gets daily counts for the past N days (including today).
     */
    fun getDailyCountsForPastDays(days: Int): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        val calendar = java.util.Calendar.getInstance()
        
        for (i in 0 until days) {
            val date = calendar.time
            val key = dateFormat.format(date)
            result[key] = prefs.getInt(key, 0)
            calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)
        }
        
        return result
    }

    /**
     * Gets weekly summary (sum of counts for the past 7 days).
     */
    fun getWeeklyTotal(): Int {
        val dailyCounts = getDailyCountsForPastDays(7)
        return dailyCounts.values.sum()
    }

    /**
     * Gets average daily count for the past 7 days.
     */
    fun getWeeklyAverage(): Double {
        val dailyCounts = getDailyCountsForPastDays(7)
        val total = dailyCounts.values.sum()
        return if (dailyCounts.isNotEmpty()) total.toDouble() / dailyCounts.size else 0.0
    }

    /**
     * Clears all historical data (keeps only today).
     */
    fun clearAllHistory() {
        val today = getTodayKey()
        val editor = prefs.edit()
        
        // Get all keys and remove non-today entries
        val allKeys = prefs.all.keys
        for (key in allKeys) {
            if (key != today && key.startsWith("20")) { // Date keys start with year
                editor.remove(key)
            }
        }
        editor.apply()
    }
    
    /**
     * Clears ALL data including today's count.
     */
    fun clearAllData() {
        val editor = prefs.edit()
        
        // Get all keys and remove all date entries
        val allKeys = prefs.all.keys
        for (key in allKeys) {
            if (key.startsWith("20")) { // Date keys start with year
                editor.remove(key)
            }
        }
        editor.apply()
    }

    /**
     * Gets the storage key for today's date.
     */
    private fun getTodayKey(): String {
        return dateFormat.format(Date())
    }

    // Legacy methods for backward compatibility
    @Deprecated("Use getTodayCount() instead", ReplaceWith("getTodayCount()"))
    fun getCount(): Int = getTodayCount()

    @Deprecated("Use setTodayCount() instead", ReplaceWith("setTodayCount(value)"))
    fun setCount(value: Int) {
        setTodayCount(value)
    }

    @Deprecated("Use resetToday() instead", ReplaceWith("resetToday()"))
    fun reset() {
        resetToday()
    }
}
