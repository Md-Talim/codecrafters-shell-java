package shell.process;

import java.io.File;
import java.io.IOException;
import java.util.List;

import shell.io.Redirection;

public class SystemProcessExecutor implements ProcessExecutor {
    @Override
    public void execute(String command, List<String> args, Redirection redirection) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(args);

            if (redirection != null) {
                File file = new File(redirection.getFile());

                // Create parent directories if they don't exist
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                if (redirection.isStderr()) {
                    processBuilder.redirectError(file);
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                } else {
                    processBuilder.redirectOutput(file);
                    processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                }
            } else {
                // Default behavior - inherit all IO
                processBuilder.inheritIO();
            }

            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException e) {
            throw new ProcessExecutionException(command + ": command not found");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessExecutionException("Process execution interrupted", e);
        }
    }
}
