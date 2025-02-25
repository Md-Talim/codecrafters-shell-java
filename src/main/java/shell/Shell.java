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

    public void run(String input) {
        try {
            Parser parser = new Parser(input);
            List<String> args = parser.parse();
            Redirection redirection = parser.getRedirection();

            if (args.isEmpty()) {
                return;
            }

            String commandName = args.get(0);
            Command command = builtinCommands.getOrDefault(
                commandName,
                new ExternalCommand(commandName, processExecutor)
            );
            command.execute(args, redirection);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isExitCommand() {
        return shouldExit;
    }
}
