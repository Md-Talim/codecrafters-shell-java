import java.io.IOException;

import shell.Shell;
import shell.autocomplete.AutoCompleter;
import shell.terminal.Termios;;

public class Main {
    public static void main(String[] args) {
        Shell shell = new Shell();

        while (true) {
            final String line = read(shell);

            if (line == null) {
                break;
            } else if (line.isBlank()) {
                continue;
            } else {
                shell.run(line);
            }
        }
    }

    public static void bell() {
        System.out.print((char) 0x7);
    }

    private static String read(Shell shell) {
        final var autocompleter = new AutoCompleter();

        try (final var _ = Termios.enableRawMode()) {
            System.out.print("$ ");

            boolean bellRang = false;
            final var line = new StringBuilder();

            while (true) {
                int input = System.in.read();

                if (input == -1) {
                    return null;
                }

                final char character = (char) input;

                switch (character) {
                    case 0x4: {
                        if (!line.isEmpty())
                            continue;
                        return null;
                    }
                    case '\r': {
                        break;
                    }
                    case '\n': {
                        System.out.print('\n');
                        return line.toString();
                    }
                    case '\t': {
                        switch (autocompleter.autocomplete(shell, line, bellRang)) {
                            case NONE -> {
                                bellRang = false;
                                bell();
                            }
                            case FOUND -> {
                                bellRang = false;
                            }
                            case MORE -> {
                                bellRang = true;
                                bell();
                            }
                        }
                        ;
                        break;
                    }
                    case 0x1b: {
                        System.in.read();
                        System.in.read();
                        break;
                    }
                    case 0x7f: {
                        if (line.isEmpty())
                            continue;
                        line.setLength(line.length() - 1);
                        System.out.print("\b \b");
                        break;
                    }
                    default: {
                        line.append(character);
                        System.out.print(character);
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
