# <img src="/assets/Full Restore App Icon.png" width="40" height="40" align="center"> Full Restore

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

## Key Features

### Quiz Suite

- **Type Quiz:** Practice offensive matchups by picking the correct damage modifier for specific
  attacker vs. defender scenarios.
- **Defense Quiz:** A more advanced mode where you must categorize all 18 attacker types against a
  specific defender (single or dual type). Features a smooth floating island sticky header and
  streak tracking.

### Pokedex and Data

- **Searchable Database:** Browse all Pokémon with a local Room database.
- **Advanced Filtering:** Filter by name, type, Pokedex number, base stat total, or tags (Legendary,
  Mythical, Baby).
- **Offline Access:** All data and sprites are stored locally—no network required.

### Team Coverage

- **Coverage Analysis:** Enter up to six Pokémon to evaluate offensive coverage and defensive
  vulnerabilities.
- **Weakness Stacking:** Identify critical core flaws by seeing how many team members share the same
  weaknesses.

### Customization

- **Theme Support:** Toggle between Light and Dark modes (persists via DataStore).
- **VGC Aesthetic:** Custom design system using Material 3 with official type colors and badges.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture with Feature-based modularity
- **Persistence:**
  - **Room:** Local Pokémon database.
  - **DataStore:** User preferences and settings.
- **Dependency Management:** Gradle Version Catalog

## Project Structure

```text
app/src/main/kotlin/dev/timothyspriegade/fullrestore/
├── core/
│   ├── common/          # Global constants and APP_TAGLINE
│   ├── designsystem/    # Theme, Colors, Typography, and shared UI Components
│   └── util/            # Type chart logic and effectiveness calculation
├── data/
│   └── local/           # Room Database, DAOs, and Entities
├── domain/
│   ├── model/           # Framework-agnostic Domain Models
│   └── usecase/         # Business logic (Quiz generation, Stats calculation)
└── features/
    ├── defensequiz/     # Advanced Defensive categorization quiz
    ├── pokedex/         # Searchable Pokemon database and filtering
    ├── settings/        # User preferences and persistence
    ├── teamcoverage/    # Team analysis and weakness stacking
    └── typequiz/        # Standard offensive effectiveness quiz
```

## Getting Started

### Requirements

- Android Studio (Ladybug or newer)
- JDK 17
- Android SDK API 26+

### Setup

1. Clone the repository.
2. Open in Android Studio and allow Gradle to sync.
3. Run the app configuration on an emulator or physical device.

## Design Principles

- **Speed First:** No splash screens or loading states for data lookups.
- **High Contrast:** Clear visual cues using type badges and status icons for immediate feedback.
- **Responsive:** Layouts adapt smoothly to maximize screen real estate.

---
*Full Restore is a fan project and is not affiliated with or endorsed by Nintendo, Creatures Inc.,
GAME FREAK inc., or The Pokémon Company.*
