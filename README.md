# 🌍 Guess The Country

A fun flag-guessing game built while learning Android development. You see a flag, you guess the country — simple as that. If you're stuck, you can ask for up to 3 hints before submitting your answer. Get it right and you move on. Get it wrong and the game ends.

It started as a simple XML-based app and grew into something with a proper architecture — Jetpack Compose for the UI, MVVM to keep things organised, Room to save scores locally, Retrofit to fetch real country data from an API, and Hilt to wire it all together. Every part of this stack was new at some point, so the code reflects a genuine learning journey rather than a polished production app.

---

## What it does

- Shows a real flag image fetched from the REST Countries API
- Lets you request up to 3 hints — region, capital city, population
- Tracks your score as you go
- Ends the game if you guess wrong
- Saves your score to a local leaderboard
- Lets you play again or switch players at the end

---

## Tech stack

| What | Why |
|---|---|
| Jetpack Compose | UI — no XML, just Kotlin functions |
| MVVM | Keeps game logic out of the screens |
| Hilt | Wires up dependencies automatically |
| Room | Saves scores to an on-device database |
| Retrofit + Moshi | Fetches country data from restcountries.com |
| Coil | Loads flag images from URLs |
| Navigation Compose | Moves between screens cleanly |

---

## Setup

1. Clone the repo
2. Get a free API key from [restcountries.com](https://restcountries.com)
3. Add it to `local.properties`:
   ```
   COUNTRIES_API_KEY=your_key_here
   ```
4. Run the app

---

## Project structure

```
com.example.guessthecountry2
├── data
│   ├── local        # Room database and DAO
│   ├── model        # Country and Score data classes
│   ├── remote       # Retrofit API interface
│   └── repository   # Single source of truth
├── di               # Hilt module
└── ui
    ├── game         # Game screen + ViewModel
    ├── leaderboard  # Leaderboard screen + ViewModel
    ├── results      # Results screen
    └── welcome      # Name entry screen
```

---

## Honest notes

This was built as a learning project. Some things could be done better — error handling is basic, the country data isn't cached offline yet, and the UI could use some polish. But it works, it follows a real architecture pattern, and it was a great way to get hands-on with the modern Android stack.

---

*Built with curiosity and a lot of Gradle errors.*