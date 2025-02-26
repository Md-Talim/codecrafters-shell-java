package shell.terminal;

public class Termios {
    private static LibC.struct_termios originalTermios;

    public static void enableRawMode() {
        LibC.struct_termios termios = new LibC.struct_termios();

        // Get current terminal attributes
        if (LibC.INSTANCE.tcgetattr(LibC.STDIN_FILENO, termios) != 0) {
            System.err.println("Failed to get terminal attributes.");
            return;
        }

        // Save original state
        originalTermios = termios.clone();

        // Disable canonical mode and echo
        termios.c_lflag &= ~(LibC.ICANON | LibC.ECHO);
        termios.c_cc[LibC.VMIN] = 1; // Minimum number of bytes
        termios.c_cc[LibC.VTIME] = 0; // No timeout

        // Set new terminal attributes
        if (LibC.INSTANCE.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, termios) != 0) {
            System.err.println("Failed to set terminal attributes.");
        }
    }

    public static void disableRawMode() {
        if (originalTermios != null) {
            LibC.INSTANCE.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW, originalTermios);
        }
    }
}

//
/*
 *
 * package shell.terminal;
 *
 * import lombok.SneakyThrows;
 *
 * public class Termios implements AutoCloseable {
 *
 * private final LibC.struct_termios previous = new LibC.struct_termios();
 *
 * @SneakyThrows
 * public Termios() {
 * checkErrno(LibC.INSTANCE.tcgetattr(LibC.STDIN_FILENO, previous));
 *
 * LibC.struct_termios termios = previous.clone();
 *
 * termios.c_lflag &= ~(LibC.ECHO | LibC.ICANON);
 * termios.c_cc[LibC.VMIN] = 0;
 * termios.c_cc[LibC.VTIME] = 1;
 *
 * checkErrno(LibC.INSTANCE.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW,
 * termios));
 * }
 *
 * @Override
 * public void close() {
 * checkErrno(LibC.INSTANCE.tcsetattr(LibC.STDIN_FILENO, LibC.TCSANOW,
 * previous));
 * }
 *
 * private static void checkErrno(int previousReturn) {
 * if (previousReturn != -1) {
 * return;
 * }
 *
 * final var errno = com.sun.jna.Native.getLastError();
 * throw new IllegalStateException("errno: %s: %s".formatted(errno,
 * LibC.INSTANCE.strerror(errno)));
 * }
 *
 * public static Termios enableRawMode() {
 * return new Termios();
 * }
 *
 * }
 */
