package com.reelcounter.accessibility

import android.accessibilityservice.AccessibilityService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.reelcounter.data.ReelRepository
import com.reelcounter.widget.ReelCounterWidget

class ReelAccessibilityService : AccessibilityService() {

    private val detector = ReelDetector()
    private lateinit var repo: ReelRepository

    private var lastScrollTime = 0L
    private val SCROLL_COOLDOWN_MS = 800L

    companion object {
        private const val TAG = "ReelAccessibilityService"
        private const val DEBUG = true // Set to false in production
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        repo = ReelRepository(this)
        if (DEBUG) Log.d(TAG, "Accessibility service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Filter for Instagram package
        val packageName = event.packageName?.toString()
        if (packageName != "com.instagram.android") return

        // Only process scroll events
        if (event.eventType != AccessibilityEvent.TYPE_VIEW_SCROLLED) return

        val now = System.currentTimeMillis()
        
        // Cooldown to prevent double counting
        if (now - lastScrollTime < SCROLL_COOLDOWN_MS) {
            if (DEBUG) Log.d(TAG, "Scroll event ignored due to cooldown")
            return
        }

        // Get the root node to inspect UI tree
        val root = rootInActiveWindow
        if (root == null) {
            if (DEBUG) Log.d(TAG, "No root window available")
            return
        }

        // Check if this is a Reel scroll
        val isReel = detector.isReelScroll(event, root)
        
        if (DEBUG) {
            Log.d(TAG, "Scroll event detected - isReel: $isReel, package: $packageName")
        }

        if (isReel) {
            repo.increment()
            lastScrollTime = now
            val currentCount = repo.getTodayCount()
            if (DEBUG) Log.d(TAG, "Reel counted! Total today: $currentCount")
            
            // Update widget
            updateWidget()
        }

        // Recycle the root node
        root.recycle()
    }

    override fun onInterrupt() {
        if (DEBUG) Log.d(TAG, "Accessibility service interrupted")
    }
    
    /**
     * Updates all widget instances when reel count changes.
     */
    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(this, ReelCounterWidget::class.java)
        )
        if (widgetIds.isNotEmpty()) {
            ReelCounterWidget.updateAppWidget(this, appWidgetManager, widgetIds[0])
            // Update all widget instances
            for (widgetId in widgetIds) {
                ReelCounterWidget.updateAppWidget(this, appWidgetManager, widgetId)
            }
        }
    }
}
