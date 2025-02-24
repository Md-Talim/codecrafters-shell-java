package shell.command;

import java.util.List;

import shell.process.ProcessExecutor;

public class ExternalCommand implements Command {
    private final String programPath;
    private final ProcessExecutor executor;

    public ExternalCommand(String programPath, ProcessExecutor executor) {
        this.programPath = programPath;
        this.executor = executor;
    }

    @Override
    public void execute(List<String> args) {
        executor.execute(programPath, args);
    }
}
