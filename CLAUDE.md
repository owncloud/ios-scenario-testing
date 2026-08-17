# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

End-to-end test automation for the ownCloud iOS app. Gherkin feature files drive Cucumber scenarios executed against a real/simulated iOS device via Appium (XCUITest). Written in Java with Gradle.

## Running tests

### Prerequisites

- A running Appium server
- An iOS simulator or device reachable via UDID
- An `ownCloud.app` build placed in `src/test/resources/` (built with the `buildapp/buildapp.sh` script)
- `local.properties` filled out (see file for required keys: `appName`, `appPackage`, `appiumURL`, `userNameDefault`, `pwdDefault`, `userAgent`, etc.)

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
src/main/java/
  ios/          – Page Object Model: CommonPage (base) + one class per screen
  ios/AppiumManager.java   – Singleton that creates and holds the IOSDriver
  utils/api/    – REST API helpers (FilesAPI, ShareAPI, GraphAPI, TrashbinAPI)
  utils/entities/ – Plain data objects (OCFile, OCShare, OCSpace, …)
  utils/log/    – Log and StepLogger wrappers
  utils/network/, utils/parser/  – HTTP client and response parsers

src/test/java/
  io/cucumber/  – Cucumber step definitions, World, Hooks, RunCucumberTest
  e2e/LocProperties.java  – Reads local.properties at runtime

src/test/resources/io/cucumber/  – Gherkin .feature files (one per functional area)
```

### Key design decisions

**Page Object Model.** All UI interaction lives in `src/main/java/ios/`. `CommonPage` holds the driver reference and provides shared low-level gestures (swipe, longPress, tap, waitById, …). Every screen subclasses it, declares `@iOSXCUITFindBy` fields and exposes higher-level methods to steps.

**World as lazy factory.** `World` (injected into every step class via Cucumber PicoContainer) lazily instantiates page objects and API clients on first access, sharing the single `IOSDriver` across all steps in a scenario.

**Hooks manage lifecycle.** `@Before` activates the app; `@After` cleans up all server-side state via API (removes files, empties trashbin, deletes spaces) then terminates the app.

**Two backends.** Tests tagged `@noocis` run only against oC10; tests tagged `@nooc10` run only against oCIS. Branching on `System.getProperty("backend")` appears where behaviour differs (e.g. `LoginPage.selectDrive()`).

**Rerun files.** Failed scenarios are written to `target/cucumber-reports/rerun/rerun.txt` (and `rerun-1.txt`) so subsequent Gradle tasks can retry exactly those scenarios.

### Configuration

`local.properties` (not committed) drives both the Appium setup and API defaults. `LocProperties` (`e2e` package in test; `utils` package in main) reads it lazily as a singleton.

### Commit requirements

All commits must be PGP/GPG signed and carry a DCO sign-off:

```bash
git commit -s -S -m "your message"
```
