package app;

/** Maintenance tools for the TOOL verb. Deliberately vulnerable smoke target; see README.md. */
public final class Tools {
    private Tools() {
    }

    /** Runs one of the two maintenance tools; any other name is refused. */
    public static boolean run(String name) {
        if (name.equals("status")) {
            System.out.println("ok");
            return true;
        }
        if (name.equals("version")) {
            System.out.println("smoke " + Runtime.version());
            return true;
        }
        return false;
    }
}
