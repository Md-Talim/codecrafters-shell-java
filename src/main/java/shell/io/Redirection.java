package shell.io;

public class Redirection {
    private final String file;
    private final int descriptor;

    public Redirection(String file, int descriptor) {
        this.file = file;
        this.descriptor = descriptor;
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
}
