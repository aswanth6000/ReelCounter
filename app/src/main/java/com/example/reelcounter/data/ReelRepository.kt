package com.example.reelcounter.data

import android.content.Context
import java.util.Date

class ReelRepository(context: Context) {

    private val store = LocalStore(context)

    /**
     * Increments today's reel count by 1.
     */
    fun increment() {
        store.incrementToday()
    }

    /**
     * Gets today's reel count.
     */
    fun getTodayCount(): Int {
        return store.getTodayCount()
    }

    /**
     * Resets today's count to 0.
     */
    fun resetToday() {
        store.resetToday()
    }

    /**
     * Gets the count for a specific date.
     */
    fun getCountForDate(date: Date): Int {
        return store.getCountForDate(date)
    }

    /**
     * Gets daily counts for the past N days (including today).
     */
    fun getDailyCountsForPastDays(days: Int): Map<String, Int> {
        return store.getDailyCountsForPastDays(days)
    }

    /**
     * Gets weekly total (sum of counts for the past 7 days).
     */
    fun getWeeklyTotal(): Int {
        return store.getWeeklyTotal()
    }

    /**
     * Gets weekly average (average daily count for the past 7 days).
     */
    fun getWeeklyAverage(): Double {
        return store.getWeeklyAverage()
    }

    /**
     * Clears all historical data.
     */
    fun clearAllHistory() {
        store.clearAllHistory()
    }
    
    /**
     * Clears ALL data including today's count.
     */
    fun clearAllData() {
        store.clearAllData()
    }
}
