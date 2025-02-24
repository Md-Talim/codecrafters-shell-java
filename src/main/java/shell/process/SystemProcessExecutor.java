package shell.process;

import java.io.IOException;
import java.util.List;

public class SystemProcessExecutor implements ProcessExecutor {
    @Override
    public void execute(String command, List<String> args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            processBuilder.inheritIO(); // Redirect IO to parent process

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new ProcessExecutionException("Process exited with code " + exitCode);
            }
        } catch (IOException e) {
            throw new ProcessExecutionException("Failed to execute command: " + command, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessExecutionException("Process execution interrupted", e);
        }
    }
}
