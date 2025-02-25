package shell.command;

import java.util.List;

import shell.io.Redirection;
import shell.process.ProcessExecutor;

public class ExternalCommand implements Command {
    private final String programPath;
    private final ProcessExecutor executor;

    public ExternalCommand(String programPath, ProcessExecutor executor) {
        this.programPath = programPath;
        this.executor = executor;
    }

    @Override
    public void execute(List<String> args, Redirection redirection) {
        executor.execute(programPath, args, redirection);
    }
}
