package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

/** Entry point of the smoke server: accepts one connection and routes its first line. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        Connection connection = DriverManager.getConnection(System.getProperty("smoke.jdbc", "jdbc:sqlite:smoke.db"));
        Router router = new Router(connection);
        try (ServerSocket listener = new ServerSocket(port)) {
            Socket socket = listener.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                router.route(line, socket.getOutputStream());
            }
            new Server(connection, new FileStore()).serve(listener);
        }
    }
}
