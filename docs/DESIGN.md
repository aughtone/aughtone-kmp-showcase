# Design Guidelines

This document outlines styling parameters, layout schemas, typography, and accessibility boundaries.

## 1. UI Toolkit
- **Framework**: Jetpack Compose with Material 3.
- **Navigation**: Use `androidx.navigation3` for all navigation logic, ensuring adaptive support across screen sizes.
- **Image Loading**: Use **Coil 3** with Ktor-integration for all remote and local media.
- **Placeholders**: Use **BlurHash** to provide instant, visually pleasing loading states for images.

## 2. Atomic Design System
- **Atoms**: Smallest UI units (Buttons, TextFields, Icons) located in `core:design`.
- **Molecules**: Groups of atoms (Search bar, Entry row).
- **Organisms**: Feature-level sections (Entry List, Header section).

## 3. General Principles
- **Accessibility**: Follow accessibility tagging hierarchies.
- **Spacing**: Avoid `Spacer` composables; use container arrangements (`verticalArrangement`, `horizontalArrangement`) and `padding` modifiers.
- **Theming**: Focus on tranquil nature themes as defined in `SPEC.md`.

## 4. Design Assets
- 📖 [Project Infographic](design/infographic.png): A visual overview of the project structure and goals.
- 📊 [Slide Deck](design/slidedeck.pdf): Presentation detailing the application features and roadmap.

## 5. Test Automation Hooks
- (Reserved for test tags and automation identifiers)
