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
    - [x] Basic `NavDisplay` and `entryProvider` implementation.
    - [ ] Integrate deep linking support.
    - [ ] Implement robust back-stack handling for nested feature routes.

## 🕵️ Investigations & Explorations
- [ ] **Observe vs. Sync Performance**:
    - **Context**: Ensuring background sync doesn't impact UI performance in large datasets.
    - **Action**: Benchmark DataStore observation overhead with 1000+ entries.
