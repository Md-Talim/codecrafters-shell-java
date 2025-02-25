package shell.parser;

import java.util.ArrayList;
import java.util.List;

import shell.io.Redirection;

public class Parser {
    private static final char END = '\0';
    private static final char SPACE = ' ';
    private static final char SINGLE = '\'';
    private static final char DOUBLE = '"';
    private static final char BACKSLASH = '\\';

    private Redirection redirection;

    private final String line;
    private int index;
    private List<String> arguments;

    public Parser(String line) {
        this.line = line;
        this.index = -1;
        this.arguments = new ArrayList<>();
    }

    public List<String> parse() {
        String argument;

        while ((argument = nextArgument()) != null) {
            if (argument.equals(">") || argument.equals("1>") || argument.equals("2>")) {
                String file = nextArgument();
                if (file == null) {
                    throw new ParseException("Expect file name after >");
                }

                int descriptor = argument.equals("2>") ? 2 : 1;
                redirection = new Redirection(file, descriptor, false);
                break;
            } else if (argument.equals(">>") || argument.equals("1>>")) {
                String file = nextArgument();
                if (file == null) {
                    throw new ParseException("Expect file name after >");
                }

                redirection = new Redirection(file, 1, true);
                break;
            }
            arguments.add(argument);
        }

        return arguments;
    }

    public Redirection getRedirection() {
        return redirection;
    }

    private String nextArgument() {
        StringBuilder builder = new StringBuilder();

        char character;
        while ((character = next()) != END) {
            switch (character) {
                case SPACE -> {
                    if (builder.length() > 0)
                        return builder.toString();
                }
                case BACKSLASH -> {
                    if ((character = next()) != END)
                        builder.append(character);
                }
                case SINGLE -> {
                    while ((character = next()) != END && character != SINGLE)
                        builder.append(character);
                }
                case DOUBLE -> {
                    while ((character = next()) != END && character != DOUBLE) {
                        // current character is backslash
                        // map it to the next character
                        if (character == BACKSLASH) {
                            character = next(); // character after backslash
                            if (!(character == BACKSLASH || character == DOUBLE))
                                builder.append(BACKSLASH);
                        }

                        builder.append(character);
                    }
                }
                default -> builder.append(character);
            }
        }

        if (builder.length() > 0)
            return builder.toString();

        return null;
    }

    private char next() {
        index++;
        if (index >= line.length()) {
            return END;
        }

        return line.charAt(index);
    }
}
