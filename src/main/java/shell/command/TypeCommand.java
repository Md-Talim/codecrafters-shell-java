package shell.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import shell.environment.Environment;
import shell.io.FileUtils;
import shell.io.Redirection;

public class TypeCommand extends BuiltinCommand {
    private final Map<String, Command> builtins;
    private final Environment environment;

    public TypeCommand(Map<String, Command> builtins, Environment environment) {
        super("type");
        this.builtins = builtins;
        this.environment = environment;
    }

    @Override
    public void execute(List<String> args, Redirection redirection) {
        if (args.size() < 2) {
            throw new CommandExecutionException("type: missing command name argument");
        }

        String commandName = args.get(1);
        String output = "", error = "";

        if (builtins.containsKey(commandName)) {
            output = commandName + " is a shell builtin";
        } else {
            String commandPath = findCommandInPath(commandName);
            if (commandPath != null)
                output = commandName + " is " + commandPath;
            else
                error = commandName + ": not found";
        }

        if (redirection == null) {
            System.out.println(output + error);
            return;
        }

        try {
            if (redirection.isStdout()) {
                FileUtils.writeToFile(output, redirection.getFile());
                System.out.println(error);
            } else {
                FileUtils.writeToFile(error, redirection.getFile());
                System.out.println(output);
            }
        } catch (Exception e) {
            System.err.println("type: " + e.getMessage());
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
