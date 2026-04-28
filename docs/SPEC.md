# Functional Specifications

This document outlines the functional requirements, system definitions, business limits, and product glossary for the application.

## 1. Domain Definitions
- **Journal Entry**: A record containing a Title, Date, and Content, optionally with image attachments and a mood rating.
- **Mood**: A user-selected state (e.g., Happy, Neutral, Sad) associated with an entry.

## 2. Business Rules
- **Entry Creation**: Fields required: Title, Date, Content.
- **Mood Selection**: A mandatory selection for each entry.
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
