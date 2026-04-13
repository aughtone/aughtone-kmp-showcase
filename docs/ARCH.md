# Architecture Guidelines

This document details the project's global engineering rules, repository synchronization patterns, and technical stack.

## General Principles

*   **Language**: Kotlin is the mandatory and preferred language. The only exception is platform-specific targets where Kotlin compiles to Java (e.g., JVM and Android targets).
*   **Dependency Injection**: **Koin** is the standard dependency injection framework for this project. Because this is a KMP project, Android-specific libraries like Dagger and Hilt are not compatible.
*   **Immutability**: Prefer `val` over `var` for predictable and thread-safe code.
*   **Null Safety**: Avoid nullable lists; always return an empty list instead of `null`.

## Module Structure

The project follows a standard Kotlin Multiplatform structure:

*   **`:androidApp`**: Native Android application target.
*   **`:iosApp`**: Native iOS application target (SwiftUI).
*   **`:desktopApp`**: Native desktop application target (JVM).
*   **`:webApp`**: Native web application target (Kotlin/JS).
*   **`:composeApp`**: Main common application structure using Compose Multiplatform (shared entry point).
*   **`:shared`**: Common business logic, data models, Ktor resources, and shared utilities.
*   **`:server`**: Ktor backend application providing REST API endpoints.
*   **`docs/`**: Scalable documentation hierarchy (Architecture, Specs, Design, Developer).

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
