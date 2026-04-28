# AI & Developer Context Map

This document serves as the primary entry point for AI agents and developers to understand the repository structure and documentation hierarchy.

## AI Processing Guidelines

To ensure consistency and quality, all AI agents MUST map their context according to the following hierarchy:

1. **Functional Logic**: Reference [SPEC.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/SPEC.md?type=file&root=%252F) before writing or updating any business logic or domain rules.
2. **Architecture**: Reference [ARCH.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/ARCH.md?type=file&root=%252F) before establishing repository definitions, data layers, or data structures.
3. **UI/UX**: Reference [DESIGN.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/DESIGN.md?type=file&root=%252F) for universal frontend principles and [docs/design/](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/design?type=file&root=%252F) for feature-specific designs.
4. **Environment**: Reference [DEVELOPER.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/DEVELOPER.md?type=file&root=%252F) for build commands and setup instructions.
5. **Action Plan**: Reference [GAPS.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/GAPS.md?type=file&root=%252F) for unimplemented features, tech debt, and investigations.

### MANDATORY PROTOCOL: Planning and Approval
You MUST always present a detailed plan and wait for explicit user approval before executing any file modifications, running commands, or performing significant actions. Failure to do so is a violation of core repository safety protocols.

### THE "UPDATE DOCS" RULE
When told to **"update docs"**, you must intelligently disperse the new context directly into the appropriate specialized file rather than dumping generic blocks. Avoid duplicating context; ensure each piece of information lives cleanly in the file that governs its operational domain.

- **[ARCH.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/ARCH.md?type=file&root=%252F)**: The Technical Map. Architectural mandates, data flow patterns, persistence models, and structural boundaries.
- **[SPEC.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/SPEC.md?type=file&root=%252F)**: The Business Map. Functional requirements, system definitions, business limits, and product glossary.
- **[DESIGN.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/DESIGN.md?type=file&root=%252F)**: The Visual Map. Styling parameters, layout schemas, typography, and accessibility boundaries.
- **[GAPS.md](air-file://4ogqbd24vmsh44djrr19/Users/bpappin/Workspace/aughtone-kmp-showcase/docs/GAPS.md?type=file&root=%252F)**: The Action Map. Unimplemented roadmap features, tech debt trackers, and investigations.

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
