package app;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

/** Dispatches one request line on its verb. Deliberately vulnerable smoke target; see README.md. */
public final class Router {
    private final Connection connection;
    private final Catalog catalog;
    private int requests;

    public Router(Connection connection) {
        this.connection = connection;
        this.catalog = new Catalog(connection);
    }

    /** Handles one request line and returns an HTTP-like status code. */
    public int route(String line, OutputStream out) throws IOException, SQLException {
        int status = 400;
        int i = 0;
        while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
            i++;
        }
        String request = line.substring(i);
        int split = request.indexOf(' ');
        String verb = split < 0 ? request : request.substring(0, split);
        String arg = split < 0 ? null : request.substring(split + 1);
        if (verb.equals("PING")) {
            reply(out, "PONG");
            status = 200;
        } else if (verb.equals("MATCH") && arg != null) {
            int at = arg.indexOf(' ');
            String pattern = at < 0 ? arg : arg.substring(0, at);
            String text = at < 0 ? "" : arg.substring(at + 1);
            boolean found = Pattern.compile(pattern).matcher(text).find();
            reply(out, found ? "MATCH" : "NOMATCH");
            status = 200;
        } else if (verb.equals("XML") && arg != null) {
            reply(out, Documents.parse(arg));
            status = 200;
        } else if (verb.equals("TOOL") && arg != null) {
            status = Tools.run(arg) ? 200 : 500;
        } else if (verb.equals("ASSET") && arg != null) {
            byte[] data = Assets.read(arg);
            status = data == null ? 404 : 200;
            if (data != null) {
                out.write(data);
            }
        } else if (verb.equals("TEMPLATE") && arg != null) {
            byte[] data = Assets.readTemplate(arg);
            status = data == null ? 404 : 200;
            if (data != null) {
                out.write(data);
            }
        } else if (verb.equals("COUNT")) {
            reply(out, Long.toString(catalog.count()));
            status = 200;
        } else if (verb.equals("PAGE") && arg != null) {
            try {
                reply(out, catalog.page(Integer.parseInt(arg)));
                status = 200;
            } catch (NumberFormatException error) {
                status = 400;
            }
        } else if (verb.equals("DEFAULTS")) {
            reply(out, Documents.parseDefaults());
            status = 200;
        }
        if (status >= 200 && status < 300) {
            requests++;
        }
        reply(out, status + " X-Count: " + requests);
        return status;
    }

    private static void reply(OutputStream out, String text) throws IOException {
        out.write((text + "\n").getBytes(StandardCharsets.UTF_8));
    }
}
