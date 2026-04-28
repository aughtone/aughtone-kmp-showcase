# Project Gap List

This document tracks core engineering deliverables before full specification integration.

## 🚀 Deferred Features (Roadmap)
- [ ] **Media Support**:
    - [ ] Implement image selection and storage.
    - [ ] Integrate BlurHash previews as required by `SPEC.md`.
- [ ] **Authentication**:
    - [ ] Define and implement user login/session management.

## 🛠️ Technical Debt (Infrastructure)
- [ ] **Navigation3 Refinement**:
    - [ ] Harden `ShowcaseNavigation.kt` and integrate feature-specific routes.
    - [ ] Implement robust back-stack handling.

## 🕵️ Investigations & Explorations
- [ ] **Observe vs. Sync Performance**:
    - **Context**: Ensuring background sync doesn't impact UI performance in large datasets.
    - **Action**: Benchmark DataStore observation overhead with 1000+ entries.
