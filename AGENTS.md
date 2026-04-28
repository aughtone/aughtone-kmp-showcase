# AI & Developer Context Map

This document serves as the primary entry point for AI agents and developers to understand the repository structure and documentation hierarchy.

## AI Processing Guidelines

To ensure consistency and quality, all AI agents MUST map their context according to the following hierarchy:

1. **Functional Logic**: Reference [docs/SPEC.md](docs/SPEC.md) before writing or updating any business logic or domain rules.
2. **Architecture**: Reference [docs/ARCH.md](docs/ARCH.md) before establishing repository definitions, data layers, or data structures.
3. **UI/UX**: Reference [docs/DESIGN.md](docs/DESIGN.md) for universal frontend principles and [docs/design/](docs/design/) for feature-specific designs.
4. **Environment**: Reference [docs/DEVELOPER.md](docs/DEVELOPER.md) for build commands and setup instructions.

### CRITICAL RULE: Documentation Updates
When a user commands you to 'update docs', you MUST intelligently disperse the new information directly into the appropriate specialized file:
- **`docs/DESIGN.md`**: For UI framework changes, component principles, or visual assets.
- **`docs/ARCH.md`**: For global engineering rules, repository patterns, or technical stack updates.
- **`docs/SPEC.md`**: For functional constraints, business logic rules, or domain limits.
- **`docs/DEVELOPER.md`**: For build scripts, local setup, or environment configurations.

## GitHub Synchronization

This project uses a specialized toolchain to keep documentation in sync with GitHub Issues.
- **Trigger**: Whenever you update `docs/SPEC.md` (Acceptance Criteria) or `docs/GAPS.md` (Technical Debt).
- **Workflow**:
    1. Mark new items with `- Issue: #NEW`.
    2. **DO NOT** run the sync script yourself. Notify the developer that documentation has been updated.
    3. The developer will run `python3 tools/github/gh.py` to synchronize and write IDs back to the files.
- **Requirements**: Ensure `GITHUB_TOKEN` and `GITHUB_REPOSITORY` are exported in your environment.

## AI Interaction Guidelines

*   **Planning Mode**: By default, AI agents should provide a concise implementation plan. Ask the developer if they would like to use Planning Mode (default) or Direct Execution Mode.
*   **Rejection Handling**: If a change is rejected, stop asking to accept it over and over again. You can ask why it was rejected once.
*   **Be direct and to the point**: Do not add conversational fluff.
*   **Testing**: When writing tests, always use Kotlin, place them in `src/commonTest/kotlin`, and use manual fakes/stubs (no mock libraries).
