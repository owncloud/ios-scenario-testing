# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

End-to-end test automation for the ownCloud iOS app. Gherkin feature files drive Cucumber scenarios executed against a real/simulated iOS device via Appium (XCUITest). Written in Java with Gradle.

## Running tests

### Prerequisites

- A running Appium server
- An iOS simulator or device reachable via UDID
- An `ownCloud.app` build placed in `src/test/resources/` (built with the `buildapp/buildapp.sh` script)
- `local.properties` filled out (see file for required keys: `appName`, `appPackage`, `appiumURL`, `userNameDefault`, `pwdDefault`, `userAgent`, `userToShare`, etc.)

### Execute tests

```bash
export OC_SERVER_URL=https://my.owncloud.server
export UDID_DEVICE=<simulator-udid>          # from xcrun simctl list
export APPIUM_URL=http://localhost:4723       # optional, defaults to localhost:4723
export BACKEND=oCIS                          # or oC10

./executeTests -t "not @ignore"
./executeTests -t "not @ignore and not @noocis"   # oCIS-compatible tests only
./executeTests -t "not @ignore and not @nooc10"   # oC10-compatible tests only
./executeTests -t @createfolder              # single feature tag
```

The script internally calls `./gradlew clean test` with the appropriate `-D` system properties forwarded.

### Gradle directly

```bash
./gradlew clean test \
  -Dserver=https://my.server \
  -Dappium=http://localhost:4723 \
  -Dudid=<udid> \
  -Dbackend=oCIS \
  -Dcucumber.filter.tags="not @ignore"
```

### Retry behaviour

The build runs up to three passes automatically (`test` → `cucumberRerun1` → `cucumberRerun2`). Only `cucumberRerun2` is a hard failure — earlier passes write a rerun file and continue. Reports land in `target/cucumber-reports/` and `target/gradle-test-reports/`.

## Architecture

### Source layout

```
src/test/java/
  e2e/
    api/          – REST API clients (CommonAPI base + FilesAPI, ShareAPI, GraphAPI, TrashbinAPI)
    assertions/   – Assertion classes (FileList, PrivateShare, PublicLink, SpaceMembers, Spaces)
    hooks/        – Cucumber lifecycle hooks (Hooks.java)
    model/        – Plain data objects (OCFile, OCShare, OCSpace, OCSpaceMember, OCSpacePermission, ShareType)
    pages/        – Page Object Model: CommonPage (base) + one class per screen + AppiumManager
    preconditions/– API-driven setup classes (FileList, Login, PublicLink, Shares, Spaces)
    runner/       – RunCucumberTest (JUnit entry point)
    steps/        – Cucumber step definitions (thin dispatchers only)
    support/
      date/       – DateUtils (java.time utilities)
      log/        – Log and StepLogger wrappers
      network/    – oCHttpClient (OkHttpClient factory)
      parser/     – SAX/JSON response parsers
      shares/     – ShareUtils (share domain logic: permission mapping, share validation)
    tasks/        – UI-driven action classes (FileList, PrivateShare, PublicLink, SpaceMembers, Spaces)
    world/        – World.java (per-scenario lazy factory)

src/test/resources/io/cucumber/  – Gherkin .feature files (one per functional area)
```

### Layered design (ScreenPlay-lite)

Every feature area follows a strict 5-layer pattern. Steps are thin dispatchers — no logic lives in them:

```
Steps  →  Preconditions / Tasks / Assertions  →  Pages  →  API  →  Model
```

- **Preconditions** set up server state via API before the UI is involved.
- **Tasks** drive UI interactions through page objects.
- **Assertions** verify state either in the UI (via pages) or on the server (via API).
- **Pages** (`e2e/pages/`) hold all `@iOSXCUITFindBy` field declarations and low-level UI methods.
- **API clients** (`e2e/api/`) make direct REST calls for setup and verification.

### World as lazy factory

`World` (injected into every step class via Cucumber PicoContainer) lazily instantiates all page objects, API clients, preconditions, tasks, and assertions on first access, sharing the single `IOSDriver` across all steps in a scenario.

### Hooks manage lifecycle

`@Before` activates the app. `@After` cleans up all server-side state via API (removes files, empties trashbin, removes spaces) then terminates the app.

### Two backends

Tests tagged `@noocis` run only against oC10; tests tagged `@nooc10` run only against oCIS. Branching on `System.getProperty("backend")` appears where behaviour differs (e.g. `LoginPage.selectDrive()`, space endpoint resolution in `CommonAPI`).

### Appium driver

`AppiumManager` (in `e2e/pages/`) is a singleton that creates and holds the `IOSDriver`. It uses `XCUITestOptions` (java-client 9.x typed options, not the deprecated `DesiredCapabilities`). Driver capabilities are configured via `local.properties` and system properties.

### Key model types

- `OCFile` — file/folder metadata from WebDAV responses
- `OCShare` — share metadata; `type` field is a `ShareType` enum (`PRIVATE`, `GROUP`, `PUBLIC_LINK`, `REMOTE`)
- `OCSpace` / `OCSpaceMember` / `OCSpacePermission` — oCIS spaces and their members

### Dependencies

Managed via Gradle version catalog (`gradle/libs.versions.toml`) with bundles. All dependencies are `testImplementation`. Main bundles: `cucumber`, `appium`, `http`, `commons`, `testing`.

### Configuration

`local.properties` (not committed) drives both the Appium setup and API defaults. `LocProperties` reads it lazily as a singleton.

### Rerun files

Failed scenarios are written to `target/cucumber-reports/rerun/rerun.txt` (and `rerun-1.txt`) so subsequent Gradle tasks can retry exactly those scenarios.

### Commit requirements

All commits must be PGP/GPG signed and carry a DCO sign-off:

```bash
git commit -s -S -m "your message"
```
