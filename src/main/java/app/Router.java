package app;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

/** Dispatches one request line on its verb. Deliberately vulnerable smoke target; see README.md. */
public final class Router {
    private final Connection connection;
    private int requests;

    public Router(Connection connection) {
        this.connection = connection;
    }

    /** Handles one request line and returns an HTTP-like status code. */
    public int route(String line, OutputStream out) throws IOException {
        int status = 400;
        String request = line;
        int split = request.indexOf(' ');
        String verb = split < 0 ? request : request.substring(0, split);
        String arg = split < 0 ? null : request.substring(split + 1);
        if (verb.equals("PING")) {
            reply(out, "PONG");
            status = 200;
        }
        if (status >= 200 && status < 300) {
            requests++;
        }
        reply(out, Integer.toString(status));
        return status;
    }

    private static void reply(OutputStream out, String text) throws IOException {
        out.write((text + "\n").getBytes(StandardCharsets.UTF_8));
    }
}
