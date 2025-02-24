import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Navigator {
    private String currentWorkingDirectory;

    Navigator() {
        this.currentWorkingDirectory = System.getProperty("user.dir");
    }

    public boolean isDirectory(String directory) {
        Path path = Paths.get(directory);
        return Files.exists(path) && Files.isDirectory(path);
    }

    public String getWorkingDirectory() {
        return currentWorkingDirectory;
    }

    public void setWorkingDirectory(String directory) {
        currentWorkingDirectory = directory;
    }
}
