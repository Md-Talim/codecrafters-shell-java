import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Executable {
    private List<String> pathList = new ArrayList<>();

    Executable() {
        String $path = System.getenv("PATH");
        pathList = Arrays.asList($path.split(":"));
    }

    public String getLocation(String program) {
        for (String location : pathList) {
            Path path = Paths.get(location, program);

            if (Files.isExecutable(path)) {
                return path.toString();
            }
        }

        return null;
    }
}
