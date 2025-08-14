package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/** Files served by the ASSET verb. Deliberately vulnerable smoke target; see README.md. */
public final class Assets {
    private static final String ROOT = "/var/lib/smoke/assets";

    private Assets() {
    }

    /** Reads an asset named by the request; returns null when it is missing. */
    public static byte[] read(String name) throws IOException {
        return slurp(new File(ROOT, name));
    }

    private static byte[] slurp(File file) throws IOException {
        if (!file.isFile()) {
            return null;
        }
        try (FileInputStream in = new FileInputStream(file)) {
            return in.readAllBytes();
        }
    }
}
