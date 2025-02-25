package shell.io;

public class Redirection {
    private final String file;
    private final int descriptor;
    private final boolean append;

    public Redirection(String file, int descriptor, boolean append) {
        this.file = file;
        this.descriptor = descriptor;
        this.append = append;
    }

    public String getFile() {
        return file;
    }

    public int getDescriptor() {
        return descriptor;
    }

    public boolean isStderr() {
        return descriptor == 2;
    }

    public boolean isStdout() {
        return descriptor == 1;
    }

    public boolean isAppend() {
        return append;
    }
}
