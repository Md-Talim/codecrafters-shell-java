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

                createParentDirs(file);

                if (redirection.isStderr()) {
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    processBuilder.redirectError(getRedirect(file, redirection.isAppend()));
                } else {
                    processBuilder.redirectOutput(getRedirect(file, redirection.isAppend()));
                    processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                }
            } else {
                processBuilder.inheritIO(); // Default behavior - inherit all IO
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

    private void createParentDirs(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private ProcessBuilder.Redirect getRedirect(File file, boolean isAppend) {
        return isAppend ? ProcessBuilder.Redirect.appendTo(file) : ProcessBuilder.Redirect.to(file);
    }
}
