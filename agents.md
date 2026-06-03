# agents.md -- iOS Scenario Testing

## Repository Overview

Automated end-to-end test framework for the ownCloud iOS app using Cucumber/Gherkin with Appium for device interaction. Written in Java with Gradle as the build system. Licensed under MIT.

## Architecture & Key Paths

- `src/` -- Java step definitions and test infrastructure
- `build.gradle` -- Gradle build configuration
- `gradlew` / `gradlew.bat` -- Gradle wrapper scripts
- `settings.gradle` -- Gradle settings
- `files/` -- Test fixture files
- `server/` -- Server-side test configuration
- `buildapp/` -- App build scripts
- `executeTests/` -- Test execution scripts
- `sample-dat/` -- Sample test data

## Development Conventions

- Feature files written in Gherkin syntax
- Step definitions in Java
- Gradle for build and dependency management
- Reports via Cucumber Reports service

## Build & Test Commands

```bash
./gradlew test          # Run all tests
./gradlew build         # Build the project
```

## Important Constraints

- Licensed under MIT. The OSPO is driving Apache 2.0 migration across repositories.
- Do not introduce new **copyleft-licensed dependencies** (GPL, AGPL, LGPL, MPL) without explicit discussion in an issue first. This is especially important for repos that are migrating to or already under Apache 2.0, as copyleft dependencies would block or complicate that migration.
- Requires a running Appium instance and iOS simulator.
- All contributions require a DCO sign-off.


## OSPO Policy Constraints

### GitHub Actions
- **Only** use actions owned by `owncloud`, created by GitHub (`actions/*`), verified on the GitHub Marketplace, or verified by the ownCloud Maintainers.
- Pin all actions to their full commit SHA (not tags): `uses: actions/checkout@<SHA> # vX.Y.Z`
- Never introduce actions from unverified third parties.

### Dependency Management
- Dependabot is configured for automated dependency updates.
- Review and merge Dependabot PRs as part of regular maintenance.
- Do not introduce new dependencies without discussion in an issue first.

### Git Workflow
- **Rebase policy**: Always rebase; never create merge commits. Use `git pull --rebase` and `git rebase` before pushing.
- **Signed commits**: All commits **must** be PGP/GPG signed (`git commit -S -s`).
- **DCO sign-off**: Every commit needs a `Signed-off-by` line (`git commit -s`).
- **Conventional Commits & Squash Merge**: Use the [Conventional Commits](https://www.conventionalcommits.org/) format where the repository enforces it. Many repos use squash merge, where the PR title becomes the commit message on the default branch — apply Conventional Commits format to PR titles as well. A reusable GitHub Actions workflow enforces this.

## Context for AI Agents

This is a test-only repository. Feature files in Gherkin define test scenarios, and Java code in `src/` implements the step definitions using Appium for iOS device interaction. Changes to test scenarios should follow Gherkin best practices.
