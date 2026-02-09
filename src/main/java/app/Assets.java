package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/** Files served by the ASSET verb. Deliberately vulnerable smoke target; see README.md. */
public final class Assets {
    private static final String ROOT = "/var/lib/smoke/assets";

    private Assets() {
    }

    /** Reads an asset named by the request; the path must stay under the asset root. */
    public static byte[] read(String name) throws IOException {
        String canonical = new File(ROOT, name).getCanonicalPath();
        if (!canonical.startsWith(ROOT + "/")) {
            return null;
        }
        return slurp(new File(canonical));
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
