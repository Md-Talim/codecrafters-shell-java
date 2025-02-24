package shell.command;

import java.nio.file.Path;
import java.util.List;

import shell.environment.Environment;

public class PwdCommand extends BuiltinCommand {
    private final Environment environment;

    public PwdCommand(Environment environment) {
        super("pwd");
        this.environment = environment;
    }

    @Override
    public void execute(List<String> args) {
        Path currentPath = environment.getCurrentDirectory();
        System.out.println(currentPath.toAbsolutePath());
    }
}
