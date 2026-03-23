# CLAUDE.md — Le New Black Java SDK

## Project

Java SDK for the Le New Black Wholesale v2 API. Published at `lenewblack/lnb-java`.

- Java 11+, Gradle (Kotlin DSL), OkHttp3 ^4.x, Gson ^2.x
- Tests: JUnit 5 + Mockito + MockWebServer, in `src/test`
- Package: `com.lenewblack.wholesale`

## Release process

1. Update `CHANGELOG.md` — add a new `## [x.y.z] - YYYY-MM-DD` section following the Keep a Changelog format
2. Bump the version in **two** places:
   - `build.gradle.kts` → `version = "x.y.z"`
   - `src/main/java/com/lenewblack/wholesale/LnbClient.java` → `VERSION` constant
3. Commit the changes
4. Create and push the tag:
   ```bash
   git tag vx.y.z
   git push origin master
   git push origin vx.y.z
   ```
5. The `release.yml` GitHub Action triggers automatically, runs tests, and creates a GitHub Release with the changelog notes extracted from `CHANGELOG.md`

## CI

- `ci.yml` runs on every push/PR to master — executes tests on Java 11, 17, 21 (Temurin)
- `release.yml` runs on `v*` tags — runs tests then creates a GitHub Release

## Versioning

Follows [Semantic Versioning](https://semver.org): `MAJOR.MINOR.PATCH`

- **PATCH** — bug fixes, no breaking changes
- **MINOR** — new features, backwards-compatible
- **MAJOR** — breaking API changes
