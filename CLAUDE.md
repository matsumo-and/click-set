# click-set

A vibration metronome app for Android, Wear OS, iOS, and watchOS. Supports presets and setlist management.

## Tech Stack

- **Kotlin Multiplatform** + **Compose Multiplatform** — shared UI across Android and iOS
- `apps/android/` — shared Compose Multiplatform UI and platform-specific code
- `core/` — business logic and domain models (shared)
- `apps/ios/` — iOS / watchOS app entry point (SwiftUI)

## Project Structure

- `apps/android/src/commonMain/` — shared UI components and screens
- `apps/android/src/androidMain/` — Android-specific implementation (including Wear OS)
- `apps/android/src/iosMain/` — iOS-specific implementation
- `core/src/commonMain/` — domain models, use cases, repositories
- `apps/ios/iosApp/` — iOS / watchOS SwiftUI entry point

## Build

```shell
# Android debug build
./gradlew :apps:android:assembleDebug

# Code quality check (lint + static analysis)
./gradlew spotlessCheck detekt
```

## Code Quality

- **Spotless + ktlint** — Kotlin / Gradle file formatting
- **Detekt** — static analysis (`config/detekt/detekt.yml`)
- Auto-fix formatting: `./gradlew spotlessApply`

## Rules

- Delegate investigation tasks to subagents
- Always run quality checks after implementation (use the `verify` skill)