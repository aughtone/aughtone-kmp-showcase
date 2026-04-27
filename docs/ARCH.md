# Architecture Guidelines

This document details the project's global engineering rules, repository synchronization patterns, and technical stack.

## General Principles

*   **Language**: Kotlin is the mandatory and preferred language. The only exception is platform-specific targets where Kotlin compiles to Java (e.g., JVM and Android targets).
*   **Dependency Injection**: **Koin** is the standard dependency injection framework for this project. Because this is a KMP project, Android-specific libraries like Dagger and Hilt are not compatible.
*   **Immutability**: Prefer `val` over `var` for predictable and thread-safe code.
*   **Null Safety**: Avoid nullable lists; always return an empty list instead of `null`.

## Module Structure

The project is divided into several functional directories:

- **`app/`**: Contains application orchestration and entry points.
    - `:app:common`: Shared Compose UI, Navigation3 host, and global DI.
    - `:app:android`, `:app:ios`, `:app:desktop`, `:app:web`, `:app:server`: Platform targets.
- **`core/`**: Foundational shared logic.
    - `:core:domain`: Pure business logic (Models, Repository interfaces).
    - `:core:data`: Bridges domain repositories to DataStore and API.
    - `:core:api`: Ktor-based networking.
    - `:core:design`: Design system, themes, and generic UI components.
    - `:core:database`: Multiplatform persistence using **Jetpack DataStore**.
- **`libs/`**: Shared library utilities.
    - `:libs:network`: Networking infrastructure.
- **`feature/`**: Self-contained feature modules (e.g., `:feature:journal`).

## Dependency Rules
To maintain a clean and maintainable codebase, we enforce the following dependency rules:
- **`:core:domain` module**: This module contains core business logic (entities, use cases, repository interfaces) and **must not** depend on any other modules.
- **`lib/` modules**: These are foundational library modules and **must not** depend on `:app:*`, `:core:*`, or `:feature:*` modules.
- **`core/` modules** (excluding `:core:domain`): Can depend on `libs/` and `:core:domain`, but should not depend on `feature/` or `app/`. These modules **must not** contain any platform-specific logic.
- **`feature/` modules**: Can depend on `core/` and `libs/`. They should be self-contained and not depend on other features or the `app/` module.
- **`app/` modules**: Can depend on any other module (`feature/`, `core/`, `libs/`).

## Coding Standards

### State Management
- Use `data class` to represent UI state (static and predictable structure).
- Do not use sealed classes for state objects.

### UI Toolkit
- Jetpack Compose with Material 3.
- Use container arrangements and padding modifiers over `Spacer` composables.

### Repository Patterns
- Prefer direct repository interaction within routes/ViewModels.
- Maintain immutability throughout the data pipeline.

### Persistence
- **Framework**: Use **Jetpack DataStore** for all multiplatform storage requirements.
- **Serialization**: Use **Okio** or **Kotlinx Serialization** to serialize data objects.
- **Relational Databases**: Do **NOT** use SQLDelight or any relational database frameworks. This project avoids SQL overhead in favor of simple, serialized file storage.

## Dependency Management
- Use Gradle Version Catalog (`gradle/libs.versions.toml`).
- Keep dependencies updated to the latest stable versions.
