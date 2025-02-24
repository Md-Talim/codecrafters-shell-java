import java.util.ArrayList;
import java.util.List;

class Parser {
    private static final char END = '\0';
    private static final char SPACE = ' ';
    private static final char SINGLE = '\'';
    private static final char DOUBLE = '"';
    private static final char BACKSLASH = '\\';

    private final String line;
    private int index;
    private List<String> arguments;

    Parser(String line) {
        this.line = line;
        this.index = -1;
        this.arguments = new ArrayList<>();
    }

    public List<String> parse() {
        String argument;

        while ((argument = nextArgument()) != null) {
            arguments.add(argument);
        }

        return arguments;
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
                    while ((character = next()) != END && character != DOUBLE)
                        builder.append(character);
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
