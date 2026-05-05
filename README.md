# Full Restore
> Full Restore is a Jetpack Compose Android app for practicing Pokemon type matchups. It delivers a fast, single-screen quiz loop with streak tracking, multiple quiz modes, and a settings tab with a persistent light mode toggle.

## Highlights
- Type effectiveness quiz with instant feedback and streak tracking.
- Three quiz modes: single defender, dual defender, or random defenders.
- Bottom navigation with a dedicated Settings tab.
- Light mode toggle persists across restarts using DataStore preferences.
- Custom Material 3 color system and type badges for clear visual cues.

## Tech Stack
- Kotlin + Jetpack Compose
- Material 3 (Compose)
- AndroidX Lifecycle + ViewModel
- DataStore Preferences
- Gradle Version Catalog

## Getting Started

### Requirements
- Android Studio (Ladybug or newer recommended)
- JDK 17 (recommended by Android Studio)
- Android SDK with API 26+ (minSdk = 26)

### Run in Android Studio
1. Open the project folder.
2. Let Gradle sync finish.
3. Select a device or emulator.
4. Run the `app` configuration.

### Run from CLI
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Gameplay
- Each question picks an attacker and one or two defenders.
- Choose the correct damage modifier (4x, 2x, 1x, 0.5x, 0.25x, immune).
- Your streak increases with correct answers and resets on a miss.

## Settings
- Navigate to the Settings tab in the bottom bar.
- Enable Light mode to switch from the default Dark palette.
- The setting is persisted via DataStore and restored on app launch.

## Project Structure
```
app/src/main/kotlin/dev/timothyspriegade/fullrestore/
  core/
    common/          App constants
    designsystem/    Theme, colors, typography, shared UI components
    util/            Type chart logic
  domain/
    model/           Domain models (types, effectiveness, quiz mode)
    usecase/         Quiz generation and effectiveness calculation
  features/
    settings/        Settings UI + persistence
    typequiz/        Quiz UI + ViewModel
```

## Design System
- Light and dark palettes are defined in `app/src/main/kotlin/dev/timothyspriegade/fullrestore/core/designsystem/Theme.kt`.
- Colors live in `app/src/main/kotlin/dev/timothyspriegade/fullrestore/core/designsystem/Color.kt`.

## Persistence
- Settings are stored using DataStore Preferences in
  `app/src/main/kotlin/dev/timothyspriegade/fullrestore/features/settings/data/UserSettingsStore.kt`.

## Build Notes
- The app uses the Compose BOM pinned in `gradle/libs.versions.toml`.
- No network calls or external APIs are required.
