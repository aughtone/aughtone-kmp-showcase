# Architecture Guidelines

This document details the project's global engineering rules, repository synchronization patterns, and technical stack.

## General Principles

*   **Language**: Kotlin is the mandatory and preferred language. The only exception is platform-specific targets where Kotlin compiles to Java (e.g., JVM and Android targets).
*   **Dependency Injection**: **Koin** is the standard dependency injection framework for this project. Because this is a KMP project, Android-specific libraries like Dagger and Hilt are not compatible.
*   **Immutability**: Prefer `val` over `var` for predictable and thread-safe code.
*   **Null Safety**: Avoid nullable lists; always return an empty list instead of `null`.

## Module Structure

The project is divided into several top-level directories:

- **`app/`**: Contains platform-specific application modules.
    - Android, Desktop, Web, Server.
- **`core/`**: Shared core modules. These modules must contain only platform-agnostic logic; platform-specific implementations (`expect`/`actual`) are not allowed in `:core:*` modules.
    - `:core:domain`: Pure business logic. Contains domain models (entities), repository interfaces, and use cases.
    - `:core:data`: Implementation of data access logic. Contains repository implementations, local data sources (e.g., Prefs), and DI configuration for data.
    - `:core:api`: Networking logic and API definitions. Contains Ktor-based API clients, API response models, and endpoint resources.
    - `:core:design`: Contains generic, reusable UI components (e.g., SearchBar, buttons, cards) and common UI utilities.
- **`lib/`**: Shared library modules providing foundational services.
    - `libs:network`: Ktor-based networking.
    - `libs:database`: SQLDelight-based local storage.
    - `libs:version`: Versioning and metadata.
- **`feature/`**: Feature-specific modules (e.g., `feature:home`). Each feature is self-contained.

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

## Dependency Management
- Use Gradle Version Catalog (`gradle/libs.versions.toml`).
- Keep dependencies updated to the latest stable versions.
