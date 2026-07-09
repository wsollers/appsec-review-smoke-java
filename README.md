# appsec-review-smoke-java

A deliberately vulnerable **smoke target** for [appsec-review](https://github.com/wsollers/appsec-review).
It exists so that a review of a tiny, fully understood Java program has one correct answer at every
stage. **Do not deploy, run against untrusted input, or reuse any of this code.**

The program is a toy request server: `app.Main` reads one request line from a socket and
`app.Router` dispatches it; `app.Server`, `app.Cli` and `app.Handler` are the code-graph fixture
classes.

## Build

One Maven module, no network, no generated code. The code imports nothing outside the JDK, so the
direct compiler recipe is the build:

```sh
javac -d build/classes $(find src -name '*.java')
```

`pom.xml` declares the module and its dependencies for SBOM tools; `mvn -o compile` needs the
Maven core plugins in the local repository, which the appsec-review `java` build image (javac 21,
Maven 3.9.9) does not carry, so the build runs through `javac` as an unprivileged user with
networking disabled.

## History

`tools/make-history.sh` rebuilds this repository's commit graph from `tools/history/steps/` with
fixed authors and dates, so the history is reproducible and can be reviewed as code.

## License

MIT; see `LICENSE`.
