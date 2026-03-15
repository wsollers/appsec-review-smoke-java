# Security notes

This repository is a deliberately vulnerable smoke target. It is never deployed, so there is no
disclosure process. The entries below record the issues that were fixed in this history, so that
the record of what changed and why is in one place.

## 2026-02-09: path traversal in `Assets.read` (CWE-22)

The `ASSET` verb joined the client-supplied name onto the asset root and opened the result, so a
name containing `../` read files outside the asset directory. `Assets.read` now canonicalises the
joined path with `getCanonicalPath()` and refuses any result that does not start with the asset
root.

## 2026-01-28: command injection in `Tools.run` (CWE-78)

The `TOOL` verb built a command line from the client-supplied tool name and ran it with
`Runtime.exec()`, so a crafted name ran arbitrary programs. `Tools.run` now accepts only the names
`status` and `version` and performs them directly; there is no command string.

## 2026-01-11: commons-text 1.9 (CVE-2022-42889)

commons-text 1.9 evaluated `script`, `dns` and `url` lookups in `StringSubstitutor` interpolation,
so untrusted template text could run code. The declared version was raised to 1.10.0, which
disables those lookups by default.
