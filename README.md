# 🌍 Guess The Country

A flag-guessing Android game built as a learning project to explore modern Android development practices. Players are shown a country's flag, can request up to 3 hints, and must type the correct country name to progress. Scores are saved locally and displayed on a leaderboard.

---

## Screenshots

> will be added here once the UI is complete_

---

## Features

- 🏳️ Real flag images fetched from the REST Countries API
- 💡 Up to 3 progressive hints per country (capital, region, population, language, currency, borders)
- 🧮 Live score tracking per session
- 🏆 Persistent leaderboard using local Room database
- 🔀 Randomised country order each game
- 👤 Player name entry before each game

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose |
| Architecture | MVVM (Model-View-ViewModel) |
| Dependency Injection | Hilt |
| Local Database | Room |
| Networking | Retrofit |
| Image Loading | Coil |
| Language | Kotlin |
| Min SDK | API 24 (Android 7.0) |

---

## Architecture

The app follows the MVVM pattern with a clean separation of concerns across three layers:

```
UI Layer (Compose Screens)
        ↓
Logic Layer (ViewModels)
        ↓
Data Layer (Repository → Room + Retrofit)
```

**UI layer** — Compose screens observe state from the ViewModel and emit user events back up. Screens never touch the database or network directly.

**Logic layer** — `GameViewModel` owns game state (current country, score, hints used). `LeaderboardViewModel` fetches and sorts saved scores. Both are injected by Hilt.

**Data layer** — The `CountryRepository` acts as a single source of truth. It decides whether to return cached data from Room or fetch fresh data from the REST Countries API via Retrofit. Scores are always written to Room at the end of each game.

---

## API

Country data is fetched from the [REST Countries API](https://restcountries.com):

```
GET https://restcountries.com/v3.1/all
```

No API key required. The response provides everything the game needs:

```json
{
  "name": { "common": "Kenya" },
  "capital": ["Nairobi"],
  "region": "Africa",
  "subregion": "Eastern Africa",
  "population": 53771296,
  "languages": { "swa": "Swahili", "eng": "English" },
  "currencies": { "KES": { "name": "Kenyan shilling" } },
  "borders": ["ETH", "SOM", "TZA"],
  "flags": {
    "png": "https://flagcdn.com/w320/ke.png"
  }
}
```

Each response field maps to one of the 3 hints shown in-game.

---

## Project Structure

```
app/src/main/java/com/example/guessthecountry/
│
├── di/
│   └── AppModule.kt              # Hilt module — provides Retrofit, Room, Repository
│
├── data/
│   ├── model/
│   │   ├── Country.kt            # API response data class
│   │   └── ScoreEntity.kt        # Room entity for saved scores
│   ├── local/
│   │   ├── AppDatabase.kt        # Room database
│   │   └── ScoreDao.kt           # DAO — insert and query scores
│   ├── remote/
│   │   └── CountryApiService.kt  # Retrofit interface
│   └── repository/
│       └── CountryRepository.kt  # Combines local + remote sources
│
├── ui/
│   ├── welcome/
│   │   └── WelcomeScreen.kt      # Name entry screen
│   ├── game/
│   │   ├── GameScreen.kt         # Flag + hint + guess UI
│   │   └── GameViewModel.kt      # Game logic and state
│   ├── results/
│   │   └── ResultsScreen.kt      # End of game summary
│   └── leaderboard/
│       ├── LeaderboardScreen.kt  # Top scores list
│       └── LeaderboardViewModel.kt
│
└── MainActivity.kt               # Nav host — single activity
```

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17
- Android device or emulator running API 24+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/guessthecountry.git
   ```
2. Open the project in Android Studio
3. Let Gradle sync complete
4. Run the app on your device or emulator

No API keys or environment variables are needed — the REST Countries API is open and free.

---

## What I Learned Building This

This project was built as a hands-on introduction to modern Android development. Key concepts covered:

- **Jetpack Compose** — declarative UI, state hoisting, recomposition
- **MVVM** — separating UI logic from business logic using ViewModels
- **Hilt** — dependency injection, scoping, `@HiltViewModel`
- **Room** — defining entities, DAOs, and a database with coroutines
- **Retrofit** — defining API interfaces, parsing JSON with Gson/Moshi
- **Coroutines + Flow** — async data fetching and reactive UI updates
- **Navigation Component** — single-activity navigation with Compose

---

## Suggested Improvements

These are features that would make the app more complete, more fun, and better demonstrate advanced Android concepts:

### Gameplay
- **Difficulty levels** — Easy shows common flags, Hard uses obscure territories and dependencies
- **Multiple choice mode** — Instead of typing, the player picks from 4 options, lowering the barrier to entry
- **Timer mode** — Each country has a countdown, adding pressure and a time-bonus scoring system
- **Streak bonus** — Reward consecutive correct answers with multiplied points
- **Region filter** — Let the player choose to only see flags from Africa, Europe, Asia, etc.
- **Skip option** — Allow the player to skip a country at the cost of points

### Leaderboard
- **Online leaderboard** — Use a backend (Firebase Firestore or a simple REST API) to share scores globally
- **Filter by region** — Let players compare scores only within their chosen region mode
- **Personal best tracking** — Highlight when a player beats their own high score

### UX and Polish
- **Animations** — Animate the flag entrance, hint reveals, and correct/wrong feedback using Compose `AnimatedVisibility` and `animateContentSize`
- **Sound effects** — Add subtle audio feedback for correct answers, wrong answers, and hint reveals
- **Dark mode** — Full dark theme support using Material 3 dynamic colour theming
- **Onboarding** — A short tutorial screen explaining the rules for first-time players
- **Accessibility** — Content descriptions on flag images for screen readers, minimum touch target sizes

### Technical
- **Offline support** — Cache the full country list in Room on first launch so the game works without internet
- **Unit tests** — Test `GameViewModel` logic (score increments, hint counter, game end conditions) with JUnit and Turbine
- **UI tests** — Use Compose testing APIs to verify screen transitions and button states
- **Pagination** — Load countries in pages using Paging 3 rather than all at once
- **WorkManager** — Schedule a daily background sync to refresh country data
- **Widget** — An Android home screen widget showing a daily flag challenge

---

## Contributing

This is a learning project but pull requests are welcome. If you find a bug or have a suggestion, open an issue first to discuss the change.

---

## License

MIT License — see [LICENSE](LICENSE) for details.

---

## Acknowledgements

- [REST Countries API](https://restcountries.com) — free country data and flag images
- [FlagCDN](https://flagcdn.com) — flag image hosting
- [Jetpack Libraries](https://developer.android.com/jetpack) — the backbone of the architecture
