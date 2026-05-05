# <img src="app/src/main/res/assets/Full Restore App Icon.png" width="40" height="40" align="center"> Full Restore

> Full Restore is a Jetpack Compose Android app for practicing Pokemon type matchups and is supposed
> to work as a handy toobox for competetive players.

## Motivation

I have been a Pokémon Competitive player on and off since 2016, but due to Pokémon Champions,
I have just switched formats from Singles to Doubles/VGC. After a two-year break,
I came back to competitive play and realized that my knowledge was rusty, especially with regard to
type matchups and speed tiers.

So I looked for apps and tools to help me learn these things and maybe even look them up while being
in Team Select or on the go.
I found a couple of tools, but most of them were either A. only online or B. full of ads,
which I found annoying. Then I remembered that I am a software developer and our favourite saying
is 'I'll just do it myself',
so I thought of this little toolbox for VGC Pokémon.

## Highlights
- Type effectiveness quiz with instant feedback and streak tracking.
- Three quiz modes: single defender, dual defender, or random defenders.
- Bottom navigation with a dedicated Settings tab.
- Pokedex tab with searchable list, sprite images, and filters.
- Settings tab where each feature is taggable for further customizability
- Light mode toggle persists across restarts using DataStore preferences.
- Custom Material 3 color system and type badges for clear visual cues.
- Team coverage tool to analyze strengths and weaknesses for 6 type-only picks, including weakness stacking analysis.

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

## Pokedex

- Open the Pokedex tab to browse all Pokemon in the local database.
- Search by name, Pokedex number, or type.
- Use filters for type, tags (Legendary/Mythical/Baby), and minimum base stat total.
- Each entry shows its sprite, types, and base stats.

## Team Coverage

- Enter up to six Pokemon by type only (primary + optional secondary).
- Get offensive coverage (best hit) and defensive profile (worst weakness).
- **Weakness Stacking:** Analyze how many team members are vulnerable to specific types to identify critical core flaws.

## Project Structure
```
app/src/main/kotlin/dev/timothyspriegade/fullrestore/
  core/
    common/          App constants
    designsystem/    Theme, colors, typography, shared UI components
    util/            Type chart logic
  data/
    local/           Room database, entities, and DAOs
  domain/
    model/           Domain models (types, effectiveness, quiz mode)
    usecase/         Quiz generation and effectiveness calculation
  features/
    settings/        Settings UI + persistence
    pokedex/         Pokedex UI + ViewModel
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
