import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Navigator {
    private String currentWorkingDirectory;

    Navigator() {
        this.currentWorkingDirectory = System.getProperty("user.dir");
    }

    private boolean isDirectory(Path path) {
        return Files.exists(path) && Files.isDirectory(path);
    }

    public String getWorkingDirectory() {
        return currentWorkingDirectory;
    }

    public void setWorkingDirectory(String directory) {
        Path path = Paths.get(directory);

        if (directory.startsWith(".")) {
            path = Paths.get(currentWorkingDirectory, directory);
        }

        Path absolutePath = path.normalize().toAbsolutePath();

        if (isDirectory(absolutePath)) {
            currentWorkingDirectory = absolutePath.toString();
        } else {
            System.out.println("cd: " + directory + ": No such file or directory");
        }
    }
}
