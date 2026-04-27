# Project Gaps Analysis

This document tracks the discrepancies between the project's formal documentation (`SPEC.md`, `ARCH.md`, `DESIGN.md`) and its current implementation.

## 1. Architectural Gaps

### Legacy Artifact Pollution
- **Status**: [x] DONE
- **Description**: The root-level directories `composeApp/` and `androidApp/` were inactive legacy artifacts. They have been removed to ensure a clean workspace context.
- **Issue**: #15

### Navigation3 Refinement
- **Status**: [ ] IN PROGRESS
- **Description**: Basic Navigation3 setup exists in `app:common`, but it lacks the robust feature-specific routing and back-stack handling standards described in `DESIGN.md`.
- **Issue**: #13

## 2. Documentation Gaps

### Persistence Standards
- **Status**: [x] DONE
- **Description**: `ARCH.md` has been updated with a formal "Persistence" section strictly forbidding SQLDelight and mandating Jetpack DataStore.
- **Issue**: #16
