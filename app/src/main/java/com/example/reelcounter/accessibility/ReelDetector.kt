package com.example.reelcounter.accessibility

import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Heuristic-based detector for Instagram Reels scrolling.
 * Uses UI tree inspection to identify Reels screen vs Feed/Explore/Profile.
 */
class ReelDetector {

    private val SCROLL_THRESHOLD = 150 // Minimum scroll delta for strong indicator (lowered for better detection)

    /**
     * Determines if a scroll event is a Reel scroll based on heuristics.
     * Uses a scoring system: if enough indicators are present, it's likely a Reel.
     * 
     * @param event The accessibility scroll event
     * @param root The root node of the active window
     * @return true if this appears to be a Reel scroll, false otherwise
     */
    fun isReelScroll(event: AccessibilityEvent, root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false

        var score = 0

        // Check scroll delta if available (API 28+)
        // Significant vertical scroll is a strong indicator of Reel swiping
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val scrollDelta = event.scrollDeltaY
            if (scrollDelta != null) {
                val absDelta = kotlin.math.abs(scrollDelta)
                if (absDelta >= SCROLL_THRESHOLD) {
                    score += 3 // Very strong indicator of reel swipe
                } else if (absDelta > 100) {
                    score += 2 // Moderate scroll, likely a reel
                } else if (absDelta > 50) {
                    score += 1 // Minor scroll, still possible
                }
            } else {
                // No scroll delta available, but we got a scroll event
                score += 1
            }
        } else {
            // For older APIs, assume scroll events are significant
            // Since we're already filtering for Instagram, scroll events are likely Reels
            score += 2
        }

        // Heuristic 1: Check for "Reels" text in the UI tree
        if (hasReelsText(root)) {
            score += 3 // Very strong indicator
        }

        // Heuristic 2: Check for Reel-specific UI elements (Like, Comment, Share buttons)
        if (hasReelActionButtons(root)) {
            score += 2 // Strong indicator
        }

        // Heuristic 3: Check for vertical scrollable layout (common in Reels)
        if (isScrollableVerticalLayout(root)) {
            score += 1
        }

        // Heuristic 4: Check for video-related UI elements
        if (hasVideoIndicators(root)) {
            score += 2 // Strong indicator
        }

        // Heuristic 5: Check for full-screen content (Reels are typically full-screen)
        if (isFullScreenContent(root)) {
            score += 1
        }

        // Need at least 2 points to consider it a Reel scroll
        // Lowered threshold to be more responsive to actual Reel swipes
        // If we have a significant scroll in Instagram, it's likely a Reel
        return score >= 2
    }

    /**
     * Checks if "Reels" text is present in the UI tree.
     * Also checks for variations like "reels", "reel", etc.
     */
    private fun hasReelsText(root: AccessibilityNodeInfo): Boolean {
        val text = root.text?.toString()?.lowercase() ?: ""
        val contentDescription = root.contentDescription?.toString()?.lowercase() ?: ""
        
        // Check for reel-related keywords
        val reelKeywords = listOf("reel", "reels", "short", "shorts")
        if (reelKeywords.any { text.contains(it) || contentDescription.contains(it) }) {
            return true
        }

        // Recursively check children (limit depth to avoid performance issues)
        return hasReelsTextRecursive(root, 0, 3)
    }

    private fun hasReelsTextRecursive(node: AccessibilityNodeInfo, depth: Int, maxDepth: Int): Boolean {
        if (depth >= maxDepth) return false

        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                val text = child.text?.toString()?.lowercase() ?: ""
                val contentDescription = child.contentDescription?.toString()?.lowercase() ?: ""
                val reelKeywords = listOf("reel", "reels", "short", "shorts")
                
                if (reelKeywords.any { text.contains(it) || contentDescription.contains(it) }) {
                    child.recycle()
                    return true
                }
                
                if (hasReelsTextRecursive(child, depth + 1, maxDepth)) {
                    child.recycle()
                    return true
                }
                child.recycle()
            }
        }
        return false
    }

    /**
     * Checks for presence of Like, Comment, Share buttons which are typical of Reels.
     * Instagram Reels have these action buttons visible on screen.
     */
    private fun hasReelActionButtons(root: AccessibilityNodeInfo): Boolean {
        val buttonCount = countReelActionButtons(root, 0, 4)
        // If we find 2+ action buttons (like, comment, share, etc.), it's likely a Reel
        return buttonCount >= 2
    }

    private fun countReelActionButtons(node: AccessibilityNodeInfo, depth: Int, maxDepth: Int): Int {
        if (depth >= maxDepth) return 0

        var count = 0
        val text = node.text?.toString()?.lowercase() ?: ""
        val contentDescription = node.contentDescription?.toString()?.lowercase() ?: ""
        val className = node.className?.toString()?.lowercase() ?: ""

        // Look for button-like elements with Reel-specific actions
        val reelKeywords = listOf("like", "comment", "share", "send", "save", "more", "follow")
        val hasReelKeyword = reelKeywords.any { keyword ->
            text.contains(keyword) || contentDescription.contains(keyword)
        }

        // Check if it's a button or clickable element
        val isInteractive = node.isClickable || node.isCheckable || 
                           className.contains("button") || className.contains("imagebutton") ||
                           className.contains("imageview") // Instagram often uses ImageViews as buttons

        if (hasReelKeyword && isInteractive) {
            count++
        }

        // Recursively check children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                count += countReelActionButtons(child, depth + 1, maxDepth)
                child.recycle()
            }
        }

        return count
    }

    /**
     * Checks if the layout is scrollable vertically (common in Reels).
     */
    private fun isScrollableVerticalLayout(root: AccessibilityNodeInfo): Boolean {
        // Reels are scrollable vertically
        if (root.isScrollable) {
            return true
        }

        // Check children for scrollable views
        for (i in 0 until root.childCount) {
            val child = root.getChild(i)
            if (child != null) {
                if (child.isScrollable) {
                    child.recycle()
                    return true
                }
                child.recycle()
            }
        }
        return false
    }

    /**
     * Checks for video-related UI elements in the tree.
     */
    private fun hasVideoIndicators(root: AccessibilityNodeInfo): Boolean {
        return hasVideoIndicatorsRecursive(root, 0, 4)
    }

    private fun hasVideoIndicatorsRecursive(node: AccessibilityNodeInfo, depth: Int, maxDepth: Int): Boolean {
        if (depth >= maxDepth) return false

        val className = node.className?.toString()?.lowercase() ?: ""
        val contentDescription = node.contentDescription?.toString()?.lowercase() ?: ""
        val text = node.text?.toString()?.lowercase() ?: ""

        // Look for video-related classes and content
        val videoKeywords = listOf("video", "player", "media", "view", "surface")
        if (videoKeywords.any { className.contains(it) || contentDescription.contains(it) || text.contains(it) }) {
            return true
        }

        // Check children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                if (hasVideoIndicatorsRecursive(child, depth + 1, maxDepth)) {
                    child.recycle()
                    return true
                }
                child.recycle()
            }
        }
        return false
    }

    /**
     * Checks if the content appears to be full-screen (Reels are typically full-screen).
     */
    private fun isFullScreenContent(root: AccessibilityNodeInfo): Boolean {
        val bounds = android.graphics.Rect()
        root.getBoundsInScreen(bounds)
        
        // Get screen dimensions (approximate - Reels typically take most of the screen)
        // We check if the bounds are large (indicating full-screen or near full-screen content)
        val width = bounds.width()
        val height = bounds.height()
        
        // If the view takes up a significant portion of the screen, it might be a Reel
        // This is a rough heuristic - Reels are usually 80%+ of screen height
        // We'll consider it full-screen if height > 1000px (typical phone screen is 1920-2400px tall)
        return height > 1000
    }
}
