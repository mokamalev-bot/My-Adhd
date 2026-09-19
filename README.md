# My ADHD

My ADHD is a multilingual Android support app for time management, task organization, focus, routines, and behavioral self-management.

> This app provides educational and organizational support. It does not diagnose ADHD, prescribe medication, or replace a qualified healthcare professional.

## Phase 2

- Kotlin + Jetpack Compose + Material 3
- English and Arabic localization with RTL support
- First-run onboarding and language selection
- Local task storage with Room
- Add, complete, and delete tasks
- Focus timer with selectable durations
- Bottom navigation: Home, Tasks, Focus, Profile
- No API keys or medical decisions in the Android client

## Build

1. Open the repository in Android Studio (JDK 17).
2. Allow Gradle to sync.
3. Run the `app` configuration on an Android device or emulator.
4. Build a debug APK with `./gradlew assembleDebug`.

## Safety

The application is a support and organization tool. It must not diagnose ADHD, recommend or prescribe medication, claim to cure ADHD, or replace professional care. Future AI features must use a secure backend gateway and safety validation.
