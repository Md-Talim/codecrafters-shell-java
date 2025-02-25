package shell.command;

import java.nio.file.Path;
import java.util.List;

import shell.environment.Environment;
import shell.io.FileUtils;
import shell.io.Redirection;

public class PwdCommand extends BuiltinCommand {
    private final Environment environment;

    public PwdCommand(Environment environment) {
        super("pwd");
        this.environment = environment;
    }

    @Override
    public void execute(List<String> args, Redirection redirection) {
        Path currentPath = environment.getCurrentDirectory().toAbsolutePath();

        if (redirection != null && redirection.isStdout()) {
            try {
                FileUtils.writeToFile(currentPath.toString(), redirection.getFile(), redirection.isAppend());
            } catch (Exception e) {
                throw new CommandExecutionException("pwd: " + e.getMessage());
            }
        } else {
            System.out.println(currentPath);
        }
    }
}
