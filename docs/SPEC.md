# Functional Specifications

This document outlines the functional requirements, system definitions, business limits, and product glossary for the application.

## 1. Domain Definitions
- **Journal Entry**: A record containing:
    - `id`: Unique identifier (String).
    - `title`: Short summary (String).
    - `date`: Entry timestamp (LocalDate).
    - `content`: Narrative body (String).
    - `mood`: Emotional context (Mood).
- **Mood**: A user-selected state associated with an entry. Options:
    - `Happy`, `Sad`, `Calm`, `Energetic`, `Anxious`.

## 2. Business Rules
- **Entry Creation**: Fields required: Title, Date, Content, Mood.
- **Mood Selection**: A mandatory selection for each entry to categorize emotional trends.
- **Media Support**:
    - **Image Attachments**: Users can attach images to entries.
    - **Instant Previews**: Use BlurHash placeholders for all entry images to maintain UI fluidity.
- **Authentication**: (To be defined - placeholders for user login/session management)

## 3. Permissions & Security
- **Data Privacy**: All journal entries are private to the user and stored locally first.
- **Privacy Masking**: (Policy for masking sensitive data in logs or previews if applicable)

## 4. Feature Set (Current)
- **Home Screen**: Displays a list of past entries sorted by date.
- **Visual Style**: Consistent use of Material 3 components and themes.
- **Theming**: Tranquil nature scenes incorporated into header sections.
