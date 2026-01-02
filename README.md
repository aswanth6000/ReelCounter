# ReelCounter 📊

A privacy-first Android app that tracks your Instagram Reel usage for digital wellbeing awareness. All data stays on your device - no internet, no cloud, no tracking.

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Min SDK](https://img.shields.io/badge/min%20SDK-26-green.svg)
![Target SDK](https://img.shields.io/badge/target%20SDK-36-orange.svg)

## 🌟 Features

- **Automatic Reel Detection**: Uses Android Accessibility API to detect when you scroll through Instagram Reels
- **Daily Tracking**: See how many reels you watch each day
- **Weekly Summaries**: View your weekly total and daily average
- **7-Day History**: Track your usage over the past week
- **Home Screen Widget**: Quick access to today's count on your home screen
- **Privacy First**: 
  - No internet permission
  - No data collection
  - No cloud storage
  - All data encrypted locally
  - Data never leaves your device

## 🔒 Privacy & Security

ReelCounter is designed with privacy as the top priority:

- ✅ **No Internet Access**: The app explicitly removes internet permission
- ✅ **Local Storage Only**: All data is stored on your device using EncryptedSharedPreferences
- ✅ **No Analytics**: No tracking, no logging, no data sharing
- ✅ **Open Source**: You can review the code to verify privacy claims
- ✅ **Accessibility Justification**: Uses Accessibility API solely for digital wellbeing awareness

## 📱 Screenshots

*Add screenshots here if available*

## 📥 Download

### Latest Release (v1.0)

**Download the APK:**

The release APK is located at: `app/release/app-release.apk`

For GitHub releases, upload the APK to the [Releases](../../releases) page and link it here.

**Direct Download:**
- [Download ReelCounter v1.0 APK](app/release/app-release.apk) *(if viewing on GitHub, check Releases page)*

### Installation Instructions

1. **Enable Unknown Sources** (if not already enabled):
   - Go to Settings → Security → Enable "Install unknown apps" or "Unknown sources"
   - Select your file manager/browser to allow installations

2. **Download the APK**:
   - Download `app-release.apk` from the releases section

3. **Install the App**:
   - Open the downloaded APK file
   - Tap "Install" when prompted
   - Wait for installation to complete

4. **Enable Accessibility Service**:
   - Open the ReelCounter app
   - Tap "Open Settings" when prompted
   - Find "ReelCounter" in the accessibility services list
   - Toggle it ON
   - Return to the app

5. **Start Tracking**:
   - The app will automatically start tracking when you scroll through Instagram Reels
   - Open Instagram and swipe through Reels to see the counter update

## 🚀 How to Use

1. **First Launch**:
   - The app will request accessibility permission
   - Follow the on-screen instructions to enable it

2. **View Your Stats**:
   - Today's count is displayed prominently
   - Weekly totals and averages are shown below
   - Scroll down to see the last 7 days breakdown

3. **Add Widget** (Optional):
   - Long-press on your home screen
   - Select "Widgets"
   - Find "ReelCounter"
   - Add to your home screen

4. **Reset Data** (if needed):
   - Scroll to the bottom of the dashboard
   - Tap the red "Reset All Data" button
   - Confirm the warning dialog

## 🛠️ Technical Details

### Requirements

- **Android Version**: Android 8.0 (API 26) or higher
- **Instagram App**: Must have Instagram installed
- **Accessibility Permission**: Required for Reel detection

### Architecture

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM-inspired
- **Storage**: EncryptedSharedPreferences
- **Background Tasks**: WorkManager

### How It Works

1. **Detection**: The app uses Android's AccessibilityService to monitor scroll events
2. **Filtering**: Only processes events from Instagram (`com.instagram.android`)
3. **Heuristics**: Uses UI tree inspection to identify Reels vs Feed/Explore
4. **Counting**: Increments count when a Reel scroll is detected (with cooldown to prevent double-counting)
5. **Storage**: Saves counts by date in encrypted local storage

### Detection Logic

The app uses heuristic-based detection to identify Reel scrolls:
- Checks for "Reels" text in UI tree
- Looks for Reel-specific UI elements (Like, Comment, Share buttons)
- Identifies full-screen vertical video layouts
- Uses scroll delta thresholds to filter significant scrolls

**Note**: Detection is heuristic-based and may not be 100% accurate. Instagram's UI changes frequently, so the app uses multiple indicators to improve accuracy.

## 🏗️ Building from Source

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK 26+

### Build Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ReelCounter.git
   cd ReelCounter
   ```

2. Open in Android Studio:
   - File → Open → Select the project directory

3. Sync Gradle:
   - Android Studio will automatically sync, or click "Sync Now"

4. Build the APK:
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - Or use: `./gradlew assembleRelease`

5. Find the APK:
   - Location: `app/build/outputs/apk/release/app-release.apk`

## 📋 Permissions

ReelCounter requires the following permissions:

- **Accessibility Service**: Required to detect Instagram Reel scrolls
  - This permission is clearly explained in the app
  - Used solely for digital wellbeing tracking
  - No data is collected or shared

**Explicitly Removed**:
- ❌ Internet Permission (removed for privacy)

## ⚠️ Limitations

- **Heuristic Detection**: Detection is based on UI inspection and may not be 100% accurate
- **Instagram Updates**: UI changes in Instagram may affect detection accuracy
- **Accessibility Required**: Must enable accessibility service for the app to work
- **No Exact Tracking**: The app provides usage estimation, not exact tracking

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Built with Android Jetpack Compose
- Uses EncryptedSharedPreferences for secure local storage
- Privacy-first design inspired by digital wellbeing principles

## 📧 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Check existing issues for solutions

## 🔄 Changelog

### Version 1.0 (Initial Release)
- ✅ Automatic Reel detection via Accessibility API
- ✅ Daily and weekly tracking
- ✅ 7-day history view
- ✅ Home screen widget
- ✅ Dark theme with green accents
- ✅ Privacy-first design
- ✅ Encrypted local storage
- ✅ Reset functionality

---

**Made with ❤️ for digital wellbeing awareness**

*Remember: This app is for awareness and self-reflection. Use it to understand your usage patterns, not to judge yourself.*

