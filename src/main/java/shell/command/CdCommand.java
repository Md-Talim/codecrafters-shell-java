package shell.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import shell.environment.Environment;
import shell.io.Redirection;

public class CdCommand extends BuiltinCommand {
    private final Environment environment;

    public CdCommand(Environment environment) {
        super("cd");
        this.environment = environment;
    }

    @Override
    public void execute(List<String> args, Redirection redirection) {
        if (args.size() < 2) {
            throw new CommandExecutionException("cd: missing directory argument.");
        }

        String directory = args.get(1);
        Path newPath = Paths.get(directory);

        try {
            environment.changeDirectory(newPath);
        } catch (Exception e) {
            throw new CommandExecutionException("cd: " + e.getMessage());
        }
    }
}
