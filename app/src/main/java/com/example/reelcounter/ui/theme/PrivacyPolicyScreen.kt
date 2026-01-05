package com.reelcounter.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reelcounter.ui.theme.BrightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
                        fontWeight = FontWeight.Bold,
                        color = BrightGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text(
                            text = "←",
                            style = MaterialTheme.typography.titleLarge,
                            color = BrightGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Last Updated
            Text(
                text = "Last Updated: January 2025",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Introduction
            SectionTitle("Introduction")
            SectionText(
                "ReelCounter (\"we\", \"our\", or \"the app\") is committed to protecting your privacy. " +
                "This Privacy Policy explains how we handle information when you use our Android application."
            )

            SectionTitle("Our Privacy-First Commitment")
            SectionText(
                "ReelCounter is designed with privacy as the core principle. " +
                "We believe your data belongs to you and should never leave your device."
            )

            // Information We Collect
            SectionTitle("Information We Collect")
            SubSectionTitle("We Collect NO Information")
            SectionText(
                "ReelCounter does NOT collect, transmit, or store any personal information. Specifically:"
            )
            
            PrivacyItem("No Personal Data", "We do not collect names, email addresses, phone numbers, or any personally identifiable information")
            PrivacyItem("No Usage Analytics", "We do not track how you use the app")
            PrivacyItem("No Device Information", "We do not collect device IDs, location data, or device specifications")
            PrivacyItem("No Instagram Data", "We do not access, store, or transmit any Instagram content, messages, or account information")
            PrivacyItem("No Network Activity", "The app has no internet permission and cannot send data anywhere")

            SubSectionTitle("What We Store Locally")
            SectionText("The only data stored by ReelCounter is:")
            BulletPoint("Reel Count Data: Daily counts of Instagram Reels you've viewed")
            BulletPoint("Storage Location: All data is stored locally on your device using Android's EncryptedSharedPreferences")
            BulletPoint("Encryption: All stored data is encrypted using AES-256 encryption")
            BulletPoint("No Cloud Sync: Data never leaves your device")

            // How We Use Information
            SectionTitle("How We Use Information")
            SectionText(
                "Since we collect no information, there is nothing to use, share, or sell.\n\n" +
                "The locally stored reel count data is used solely to:\n" +
                "• Display your daily reel count\n" +
                "• Calculate weekly totals and averages\n" +
                "• Show your 7-day usage history\n\n" +
                "This data is processed entirely on your device and never transmitted anywhere."
            )

            // Data Storage and Security
            SectionTitle("Data Storage and Security")
            SubSectionTitle("Local Storage Only")
            BulletPoint("All data is stored on your device using Android's EncryptedSharedPreferences")
            BulletPoint("Data is encrypted with AES-256-GCM encryption")
            BulletPoint("No data is stored on external servers or cloud services")
            BulletPoint("No backups are created outside your device")

            SubSectionTitle("Data Deletion")
            SectionText("You can delete all data at any time by:")
            BulletPoint("Using the \"Reset All Data\" button in the app")
            BulletPoint("Uninstalling the app (which removes all stored data)")

            // Permissions
            SectionTitle("Permissions")
            SubSectionTitle("Accessibility Service Permission")
            SectionText(
                "ReelCounter requires Accessibility Service permission to function. " +
                "This permission is used exclusively for:\n" +
                "• Detecting scroll events in the Instagram app\n" +
                "• Identifying when you view Instagram Reels\n" +
                "• Counting reel views for digital wellbeing awareness"
            )
            
            SectionText("What we do NOT do with this permission:")
            PrivacyItem("We do not read your messages, posts, or any Instagram content")
            PrivacyItem("We do not interact with Instagram on your behalf")
            PrivacyItem("We do not capture screenshots or record your screen")
            PrivacyItem("We do not access any other apps or services")
            PrivacyItem("We do not transmit any accessibility data")

            SubSectionTitle("No Internet Permission")
            SectionText(
                "ReelCounter explicitly removes internet permission from the app. " +
                "This means the app cannot access the internet, send data to any server, " +
                "receive data from any server, or use network APIs. " +
                "This is a core privacy feature of the app."
            )

            // Data Sharing
            SectionTitle("Data Sharing")
            SectionText(
                "We do NOT share your data with anyone.\n\n" +
                "• No third-party services\n" +
                "• No analytics providers\n" +
                "• No advertising networks\n" +
                "• No data brokers\n" +
                "• No government agencies (unless required by law, but we have no data to provide)\n\n" +
                "Since we collect no data and store everything locally, there is nothing to share."
            )

            // Children's Privacy
            SectionTitle("Children's Privacy")
            SectionText(
                "ReelCounter does not knowingly collect information from children under 13. " +
                "Since we collect no information at all, this is not applicable. " +
                "However, if you are under 13, please get parental permission before using the app."
            )

            // Your Rights
            SectionTitle("Your Rights")
            SectionText("You have complete control over your data:")
            BulletPoint("Access: View your data anytime in the app")
            BulletPoint("Delete: Delete all data using the \"Reset All Data\" button")
            BulletPoint("Control: Uninstall the app to remove all data")
            BulletPoint("Transparency: Review this privacy policy and our open-source code")

            // Third-Party Services
            SectionTitle("Third-Party Services")
            SectionText(
                "ReelCounter does not use any third-party services, SDKs, or libraries that collect data. " +
                "The app uses only Android system libraries, Jetpack Compose, EncryptedSharedPreferences, " +
                "and WorkManager. None of these transmit data outside your device."
            )

            // Summary
            SectionTitle("Summary")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "In simple terms:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = BrightGreen,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    BulletPoint("Your data stays on your device")
                    BulletPoint("Everything is encrypted locally")
                    BulletPoint("No internet access = no data transmission")
                    BulletPoint("No tracking, no analytics, no collection")
                    BulletPoint("You control your data completely")
                    BulletPoint("Open source = you can verify everything")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ReelCounter respects your privacy because privacy is a fundamental right.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = BrightGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Effective Date: January 2025\nVersion: 1.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = BrightGreen,
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun SubSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun SectionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun PrivacyItem(title: String, description: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "• ",
            style = MaterialTheme.typography.bodyLarge,
            color = BrightGreen,
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "• ",
            style = MaterialTheme.typography.bodyLarge,
            color = BrightGreen,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

