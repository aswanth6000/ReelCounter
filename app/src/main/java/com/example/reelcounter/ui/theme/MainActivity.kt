package com.reelcounter.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.reelcounter.data.ReelRepository
import com.reelcounter.utils.AccessibilityUtils

class MainActivity : ComponentActivity() {
    private val serviceName by lazy { "${packageName}.accessibility.ReelAccessibilityService" }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val repository = ReelRepository(this)
        
        setContent {
            var isServiceEnabled by remember { 
                mutableStateOf(
                    AccessibilityUtils.isAccessibilityServiceEnabled(this, serviceName)
                )
            }
            var checkKey by remember { mutableStateOf(0) }
            var showPrivacyPolicy by remember { mutableStateOf(false) }
            
            // Check permission on startup and when checkKey changes
            LaunchedEffect(checkKey) {
                isServiceEnabled = AccessibilityUtils.isAccessibilityServiceEnabled(this@MainActivity, serviceName)
            }
            
            ReelCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when {
                        showPrivacyPolicy -> {
                            PrivacyPolicyScreen(
                                onBack = { showPrivacyPolicy = false },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        isServiceEnabled -> {
                            DashboardScreen(
                                repository = repository,
                                onShowPrivacyPolicy = { showPrivacyPolicy = true },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        else -> {
                            PermissionRequestScreen(
                                onRequestPermission = {
                                    AccessibilityUtils.openAccessibilitySettings(this@MainActivity)
                                },
                                onCheckAgain = {
                                    checkKey++ // Trigger LaunchedEffect to re-check
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Permission check will happen automatically when user returns
        // The "Check Again" button in PermissionRequestScreen handles manual refresh
    }
}