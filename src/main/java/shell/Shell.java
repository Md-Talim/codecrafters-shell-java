package shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shell.command.*;
import shell.environment.Environment;
import shell.environment.SystemEnvironment;
import shell.io.Redirection;
import shell.parser.Parser;
import shell.process.ProcessExecutor;
import shell.process.SystemProcessExecutor;
import shell.terminal.Termios;

public class Shell {
    private final Map<String, Command> builtinCommands;
    private final Environment environment;
    private final ProcessExecutor processExecutor;
    private boolean shouldExit;

    public Shell() {
        this.environment = new SystemEnvironment();
        this.processExecutor = new SystemProcessExecutor();
        this.builtinCommands = new HashMap<>();
        this.shouldExit = false;

        initializeBuiltinCommands();
    }

    private void initializeBuiltinCommands() {
        builtinCommands.put("exit", new ExitCommand(() -> shouldExit = true));
        builtinCommands.put("echo", new EchoCommand());
        builtinCommands.put("pwd", new PwdCommand(environment));
        builtinCommands.put("cd", new CdCommand(environment));
        builtinCommands.put("type", new TypeCommand(builtinCommands, environment));
    }

    private void executeCommand(String input) {
        try {
            Parser parser = new Parser(input);
            List<String> args = parser.parse();
            Redirection redirection = parser.getRedirection();

            if (args.isEmpty()) {
                return;
            }

            String commandName = args.get(0);
            Command command = builtinCommands.getOrDefault(commandName,
                    new ExternalCommand(commandName, processExecutor));
            command.execute(args, redirection);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String autocomplete(String input) {
        List<String> matches = builtinCommands.keySet()
                .stream()
                .filter(command -> command.startsWith(input))
                .sorted()
                .toList();

        if (matches.size() == 1) {
            return matches.get(0) + " ";
        }

        return null;
    }

    private String read() {
        Termios.enableRawMode();
        final var line = new StringBuilder();

        try {
            while (true) {
                int key = System.in.read();

                if (key == -1 || key == 4) { // CTRL + D (EOF)
                    return null;
                } else if (key == '\n') { // ENTER KEY
                    System.out.println();
                    return line.toString();
                } else if (key == '\t') { // HANDLE TAB KEY
                    String completion = autocomplete(line.toString());
                    if (completion != null) {
                        System.out.print("\r$ " + completion);
                        line.setLength(0);
                        line.append(completion);
                    }
                } else if (key == 127) { // BACKSPACE KEY
                    if (line.length() > 0) {
                        line.setLength(line.length() - 1);
                        System.out.print("\b \b"); // Move cursor back, erase character
                    }
                } else {
                    line.append((char) key);
                    System.out.print((char) key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Termios.disableRawMode();
        }

        return "";
    }

    public void run() {
        while (true) {
            System.out.print("$ ");
            final String line = read();

            if (line == null) {
                break;
            }

            executeCommand(line);

            if (shouldExit) {
                break;
            }
        }
    }
}
