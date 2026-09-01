# AGENTS.md -- iOS Scenario Testing

## Repository Overview

Automated end-to-end test framework for the ownCloud iOS app using Cucumber/Gherkin with Appium for device interaction. Written in Java with Gradle as the build system. Licensed under MIT.

Gherkin feature files drive Cucumber scenarios executed against a real or simulated iOS device via Appium (XCUITest). There is no production code here -- only tests and their supporting framework.

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

### Source layout

```
src/test/java/
  e2e/
    api/          -- REST API clients (CommonAPI base + FilesAPI, ShareAPI, GraphAPI, TrashbinAPI)
    assertions/   -- Assertion classes (FileList, PrivateShare, PublicLink, SpaceMembers, Spaces)
    hooks/        -- Cucumber lifecycle hooks (Hooks.java)
    model/        -- Plain data objects (OCFile, OCShare, OCSpace, OCSpaceMember, OCSpacePermission, ShareType)
    pages/        -- Page Object Model: CommonPage (base) + one class per screen + AppiumManager
    preconditions/-- API-driven setup classes (FileList, Login, PublicLink, Shares, Spaces)
    runner/       -- RunCucumberTest (JUnit entry point)
    steps/        -- Cucumber step definitions (thin dispatchers only)
    support/
      date/       -- DateUtils (java.time utilities)
      log/        -- Log and StepLogger wrappers
      network/    -- oCHttpClient (OkHttpClient factory)
      parser/     -- SAX/JSON response parsers
      shares/     -- ShareUtils (share domain logic: permission mapping, share validation)
    tasks/        -- UI-driven action classes (FileList, PrivateShare, PublicLink, SpaceMembers, Spaces)
    world/        -- World.java (per-scenario lazy factory)

src/test/resources/io/cucumber/  -- Gherkin .feature files (one per functional area)
```

### Layered design (ScreenPlay-lite)

Every feature area follows a strict 5-layer pattern. Steps are thin dispatchers --
no logic lives in them:

```
Steps  ->  Preconditions / Tasks / Assertions  ->  Pages  ->  API  ->  Model
```

- **Preconditions** set up server state via API before the UI is involved.
- **Tasks** drive UI interactions through page objects.
- **Assertions** verify state either in the UI (via pages) or on the server (via API).
- **Pages** (`e2e/pages/`) hold all `@iOSXCUITFindBy` field declarations and low-level UI methods.
- **API clients** (`e2e/api/`) make direct REST calls for setup and verification.

### World as lazy factory

`World` (injected into every step class via Cucumber PicoContainer) lazily
instantiates all page objects, API clients, preconditions, tasks and assertions on
first access, sharing the single `IOSDriver` across all steps in a scenario.

### Hooks manage lifecycle

`@Before` activates the app. `@After` cleans up all server-side state via API
(removes files, empties trashbin, removes spaces) then terminates the app.

### Two backends

Tests tagged `@noocis` run only against oC10; tests tagged `@nooc10` run only
against oCIS. Branching on `System.getProperty("backend")` appears where behaviour
differs (e.g. `LoginPage.selectDrive()`, space endpoint resolution in `CommonAPI`).

### Appium driver

`AppiumManager` (in `e2e/pages/`) is a singleton that creates and holds the
`IOSDriver`. It uses `XCUITestOptions` (java-client 9.x typed options, not the
deprecated `DesiredCapabilities`). Driver capabilities are configured via
`local.properties` and system properties.

### Key model types

- `OCFile` -- file/folder metadata from WebDAV responses
- `OCShare` -- share metadata; `type` field is a `ShareType` enum (`PRIVATE`, `GROUP`, `PUBLIC_LINK`, `REMOTE`)
- `OCSpace` / `OCSpaceMember` / `OCSpacePermission` -- oCIS spaces and their members

## Development Conventions

- Feature files written in Gherkin syntax
- Step definitions in Java
- Gradle for build and dependency management
- Reports via Cucumber Reports service
- Dependencies are managed through the Gradle version catalog
  (`gradle/libs.versions.toml`) with bundles; all of them are `testImplementation`.
  Main bundles: `cucumber`, `appium`, `http`, `commons`, `testing`.
- `local.properties` (not committed) drives both the Appium setup and the API
  defaults. `LocProperties` reads it lazily as a singleton. Required keys include
  `appName`, `appPackage`, `appiumURL`, `userNameDefault`, `pwdDefault`,
  `userAgent` and `userToShare` -- see the file for the full list.

## Build & Test Commands

### Prerequisites

- A running Appium server
- An iOS simulator or device reachable via UDID
- An `ownCloud.app` build placed in `src/test/resources/` (built with `buildapp/buildapp.sh`)
- `local.properties` filled out

### Execute tests

```bash
export OC_SERVER_URL=https://my.owncloud.server
export UDID_DEVICE=<simulator-udid>          # from xcrun simctl list
export APPIUM_URL=http://localhost:4723      # optional, defaults to localhost:4723
export BACKEND=oCIS                          # or oC10

./executeTests -t "not @ignore"
./executeTests -t "not @ignore and not @noocis"   # oCIS-compatible tests only
./executeTests -t "not @ignore and not @nooc10"   # oC10-compatible tests only
./executeTests -t @createfolder                   # single feature tag
```

The script internally calls `./gradlew clean test` with the appropriate `-D`
system properties forwarded.

### Gradle directly

```bash
./gradlew clean test \
  -Dserver=https://my.server \
  -Dappium=http://localhost:4723 \
  -Dudid=<udid> \
  -Dbackend=oCIS \
  -Dcucumber.filter.tags="not @ignore"

./gradlew build         # Build the project
```

### Retry behaviour

The build runs up to three passes automatically
(`test` -> `cucumberRerun1` -> `cucumberRerun2`). Only `cucumberRerun2` is a hard
failure -- earlier passes write a rerun file and continue. Failed scenarios are
written to `target/cucumber-reports/rerun/rerun.txt` (and `rerun-1.txt`) so the
subsequent Gradle tasks retry exactly those scenarios. Reports land in
`target/cucumber-reports/` and `target/gradle-test-reports/`.

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

Respect the layer boundaries above: put no logic in step definitions, reach the UI
only through page objects, and set up or verify server state through the API
clients rather than driving the UI for it.
