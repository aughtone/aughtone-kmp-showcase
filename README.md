# kmpshowcase

A Kotlin Multiplatform (KMP) template project using Compose Multiplatform, Ktor, and Koin.

## Project Structure

*   **`:shared:composeApp`**: Shared UI and application logic.
*   **`:app:android`**: The native Android application target.
*   **`:app:ios`**: The native iOS application target.
*   **`:app:desktop`**: The native desktop application target for JVM.
*   **`:app:web`**: The native web application target (Kotlin/Wasm).
*   **`:server`**: The Ktor backend application.
*   **`:shared:server-api`**: Shared models and endpoints for both client and server.
*   **`:shared:feature`**: Feature-specific modules.
*   **`:shared:core`**: Core infrastructure modules (Data, Domain, Network, Database, Design).

## Getting Started

### Prerequisites

*   Android Studio Ladybug or later.
*   Xcode 15+ (for iOS development).
*   JDK 21.

### Running the Applications

#### Android
Open the project in Android Studio and run the `androidApp` configuration.

#### iOS
Open the `app/ios` directory in Xcode or run from Android Studio.

#### Desktop
Run the desktop application:
```bash
./gradlew :app:desktop:run
```

#### Web
Run the web application:
```bash
./gradlew :app:web:wasmJsBrowserDevelopmentRun
```

#### Server
Run the Ktor server:
```bash
./gradlew :server:run
```

## Documentation

*   [AGENTS.md](./AGENTS.md): Development guide for AI agents and developers.
*   [Architecture](./docs/architecture.md): Detailed project architecture.
