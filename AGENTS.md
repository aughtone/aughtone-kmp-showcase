# Template Project Development Guide

This document is a comprehensive guide for developers and AI agents contributing to this **Kotlin Multiplatform (KMP)** template project. This repository serves as a foundational template used to generate new projects. It details the project's architecture, module structure, and development conventions.

## General Principles

*   **Language**: Kotlin is the mandatory and preferred language. The only exception is platform-specific targets where Kotlin compiles to Java (e.g., JVM and Android targets).
*   **Dependency Injection**: **Koin** is the standard dependency injection framework for this project. Because this is a KMP project, Android-specific libraries like Dagger and Hilt are not compatible.


## Module Structure

The project is organized into a modular architecture to promote separation of concerns and scalability.

### Apps
*   **`:app:android`**: The native Android application target. Contains the `MainActivity`, `AndroidManifest.xml`, and any Android-specific startup logic.
*   **`:app:ios`**: The native iOS application target. Contains the SwiftUI entry point and iOS-specific configurations.
*   **`:app:desktop`**: The native desktop application target for JVM. Contains the desktop window setup and entry point.
*   **`:app:web`**: The native web application target (Kotlin/Wasm or JS). Contains the web-specific entry point and configurations.
*   **`:app:common`**: Shared UI and app structure using Compose Multiplatform. It acts as the shared app entry point, bringing together the UI, features, and cross-platform setup.
*   **`:app:server`**: The Ktor backend application. It provides the REST API endpoints and backend logic.
*   **`:core:api`**: Shared API definitions, request/response models, and Ktor resources used by both client and server.

### Features
*   **`:feature:*`**: Feature-specific modules (e.g., `:feature:journal`). Each feature module should contain its own UI, ViewModels/StateHolders, and internal business logic. Feature modules should be independent and only depend on `:core` modules.

### Core
*   **`:core:database`**: Local data persistence.
*   **`:core:network`**: Network communication logic using Ktor client.
*   **`:core:data`**: Data layer coordinating between local and remote sources. Contains repository implementations.
*   **`:core:domain`**: Shared business logic, use cases, and domain models.
*   **`:core:design`**: Shared design system, UI components, and theming.

## Development Guidelines

### Coding Standards

*   **Immutability**: Prefer `val` over `var` to create predictable and thread-safe code.
*   **Null Safety**: Avoid nullable lists. Always return an empty list instead of `null`.
*   **State Management**: Use a `data class` to represent UI state. This keeps the state's structure static and predictable. Do not use sealed classes for state, as this implies a change in the state object itself rather than its properties.
*   **UI Toolkit**: This project uses Jetpack Compose with Material 3. Prefer Material 3 components and theming over Material 2. Avoid using `Spacer` composables; instead, use container arrangements (`verticalArrangement`, `horizontalArrangement`) and the `padding` modifier to manage spacing.
*   **String Escaping**: When needing to escape a dollar sign in a string, prefer using the double dollar sign (`$$`) over string interpolation (`"${\'$'}"`).

### Testing

*   **No Mock Libraries**: You must **never** use any mock libraries (e.g., MockK, Mockito). KMP projects do not have reliable mock libraries, and they can muddy the code. Use manually created **fakes and stubs** instead.
*   **Language**: When writing unit tests, you must always use Kotlin.
*   **Location**: Unit tests should be placed in `src/commonTest/kotlin` in accordance with multiplatform projects.
*   **Coroutines**: When testing code that uses coroutines, use `runTest` from the `kotlinx-coroutines-test` library instead of `runBlocking`.

### Dependency Management

This project uses a [Gradle Version Catalog](https://docs.gradle.org/current/userguide/version_catalog_plugin.html) defined in the `gradle/libs.versions.toml` file.

*   Keep dependencies eagerly updated to the latest stable version.
*   If a dependency is pinned to an older version, a comment must be added explaining the reason.

## AI Interaction Guidelines

*   **Rejection Handling**: If a change is rejected, stop asking to accept it over and over again. You can ask why it was rejected once.
*   **Be direct and to the point**: Do not add conversational fluff.
