package shell.process;

import java.util.List;

import shell.io.Redirection;

public interface ProcessExecutor {
    /**
     * Executes a command with the given arguments
     *
     * @param command The command to execute
     * @param args    The command arguments including the command itself
     * @throws ProcessExecutionException if the process execution fails
     */
    void execute(String command, List<String> args, Redirection redirection);
}
