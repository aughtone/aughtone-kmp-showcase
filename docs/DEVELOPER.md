# Developer Guide

This document is the primary onboarding and operational guide for developers contributing to the Aughtone KMP Showcase. It covers the project's documentation strategy, repository layout, technical standards, and execution lifecycle.

---

## 1. Scalable Documentation Strategy

### Why This Structure Exists
This repository uses a specialized documentation hierarchy designed for **AI Context Optimization** and **Clean Architecture** alignment. 

*   **Context Isolation**: By separating business logic (`SPEC.md`), engineering rules (`ARCH.md`), UI/UX guidelines (`DESIGN.md`), and setup instructions (`DEVELOPER.md`), we prevent "token pollution." AI models can parse only the relevant domain files without being distracted by build scripts or visual assets.
*   **Scale Ready**: As the project grows, this structure prevents any single file from becoming a bloated "catch-all," ensuring developers and AI agents can always find the "ground truth" for a specific layer of the application.
*   **Intelligent Updates**: AI agents are trained via `AGENTS.md` to intelligently disperse new information into these specialized files, maintaining documentation integrity over the long term.

---

## 2. Repository Overview

The showcase is a Kotlin Multiplatform (KMP) project targeting Android, iOS, Web, Desktop (JVM), and a Ktor Server.

### Project Structure (Modules)

*   **[:app:android](../app/android)**: Native Android application target.
*   **[:app:common](../app/common)**: Shared Compose Multiplatform UI, Navigation3, and main app entry point.
*   **[:app:desktop](../app/desktop)**: Native desktop application target for JVM.
*   **[:app:ios](../app/ios)**: Native iOS application target (SwiftUI entry point).
*   **[:app:server](../app/server)**: Ktor backend application.
*   **[:core:domain](../core/domain)**: Pure business logic and domain models.
*   **[:core:database](../core/database)**: Multiplatform DataStore persistence.
*   **[:feature:journal](../feature/journal)**: Core journaling feature module.
*   **[docs/](./)**: This documentation directory (Scalable Hierarchy).

### Visual Architecture

![Infographic](design/infographic.png)

---

## 3. Technical Guidelines

### Development Conventions
- **Immutability**: Prefer `val` over `var`.
- **Null Safety**: Avoid nullable lists; return empty lists instead.
- **Testing**: No mock libraries allowed. Use manually created **fakes and stubs**.
- **Coroutines**: Use `runTest` for testing asynchronous code.
- **Dependencies**: Managed via `gradle/libs.versions.toml`.

---

## 4. Execution & Lifecycle

### Build and Run Instructions

#### Android Application
Use the IDE run configurations or the following terminal commands:
- **macOS/Linux**: `./gradlew :app:android:assembleDebug`
- **Windows**: `.\gradlew.bat :app:android:assembleDebug`

#### Desktop (JVM) Application
- **macOS/Linux**: `./gradlew :app:desktop:run`
- **Windows**: `.\gradlew.bat :app:desktop:run`

#### Ktor Server
- **macOS/Linux**: `./gradlew :app:server:run`
- **Windows**: `.\gradlew.bat :app:server:run`

#### Web Application (JS Target)
- **macOS/Linux**: `./gradlew :app:web:jsBrowserDevelopmentRun`
- **Windows**: `.\gradlew.bat :app:web:jsBrowserDevelopmentRun`

#### iOS Application
Open the `/iosApp` directory in Xcode and run from the IDE, or use the provided IDE run configurations.

---

## 5. AI Export Utility

### Master AI Prompt
If you want to apply this scalable documentation refactor to another project, copy the block below and paste it into your AI assistant workspace.

```text
Please execute a complete refactor of our repository's documentation structure. Your goal is to separate UI/UX designs, high-level architecture decisions, strict business rules, and developer onboarding instructions into an enterprise `docs/` hierarchy. 

**Execution Steps Checklist:**

1. **Retain and Relocate `README.md`:** 
   Rename the current root `README.md` file to `docs/DEVELOPER.md`. This strictly preserves all existing compiler commands, run configurations, and local environment setup information without losing any developer context.

2. **Dismantle Hybrid specification files (e.g., `SPEC.md` or `ARCHITECTURE.md`):**
   If we have a single massive spec file at the root, fracture it into two separate files:
   - `docs/SPEC.md`: Extract strictly functional constraints, business logic rules, domain limits, and User behavior configurations. 
   - `docs/ARCH.md`: Extract strictly global engineering rules, repository synchronization patterns, caching pipelines, and platform technical stacks.
   - *Once successfully extracted, delete the original bloated file from the root directory.*

3. **Reorganize UI/UX Designs:**
   - Create a `docs/design/` directory.
   - Recursively move any pre-existing feature-specific design folders (e.g., `docs/feature_a`, `docs/feature_b`) into the new `docs/design/` directory.
   - Create a `docs/DESIGN.md` file. It must document this project's universal frontend component principles (e.g. Atomic Design utilization, Accessibility tagging hierarchies, global UI framework rules like Jetpack Compose/React), and act as a hyperlink index pointing to the specific sub-folders inside `docs/design/`.

4. **Construct the New Root Gateway `README.md`:**
   Write a brand-new `README.md` at the project root. It should no longer contain raw build scripts. Instead, it must serve as an elegant project summary and a Master Table of Contents featuring hyperlinked gateways to the 4 sectors:
   - 📖 **The Developer Guide** -> `docs/DEVELOPER.md`
   - 📐 **Architecture Guidelines** -> `docs/ARCH.md`
   - 🧠 **Functional Specifications** -> `docs/SPEC.md`
   - 🎨 **Design & UI Rules** -> `docs/DESIGN.md`

5. **Update the Context Map (`AGENTS.md` / `.cursorrules`):**
   Ensure you **strictly retain** any pre-existing logic if the file already exists—do not overwrite existing business rules! Update the document by prominently appending/updating instructions that inform future AI processing windows to map their context directly to the new `docs/` hierarchy:
   - Command the AI to reference `docs/SPEC.md` before writing logic.
   - Command the AI to reference `docs/ARCH.md` before establishing repository definitions or data layers.
   - Provide the path `docs/design/<feature>/DESIGN.md` when rendering UI screens.
   - **CRITICAL RULE**: Add a directive that explicitly teaches the AI: *"When a user commands you to 'update docs', you must intelligently disperse the new information directly into the appropriate specialized file (`docs/DESIGN.md`, `docs/ARCH.md`, or `docs/SPEC.md`)."*
```
