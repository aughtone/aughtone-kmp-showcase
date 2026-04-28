# Project Architecture Guidelines

This document details the project's global engineering rules, repository synchronization patterns, and technical stack.

## 1. Core Principles
- **Offline-First**: All application features must function without an active network connection, using local storage as the primary source of truth.
- **Language**: Kotlin is the mandatory and preferred language. The only exception is platform-specific targets where Kotlin compiles to Java (e.g., JVM and Android targets).
- **Dependency Injection**: **Koin** is the standard dependency injection framework for this project.
- **Immutability**: Prefer `val` over `var` for predictable and thread-safe code.
- **Null Safety**: Avoid nullable lists; always return an empty list instead of `null`.

## 2. Data Synchronization
- **Observe vs. Sync Pattern**: UI observes local storage changes. Background sync processes update local storage from remote APIs without direct UI blocking.
- **Networking**: Ktor-based networking using `Resource` endpoints.
    - `JournalEntryResource`: Root endpoint for `/journal-entries`.
    - `JournalEntryResource.Id`: Specific entry access via `/{id}`.
- **Data Transfer**: `JournalEntryDto` used for network boundaries, mapped to `JournalEntry` domain model.

## 3. Persistence
- **Database Resilience**: Local storage must handle serialization failures gracefully, reverting to last known good states if corruption occurs.
- **Framework**: Use **Jetpack DataStore** for all multiplatform storage requirements.
- **Serialization**: Use **Okio** or **Kotlinx Serialization** to serialize data objects.
- **Relational Databases**: Do **NOT** use SQLDelight or any relational database frameworks. This project avoids SQL overhead in favor of simple, serialized file storage.

## 4. Module Structure
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

## 5. Dependency Rules
To maintain a clean and maintainable codebase, we enforce the following dependency rules:
- **`:core:domain` module**: Must not depend on any other modules.
- **`lib/` modules**: Must not depend on `:app:*`, `:core:*`, or `:feature:*` modules.
- **`core/` modules** (excluding `:core:domain`): Can depend on `libs/` and `:core:domain`, but should not depend on `feature/` or `app/`. Must not contain platform-specific logic.
- **`feature/` modules**: Can depend on `core/` and `libs/`. Self-contained; no cross-feature dependencies.
- **`app/` modules**: Can depend on any other module.

## 6. Coding Standards
### State Management
- Use `data class` to represent UI state.
- Do not use sealed classes for state objects.

### UI Toolkit
- Jetpack Compose with Material 3.
- Use container arrangements and padding modifiers over `Spacer` composables.

### Repository Patterns
- Prefer direct repository interaction within routes/ViewModels.
- Maintain immutability throughout the data pipeline.
