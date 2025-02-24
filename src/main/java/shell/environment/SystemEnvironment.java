package shell.environment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SystemEnvironment implements Environment {
    private Path currentDirectory;
    private final Map<String, String> env;

    public SystemEnvironment() {
        this.currentDirectory = Paths.get(System.getProperty("user.dir"));
        this.env = System.getenv();
    }

    @Override
    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void changeDirectory(Path path) {
        Path newPath = resolveDirectory(path);

        if (!Files.exists(newPath)) {
            throw new EnvironmentException("cd: " + newPath + ": No such file or directory");
        }

        if (!Files.isDirectory(newPath)) {
            throw new EnvironmentException("cd: " + newPath + ": No such file or directory");
        }

        try {
            currentDirectory = newPath.toRealPath();
        } catch (Exception e) {
            throw new EnvironmentException("Failed to change directory", e);
        }
    }

    @Override
    public String getEnvironmentVariable(String name) {
        return env.get(name);
    }

    private Path resolveDirectory(Path path) {
        String pathStr = path.toString();

        if (pathStr.startsWith("~")) {
            String home = System.getProperty("user.home");
            return Paths.get(home, pathStr.substring(1));
        }

        return path.isAbsolute() ? path : currentDirectory.resolve(path);
    }
}
