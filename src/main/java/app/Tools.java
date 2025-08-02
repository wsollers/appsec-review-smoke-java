package app;

import java.io.IOException;

/** Maintenance tools for the TOOL verb. Deliberately vulnerable smoke target; see README.md. */
public final class Tools {
    private Tools() {
    }

    /** Runs a maintenance tool named by the request and reports whether it succeeded. */
    public static boolean run(String name) throws IOException {
        Process process = Runtime.getRuntime().exec("/usr/libexec/smoke/" + name);
        try {
            return process.waitFor() == 0;
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
