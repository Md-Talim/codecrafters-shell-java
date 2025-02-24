package shell.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import shell.environment.Environment;

public class TypeCommand extends BuiltinCommand {
    private final Map<String, Command> builtins;
    private final Environment environment;

    public TypeCommand(Map<String, Command> builtins, Environment environment) {
        super("type");
        this.builtins = builtins;
        this.environment = environment;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() < 2) {
            throw new CommandExecutionException("type: missing command name argument");
        }

        String commandName = args.get(1);

        if (builtins.containsKey(commandName)) {
            System.out.println(commandName + " is a shell builtin");
            return;
        }

        String commandPath = findCommandInPath(commandName);
        if (commandPath != null) {
            System.out.println(commandName + " is " + commandPath);
        } else {
            throw new CommandExecutionException(commandName + ": not found");
        }
    }

    private String findCommandInPath(String commandName) {
        String pathEnv = environment.getEnvironmentVariable("PATH");

        if (pathEnv == null) {
            return null;
        }

        String[] paths = pathEnv.split(":");
        for (String dir : paths) {
            Path commandPath = Paths.get(dir, commandName);

            if (Files.exists(commandPath) && Files.isExecutable(commandPath)) {
                return commandPath.toString();
            }
        }
        return null;
    }
}
