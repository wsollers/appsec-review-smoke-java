# Changelog

All notable changes to this project are documented in this file. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [Unreleased]

### Added

- Smoke server with PING, TOOL, ASSET and COUNT verbs.
- Maven manifest declaring log4j-core, snakeyaml, commons-io and commons-text.

### Security

- 2026-01-11: Upgraded commons-text from 1.9 to 1.10.0 (CVE-2022-42889, GHSA-599f-7c49-w659, remote code execution through StringSubstitutor interpolation).
- 2026-01-28: Fixed command injection in `Tools.run` (CWE-78); tool names are now matched against a fixed list.
- 2026-02-09: Fixed path traversal in `Assets.read` (CWE-22); paths are canonicalised and confined to the asset root.
