package shell.environment;

import java.nio.file.Path;

public interface Environment {
    Path getCurrentDirectory();

    void changeDirectory(Path path);

    String getEnvironmentVariable(String name);
}
