# appsec-review-smoke-java

A deliberately vulnerable **smoke target** for [appsec-review](https://github.com/wsollers/appsec-review).
It exists so that a review of a tiny, fully understood Java program has one correct answer at every
stage. **Do not deploy, run against untrusted input, or reuse any of this code.**

The program is a toy request server: `app.Main` reads one request line from a socket and
`app.Router` dispatches it; `app.Server`, `app.Cli` and `app.Handler` are the code-graph fixture
classes.

## License

MIT; see `LICENSE`.
