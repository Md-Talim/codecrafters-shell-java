package shell.terminal;

import java.util.Arrays;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public interface LibC extends Library {

    public LibC INSTANCE = Native.load("c", LibC.class);

    public final int STDIN_FILENO = 0;

    public final int NCCS = 32;

    /** Canonical input (erase and kill processing). */
    public final int ICANON = 2;

    /** Enable echo. */
    public final int ECHO = 8;

    /** Ignore CR */
    public final int IGNCR = 128;

    public final int VTIME = 5;
    public final int VMIN = 6;

    public final int TCSANOW = 0;

    public final int ICRNL = 400;

    String strerror(int errnum);

    int tcgetattr(int fd, final struct_termios termios_p);

    int tcsetattr(int fd, int optional_actions, final struct_termios termios_p);

    @Structure.FieldOrder({
            "c_iflag",
            "c_oflag",
            "c_cflag",
            "c_lflag",
            "c_line",
            "c_cc",
            "c_ispeed",
            "c_ospeed",
    })
    public class struct_termios extends Structure implements Cloneable {

        /** input modes */
        public int c_iflag;

        /** output modes */
        public int c_oflag;

        /** control modes */
        public int c_cflag;

        /** local modes */
        public int c_lflag;

        /* line discipline */
        public byte c_line;

        /** special characters */
        public byte[] c_cc = new byte[NCCS];

        /** input speed */
        public int c_ispeed;

        /** output speed */
        public int c_ospeed;

        @Override
        protected struct_termios clone() {
            final var copy = new struct_termios();

            copy.c_iflag = c_iflag;
            copy.c_oflag = c_oflag;
            copy.c_cflag = c_cflag;
            copy.c_lflag = c_lflag;
            copy.c_line = c_line;
            copy.c_cc = c_cc.clone();
            copy.c_ispeed = c_ispeed;
            copy.c_ospeed = c_ospeed;

            return copy;
        }

        @Override
        public String toString() {
            return "struct_termios[c_iflag=" + c_iflag + ", c_oflag=" + c_oflag + ", c_cflag=" + c_cflag + ", c_lflag="
                    + c_lflag + ", c_line=" + c_line + ", c_cc=" + Arrays.toString(c_cc) + ", c_ispeed=" + c_ispeed
                    + ", c_ospeed=" + c_ospeed + "]";
        }

    }

}
