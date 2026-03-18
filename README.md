# Kotlin Multiplatform Showcase

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop (JVM), and Server.

## Project Structure

The project is organized into the following modules:

### Apps
*   **[:androidApp](./androidApp)**: The native Android application target.
*   **[:iosApp](./iosApp)**: The native iOS application target.
*   **[:desktopApp](./desktopApp)**: The native desktop application target for JVM.
*   **[:webApp](./webApp)**: The native web application target (Kotlin/Wasm).
*   **[:composeApp](./composeApp)**: Shared UI and app structure using Compose Multiplatform.
*   **[:server](./server)**: Ktor backend application.

### Features
*   **[:feature:journal](./feature/journal)**: Journaling feature including list and details screens.

### Core
*   **[:core:database](./core/database)**: Local data persistence using DataStore.
*   **[:core:network](./core/network)**: Network communication using Ktor client.
*   **[:core:data](./core/data)**: Data layer coordinating between database and network.
*   **[:core:domain](./core/domain)**: Shared business logic and domain models.
*   **[:core:design](./core/design)**: Shared design system and UI components.

## Build and Run Instructions

### Android
```shell
./gradlew :composeApp:assembleDebug
```

### Desktop (JVM)
```shell
./gradlew :composeApp:run
```

### Server
```shell
./gradlew :server:run
```

### Web (Wasm)
```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### iOS
Open the `iosApp` directory in Xcode or run from Android Studio.

## Development Guidelines

- **Architecture**: The project follows a modular architecture with feature-based modules and a core layer for shared functionality.
- **Dependency Injection**: Koin is used for DI across all modules.
- **UI**: Compose Multiplatform with Material 3 for shared UI.
- **Testing**: Use fakes/stubs for testing. No mock libraries allowed.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) and [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform).
