# Design & UI Rules

This document outlines the universal frontend component principles and provides an index to visual assets and feature-specific designs.

## Universal Frontend Principles

- **Navigation3**: Use `androidx.navigation3` for all navigation logic, ensuring adaptive support across screen sizes.
- **Atomic Design**: Structure UI components into atoms, molecules, and organisms within `core:design`.
- **Accessibility**: Follow accessibility tagging hierarchies to ensure the application is usable by everyone.
- **Image Loading**: Use **Coil 3** with Ktor-integration for all remote and local media.
- **Placeholders**: Use **BlurHash** to provide instant, visually pleasing loading states for images.
- **Spacing**: Avoid `Spacer` composables; use container arrangements (`verticalArrangement`, `horizontalArrangement`) and `padding` modifiers.

## Design Assets

- 📖 [Project Infographic](design/infographic.png): A visual overview of the project structure and goals.
- 📊 [Slide Deck](design/slidedeck.pdf): Presentation detailing the application features and roadmap.

## Feature Specific Designs

- *No feature-specific designs documented yet.*
