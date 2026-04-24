# click-set

A vibration metronome app for Android, Wear OS, iOS, and watchOS. Supports presets and setlist management.

## Tech Stack

- **Kotlin Multiplatform** + **Compose Multiplatform** — shared UI across Android and iOS
- **SQLDelight** — cross-platform local database
- **Kotlin Coroutines / Flow** — reactive data streams
- **Clean Architecture** — domain / data / presentation layer separation

## Project Structure

```
click-set/
├── apps/
│   ├── android/          # Compose Multiplatform app (Android + iOS shared UI)
│   │   └── src/
│   │       ├── commonMain/     # Shared UI components and screens
│   │       ├── androidMain/    # Android-specific implementation (Wear OS included)
│   │       └── iosMain/        # iOS-specific implementation
│   └── ios/              # iOS / watchOS SwiftUI entry point
│       └── iosApp/
├── core/                 # Domain models, use cases, repositories
│   └── src/
│       ├── commonMain/   # Shared business logic + SQLDelight schemas
│       └── androidMain/  # Android-specific infrastructure
└── config/
    └── detekt/           # Static analysis configuration
```

## Domain Model

- **Song** — a single song with BPM and time signature
- **Setlist** — an ordered list of songs for a live performance

## Build

### Android

```shell
# Debug build
./gradlew :apps:android:assembleDebug

# Run on connected device/emulator
./gradlew :apps:android:installDebug
```

### iOS

Open `apps/ios/iosApp.xcodeproj` in Xcode and run from there.

## Code Quality

```shell
# Auto-fix formatting (Spotless + ktlint)
./gradlew spotlessApply

# Static analysis (Detekt)
./gradlew detekt

# Run both checks
./gradlew spotlessCheck detekt
```

Configuration: `config/detekt/detekt.yml`

## CI

GitHub Actions runs build and test on every push. See `.github/workflows/` for details.

## License

[MIT](LICENSE)