# iOS Scenario Testing

<!-- OSPO-managed README | Generated: 2026-04-16 | v2 -->

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE) [![ownCloud OSPO](https://img.shields.io/badge/OSPO-ownCloud-blue)](https://kiteworks.com/opensource)

End-to-end test automation framework for the ownCloud iOS app, using Gherkin-syntax feature files processed by Cucumber with Appium for device and simulator interaction. Written in Java, this project defines and executes behavioral scenarios against the iOS app to verify functionality across releases.

## Getting Started

Follow the steps below to set up and run the end-to-end test suite.

### Prerequisites

- Java JDK
- Gradle
- Appium instance running and reachable
- iOS simulator attached and reachable
- `authorize-ios`, `ios-deploy`, `ideviceinstaller`, `ios-webkit-debug-proxy`, `ios-sim`

### Running Tests

```bash
git clone https://github.com/owncloud/ios-scenario-testing.git
cd ios-scenario-testing
./gradlew test
```

## Documentation

- [Gherkin Syntax Reference](https://cucumber.io/docs/gherkin/)
- [Appium Documentation](http://appium.io/)
- [Cucumber Reports](https://reports.cucumber.io/)

## Part of the ownCloud Mobile Ecosystem

This repository provides automated scenario tests for the [ownCloud iOS app](https://github.com/owncloud/ios-app). Test results are published via [Cucumber Reports](https://reports.cucumber.io/).

## Reference

Key details from the project's test architecture and configuration:

### Architecture

Tests use [Gherkin Syntax](https://cucumber.io/docs/gherkin/) scenarios interpreted by [Cucumber](https://cucumber.io/), with step implementations in Java and device interaction via [Appium](http://appium.io/). Reports are generated with [Cucumber Reports](https://reports.cucumber.io/).

### Environment Variables

| Variable | Required | Default | Description |
|---|---|---|---|
| `$OC_SERVER_URL` | Yes | -- | ownCloud server URL to test against |
| `$APPIUM_URL` | No | `localhost:4723/wd/hub` | Appium server URL |
| `$UDID_DEVICE` | No | -- | Simulator UDID (from `xcrun simctl list`) |

### Building the App

Use the `buildapp/buildapp.sh` script to build the [ownCloud iOS app](https://github.com/owncloud/ios-app). The script disables the welcome wizard and release notes, forces basic auth, and moves the artifact to `src/test/resources/`.

### Backend-Specific Tags

- `@nooc10` -- tests for oCIS only, not suitable for oC10
- `@noocis` -- tests for oC10 only, not suitable for oCIS

Example: `./executeTests -t "not @ignore and not @noocis"` runs tests suitable for oCIS.

### Test Results

Reports in HTML and JSON are generated in the `target/` directory. Integration with [Cucumber Reports](https://cucumber.io/docs/cucumber/reporting/?lang=java) is available by setting `CUCUMBER_PUBLISH_TOKEN` and enabling it in `cucumber.properties`.

### Version Matrix

| Component | Version |
|---|---|
| Cucumber | 7.21.1 |
| Appium | 2.18.0 |
| Appium XCUITest Driver | 7.26.4 |
| Java Client | 9.4.0 |

## Community & Support

**[Star](https://github.com/owncloud/ios-scenario-testing)** this repo and **Watch** for release notifications!

- [ownCloud Website](https://owncloud.com)
- [Community Discussions](https://github.com/orgs/owncloud/discussions)
- [Matrix Chat](https://app.element.io/#/room/#owncloud:matrix.org)
- [Documentation](https://doc.owncloud.com)
- [Enterprise Support](https://owncloud.com/contact-us/)
- [OSPO Home](https://kiteworks.com/opensource)

## Contributing

We welcome contributions! Please read the [Contributing Guidelines](CONTRIBUTING.md)
and our [Code of Conduct](CODE_OF_CONDUCT.md) before getting started.

### Workflow

- **Rebase Early, Rebase Often!** We use a rebase workflow. Always rebase on the target branch before submitting a PR.
- **Dependabot**: Automated dependency updates are managed via Dependabot. Review and merge dependency PRs promptly.
- **Signed Commits**: All commits **must** be PGP/GPG signed. See [GitHub's signing guide](https://docs.github.com/en/authentication/managing-commit-signature-verification).
- **DCO Sign-off**: Every commit must carry a `Signed-off-by` line:
  ```
  git commit -s -S -m "your commit message"
  ```
- **GitHub Actions Policy**: Workflows may only use actions that are (a) owned by `owncloud`, (b) created by GitHub (`actions/*`), or (c) verified in the GitHub Marketplace.

## Security

**Do not open a public GitHub issue for security vulnerabilities.**

Report vulnerabilities at **<https://security.owncloud.com>** -- see [SECURITY.md](SECURITY.md).

Bug bounty: [YesWeHack ownCloud Program](https://yeswehack.com/programs/owncloud-bug-bounty-program)

## License

This project is licensed under the [MIT](LICENSE).

## About the ownCloud OSPO

The [Kiteworks Open Source Program Office](https://kiteworks.com/opensource), operating under
the [ownCloud](https://owncloud.com) brand, launched on May 5, 2026, to steward the open source
ecosystem around ownCloud's products. The OSPO ensures transparent governance, license compliance,
community health, and sustainable collaboration between the open source community and
[Kiteworks](https://www.kiteworks.com), which acquired ownCloud in 2023.

- **OSPO Home**: <https://kiteworks.com/opensource>
- **GitHub**: <https://github.com/owncloud>
- **ownCloud**: <https://owncloud.com>

For questions about the OSPO or licensing, contact ospo@kiteworks.com.

### License Migration to Apache 2.0

The OSPO is driving a strategic relicensing of ownCloud repositories toward the
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0), following
the [Apache Software Foundation's third-party license policy](https://www.apache.org/legal/resolved.html).

Individual repositories will migrate as their audit is completed. The LICENSE file
in each repo reflects its **current** license status (not the target).

**Current license: MIT** (Category A per Apache policy -- permissive, compatible with Apache-2.0).

Migration prerequisites for this repository:

- **CLA/DCO coverage**: All past contributors must have signed agreements permitting relicensing
- **Header updates**: All source file headers must be updated from MIT to Apache-2.0 notice
- **Dependency audit**: Verify no incompatible transitive dependencies
