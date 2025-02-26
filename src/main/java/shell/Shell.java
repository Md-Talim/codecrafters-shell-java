package shell;

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

public class Shell {
    private final Map<String, Command> builtinCommands;
    private final Environment environment;
    private final ProcessExecutor processExecutor;
    public static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

    public Shell() {
        this.environment = new SystemEnvironment();
        this.processExecutor = new SystemProcessExecutor();
        this.builtinCommands = new HashMap<>();

        initializeBuiltinCommands();
    }

    private void initializeBuiltinCommands() {
        builtinCommands.put("exit", new ExitCommand());
        builtinCommands.put("echo", new EchoCommand());
        builtinCommands.put("pwd", new PwdCommand(environment));
        builtinCommands.put("cd", new CdCommand(environment));
        builtinCommands.put("type", new TypeCommand(builtinCommands, environment));
    }

    public Map<String, Command> getBuiltinCommands() {
        return builtinCommands;
    }

    public String[] get$PATH() {
        final var separator = IS_WINDOWS ? ";" : ":";

        return System.getenv("PATH").split(separator);
    }

    public void run(String input) {
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
}
